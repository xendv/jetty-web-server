package jetty.web.server;

import jetty.web.server.handlers.SecurityHandlerBuilder;
import jetty.web.server.servlets.ContentServlet;
import jetty.web.server.servlets.HelperServlet;
import jetty.web.server.servlets.MyServlet;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URL;

public class App {

    public static void main(String[] args) throws Exception {
        final Server server = new DefaultServer().build();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        final URL resource = LoginService.class.getResource("/static");
        context.setBaseResource(Resource.newResource(resource.toExternalForm()));

        context.addServlet(new ServletHolder("DefaultContentServlet", new MyServlet()), "/");
        context.addServlet(new ServletHolder("HelperServlet", new HelperServlet()), "/help");
        context.addServlet(
                new ServletHolder("ContentServlet",
                        new ContentServlet(new ContentGenerator())
                ),
                "/products"
        );

        server.setHandler(context);
        final String hashConfig = App.class.getResource("/hash_config").toExternalForm();
        final HashLoginService hashLoginService = new HashLoginService("login", hashConfig);
        final ConstraintSecurityHandler securityHandler = new SecurityHandlerBuilder().build(hashLoginService);

        server.addBean(hashLoginService);
        securityHandler.setHandler(context);
        server.setHandler(securityHandler);

        server.start();
    }
}
