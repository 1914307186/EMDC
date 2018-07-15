package com.briup.environment.client;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;


public interface Client extends EmdcModule{
	public void send(Collection<Environment> c) throws Exception;
}
