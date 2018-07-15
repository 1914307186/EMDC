package com.briup.environment.server;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;

public interface Server extends EmdcModule{
	public Collection<Environment> revicer() throws Exception;
	public void shutdown() throws Exception;
}
