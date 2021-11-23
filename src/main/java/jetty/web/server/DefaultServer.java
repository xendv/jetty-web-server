package jetty.web.server;

import jetty.web.server.entities.ContentGenerator;
import jetty.web.server.handlers.SecurityHandlerBuilder;
import jetty.web.server.servlets.ContentServlet;
import jetty.web.server.servlets.HelperServlet;
import jetty.web.server.servlets.MyServlet;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.io.IOException;
import java.net.URL;

@SuppressWarnings({"NotNullNullableValidation", "FieldCanBeLocal"})
public class DefaultServer {
    private final Server server = new Server();
    private static final int port = 3466;

    public Server build() {
        return build(port);
    }

    public Server build(int port) {
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{serverConnector});
        setUp();
        return server;
    }

    public void setUp(){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        try {
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
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
