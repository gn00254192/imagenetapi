package ncnu.csie.viplab.mjimagenetapi;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class search extends HttpServlet {
	private final Logger logger = Logger.getLogger(getClass().getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		String name = req.getParameter("name");
		int rn=Integer.parseInt(req.getParameter("asc"));
		translat tral = new translat();
		logger.severe(name);
		if (tral.isChinese(name)) {
			name = tral.translate(name);
		}
		pathdb db = new pathdb();
		imagenetnode in = new imagenetnode();
//		resp.getWriter().println(name);
		if (db.findname(name)) {
			resp.getWriter().println("1");
			db.showpath(name, resp);
		} else {
			resp.getWriter().println("2");
			String[] nodenumber = in.searchname(name);
			if (nodenumber.length<3)
			{
				for(int i=0;i<5;i++)
				{
					nodenumber = in.searchname(name);
					if (nodenumber.length>3)
					{
						break;
					}
				}
			}
			for (int i = 0; i < 5; i++) {
				if (!nodenumber[i].equals("-1"))
					in.shownode(nodenumber[i],rn, resp);
			}
			in.googleimagesearch(name, resp);

		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		doGet(req, resp);
	}
}
