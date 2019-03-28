package runningApp;

import logger.Log;
import logger.Logger;

import java.util.ArrayList;

public class RunningApplication {
    public static void main(String args[]){
        Logger logger = new Logger();
        logger.init("config.yaml");
        /*FtpClientLogger ftpLogger = new FtpClientLogger("localhost", 30, "low3llg3org3",
                "Pikmin123","C:\\Users\\M4600_SSD\\IdeaProjects\\rotatinglogger\\src\\test\\local_logs\\","logger1.log");*/
        Log logger1 = logger.getLogger("logger1");
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < 1000; i++){
            MultithreadingDemo thread = new MultithreadingDemo(logger1, i);
            thread.start();
        }


//        Logger logger = Logger.getLogger("logger1");
//        logger.info("test message");

        //write other code to keep application
        //running so other api can be used
    }
    static class MultithreadingDemo extends Thread
    {
        Log log;
        int logNumber;
        public MultithreadingDemo(Log log, int logNumber){
            this.log = log;
            this.logNumber = logNumber;
        }
        public void run()
        {
            try
            {
                // Displaying the thread that is running
                log.writeLog(log.getLevel(), "Thread " + logNumber + " is printing a log right now! This larger log than the last one.");

            }
            catch (Exception e)
            {
                // Throwing an exception
                System.out.println ("Exception is caught");
            }
        }
    }
//    public void Foo(){
//        Logger logger = Logger.getLogger("logger2");
//        logger.debug("test message");
//    }
//    public void Bar(){
//        Logger logger = Logger.getLogger("logger2");
//        logger.debug("test message");
//    }
}