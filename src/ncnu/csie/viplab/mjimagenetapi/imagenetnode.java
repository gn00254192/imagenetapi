package ncnu.csie.viplab.mjimagenetapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;

public class imagenetnode {
	int shownumber = 8;
	static final int MULTICAST_SIZE = 1000;
	private static final FetchOptions DEFAULT_FETCH_OPTIONS = FetchOptions.Builder
			.withPrefetchSize(MULTICAST_SIZE).chunkSize(MULTICAST_SIZE);
	int[] temp;

	public String[] searchname(String name) {
		String[] nodenumber = new String[5];
		for (int i = 0; i < 5; i++) {
			nodenumber[i] = "-1";
		}

		boolean get = false;
		URL myUrl;
		try {
			myUrl = new URL("http://www.image-net.org/search?q=" + name);
			// 取得 URLConnection
			HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();

			conn.setDoInput(true); // 設定為可從伺服器讀取資料
			conn.setDoOutput(true); // 設定為可寫入資料至伺服器
			conn.setRequestMethod("GET"); // 設定請求方式為 GET
			// 以下是設定 MIME 標頭中的 Content-type
			conn.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			conn.connect(); // 開始連接
			conn.setReadTimeout(300);
			// 透過 URLConnection 的 getOutputStream() 取的 OutputStream, 並建立以UTF-8
			// 為編碼的 OutputStreamWriter

			String inputLine = "";
			// 利用 URLConnection 的 getInputStream() 取得 InputStream
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			int count = 0;
			while ((inputLine = reader.readLine()) != null) {
				int index = inputLine.indexOf("synset?wnid=");
				if (index > 0) {
					get = true;
					if (count % 4 == 0 && (count < 19)) {
						nodenumber[count / 4] = inputLine.substring(index + 13,
								index + 21);

					}

					count++;
				}

			}
			reader.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nodenumber;

	}

	public void shownode(String nnodenumber, int rn, HttpServletResponse resp) {
		int nodenumber = Integer.parseInt(nnodenumber);
		if (isnodenough(nodenumber)) {
			int rank = Nodenumber.choice(nodenumber);
			int[] piclist = getlist(nodenumber, nnodenumber, rn, resp);

			int countline = 0;
			URL myUrl = null;
			try {
				myUrl = new URL("http://mcmjimagenetapi" + rank
						+ ".appspot.com/mjimagenetapi?node=n" + nnodenumber
						+ "&begin=" + (piclist[0]) + "&howmuch="
						+ (piclist[shownumber - 1] + 1));

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 取得 URLConnection
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) myUrl.openConnection();
				conn.setReadTimeout(800);
				conn.setDoInput(true); // 設定為可從伺服器讀取資料
				conn.setDoOutput(true); // 設定為可寫入資料至伺服器
				conn.setRequestMethod("GET"); // 設定請求方式為 GET
				// 以下是設定 MIME 標頭中的 Content-type
				conn.setRequestProperty("Content-type",
						"application/x-www-form-urlencoded");
				conn.connect(); // 開始連接
				// 透過 URLConnection 的 getOutputStream() 取的 OutputStream,
				// 並建立以UTF-8
				// 為編碼的 OutputStreamWriter

				// 利用 URLConnection 的 getInputStream() 取得 InputStream
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String inputLine;
				int countrol = 0;
				while ((inputLine = reader.readLine()) != null) {
					if (inputLine.length() > 3) {
						// resp.getWriter().println(countline+","+countrol+","+piclist[countrol]);
						if (countrol == 0) {
							resp.getWriter().println(
									"1," + nnodenumber + "," + (piclist[0])
											+ "," + inputLine);
							countrol++;
						} else if (countline == piclist[countrol]) {
							resp.getWriter().println(
									"1," + nnodenumber + ","
											+ (piclist[countrol] + piclist[0])
											+ "," + inputLine);
							countrol++;

						}

					}
					countline++;

				}
				conn.disconnect();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// public void shownodemore(String nnodenumber, HttpServletResponse resp) {
	// int nodenumber = Integer.parseInt(nnodenumber);
	// if (isnodenough(nodenumber)) {
	// int rank = Nodenumber.choice(nodenumber);
	// int[] piclist = getlist(nodenumber);
	//
	// int countline = 0;
	// URL myUrl = null;
	// try {
	// myUrl = new URL("http://mcmjimagenetapi" + rank
	// + ".appspot.com/mjimagenetapi?node=n" + nnodenumber
	// + "&begin=" + (piclist[0] + 1) + "&howmuch="
	// + (piclist[shownumber] + 1));
	//
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // 取得 URLConnection
	// HttpURLConnection conn;
	// try {
	// conn = (HttpURLConnection) myUrl.openConnection();
	//
	// conn.setDoInput(true); // 設定為可從伺服器讀取資料
	// conn.setDoOutput(true); // 設定為可寫入資料至伺服器
	// conn.setRequestMethod("GET"); // 設定請求方式為 GET
	// // 以下是設定 MIME 標頭中的 Content-type
	// conn.setRequestProperty("Content-type",
	// "application/x-www-form-urlencoded");
	// conn.connect(); // 開始連接
	// // 透過 URLConnection 的 getOutputStream() 取的 OutputStream,
	// // 並建立以UTF-8
	// // 為編碼的 OutputStreamWriter
	//
	// // 利用 URLConnection 的 getInputStream() 取得 InputStream
	// BufferedReader reader = new BufferedReader(
	// new InputStreamReader(conn.getInputStream(), "UTF-8"));
	// String inputLine;
	// int countrol = 1;
	// while ((inputLine = reader.readLine()) != null) {
	// if (countline == piclist[countrol]) {
	// resp.getWriter().println(
	// "1," + nnodenumber + ","
	// + (piclist[countrol] + piclist[0])
	// + "," + inputLine);
	// countrol++;
	// }
	// countline++;
	//
	// }
	// reader.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	public void googleimagesearch(String name, HttpServletResponse resp) {

		// System.out.println(site);
		try {
			String site = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&start=10&q="
					+ name;
			URL url = new URL(site);
			String str, s1 = "0";
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			urlc.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2");
			urlc.setReadTimeout(800);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					urlc.getInputStream(), "UTF-8"));
			if ((str = input.readLine()) != null) {
				int count = 1;
				while (str.indexOf("\"url\"") > 0) {
					resp.getWriter().println(
							"2,google,"
									+ count
									+ ","
									+ str.substring(str.indexOf("\"url\"") + 7,
											str.indexOf("\"visibleUrl\"") - 2));
					str = str.substring(str.indexOf("\"visibleUrl\"") + 10,
							str.length());
					count++;

				}
			}
			urlc.disconnect();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isnodenough(int nodenumber) {
		return true;
	}

	public int[] getlist(int nodenumber, String nnodenumber, int rn,
			HttpServletResponse resp) {

		int w = 0;

		try {
			String site = "http://mcmjimagenetapi"
					+ Nodenumber.choice(nodenumber)
					+ ".appspot.com/mjimagenetapinode?node=n" + nnodenumber;
			URL url = new URL(site);
			String str;
			HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			urlc.setReadTimeout(800);
			urlc.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2");
			urlc.setReadTimeout(800);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					urlc.getInputStream(), "UTF-8"));
			if ((str = input.readLine()) != null) {
				w = Integer.parseInt(str);
			}
			urlc.disconnect();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		@SuppressWarnings("deprecation")
		Query query = new Query("ans").addSort("nodenumber",
				SortDirection.ASCENDING);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
		Entity entity = null;

		int[] list = new int[shownumber];
		// if (entities != null) {
		// list[0] = 1;
		// for (int i = 1; i < shownumber; i++)
		// list[i] = i;
		// int ind = 0, entnum = 0, n = 0;
		// for (int i = 0; i < entities.size(); i++) {
		// try {
		// resp.getWriter().println("haha,"+entities.get(i)
		// .getProperty("nodenumber").toString());
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// if (Integer.parseInt(entities.get(i).getProperty("node")
		// .toString()) == nodenumber) {
		// n = Integer.parseInt(entities.get(i)
		// .getProperty("nodenumber").toString());
		//
		// if (entnum != n - 1) {
		// if (ind == 0) {
		// list[ind] = entnum + 1;
		// ind++;
		// if (ind == shownumber)
		// break;
		// entnum++;
		// for (int j = ind; (j + list[0]) < n; j++) {
		// if ((j + list[0]) < n) {
		// list[ind] = entnum + 1 - list[0];
		// ind++;
		// entnum++;
		// if (ind == shownumber)
		// break;
		// }
		// }
		// if (ind == shownumber)
		// break;
		// } else {
		// for (int j = ind; (j + list[0]) < n; j++) {
		// if ((j + list[0]) < n) {
		// list[ind] = entnum + 1 - list[0];
		// ind++;
		// entnum++;
		// if (ind == shownumber)
		// break;
		// }
		// }
		// if (ind == shownumber)
		// break;
		// }
		// }
		// entnum = n;
		// }
		// }
		// if (ind < shownumber) {
		// if (ind == 0) {
		// list[0] = n + 1;
		// ind++;
		// for (int i = 1; i < shownumber; i++) {
		// list[i] = i;
		// }
		//
		// } else if (ind == 1) {
		//
		// list[1] = n + 1 - list[0];
		// ind++;
		// for (int i = ind; i < shownumber; i++) {
		// list[i] = list[i - 1] + 1;
		// }
		//
		// } else {
		//
		// for (int i = ind; i < shownumber; i++) {
		// list[i] = list[i - 1] + 1;
		// }
		// }
		//
		// }
		// } else {
		temp=new int[shownumber-1];
		list[0] = (int) (rn % w + 1);
		w = w - list[0];
		if (w > 5) {
			for (int q = 0; q < shownumber-1; q++) {
				temp[q] = (int) (Math.random() * (w - 1) + 1);

			}
			Arrays.sort(temp);
			for (int q = 0; q < shownumber-1; q++) 
			{
				list[q+1]=temp[q];
			}

		}

		// }
		return list;
	}
}
