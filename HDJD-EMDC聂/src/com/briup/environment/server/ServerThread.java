package com.briup.environment.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.BackupImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LoggerImpl;

public class ServerThread extends Thread{
	Log log = new LoggerImpl();
	BackupImpl backupImpl = new BackupImpl();
	DBStoreImpl dbStoreImpl = new DBStoreImpl();
	Socket socket = null;
	
	public ServerThread(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		InputStream is = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			is = socket.getInputStream();
			bis = new BufferedInputStream(is);
			ois = new ObjectInputStream(bis);
			@SuppressWarnings("unchecked")
			Collection<Environment> coll = (Collection<Environment>) ois.readObject();
			log.debug("服务器已接收到数据，准备入库");
			// 读取之前未入库的备份数据
			@SuppressWarnings("unchecked")
			Collection<Environment> loadColl = (Collection<Environment>) backupImpl.load("server_back", true);
			if(loadColl!=null) dbStoreImpl.saveDB2(loadColl);
			dbStoreImpl.saveDB2(coll);
			log.debug("入库成功，总记录数："+coll.size());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				ois.close();
				bis.close();
				is.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
