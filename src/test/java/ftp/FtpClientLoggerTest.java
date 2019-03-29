package ftp;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;

/**
 * Junit tester for FtpClientLogger class
 */
public class FtpClientLoggerTest {

    FtpClientLogger ftpClientLogger;

    @Before
    public void beforeTest(){//to test proper initialization please put valid server info!!
        String host = "localhost";
        int port = 22;
        String username = "username";
        String password = "password";
        String fileDirectory = "\\directory\\";
        String fileName = "file.log";
        float interval = (float) 0.2;
        ReentrantLock fileLock = new ReentrantLock();
        ftpClientLogger = new FtpClientLogger(host, port, username, password, fileDirectory, fileName,  interval, fileLock);
    }


    @Test
    public void testPositiveInitialization(){
        assertEquals(200, ftpClientLogger.ftpClient.getReplyCode());//if status of ftpClient is 200 then setup was good
    }
    @Test
    public void testFileTimestamp(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        String oldFileName = "test.log";
        Date date = new Date();
        int indexOfLogExtension = oldFileName.lastIndexOf(".");
        String fileWithDate = oldFileName.substring(0, indexOfLogExtension) + dateFormatter.format(date) + oldFileName.substring(indexOfLogExtension);
        assertEquals(fileWithDate, ftpClientLogger.timeStampFile(oldFileName, date));
    }
    @Test
    public void testEmptyLogUploadScenario(){//Be sure there is an empty log in directory provided
        String workingDir = System.getProperty("user.dir");//get current working directory

        String host = "localhost";
        int port = 22;
        String username = "username";
        String password = "password";
        String fileDirectory = workingDir + "\\src\\test\\test_logs\\";
        String fileName = "empty.log";
        float interval = (float) 0.2;
        ReentrantLock fileLock = new ReentrantLock();
        ftpClientLogger = new FtpClientLogger(host, port, username, password, fileDirectory, fileName,  interval, fileLock);
        int actualValue = -3;//0,-1,and -2 are all already taken
        try{
            actualValue = ftpClientLogger.uploadLog();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        assertEquals(0, actualValue);
    }
    @Test
    public void testPositiveUploadScenario(){
        String workingDir = System.getProperty("user.dir");//get current working directory
        String host = "localhost";
        int port = 22;
        String username = "goldengod";
        String password = "falconpunch";
        String fileDirectory = workingDir + "\\src\\test\\test_logs\\";
        String fileName = "actual.log";
        File file = new File(fileDirectory + fileName);
        ReentrantLock fileLock = new ReentrantLock();

        float interval = (float) 0.2;
        ftpClientLogger = new FtpClientLogger(host, port, username, password, fileDirectory, fileName,  interval, fileLock);
        int actualValue = -3;//0,-1,and -2 are all already taken
        try{
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write("This is an actual log");
            fw.close();
            actualValue = ftpClientLogger.uploadLog();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        assertEquals(1, actualValue);
    }
    @Test
    public void NegativeUploadScenario(){
        String workingDir = System.getProperty("user.dir");//get current working directory
        String host = "localhost";
        int port = 22;
        String username = "invalid";//if invalid user this test will always work.
        String password = "user";
        String fileDirectory = workingDir + "\\src\\test\\test_logs\\";
        String fileName = "actual.log";
        File file = new File(fileDirectory + fileName);
        float interval = (float) 0.2;
        ReentrantLock fileLock = new ReentrantLock();
        ftpClientLogger = new FtpClientLogger(host, port, username, password, fileDirectory, fileName,  interval, fileLock);
        int actualValue = -3;//0,-1,and -2 are all already taken
        try{
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.write("This is an actual log");
            fw.close();
            actualValue = ftpClientLogger.uploadLog();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        assertEquals(-1, actualValue);
    }



}
