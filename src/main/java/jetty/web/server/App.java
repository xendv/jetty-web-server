package jetty.web.server;

import jetty.web.server.initializers.DBFlywayInitializer;
import org.eclipse.jetty.server.Server;

public final class App {

    public static void main(String[] args) throws Exception {
        DBFlywayInitializer.initDBFlyway();
        final Server server = new DefaultServer().build();
        server.start();
    }
}
