package jetty.web.server;

import org.eclipse.jetty.server.Server;

public class App {

    public static void main(String[] args) throws Exception {
        final Server server = new DefaultServer().build();
        server.start();
    }
}
