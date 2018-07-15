package com.briup.environment.util;

import java.util.Properties;

import org.apache.log4j.*;

public class LoggerImpl implements Log{
	//private ConfigurationImpl configuration;
	private String filePath = "src/log4j.properties";
	private Logger debugLogger = Logger.getLogger("debuglog");
	private Logger defaultLogger = Logger.getRootLogger();
	
	public LoggerImpl() {
		to_init();
	}
	// 加载配置文件
	private void to_init(){
        PropertyConfigurator.configure(filePath);
    }

	@Override
	public void debug(String msg) {
		debugLogger.debug(msg);
	}

	@Override
	public void info(String msg) {
		defaultLogger.info(msg);
	}

	@Override
	public void warn(String msg) {
		defaultLogger.warn(msg);
	}

	@Override
	public void error(String msg) {
		defaultLogger.error(msg);
	}

	@Override
	public void fatal(String msg) {
		defaultLogger.fatal(msg);
	}
	
	public static void main(String[] args){
		LoggerImpl log = new LoggerImpl();
		log.to_init();
		
		// 日志级别依次升高
		log.debug("this is debug");
		log.info("this is info");
        log.warn("this is warn");
        log.error("this is error");
        log.fatal("this is fatal");
        
	}
	@Override
	public void init(Properties p) {
		this.filePath = p.getProperty("log_file");
		
	}
	@Override
	public void setConfiguration(ConfigurationImpl configurationImpl) {
		//this.configuration = configurationImpl;
	}

}
