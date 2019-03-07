package run;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.e4a.runtime.Authcode;

public class Start {

	/**
	 * 解密一个加密文件
	 * 
	 * @param file
	 * @return
	 */
	public static String decodeAuthcodeFile(File file) {
		try {
			return Authcode.Decode(FileUtils.readFileToString(file, Charset.defaultCharset().name()),
					"199071lh@zhaoqianwangpengluzhipengliuxueyingzhangdeiqian");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析出站点名数组
	 * 
	 * @param decode
	 * @return
	 */
	public static String[] parseStationArray(String decode) {
		String replace = decode.replace("\r\n", "");
		String substringBetween = StringUtils.substringBetween(replace, "路开", "路束");
		String deleteEnd = substringBetween.substring(0, substringBetween.length() - 2);
		return deleteEnd.split("\\|\\|");
	}

	public static void main(String[] args) {
		String baseFilePath = "D:\\逆向\\材料\\胜利的果实：站点名\\大庆";
		Collection<File> listFiles = FileUtils.listFiles(new File("D:\\逆向\\材料\\daqingisba"), null, false);
		for (File file : listFiles) {
			String filename = file.getName();
			String busName = filename.substring(0, filename.lastIndexOf("."));
			String result = decodeAuthcodeFile(file);
			String[] stationArray = null;
			try {
				stationArray = parseStationArray(result);
			} catch (Exception e) {
				System.out.println("错误，文件名：" + filename);
				e.printStackTrace();
			}
			File saveJsonFile = new File(baseFilePath + File.separator + busName + ".json");
			String data = JSON.toJSONString(stationArray);
			try {
				FileUtils.write(saveJsonFile, data, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
