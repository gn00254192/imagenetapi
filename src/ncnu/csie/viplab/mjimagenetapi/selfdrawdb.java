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
public class selfdrawdb extends HttpServlet {
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

		Entity entity = new Entity("selfdrawpath");   // 創一個entity叫path
		int i;
		for (i = 0; (i < count); i++){
			entity.setProperty("path" + i, req.getParameter("path" + i));
		}

		

		// 設定entity 實體的屬性
		entity.setProperty("width", req.getParameter("width"));
		entity.setProperty("hight", req.getParameter("hight"));
		entity.setProperty("deviceid", req.getParameter("deviceid"));
		entity.setProperty("pin", req.getParameter("pin"));
		 // 儲存entity 物件
		datastore.put(entity);

		// }

		// }
		txn.commit();

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ename = req.getParameter("search");
		logger.severe(ename);

		doGet(req, resp);
	}

}
