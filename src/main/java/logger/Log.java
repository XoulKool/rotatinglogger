package logger;

import ftp.FtpClientLogger;

import java.io.*;

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
    int interval;

    BufferedWriter writer;//This is a shared resource between all logging threads for this Log.  All threads of this Log
                          //will use this Bufffered Writer to conserve memory

    final boolean APPEND = true;//variable to be used in file writer.

    /**
     * This method will fire up the connection to the ftp server, and will consequently start the wait for an upload
     * to the server.
     */
    public void fireUpLog() {
        System.out.println(this.username);
        FtpClientLogger ftpClientLogger = new FtpClientLogger(this.host, this.port, this.username, this.password, this.logFileDirectory, this.logFileName);
    }

    /**
     * This function will be used by error, info, and debug logging functions to write to the log file associated with
     * this instance of Log.  The method is synchronized so that only one thread can write to this log at a time.
     */
    public synchronized void writeLog(String logType, String log) {

        String logToBeWritten = logType + "---->     " + log;
        File file = new File(this.logFileDirectory + this.logFileName);
        try {
            file.createNewFile();//Creates new file if and only if a file with this name does not exist
            writer = new BufferedWriter(new FileWriter(file, APPEND));
            writer.append(logToBeWritten + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an IO error logging to");
            e.printStackTrace();
        }

    }


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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
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
