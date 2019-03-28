package runningApp;

import logger.Log;
import logger.Logger;


public class RunningApplication {
    public static void main(String args[]){
        Logger logger = new Logger();
        logger.init("config.yaml");
        /*FtpClientLogger ftpLogger = new FtpClientLogger("localhost", 30, "low3llg3org3",
                "Pikmin123","C:\\Users\\M4600_SSD\\IdeaProjects\\rotatinglogger\\src\\test\\local_logs\\","logger1.log");*/
        Log logger1 = logger.getLogger("logger1");



        /*for(int i = 0; i < 1000; i++){
            MultithreadingDemo thread = new MultithreadingDemo(logger1, i);
            thread.start();
        }*/

//        logger1.info("test info message");
//        logger1.error("test error message");
//        logger1.debug("test debug message");

    }
    static class MultithreadingDemo extends Thread
    {
        Log log;
        public MultithreadingDemo(Log log){
            this.log = log;
        }
        public void run()
        {
            try
            {
                // Displaying the thread that is running


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