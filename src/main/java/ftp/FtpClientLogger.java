package ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used by Log class to generate its own usable instance of Apache's FTP client
 * Each Log generated by the logger will then upload its own log using its respective FtpClientLogger
 */
public class FtpClientLogger extends Thread {

    FTPClient ftpClient;
    String fileDirectory;
    String fileName;
    float sleepInterval;
    SimpleDateFormat dateFormatter;
    String newLogPath;
    String newLogFileName;
    File localLog;
    File rotatingLog;

    final int MINUTE_MULTIPLIER = 60000;

    /**
     * Once initialized, the Logger class  will call on the run method of this thread-extended class to begin waiting for a
     * chance to upload the current log file to server specified in config via FTP (service provided by apache commons-net).
     * If the log file is empty, the thread will fall back asleep and wait for
     * the next chance.  If it is not empty, the thread will get the current date/time and update the name of the log
     * with this timestamp. Upload will then commence.
     * <p>
     * The log class will provide the following parameters to fire up the FTP Client:
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param fileDirectory
     * @param fileName
     * @param intervalInMinutes
     */
    public FtpClientLogger(String host, int port, String username, String password, String fileDirectory, String fileName, float intervalInMinutes) {

        ftpClient = new FTPClient();
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        this.sleepInterval = intervalInMinutes * MINUTE_MULTIPLIER;
        this.dateFormatter = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        this.newLogPath = "";

        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The main sleeper function. In here is logic that will not upload any empty log files, and the logic to upload
     * the current log with the proper timestamp
     */
    public void run() {
        while (true) {//Run this thread indefinitely until it is interrupted
            try {
                this.sleep((long) sleepInterval);
                uploadLog();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException ex) {
                System.out.println("Shutting down thread");
            }

        }
    }

    /**
     * Facility to change old filename to the same file but with date/time appended to the name.
     */
    public String timeStampFile(String fileName, Date date) {
        int indexOfLogExtension = fileName.lastIndexOf(".");
        String fileWithDate = fileName.substring(0, indexOfLogExtension) + dateFormatter.format(date) + fileName.substring(indexOfLogExtension);
        return fileWithDate;
    }

    /**
     * Method which uploads log with new updated date/time filename to ftp server.
     * If log is empty, do not attempt upload, return 0.
     * If there is an error in uploading log, return -1.
     * If the log is uploaded successfully, return 1.
     *
     * @throws IOException
     */
    public int uploadLog() throws IOException {
        localLog = new File(fileDirectory + fileName);

        if (localLog.length() == 0)//If file is empty, go back to sleep and do not upload
            return 0;

        Date date = new Date();
        newLogFileName = timeStampFile(fileName, date);
        String newLogPath = fileDirectory + newLogFileName;
        File rotatedFile = new File(newLogPath);
        localLog.renameTo(rotatedFile);
        InputStream inputStream = new FileInputStream(rotatedFile);
        System.out.println("[Uploading Log]:     " + newLogFileName);
        boolean done = ftpClient.storeFile(newLogFileName, inputStream);//variable to see if file was successfully transferred
        inputStream.close();
        if (done) {
            System.out.println(newLogFileName + " was uploaded successfully");
            rotatedFile.delete();//Deleted newly created rotated log
            return 1;
        } else {
            System.out.println("There was an error uploading log " + newLogFileName);
            System.out.println(ftpClient.getReplyCode());
            return -1;
        }
    }
}

