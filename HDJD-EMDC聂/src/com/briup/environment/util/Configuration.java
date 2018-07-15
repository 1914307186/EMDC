package com.briup.environment.util;

import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.gui.Login;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

public interface Configuration {
	public Log getLogger();

	public Server getServer();

	public Client getClient();

	public DBStore getDBStore();

	public Gather getGather();

	public Backup getBackup();

	public Login getLogin();

}
