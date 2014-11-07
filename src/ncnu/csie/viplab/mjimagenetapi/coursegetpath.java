package ncnu.csie.viplab.mjimagenetapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class coursegetpath extends HttpServlet {
	static final int MULTICAST_SIZE = 1000;
	private static final FetchOptions DEFAULT_FETCH_OPTIONS = FetchOptions.Builder
			.withPrefetchSize(MULTICAST_SIZE).chunkSize(MULTICAST_SIZE);
	private final Logger logger = Logger.getLogger(getClass().getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		if (req.getParameter("act").equals("1")) {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Transaction txn = datastore.beginTransaction();

			//-----------------------------用key查詢方式--------------------------
			//SELECT * FROM path where __key__ = KEY('ag1zfmltYWdlbmV0YXBpchELEgRwYXRoGICAgMDIqoQJDA')
			//----------------------------------------------------------------
			
			
			
//			@SuppressWarnings("deprecation")
//			Query query = new Query("path");
//			query.addFilter("node", Query.FilterOperator.EQUAL,
//					req.getParameter("node"));
//			query.addFilter("key", Query.FilterOperator.EQUAL,
//				Long.parseLong(req.getParameter("key")));
			Key personKey = KeyFactory.createKey("coursepath",
					Long.parseLong(req.getParameter("key")));
			Entity entity =null ;
			try {
				entity = datastore.get(personKey);

			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.severe("personKey" + personKey);
			logger.severe("entity" + entity);
			// 會出這個SELECT * FROM path WHERE userId = google,1 AND providerId =
			// 1077

			//PreparedQuery preparedQuery = datastore.prepare(query);
			//logger.severe("preparedQuery" + preparedQuery);
			//List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
			//Entity entity = null;
		
			//if (!entities.isEmpty()) {
			//	entity = entities.get(0);
				try {
					resp.getWriter().println(entity.getKey().getId());
					
					resp.getWriter().println(entity.getProperty("width"));
					resp.getWriter().println(entity.getProperty("hight"));
					resp.getWriter().println(entity.getProperty("deviceid"));
				
					for (int j = 0; j <= Integer.parseInt(entity.getProperty(
							"number").toString()); j++)
						resp.getWriter().print(entity.getProperty("path" + j));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//}
			txn.commit();

		}  else if (req.getParameter("act").equals("5")) {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			@SuppressWarnings("deprecation")
			Query query = new Query("path").addSort("node",
					SortDirection.ASCENDING);
			PreparedQuery preparedQuery = datastore.prepare(query);
			List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
			Entity entity = null;
			if (entities != null)
				for (int i = 0; i < entities.size(); i++) {
					entity = entities.get(i);
					resp.getWriter().println(entity.getProperty("node"));
				}
		
		} else if (req.getParameter("act").equals("7")) {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			@SuppressWarnings("deprecation")
			Query query = new Query("path").addFilter("node",
					FilterOperator.EQUAL, req.getParameter("node"));
			PreparedQuery preparedQuery = datastore.prepare(query);
			List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
			Entity entity = null;
			if (!entities.isEmpty()) {
				entity = entities.get(0);
				try {
					resp.getWriter().println(entity.getProperty("url"));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else if (req.getParameter("act").equals("8")) {
			//輸出path每個的number值
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			@SuppressWarnings("deprecation")
			Query query = new Query("coursepath");		
			PreparedQuery preparedQuery = datastore.prepare(query);
			List<Entity> entities = preparedQuery.asList(DEFAULT_FETCH_OPTIONS);
			Entity entity = null;
			if (entities != null)
				for (int i = 0; i < entities.size(); i++) {
					entity = entities.get(i);
					resp.getWriter().println(entity.getKey().getId());
				}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		doGet(req, resp);
	}
}
