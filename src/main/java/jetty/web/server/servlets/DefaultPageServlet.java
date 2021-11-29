package jetty.web.server.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public final class DefaultPageServlet extends BaseHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final URL reqResource = DefaultPageServlet.class.getResource(request.getRequestURI());
        if (reqResource == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else super.doGet(request, response, "/static/pages/main");
    }

    @Override
    public String getServletInfo() {
        return "Main Page Servlet";
    }

    @Override
    public void destroy() {
    }
}
