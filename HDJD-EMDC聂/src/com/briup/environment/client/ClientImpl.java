package com.briup.environment.client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.Backup;
import com.briup.environment.util.BackupImpl;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LoggerImpl;

public class ClientImpl implements Client {
	private Log log = new LoggerImpl();
	private Backup backup = new BackupImpl();
	private ConfigurationImpl configuration;
	private String ip = "127.0.0.1";
	private int port = 404; 
	

	@Override
	public void send(Collection<Environment> c) {
		Socket socket = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			socket = new Socket(ip, port);
			os = socket.getOutputStream();
			bos = new BufferedOutputStream(os);
			oos = new ObjectOutputStream(bos);
			log.debug("已连接服务器，准备发送数据");
			@SuppressWarnings("unchecked")
			Collection<Environment> loadColl = (Collection<Environment>) backup.load("backup/back.bat", true);
			if(loadColl!=null){
				c.addAll(loadColl);
			}
			oos.writeObject(c);
			log.debug("客户端发送数据完成！");
		} catch (IOException e) {
			backup.store("client_back", c, false);
			e.printStackTrace();
		}finally{
			try {
				if(oos!=null) oos.close();
				if(bos!=null) bos.close();
				if(os!=null) os.close();
				if(socket!=null) socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void init(Properties p) {
		this.ip = p.getProperty("ip");
		this.port = Integer.valueOf(p.getProperty("port"));
	}


	@Override
	public void setConfiguration(ConfigurationImpl configurationImpl) {
		this.configuration = configurationImpl;
		this.log = this.configuration.getLogger();
		this.backup = this.configuration.getBackup();
	}

}
