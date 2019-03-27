package logger;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger {
    HashMap<String, Log> loggerMap = new HashMap();//This variable will be a mapping of the names of the loggers to the actual logging object
    /**
     * Given the path of a config file, instantiate all logs and their associated FTPClients.
     * FTP clients should sleep until time interval indicated in config.
     * This method utilizes the snakeyaml java library, which enables us to store the yaml
     * in an "in-house" Log class.
     * @param configPath
     */
    public void init(String configPath){
        Yaml yaml = new Yaml(new Constructor(Log.class));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configPath);
        }
        catch(FileNotFoundException e){//If user does not provide valid config, do not continue the program.
            System.out.println("Unable to find yaml config.");
            System.exit(0);
        }
        int numberOfLoggers = 0;
        Iterable<Object> loggersIterable = yaml.loadAll(inputStream);
        ArrayList<Log> loggersList = new ArrayList<Log>();
        while(loggersIterable.iterator().hasNext()){
            numberOfLoggers++;
            loggersList.add((Log) loggersIterable.iterator().next());
        }
        //This for loop will fire up all of the ftp clients for each of the loggers
        for(int i = 0; i < numberOfLoggers; i++){
            Log newLog = loggersList.get(i);
            loggerMap.put(newLog.name, newLog);
            loggersList.get(i).fireLog();
        }
    }

    /**
     * Retrieves the running Log instance, given the name of the log specified in the config yaml.  Returns null if
     * the Log does not exist.
     * @param loggerName
     * @return
     */
    public Log getLogger(String loggerName){
        if(loggerMap.containsKey(loggerName)){
            return loggerMap.get(loggerName);
        }
        else{
            return null;
        }
    }

}
