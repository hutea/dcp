package com.zhgl.run.server;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

public class MinaUDPClient {
	private static Log logS_1 = LogFactory.getLog("socketS_1");

	/**
	 * 人员变更通知终端更新指纹
	 * 
	 * @param tid
	 */
	public static void updateFinger(String tid) {
//		String ip = ActiveTowers.ip(tid);
//		int port = ActiveTowers.port(tid);
//		Data16Parse data16Parse = ActiveTowers.data16parse(tid);
//		if (!"".equals(ip) && data16Parse != null && port > 0) {
//			String content = data16Parse.getVersion()
//					+ data16Parse.getVendorCode() + "4001"
//					+ data16Parse.getDeviceCode() + "03";
//			int crc = CRC16.crc(content);
//			send(content + SocketResoponseUtil.keepLength(crc, 4), ip, port);
//			logS_1.info(content + SocketResoponseUtil.keepLength(crc, 4));
//		}
	}

	private static void send(String content, String ip, int port) {
		IoConnector connector = new NioDatagramConnector();
		connector.setHandler(new ClientUDPIoHandler());
		ConnectFuture connFuture = connector.connect(new InetSocketAddress(ip,
				port));// 郑悦2020
		connFuture.awaitUninterruptibly();
		IoSession session = connFuture.getSession();
		/** ******处理数据******* */
		String msg = "fefb" + content.toLowerCase().replaceAll("fe", "fefe")
				+ "fefa"; // 加帧头帧尾
		StringBuffer sb = new StringBuffer(msg);
		byte[] bts = new byte[sb.length() / 2];
		for (int i = 0, k = 0; i < sb.length(); k++, i = i + 2) {
			String str = sb.substring(i, i + 2);
			int inte = Integer.parseInt(str, 16);
			byte b = (byte) inte;
			bts[k] = b;
		}
		/** ******处理数据******* */
		WriteFuture future = session.write(IoBuffer.wrap(bts));
		future.awaitUninterruptibly();
		connector.dispose();
	}
}

class ClientUDPIoHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
