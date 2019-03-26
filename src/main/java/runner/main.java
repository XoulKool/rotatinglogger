package runner;

import logger.Logger;

import java.io.FileNotFoundException;

public class main{
    public static void main(String args[]){
        Logger logger = new Logger();
        logger.init("config.yaml");

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