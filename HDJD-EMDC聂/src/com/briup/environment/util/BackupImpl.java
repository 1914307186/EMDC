package com.briup.environment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;


public class BackupImpl implements Backup {
	private Log log = new LoggerImpl();
	private ConfigurationImpl configuration;
	String path = "backup/";

	@Override
	public void store(String fileName, Object obj, boolean append) {
		File file = new File(path+fileName);
		try {
			if(!file.exists()) file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file, append);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.close();
            log.debug("备份数据完成。");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public Object load(String filePath, boolean del) {
		Object coll = null;
		File file = new File(path+filePath);
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		if(!file.exists()) return null;
		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			coll = objectInputStream.readObject();
			log.debug("读取备份文件成功。");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				if(objectInputStream!=null) objectInputStream.close();
				if(fileInputStream!=null) fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(del){
			file.delete();
			log.debug("备份文件已删除。");
		}
		return coll;
	}

	@Override
	public void init(Properties p) {
		this.path = p.getProperty("back_path");
	}

	@Override
	public void setConfiguration(ConfigurationImpl configurationImpl) {
		this.configuration = configurationImpl;
		this.log = this.configuration.getLogger();
	}

}
