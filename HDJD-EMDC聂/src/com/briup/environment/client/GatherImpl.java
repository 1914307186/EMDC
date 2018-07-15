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
		 * 1��path2ָ����·���ж�ȡ������һ�ζ�ȡ�����ֽ������ļ� ��һ�θ��ļ������ڣ���Ҫ������ж�
		 * 2��ȡradwtmp�ļ�����Ч�ֽ����������ص�ֵ���浽path2ָ�����ļ��� 3���Թ���һ�ζ�ȡ���ļ��ֽ������ڽ��б��ν���
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
		 * ���������ַ������ж�ȡ���� ����|�ָ��ַ��������ݵ��ĸ��ֶεĲ�ͬ�ֱ����¶� ʪ�� ������̼Ũ��
		 * ���߸��ַ���ʾ16���ƻ���ֵ����16����ת����10����
		 * �¶Ⱥ�ʪ����ͬһ����¼����ȡһ����Ҫ��������Environment���󣬲��ֱ���ֵ ��װ�õ�������ӵ�collection��
		 */
		String s = null;
		String[] str = null;
		Environment environment = null;
		
		// str[3]Ϊ16ʱ����ʾ�¶�ʪ��
		int count = 0;
		
		// str[3]Ϊ256ʱ����ʾ����ǿ��
		int count2 = 0;
		
		// str[3]Ϊ1280ʱ����ʾ������̼Ũ��
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
				// ʪ�����ݶ���
				float value = (float) ((Integer.parseInt(
						str[6].substring(0, 4), 16) * 0.00268127) - 46.85);
				environment.setName("�¶�");
				environment.setData(value);
				list.add(environment);
				
				// (String name, String srcId, String desId, String devId,
				// String sensorAddress, int count, String cmd, int status,
				// float data, Timestamp gather_date)
				// �¶����ݶ���
				float value2 = (float) ((Integer.parseInt(str[6].substring(4, 8), 16) * 0.00190735) - 6);
				Environment environment2 = new Environment("ʪ��", str[0],
						str[1], str[2], str[3], Integer.valueOf(str[4]),
						str[5], Integer.valueOf(str[7]), value2, gather_date);
				list.add(environment2);
				count++;
			} else if (str[3].equals("256")) {
				environment.setName("����ǿ��");
				environment
						.setData(Integer.parseInt(str[6].substring(0, 4), 16));
				list.add(environment);
				count2++;
			} else if (str[3].equals("1280")) {
				environment.setName("������̼");
				float value = (float) Integer.parseInt(str[6].substring(0, 4), 16);
				environment.setData(value);
				list.add(environment);
				count3++;
			}
		}
		log.debug("�ɼ��Ļ������ݣ�" + list.size());
		log.info("�¶Ⱥ�ʪ�ȣ�" + count);
		log.info("����ǿ�ȣ�" + count2);
		log.info("������̼��" + count3);
		
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
