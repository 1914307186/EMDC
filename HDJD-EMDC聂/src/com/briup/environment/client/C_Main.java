package com.briup.environment.client;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.ConfigurationImpl;

public class C_Main {
	public static void main(String[] args) throws Exception{
		ConfigurationImpl configuration = new ConfigurationImpl();
		Collection<Environment> coll = configuration.getGather().gather();
		configuration.getClient().send(coll);
		
		/*GatherImpl gatherImpl = new GatherImpl();
		ClientImpl clientImpl = new ClientImpl();
		Collection<Environment> coll = gatherImpl.gather();
		clientImpl.send(coll);*/

	}
}
