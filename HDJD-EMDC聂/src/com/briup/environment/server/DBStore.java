package com.briup.environment.server;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;

public interface DBStore extends EmdcModule{
	public void saveDB(Collection<Environment> c);
}
