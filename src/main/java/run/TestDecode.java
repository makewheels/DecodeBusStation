package run;

import com.e4a.runtime.Authcode;

public class TestDecode {
	public static void main(String[] args) {
		String source = "fr54m+p9Vl6a6OIKA5BxWnM5DdR5Cf0Y2kI3yFWnxqSQOw==";
		String key = "yangbosen123";
		String decode = Authcode.Decode(source, key);
		System.out.println(decode);
	}
}
