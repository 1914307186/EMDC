package com.briup.environment.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.gui.Login;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

public class ConfigurationImpl implements Configuration {
	private Map<String, EmdcModule> emdcMap = new HashMap<String, EmdcModule>();
	
	public ConfigurationImpl(){
		this("src/config.xml");
	}

	public ConfigurationImpl(String config){
		DocumentBuilderFactory dbfact = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbfact.newDocumentBuilder();
			File file = new File(config);
			Document document = builder.parse(file);
			NodeList elements = document.getElementsByTagName("emdc");
			Element element1 = (Element) elements.item(0);
			NodeList childNodes = element1.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				// 遍历Element节点， 先排除非Element节点
				if (childNodes.item(i) instanceof Element) {
					Element element = (Element) childNodes.item(i);
					String cls = element.getAttribute("class");
					EmdcModule obj = (EmdcModule) Class.forName(cls).newInstance();
					Properties properties = new Properties();
					NodeList childNodes2 = element.getChildNodes();
					for(int j = 0; j<childNodes2.getLength(); j++){
						if(childNodes2.item(j) instanceof Element){
							Element element2 = (Element) childNodes2.item(j);
							properties.setProperty(element2.getNodeName(), element2.getTextContent());
						}
					}
					// 初始化对象属性
					obj.init(properties);
					// 保存到对象Map中
					emdcMap.put(element.getNodeName(), obj);
				}
			}
			for (EmdcModule emdcModule : emdcMap.values()) {
				emdcModule.setConfiguration(this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Log getLogger() {
		return (Log)emdcMap.get("log");
	}

	@Override
	public Server getServer() {
		return (Server)emdcMap.get("server");
	}

	@Override
	public Client getClient() {
		return (Client)emdcMap.get("client");
	}

	@Override
	public DBStore getDBStore() {
		return (DBStore)emdcMap.get("dbstore");
	}

	@Override
	public Gather getGather() {
		return (Gather)emdcMap.get("gather");
	}

	@Override
	public Backup getBackup() {
		return (Backup)emdcMap.get("backup");
	}

	@Override
	public Login getLogin() {
		//return (Login)emdcMap.get("login");
		return null;
	}

	public static void main(String args[]) {
		ConfigurationImpl configurationImpl = new ConfigurationImpl();
		System.out.println(configurationImpl);
		System.out.println(configurationImpl.getBackup());
		System.out.println(configurationImpl.getClient());
		
		Integer i4 = 127;  //编译时被翻译成：Integer i4 = Integer.valueOf(127); 
        Integer i5 = 127;  
        System.out.println(i4 == i5);//true 
        Integer i6 = 128;  
        Integer i7 = 128;  
        System.out.println(i6 == i7);//false 
	}

}
