import java.net.*;
import java.io.*;

public class ListeningDaemon{

    private static class Server extends Thread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                ServerSocket server = new ServerSocket(3001);
                Socket sock = server.accept();
                String message = "Welcome to the server! Meow!";
                PrintWriter writer = new PrintWriter( sock.getOutputStream(), true);
                writer.println(message);
                sock.close();
            }
            catch(IOException ioe)
            {}
        }
    }

    private static class Client extends Thread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                Socket sock = new Socket("127.0.0.1", 3001);
                BufferedReader reader = new BufferedReader( new InputStreamReader( sock.getInputStream()));
                String line = reader.readLine();
                System.out.println(line);
                sock.close();
            }
            catch(IOException ioe)
            {}
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        Server server = new Server();
        server.start();
        Client client = new Client();
        client.start();
        server.join();
        client.join();
    }
}