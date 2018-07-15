package com.briup.environment.util;

import java.util.Properties;

public interface EmdcModule {
	public void init(Properties p);
	public void setConfiguration(ConfigurationImpl configurationImpl);

}
