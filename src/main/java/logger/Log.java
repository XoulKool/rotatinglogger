package logger;

import ftp.FtpClientLogger;

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

    /**
     * This method will fire up the connection to the ftp server, and will consequently start the wait for an upload
     * to the server.
     */
    public void fireLog(){
        System.out.println(this.username);
        FtpClientLogger ftpClientLogger = new FtpClientLogger(this.host, this.port, this.username, this.password, this.logFileDirectory, this.logFileName);
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
