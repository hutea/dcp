package com.zhgl.run.server;


import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;


@Service
public class SocketServer extends IoHandlerAdapter {
	@Resource
	private ProtocolLogin login;						// 身份认证协议处理类
	@Resource
	private ProtocolStatus status;						// 状态上报协议处理类
	@Resource
	private ProtocolInfo info;							// 信息传输协议处理类
	@Resource
	private ProtocolEvent event;						// 事件上报协议处理类
	@Resource
	private ProtocolFinger finger;						// 指纹协议处理类
	
	private Log log = LogFactory.getLog("socket");		// 终端网络通信日志

	/**
	 * 发送格式： 协议版本：04 厂商代码：04（4-15） 0x00 身份验证请求帧 0x01 身份验证回应帧 0x02 状态上报请求帧 0x03
	 * 状态上报回应帧 0x04 信息传输帧 0x05 信息回应帧 0x06 事件上报帧 0x07 事件回应帧 0x08 参数同步请求帧 0x09
	 * 参数同步回应帧
	 * 
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		Long start = System.currentTimeMillis();
		String data = ((String) message).replaceAll(" ", "");			//数据转换为String，并去除空格

		DataParse dataParse = new DataParse(data);				//解析校验帧格式
		if(!dataParse.checkFomart()) {
			log.info("拒绝格式错误帧：" + data + " 【" + session.getRemoteAddress() + "】");
			return;
		}
		//log.info("接收帧：" + data + " 【" + session.getRemoteAddress() + "】");

		String response = null;
		switch (dataParse.getFrameType()) {							//帧类型
		case 0: // 身份验证请求帧
			response = login.process(dataParse);
			break;
		case 2: // 状态上报请求帧
			response = status.process(dataParse);
			break;
		case 4: // 信息传输帧
			response = info.process(dataParse);
			break;
		case 6:// 事件上报帧
			response = event.process(dataParse);
			break;
		case 8: // 指纹同步请求帧
			response = finger.process(dataParse);
			break;
		}
		if (response != null) {
			//log.info("回应帧：" + response);
			session.write(response);								//回应帧发送到终端
		}
//		if (equipmentFileNoticeService.isUpdate(dataProcess.getData16Parse(), 2)) {// 数据文件是否需要更新
//			Data16Parse data16Parse = dataProcess.getData16Parse();
//			String content = data16Parse.getVersion() + data16Parse.getVendorCode() + "8002" + data16Parse.getDeviceCode() + "0202";
//			int crc = CRC16.crc(content);
//			session.write(content + SocketResoponseUtil.keepLength(crc, 4));
//			logS_1.info(content + SocketResoponseUtil.keepLength(crc, 4));
//		}
		
		Long end = System.currentTimeMillis();
		//System.out.print(System.currentTimeMillis()+"系统分析处理数据完成，累积用时：" + (end - start) + "毫秒");
	}

	public static void main(String[] args) {
		String myvalue = "A18F";
		int valueint = Integer.parseInt(myvalue, 16);
		String bivalue = Integer.toBinaryString(valueint);
		System.out.print(bivalue);
		int valuebiint = Integer.parseInt(bivalue, 2);
		System.out.print(Integer.toHexString(valuebiint));

		StringBuffer sb = new StringBuffer("47080838363137383530303837363832363300000000010d230d2853bb");
		byte[] bts = new byte[sb.length() / 2];
		for (int i = 0, k = 0; i < sb.length() / 2; k++, i = i + 2) {
			String str = sb.substring(i, i + 2);
			int inte = Integer.parseInt(str, 16);
			byte b = (byte) inte;
			bts[k] = b;
			// //System.out.print(str + "--" + inte + "--");
		}
		//System.out.print("----");
		for (byte b : bts) {
			System.out.print(Integer.toHexString(b) + "-");
		}

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		//System.out.print(new Date() + "close:--->" + session.getRemoteAddress());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		//System.out.print(new Date() + "create:--->" + session.getRemoteAddress());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		//System.out.print(new Date() + "open:--->" + session.getRemoteAddress());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		message = "ABC";
		super.messageSent(session, message);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
		session.close(true);
	}

}
