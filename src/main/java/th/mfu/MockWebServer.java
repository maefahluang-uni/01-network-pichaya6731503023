package th.mfu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MockWebServer implements Runnable {


    private int port;


    public MockWebServer(int port) {
        this.port = port;
    }


    @Override
    public void run() {


        // TODO Create a server socket bound to specified port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
        // TODO Accept incoming client connections
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
        // TODO: Read the request from the client using BufferedReader
                    String line;
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        System.out.println("Request: " + line);
                    }
        // TODO: send a response to the client
                    String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + "<html><body>Hello, Web! on Port " + port + "</body></html>";
                    out.print(response);
                    out.flush();
        // TODO: Close the client socket
        } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            e.printStackTrace();
        }
    }
           




    public static void main(String[] args) {
        Thread server1 = new Thread(new MockWebServer(8080));
        server1.start();


        Thread server2 = new Thread(new MockWebServer(8081));
        server2.start();


        // type any key to stop the server
        // Wait for any key press to stop the mock web server
        System.out.println("Press any key to stop the server...");
        try {
            System.in.read();


            // Stop the mock web server
            server1.stop();
            server1.interrupt();
            server2.stop();
            server2.interrupt();
            System.out.println("Mock web server stopped.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


