package jetty.web.server.servlets;

import org.gradle.internal.impldep.com.google.common.io.Resources;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BaseHttpServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp, String resourcePath) throws IOException {
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            final URL requestedPage = BaseHttpServlet.class.getResource(resourcePath);
            if (requestedPage != null){
                final String content = Resources.toString(requestedPage, StandardCharsets.UTF_8);
                outputStream.write(content.getBytes());
            } else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            outputStream.flush();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        String header = resp.getHeader("Allow").replace("TRACE, ","");
        resp.setHeader("Allow", header);
    }
}
