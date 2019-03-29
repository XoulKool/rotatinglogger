package logger;

import ftp.FtpClientLogger;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class carries out most of the logging facilities for an individual logger.  The snakeyaml parser is also able to utilize this class
 * and store all variables in the YAML into the variables in this class. (The getters and setters for all variables
 * are necessary for snakeyaml to do this, as they help instantiate the java bean)
 */
public class Log {

    String name;
    String host;
    int port;
    String username;
    String password;
    String logFileDirectory;
    String logFileName;
    String level;
    float interval;

    //This lock will be passed to FtpClientLogger instance and will be shared with the associated Log.
    //The lock will prevent any IO issues between the Log class' writeLog() and FtpClientLogger's uploadFile()
    ReentrantLock fileLock = new ReentrantLock();

    BufferedWriter writer;//This is a shared resource between all logging threads for this Log.  All threads of this Log
    //will use this Bufffered Writer to conserve memory

    final boolean APPEND = true;//variable to be used in file writer.
    final String ERROR = "ERROR";
    final String INFO = "INFO";
    final String DEBUG = "DEBUG";

    /**
     * This method will fire up the connection to the ftp server, and will consequently start the wait for an upload
     * to the server.  A Reentrant lock is passed through to the FtpClientLogger to prevent race condition between File IO
     * between writeLog() and uploadLog()
     */
    public void fireUpFTPService() {
        FtpClientLogger ftpClientLogger = new FtpClientLogger(this.host, this.port, this.username, this.password, this.logFileDirectory, this.logFileName, this.interval, this.fileLock);
        ftpClientLogger.start();
    }

    /**
     * This function will be used by error, info, and debug logging functions to write to the log file associated with
     * this instance of Log.  The method is synchronized so that only one thread can write to this log at a time.
     */
    public void writeLog(String logType, String log) {

        String logToBeWritten = "[" + logType + "]-----|" + log + "|-----";
        fileLock.lock();
        try {//try for lock.  This lock is shared with this log's instance of FtpClientLogger to prevent race condition
            File file = new File(this.logFileDirectory + this.logFileName);
            try {//try for IO Excpetion
                file.createNewFile();//Creates new file if and only if a file with this name does not exist
                writer = new BufferedWriter(new FileWriter(file, APPEND));
                writer.append(logToBeWritten + "\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("There was an IO error logging to");
                e.printStackTrace();
            }
        } finally {
            fileLock.unlock();
        }
    }

    /**
     * Provide logging facility to write info logs.  Only Log classes with level INFO or DEBUG can write these logs.
     * If has proper log-level permissions, returns 1.  Otherwise it returns 0.
     *
     * @param infoLog
     */
    public int info(String infoLog) {
        if (!level.equals(ERROR)) {//If the level is anything but error, then allow this log to write to file
            writeLog(INFO, infoLog);
            return 1;
        } else {
            System.out.println(name + " has insufficient log level to write " + INFO + " logs.");
            return 0;
        }
    }

    /**
     * Provide logging facility to write error logs.  All levels are able to write these types of logs.
     *
     * @param errorLog
     */
    public void error(String errorLog) {
        writeLog(ERROR, errorLog);
    }

    /**
     * Provide logging facility to write debug logs.  Only Log classes with level DEBUG can write these logs.
     *
     * @param debugLog
     */
    public int debug(String debugLog) {
        if (level.equals(DEBUG)) {//Only the debug level has the ability to write debug logs
            writeLog(DEBUG, debugLog);
            return 1;
        } else {
            System.out.println(name + " has insufficient log level to write " + DEBUG + " logs.");
            return 0;
        }
    }

//Mindless getters and setters.  These are required for snakeyaml to load all the config data into the Log class

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogFileDirectory() {
        return logFileDirectory;
    }

    public void setLogFileDirectory(String logFileDirectory) {
        this.logFileDirectory = logFileDirectory;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
