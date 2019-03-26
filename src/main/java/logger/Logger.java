package logger;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Logger {
    public void init(String directory){
        Yaml yaml = new Yaml(new Constructor(Log.class));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(directory);
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
        for(int i = 0; i < numberOfLoggers; i++){
            Log newLog = loggersList.get(i);
            System.out.println(newLog.name);
        }
    }

}
