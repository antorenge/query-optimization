package io.fortumo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * This is a server app!
 *
 */
public class App {

    public static final int PORT = 12000;

    private final String[] args;
    private Server server;

    public App(String[] args) {
        this.args = args;
    }

    public void start(boolean joinThread) throws Exception {
        System.out.println("Starting the server at port " + PORT);
        System.out.println("Access it from http://localhost:12000/");

        // Prepare the server.
        this.server = new Server(PORT);
        final ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SearchServlet.class, "/*");

        // Start things up!
        server.start();

        System.out.println("Server started, waiting for requests");

        // The use of server.join() the will make the current thread join and
        // wait until the server is done executing.
        if (joinThread) {
            server.join();
        }
    }

    public void stop() throws Exception {
        this.server.stop();
    }


    public static void main(String[] args) {
        final App app = new App(args);
        try {
            app.start(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
