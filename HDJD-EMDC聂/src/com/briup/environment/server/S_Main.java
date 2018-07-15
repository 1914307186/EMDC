package com.briup.environment.server;

import java.io.IOException;

import com.briup.environment.util.ConfigurationImpl;

public class S_Main {
	public static void main(String[] args) throws IOException{
		ConfigurationImpl configuration = new ConfigurationImpl();
		Server server = configuration.getServer();
		try {
			server.revicer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*ServerImpl serverImpl = new ServerImpl();
		serverImpl.setFlag(2);
		serverImpl.revicer();*/
	}

}
