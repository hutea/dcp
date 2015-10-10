package com.zhgl.run.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.SynchronizedProtocolDecoder;
import org.apache.mina.filter.codec.SynchronizedProtocolEncoder;

import com.zhgl.util.Hex;

public class SocketServerCodeFactory implements ProtocolCodecFactory {

	private ProtocolEncoder encoder = new SynchronizedProtocolEncoder(
			new TowerCraneUDPEncoder());
	private ProtocolDecoder decoder = new SynchronizedProtocolDecoder(
			new TowerCraneUDPDecoder());

	final static char endchar = 0x0d;

	private Log log4_1 = LogFactory.getLog("socket4_1");
	
	public SocketServerCodeFactory() {
		log4_1.info("in  SocketServerCodeFactory");
		// this(Charset.forName("gb2312"));
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	class TowerCraneUDPDecoder extends ProtocolDecoderAdapter {
		public void decode(IoSession session, IoBuffer in,
				ProtocolDecoderOutput out) throws Exception {
			log4_1.info("in  decode");
			try {
				if (in.hasRemaining()) {
					byte[] data = new byte[in.remaining()];
					log4_1.info("data:"+data);
					in.get(data);
					out.write(Hex.encodeHexStr(data));
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	class TowerCraneUDPEncoder extends ProtocolEncoderAdapter {
		public void encode(IoSession session, Object message,
				ProtocolEncoderOutput out) throws Exception {
			try {
				String messageSTR = (String) message;
				String msg = "fefb" + this.filterFE(messageSTR.toLowerCase())
						+ "fefa"; // 加帧头帧尾
				StringBuffer sb = new StringBuffer(msg);
				byte[] bts = new byte[sb.length() / 2];
				for (int i = 0, k = 0; i < sb.length(); k++, i = i + 2) {
					String str = sb.substring(i, i + 2);
					int inte = Integer.parseInt(str, 16);
					byte b = (byte) inte;
					bts[k] = b;
				}
				out.write(IoBuffer.wrap(bts));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		/**
		 * 两个字符为一组替换fe为fefe
		 * 
		 * @param str：str长度必须为偶数，奇数则返回空字串
		 * @return
		 */
		private String filterFE(String str) {
			try {
				int k = str.length();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < k;) {
					int j = i + 2;
					String s = str.substring(i, j);
					if ("fe".equals(s)) {
						sb.append("fefe");
					} else {
						sb.append(s);
					}
					i = j;
				}
				return sb.toString();
			} catch (Exception e) {
				return "";
			}
		}
	}

}