package ncnu.csie.viplab.mjimagenetapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class translat {
public String translate(String name) {
		
		try {
			//http://gaduo-chien-blog.logdown.com/tags/translation  °Ñ¦Ò¤åÄmJAY
			
			URL url;
			String site = "http://translate.google.com.tw/translate_a/t?client=t&sl=zh-TW&tl=en&hl=en&sc=2&ie=UTF-8&oe=UTF-8&rom=1&prev=btn&ssel=5&tsel=3&q="
					+ URLEncoder.encode(name, "utf-8");
			url = new URL(site);
			String str;
			URLConnection urlc = url.openConnection();
			urlc.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					urlc.getInputStream(), "UTF-8"));
			str = input.readLine();
			input.close();
			for (int i = 0; i < 3; i++) {
				str = str.substring(str.indexOf("[") + 1, str.length());
				// System.out.println(str);
			}
			int index1, index2, index3;

			index1 = str.indexOf("\"");
			index2 = str.substring((index1) + 1, str.length()).indexOf("\"");
			index3 = str.indexOf("]");
			if (index1 < index3) {

				return str.substring(index1 + 1, index2 + (index1) + 1);

			}
			return "error";
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
public static boolean isChinese(String strName) {
	char[] ch = strName.toCharArray();
	for (int i = 0; i < ch.length; i++) {
		char c = ch[i];
		if (isChinese(c)) {
			return true;
		}
	}
	return false;
}

private static boolean isChinese(char c) {
	Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

	if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

	|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

	|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

	|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B

	|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

	|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS

	|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {

		return true;

	}

	return false;

}
}
