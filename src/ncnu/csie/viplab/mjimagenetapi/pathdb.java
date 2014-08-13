package ncnu.csie.viplab.mjimagenetapi;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
public class pathdb extends HttpServlet {
	String ename;
	static final int MULTICAST_SIZE = 1000;
	private static final FetchOptions DEFAULT_FETCH_OPTIONS = FetchOptions.Builder
			.withPrefetchSize(MULTICAST_SIZE).chunkSize(MULTICAST_SIZE);
	private final Logger logger = Logger.getLogger(getClass().getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		//  採用 transaction的做法是因為要同時修改多種資料 
		Transaction txn = datastore.beginTransaction();
		int count = (int) Float.parseFloat(req.getParameter("count"));		 
		// 建構DatastoreService 物件
		datastore = DatastoreServiceFactory.getDatastoreService();

		Entity entity = new Entity("path");   // 創一個entity叫path
		int i;
		for (i = 0; (i < count); i++)
			entity.setProperty("path" + i, req.getParameter("path" + i));

		String name = null;
		if (ename == null) {    //ename是english_name
			ename = req.getParameter("search");
		}
		translat tral = new translat();
		logger.severe(ename);           //Log的用法
		logger.severe(tral.isChinese(ename) + "123");

		name = tral.translate(ename);    //丟給translat去翻譯

		logger.severe(name);
		// 設定entity 實體的屬性
		entity.setProperty("number", "" + (i - 1));
		entity.setProperty("node", req.getParameter("node"));
		entity.setProperty("nodenumber", req.getParameter("nodenumber"));
		entity.setProperty("width", req.getParameter("width"));
		entity.setProperty("hight", req.getParameter("hight"));
		entity.setProperty("deviceid", req.getParameter("deviceid"));
		entity.setProperty("search", name);
		entity.setProperty("url", req.getParameter("url"));
		entity.setProperty("pin", req.getParameter("pin"));
		 // 儲存entity 物件
		datastore.put(entity);

		// }

		// }
		txn.commit();

		// resp.getWriter().println("Hello, world");
		// String act = req.getParameter("act");
		// DatastoreService datastore = DatastoreServiceFactory
		// .getDatastoreService();
		// Transaction txn = datastore.beginTransaction();
		// if (act.equals("1")) //create
		// {
		// Entity entity = new Entity("count");
		//
		// entity.setProperty("lab1","1");
		//
		//
		// datastore.put(entity);
		// txn.commit();
		// }
		// if (act.equals("2")) //delete
		// {
		// @SuppressWarnings("deprecation")
		// Query query = new Query("count").addFilter("lab1",
		// FilterOperator.EQUAL, req.getParameter("query"));
		// PreparedQuery preparedQuery = datastore.prepare(query);
		// List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
		// Entity entity = null;
		// if (!entities.isEmpty()) {
		// entity = entities.get(0);
		// datastore.delete(entity.getKey());
		// }
		//
		// txn.commit();
		// }
		// if (act.equals("3")) //update
		// {
		// @SuppressWarnings("deprecation")
		// Query query = new Query("count").addFilter("lab1",
		// FilterOperator.EQUAL, req.getParameter("query"));
		// PreparedQuery preparedQuery = datastore.prepare(query);
		// List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
		// Entity entity = null;
		// if (!entities.isEmpty()) {
		// entity = entities.get(0);
		// entity.setProperty("lab1",req.getParameter("data"));
		// datastore.put(entity);
		// }
		//
		// txn.commit();
		// }

	}

	public boolean findname(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		@SuppressWarnings("deprecation")
		Query query = new Query("path").addFilter("search",
				FilterOperator.EQUAL, name);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
		Entity entity = null;
		if (!entities.isEmpty()) {
			return true;
		}
		return false;

	}

	public void showpath(String name, HttpServletResponse resp) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		@SuppressWarnings("deprecation")
		Query query = new Query("path").addFilter("search",
				FilterOperator.EQUAL, name);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
		Entity entity = null;
		if (!entities.isEmpty()) {
			for (int i = 0; i < 6; i++) {
				if (i < entities.size()) {
					entity = entities.get(i);
					try {
						resp.getWriter().println(entity.getKey().getId());
						resp.getWriter().println(entity.getProperty("url"));
						// resp.getWriter().println(entity.getProperty("pin"));
						// resp.getWriter().println(entity.getProperty("width"));
						// for(int
						// j=0;j<=Integer.parseInt(entity.getProperty("number").toString());j++)
						// resp.getWriter().print(entity.getProperty("path"+j));
						// resp.getWriter().println("");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ename = req.getParameter("search");
		logger.severe(ename);

		doGet(req, resp);
	}

}
