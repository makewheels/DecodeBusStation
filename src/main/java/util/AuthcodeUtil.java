package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class AuthcodeUtil {

	public static String Encode(String source, String key) {
		if (source == null || key == null) {
			try {
				return "";
			} catch (Exception e) {
				return "";
			}
		}
		key = MD52(key);
		String keya = MD52(CutString(key, 0, 16));
		String keyb = MD52(CutString(key, 16, 16));
		String keyc = RandomString(4);
		try {
			return keyc + Base64.encodeBase64String(
					RC4(("0000000000" + CutString(MD52(source + keyb), 0, 16) + source).getBytes("UTF-8"),
							keya + MD52(keya + keyc)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String Decode(String source, String key) {
		if (source == null || key == null) {
			try {
				return "";
			} catch (Exception e) {
				return "";
			}
		}
		key = MD52(key);
		String keya = MD52(CutString(key, 0, 16));
		String keyb = MD52(CutString(key, 16, 16));
		String cryptkey = keya + MD52(keya + CutString(source, 0, 4));
		String result = new String(RC4(Base64.decodeBase64(CutString(source, 4)), cryptkey));
		if (CutString(result, 10, 16).equals(CutString(MD52(CutString(result, 26) + keyb), 0, 16))) {
			return CutString(result, 26);
		}
		result = new String(RC4(Base64.decodeBase64(CutString(source + "=", 4)), cryptkey));
		if (CutString(result, 10, 16).equals(CutString(MD52(CutString(result, 26) + keyb), 0, 16))) {
			return CutString(result, 26);
		}
		result = new String(RC4(Base64.decodeBase64(CutString(source + "==", 4)), cryptkey));
		if (CutString(result, 10, 16).equals(CutString(MD52(CutString(result, 26) + keyb), 0, 16))) {
			return CutString(result, 26);
		}
		return "2";
	}

	private static String CutString(String str, int startIndex, int length) {
		if (startIndex >= 0) {
			if (length < 0) {
				length *= -1;
				if (startIndex - length < 0) {
					length = startIndex;
					startIndex = 0;
				} else {
					startIndex -= length;
				}
			}
			if (startIndex > str.length()) {
				return "";
			}
		} else if (length < 0) {
			return "";
		} else {
			if (length + startIndex <= 0) {
				return "";
			}
			length += startIndex;
			startIndex = 0;
		}
		if (str.length() - startIndex < length) {
			length = str.length() - startIndex;
		}
		return str.substring(startIndex, startIndex + length);
	}

	private static String CutString(String str, int startIndex) {
		return CutString(str, startIndex, str.length());
	}

	private static byte[] GetKey(byte[] pass, int kLen) {
		int i;
		byte[] mBox = new byte[kLen];
		for (i = 0; i < kLen; i++) {
			mBox[i] = (byte) i;
		}
		int j = 0;
		for (i = 0; i < kLen; i++) {
			j = ((((mBox[i] + 256) % 256) + j) + pass[i % pass.length]) % kLen;
			byte temp = mBox[i];
			mBox[i] = mBox[j];
			mBox[j] = temp;
		}
		return mBox;
	}

	private static String RandomString(int lens) {
		char[] CharArray = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		int clens = CharArray.length;
		String sCode = "";
		Random random = new Random();
		for (int i = 0; i < lens; i++) {
			sCode = sCode + CharArray[Math.abs(random.nextInt(clens))];
		}
		return sCode;
	}

	private static byte[] RC4(byte[] input, String pass) {
		if (input == null || pass == null) {
			return null;
		}
		byte[] output = new byte[input.length];
		byte[] mBox = GetKey(pass.getBytes(), 256);
		int i = 0;
		int j = 0;
		for (int offset = 0; offset < input.length; offset++) {
			i = (i + 1) % mBox.length;
			j = (((mBox[i] + 256) % 256) + j) % mBox.length;
			byte temp = mBox[i];
			mBox[i] = mBox[j];
			mBox[j] = temp;
			output[offset] = (byte) (toInt(mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length]) ^ input[offset]);
		}
		return output;
	}

	private static String MD52(String MD5) {
		StringBuffer sb = new StringBuffer();
		try {
			byte[] md5 = MessageDigest.getInstance("MD5").digest(MD5.getBytes());
			for (byte b : md5) {
				String part = Integer.toHexString(b & 255);
				if (part.length() == 1) {
					part = "0" + part;
				}
				sb.append(part);
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}

	private static int toInt(byte b) {
		return (b + 256) % 256;
	}
}
