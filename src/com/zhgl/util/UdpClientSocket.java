package com.zhgl.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhgl.run.server.CRC16;

public class UdpClientSocket {
	private byte[] buffer = new byte[1024];

	private DatagramSocket ds = null;

	/**
	 * 构造函数，创建UDP客户端
	 * 
	 * @throws Exception
	 */
	public UdpClientSocket() throws Exception {
		ds = new DatagramSocket();
	}

	/**
	 * 设置超时时间，该方法必须在bind方法之后使用.
	 * 
	 * @param timeout
	 *            超时时间
	 * @throws Exception
	 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation
	 *         date: 2007-8-16 - 下午10:55:12
	 */
	public final void setSoTimeout(final int timeout) throws Exception {
		ds.setSoTimeout(timeout);
	}

	/**
	 * 获得超时时间.
	 * 
	 * @return 返回超时时间
	 * @throws Exception
	 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation
	 *         date: 2007-8-16 - 下午10:55:25
	 */
	public final int getSoTimeout() throws Exception {
		return ds.getSoTimeout();
	}

	public final DatagramSocket getSocket() {
		return ds;
	}

	/**
	 * 向指定的服务端发送数据信息.
	 * 
	 * @param host
	 *            服务器主机地址
	 * @param port
	 *            服务端端口
	 * @param bytes
	 *            发送的数据信息
	 * @return 返回构造后俄数据报
	 * @throws IOException
	 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation
	 *         date: 2007-8-16 - 下午11:02:41
	 */
	public final DatagramPacket send(final String host, final int port,
			final byte[] bytes) throws IOException {
		DatagramPacket dp = new DatagramPacket(bytes, bytes.length,
				InetAddress.getByName(host), port);
		ds.send(dp);
		return dp;
	}

	/**
	 * 接收从指定的服务端发回的数据.
	 * 
	 * @param lhost
	 *            服务端主机
	 * @param lport
	 *            服务端端口
	 * @return 返回从指定的服务端发回的数据.
	 * @throws Exception
	 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation
	 *         date: 2007-8-16 - 下午10:52:36
	 */
	public final String receive(final String lhost, final int lport)
			throws Exception {
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		ds.receive(dp);
		StringBuffer sb = new StringBuffer();
		for (char b : Hex.encodeHex(dp.getData())) {
			sb.append(b);
		}
		return sb.toString();
	}

	/**
	 * 关闭udp连接.
	 * 
	 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation
	 *         date: 2007-8-16 - 下午10:53:52
	 */
	public final void close() {
		try {
			ds.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 测试客户端发包和接收回应信息的方法.
	 * 
	 * @param args
	 * @throws Exception
	 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation
	 *         date: 2007-8-16 - 下午11:03:54
	 */
	public static void main(String[] args) throws Exception {
		UdpClientSocket client = new UdpClientSocket();
		String serverHost = "117.34.72.179";
		int serverPort = 22028;
		// serverHost = "192.168.1.101";
		// serverPort = 1986;
		// serverHost = "118.122.119.82";
		// serverPort = 2013;
		Log log = LogFactory.getLog("socket1");
		for (int i = 1333; i < 10000000; i++) {
			String s = product(i);// "47004038363531393130313032303431343800616263000000000000000000000000000000000000000000000000000000000031323334353600000000000000000000000000000000000000000000000000005573";
			StringBuffer sb = new StringBuffer(s);
			byte[] bts = new byte[sb.length() / 2];
			for (int j = 0, k = 0; j < sb.length(); k++, j = j + 2) {
				String str = sb.substring(j, j + 2);
				int inte = Integer.parseInt(str, 16);
				byte b = (byte) inte;
				bts[k] = b;
			}
			client.setSoTimeout(2000);
			try {
				client.send(serverHost, serverPort, bts);
				String info = client.receive(serverHost, serverPort);
				log.info(i + "-" + new Date() + "服务端回应数据：" + info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String product(int socketId) {
		StringBuffer sb = new StringBuffer("47102a");
		Integer.toBinaryString(socketId);
		String socketTemp = Integer.toHexString(socketId);
		StringBuffer sockZERO = new StringBuffer();
		for (int i = 0; i < 6 - socketTemp.length(); i++) {
			sockZERO.append("0");
		}
		sb.append(sockZERO + socketTemp);
		sb.append("0e1a01200000006400000000000000000000000000000000000000000000000000000000000000000000");

		int crc = CRC16.crc(sb.toString());
		String crcTemp = Integer.toHexString(crc);
		StringBuffer crcZERO = new StringBuffer();
		for (int i = 0; i < 4 - crcTemp.length(); i++) {
			crcZERO.append("0");
		}
		sb.append(crcZERO + crcTemp);
		return sb.toString();
	}

	/**
	 * 把16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
}
