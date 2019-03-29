package logger;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Junit tester for Log class.
 */
public class LogTest {

    static Log log;
    static File file;

    /**
     * Before every test function, instantiate the following Log fields and File
     */
    @BeforeClass
    public static void initializeLog(){
        String workingDir = System.getProperty("user.dir");
        log = new Log();
        log.setName("TestLog");
        log.setLogFileDirectory(workingDir + "\\src\\test\\test_logs\\");
        log.setLogFileName("actual.log");
        log.setLevel("DEBUG");

        //The following instantiates a File object, but does not in fact create a file!  Keep in mind for tests...
        file = new File(log.logFileDirectory + log.logFileName);

    }

    /**
     * After every test make sure the file is deleted from directory for fresh test.
     */
    @AfterClass
    public static void deleteLog(){
        file.delete();
    }

    @Test
    public void testWriteLog(){
        String logType = "debug";
        String logMessage = "I am a debug log";
        log.writeLog(logType, logMessage);
        assertTrue(file.exists());
    }
    @Test
    public void testPositiveInfoLogScenarios(){
        log.setLevel(log.INFO);
        assertEquals(1, log.info("I am an info log!"));
        log.setLevel(log.DEBUG);
        assertEquals(1, log.info("I am an info log!"));
    }
    @Test
    public void testNegativeInfoLogScenario(){

        //This test function will not write a log, and will therefore require a log file to be made
        //to avoid null pointer from @AfterClass function.

        log.setLevel(log.ERROR);
        assertEquals(0, log.info("I am an info log!"));
    }

    /**
     * Log will always be written for this type of log level, so simply check if the log exists once this function is
     * called.
     */
    @Test
    public void testErrorLogScenario(){

        log.setLevel(log.ERROR);
        log.error("I am an error log!");
        assertTrue(file.exists());
    }
    @Test
    public void testPositiveDebugLogScenario(){
        log.setLevel(log.DEBUG);
        assertEquals(1, log.debug("I am a debug log"));
    }
    @Test
    public void testNegativeDebugLogScenarios(){
        log.setLevel(log.INFO);
        assertEquals(0, log.debug("I am a debug log"));
        log.setLevel(log.ERROR);
        assertEquals(0, log.debug("I am a debug log!"));
    }

}
