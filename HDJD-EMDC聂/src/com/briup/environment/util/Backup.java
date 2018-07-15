package com.briup.environment.util;

public interface Backup extends EmdcModule{ 
	public void store(String filePath, Object obj, boolean append);
	public Object load(String filePath, boolean del);
}
