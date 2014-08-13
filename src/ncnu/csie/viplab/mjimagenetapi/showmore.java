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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
public class showmore extends HttpServlet {
	private final Logger logger = Logger.getLogger(getClass().getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		String name = req.getParameter("name");
		int rn=Integer.parseInt(req.getParameter("asc"));
		logger.severe(name);

		translat tral = new translat();
		if (tral.isChinese(name)) {

			name = tral.translate(name);
		}
		pathdb db = new pathdb();
		imagenetnode in = new imagenetnode();

		resp.getWriter().println("2");
		String[] nodenumber = in.searchname(name);

		for (int i = 0; i < 5; i++) {
			if (!nodenumber[i].equals("-1"))
				in.shownode(nodenumber[i],rn, resp);
		}
		in.googleimagesearch(name, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
}
