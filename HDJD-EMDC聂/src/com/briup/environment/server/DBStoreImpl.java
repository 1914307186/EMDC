package com.briup.environment.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.Backup;
import com.briup.environment.util.BackupImpl;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LoggerImpl;

public class DBStoreImpl implements DBStore {
	private Log log = new LoggerImpl();
	private Backup backup = new BackupImpl();
	private ConfigurationImpl configuration;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String username = "envir";
	private String password = "envir";
	private Connection connection;

	// 使用Statement来处理sql语句
	@Override
	public void saveDB(Collection<Environment> c) {
		if(c.size()==0) return;
		long startMili = System.currentTimeMillis();
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);
			Statement st = connection.createStatement();
			int n = 0;
			for (Environment environment : c) {
				n++;
				@SuppressWarnings("deprecation")
				int day = environment.getGather_date().getDate();
				String name = environment.getName();
				String srcId = environment.getSrcId();
				String dstId = environment.getDesId();
				String sensorAddress = environment.getSensorAddress();
				int count = environment.getCount();
				String cmd = environment.getCmd();
				int status = environment.getStatus();
				float data = environment.getData();
				Timestamp date = environment.getGather_date();

				String sql = "insert into e_detail_" + day + " values('" + name
						+ "','" + srcId + "','" + dstId + "','" + sensorAddress
						+ "'," + count + ",'" + cmd + "'," + status + ","
						+ data + ",to_date('" + date.toString().substring(0, 19)
						+ "','yyyy-mm-dd hh24:mi:ss'))";
				st.addBatch(sql);
				if (n > 1000) {
					st.executeBatch();
					connection.commit();
					st.clearBatch();
					n = 0;
				}
			}
			st.executeBatch();
			connection.commit();
			st.close();
			connection.close();
		} catch (Exception e) {
			backup.store("server_back", c, false);
			e.printStackTrace();
		}
		
		long endMili = System.currentTimeMillis();
		log.debug("数据入库耗时："+(endMili-startMili)+" 毫秒");
	}

	// 使用PreparedStatement来处理sql语句
	public void saveDB2(Collection<Environment> c) {
		if(c.size()==0) return;
		long startMili = System.currentTimeMillis();
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);
			int day = 0;
			PreparedStatement ps = null;
			int n = 0;
			for (Environment environment : c) {
				n++;
				@SuppressWarnings("deprecation")
				int day2 = environment.getGather_date().getDate();
				if(day2!=day){
					day = day2;
					String sql = "insert into e_detail_"+day+" values(?,?,?,?,?,?,?,?,?)";
					ps = connection.prepareStatement(sql); 
				}
				String name = environment.getName();
				String srcId = environment.getSrcId();
				String dstId = environment.getDesId();
				String sensorAddress = environment.getSensorAddress();
				int count = environment.getCount();
				String cmd = environment.getCmd();
				int status = environment.getStatus();
				float data = environment.getData();
				Timestamp date = environment.getGather_date();
				ps.setString(1, name);
				ps.setString(2, srcId);
				ps.setString(3, dstId);
				ps.setString(4, sensorAddress);
				ps.setInt(5, count);
				ps.setString(6, cmd);
				ps.setInt(7, status);
				ps.setFloat(8, data);
				ps.setTimestamp(9, date);
				
				ps.addBatch();
				if (n > 1000) {
					ps.executeBatch();
					connection.commit();
					ps.clearBatch();
					n = 0;
				}
			}
			ps.executeBatch();
			connection.commit();
			ps.close();
			connection.close();
		} catch (Exception e) {
			backup.store("server_back", c, false);
			e.printStackTrace();
		}
		long endMili = System.currentTimeMillis();
		log.debug("数据入库耗时："+(endMili-startMili)+" 毫秒");
	}
	
	public static void main(String[] args) {
		int day = 12;
		String name = "name";
		String srcId = "srcId";
		String dstId = "dstId";
		String sensorAddress = "sensor";
		int count = 12;
		String cmd = "cmd";
		int status = 1;
		float data = 16; 
		String date = "to_date('2018-01-01 12:00:00','yyyy-MM-dd HH24:mi:ss')";

		String sql = "insert into e_detail_" + day + " values('" + name + "','"
				+ srcId + "','" + dstId + "','" + sensorAddress + "'," + count
				+ ",'" + cmd + "'," + status + "," + data + "," + date + ")";
		System.out.println(sql);
	}

	@Override
	public void init(Properties pp) {
		this.driver = pp.getProperty("driver");
		this.url = pp.getProperty("url");
		this.username = pp.getProperty("username");
		this.password = pp.getProperty("password");
		
	}

	@Override
	public void setConfiguration(ConfigurationImpl configurationImpl) {
		this.configuration = configurationImpl;
		this.log = this.configuration.getLogger();
		this.backup = this.configuration.getBackup();
	}

}
