package jetty.web.server.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class HelperServlet extends BaseHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.doGet(req, resp, "/static/pages/help");
    }

    @Override
    public String getServletInfo() {
        return "Helper Servlet";
    }

    @Override
    public void destroy() {
    }
}
