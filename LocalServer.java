import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class LocalServer {

    public static void main(String[] args) throws IOException {

        // Create HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Set a default endpoint ("/")
        server.createContext("/", new MyHandler());

        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("Server running at http://localhost:8080/");
    }

    // Handles web requests
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = """
                    <!doctype html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Hello Universe</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                                background: #1e1e1e;
                                color: #ffffff;
                            }
                            h1 { font-size: 3rem; }
                        </style>
                    </head>
                    <body>
                        <h1>Hello Universe</h1>
                        <script>
                            console.log("Hello Universe");
                        </script>
                    </body>
                    </html>
                    """;

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
