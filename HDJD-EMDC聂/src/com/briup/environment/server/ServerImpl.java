package com.briup.environment.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LoggerImpl;

public class ServerImpl implements Server {
	private Log log = new LoggerImpl();
	private ConfigurationImpl configuration;
	private ServerSocket serverSocket = null;
	private int port = 404;
	private static int flag = 1;  //���÷�����������տͻ��˵�������

	@Override
	public Collection<Environment> revicer() throws IOException {
		serverSocket = new ServerSocket(port);
		log.debug("���������������ȴ�����:");
		while (flag > 0) {
			Socket socket = serverSocket.accept();
			log.debug("�ͻ������ӳɹ���");
			new ServerThread(socket).start();
			flag--;
		}
		shutdown();
		return null;
	}

	@Override
	public void shutdown() {
		flag = 0;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFlag(int flag) {
		ServerImpl.flag = flag;
	}

	@Override
	public void init(Properties p) {
		this.port = Integer.valueOf(p.getProperty("port"));
		
	}

	@Override
	public void setConfiguration(ConfigurationImpl configurationImpl) {
		this.configuration = configurationImpl;
		this.log = this.configuration.getLogger();
	}

}
