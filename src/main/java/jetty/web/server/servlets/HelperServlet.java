package jetty.web.server.servlets;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.gradle.internal.impldep.com.google.common.io.Resources;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HelperServlet extends DefaultServlet {
    @Override
    public void service(ServletRequest request,
                        ServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            final URL mainPage = MyServlet.class.getResource("/static/pages/help");
            if (mainPage != null){
                final String content = Resources.toString(mainPage, StandardCharsets.UTF_8);
                outputStream.write(content.getBytes());
            }
            outputStream.flush();
        }
    }

    @Override
    public String getServletInfo() {
        return "Helper Servlet";
    }

    @Override
    public void destroy() {
    }
}
