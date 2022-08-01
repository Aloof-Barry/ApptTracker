package model;

public class Logger {

    private static final Logger logger = new Logger();

    private Logger(){

    }

    public static Logger getLogger(){
        return logger;
    }
}
