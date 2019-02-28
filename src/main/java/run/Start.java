package run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import util.AuthcodeUtil;

public class Start {

	private static String decodeAuthcodeFile(File file) {
		try {
			return AuthcodeUtil.Decode(FileUtils.readFileToString(file, Charset.defaultCharset().name()),
					"199071lh@zhaoqianwangpengluzhipengliuxueyingzhangdeiqian");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Collection<File> listFiles = FileUtils.listFiles(new File("D:\\逆向\\材料\\daqingisba"), null, false);
		for (File file : listFiles) {
			String result = decodeAuthcodeFile(file);
			System.out.println(result);
		}
	}

}
