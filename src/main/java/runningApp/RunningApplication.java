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
        System.out.println(logger1.getUsername());

//        Logger logger = Logger.getLogger("logger1");
//        logger.info("test message");

        //write other code to keep application
        //running so other api can be used
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