package com.briup.environment.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LoggerImpl;

public class GatherImpl implements Gather {
	private Log log = new LoggerImpl();
	private List<Environment> list = new ArrayList<Environment>();
	private ConfigurationImpl configuration;
	private String srcpath = "src/radwtmp";
	private String recordpath = "src/record";

	@Override
	public Collection<Environment> gather() throws Exception {
		/*
		 * 1从path2指定的路径中读取保存上一次读取到的字节数的文件 第一次该文件不存在，需要分情况判断
		 * 2读取radwtmp文件的有效字节数，将返回的值保存到path2指定的文件中 3先略过上一次读取的文件字节数，在进行本次解析
		 */
		File file = new File(recordpath);
		long num = 0;
		if (file.exists()) {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			num = dis.readLong();
			dis.close();
		}

		RandomAccessFile raf = new RandomAccessFile(srcpath, "r");
		long num2 = raf.length();
		raf.seek(num);
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(recordpath));
		dos.writeLong(num2);
		dos.close();
		/*
		 * 构建缓存字符流按行读取数据 根据|分割字符串，根据第四个字段的不同分别赋予温度 湿度 二氧化碳浓度
		 * 第七个字符表示16进制环境值，将16进制转换成10进制
		 * 温度和湿度是同一条记录，读取一行需要创建两个Environment对象，并分别赋予值 封装好的数据添加到collection中
		 */
		String s = null;
		String[] str = null;
		Environment environment = null;
		
		// str[3]为16时，表示温度湿度
		int count = 0;
		
		// str[3]为256时，表示光照强度
		int count2 = 0;
		
		// str[3]为1280时，表示二氧化碳浓度
		int count3 = 0;

		while ((s = raf.readLine()) != null) {
			environment = new Environment();
			str = s.split("[|]");

			environment.setSrcId(str[0]);
			environment.setDesId(str[1]);
			environment.setDevId(str[2]);
			environment.setSensorAddress(str[3]);
			environment.setCount(Integer.valueOf(str[4]));
			environment.setCmd(str[5]);
			environment.setStatus(Integer.valueOf(str[7]));
			Long time = new Long(str[8]);
			Timestamp gather_date = new Timestamp(time);
			environment.setGather_date(gather_date);
			if (str[3].equals("16")) {
				// 湿度数据对象
				float value = (float) ((Integer.parseInt(
						str[6].substring(0, 4), 16) * 0.00268127) - 46.85);
				environment.setName("温度");
				environment.setData(value);
				list.add(environment);
				
				// (String name, String srcId, String desId, String devId,
				// String sensorAddress, int count, String cmd, int status,
				// float data, Timestamp gather_date)
				// 温度数据对象
				float value2 = (float) ((Integer.parseInt(str[6].substring(4, 8), 16) * 0.00190735) - 6);
				Environment environment2 = new Environment("湿度", str[0],
						str[1], str[2], str[3], Integer.valueOf(str[4]),
						str[5], Integer.valueOf(str[7]), value2, gather_date);
				list.add(environment2);
				count++;
			} else if (str[3].equals("256")) {
				environment.setName("光照强度");
				environment
						.setData(Integer.parseInt(str[6].substring(0, 4), 16));
				list.add(environment);
				count2++;
			} else if (str[3].equals("1280")) {
				environment.setName("二氧化碳");
				float value = (float) Integer.parseInt(str[6].substring(0, 4), 16);
				environment.setData(value);
				list.add(environment);
				count3++;
			}
		}
		log.debug("采集的环境数据：" + list.size());
		log.info("温度和湿度：" + count);
		log.info("光照强度：" + count2);
		log.info("二氧化碳：" + count3);
		
		raf.close();
		return list;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		
		GatherImpl gatherImpl = new GatherImpl();
		List<Environment> list = (List<Environment>) gatherImpl.gather();
		
		Set<Integer> ss = new HashSet<Integer>();
		System.out.println(list.get(0).getGather_date());
		for (int i = 0; i < list.size(); i++) {
			ss.add(list.get(i).getGather_date().getDate());
		}
		for (Integer integer : ss) {
			System.out.print(integer+" ");
		}
		
		
	}

	@Override
	public void init(Properties p) {
		this.srcpath = p.getProperty("src_file");
		this.recordpath = p.getProperty("record_file");
		
	}

	@Override
	public void setConfiguration(ConfigurationImpl configurationImpl) {
		this.configuration = configurationImpl;
		this.log = this.configuration.getLogger();
	}

}
