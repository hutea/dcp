package com.zhgl.run.server;

public class CRC16 {
	/**
	 * 得到CRC检验码
	 */
	public static int crc(String data) {
		int lengthCRC = data.length() / 2;
		char[] chars = new char[lengthCRC];
		int a = 0;
		int b = 2;
		for (int i = 0; i < lengthCRC; i++) {
			data.substring(a, b);
			chars[i] = (char) Integer.parseInt(data.substring(a, b), 16);
			a += 2;
			b += 2;
		}
		return crc(chars, lengthCRC);
	}

	private static int crc(char[] buffer, int length) {
		int wCRC16 = 65535;
		int i;
		int j;
		for (j = 0; j < length; j++) {
			wCRC16 ^= buffer[j];
			for (i = 0; i < 8; i++) {
				if ((wCRC16 & 1) == 1) {
					wCRC16 >>= 1;
					wCRC16 ^= 40961;
				} else {
					wCRC16 >>= 1;
				}
			}
		}
		return wCRC16;
	}

	public static void main(String[] args) {
		String str = "47102a0000030e1a00010000006400000000000000000000000000000000000000000000000000000000000000000000";
		int crc = CRC16.crc(str);
		String crcTemp = Integer.toHexString(crc);
		StringBuffer crcZERO = new StringBuffer();
		for (int i = 0; i < 4 - crcTemp.length(); i++) {
			crcZERO.append("0");
		}
		//System.out.print(crcZERO+crcTemp);
	}
}
