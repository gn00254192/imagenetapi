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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
public class test extends HttpServlet {
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
		Transaction txn = datastore.beginTransaction();
		datastore = DatastoreServiceFactory.getDatastoreService();

		Entity entity = new Entity("test");

		translat tral = new translat();
		

		entity.setProperty("name", req.getParameter("name"));

		datastore.put(entity);

		// }

		// }
		txn.commit();

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		datastore = DatastoreServiceFactory.getDatastoreService();

		Entity entity = new Entity("test");

		translat tral = new translat();
		

		entity.setProperty("name", req.getParameter("name"));

		datastore.put(entity);

		// }

		// }
		txn.commit();
	}

}
