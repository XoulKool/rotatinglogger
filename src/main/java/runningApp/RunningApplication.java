package runningApp;

import logger.Log;
import logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunningApplication {
    public static void main(String args[]) {
        Logger logger = new Logger();
        logger.init("config.yaml");


        for(int i = 0; i < 10; i++){
        LoggerThread loggerThread = new LoggerThread();
        LoggerThread2 loggerThread2 = new LoggerThread2();
        loggerThread.start();
        loggerThread2.start();
        }



    }
    static class LoggerThread extends Thread{
        public void run() {
            while(true) {
                try {
                    this.sleep(1);
                    aliceLog();
                    carlLog();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    static class LoggerThread2 extends Thread{
        public void run() {
            while(true) {
                try {
                    this.sleep(1);
                    bobLog();
                    davidLog();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public static void aliceLog() {
        Log logger1Alice = Logger.getLogger("logger1");
        logger1Alice.debug("debug message from Alice");
    }

    public static void bobLog() {
        Log logger1Bob = Logger.getLogger("logger1");
        logger1Bob.info("info message from Bob");
    }
    public static void carlLog() {
        Log logger2Carl = Logger.getLogger("logger2");
        logger2Carl.error("error message from Carl");
    }
    public static void davidLog() {
        Log logger2David = Logger.getLogger("logger2");
        logger2David.info("info message from David");
    }
}