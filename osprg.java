import java.io.*;
import java.net.*;

public class osprg
{
    private static boolean HALT=false;

    static class MessageServer
    {
        private ServerSocket socket;
        public static int SERVER_PORT=3000;

        public MessageServer()
        {
        }

        public void start()
        {
            try
            {
                ServerSocket socket = new ServerSocket(SERVER_PORT);
                int count=0;
                String messages[] =
                {
                    "Hello, I'm the server!",
                    "Are you surprised?",
                    "Do you think that's ridiculous?",
                    "Oh damn you client!",
                    "Stupid!",
                    "Bye STUPID!"
                };

                while(!HALT)
                {
                    Socket connection = socket.accept();
                    PrintWriter writer = new PrintWriter(connection.getOutputStream(),true);
                    writer.println(messages[count]);
                    count=(count+1)%messages.length;
                    connection.close();
                }
                socket.close();
            }
            catch(IOException ioe)
            {
                System.err.println("Server Exception: "+ioe.toString());
            }
            catch(Exception exc)
            {
            }
            finally
            {
                try {
                socket.close();} catch(Exception exc){}
                System.out.println("Server: Bye");
            }
        }
    }

    static class MessageClient
    {
        private Socket socket;
        
        public MessageClient()
        {
        }

        public void start()
        {
            System.out.println("The client has started: ");
            String userinput="y";
            try
            {
                while(userinput.toLowerCase().equals("y"))
                {
                    System.out.println("Connecting to the Server: ");
                    socket = new Socket("127.0.0.1",MessageServer.SERVER_PORT);
                    InputStream in = socket.getInputStream();
                    BufferedReader rd = new BufferedReader( new InputStreamReader( in));
                    String line=null;
                    System.out.println("Server message: ");
                    while(!((line=rd.readLine())==null))
                        System.out.println(line);
                    socket.close();
                    System.out.print("Do you wish to continue (y/N) ?: ");
                    userinput = (new java.util.Scanner(System.in)).nextLine();
                }
            }
            catch(IOException ioe)
            {
                System.err.println("Client Exception: "+ioe.toString());
            }
            catch(Exception exc)
            {
            }
            finally
            {
                System.out.println("Client: Bye");
            }
        }
    }

    public static void main( String[] args)
    {
        Thread server_thread = new Thread()
        {
            @Override
            public void run()
            {
                MessageServer server = new MessageServer();
                server.start();
            }
        };

        Thread client_thread = new Thread()
        {
            @Override
            public void run()
            {
                MessageClient client = new MessageClient();
                client.start();
            }
        };

        server_thread.start();
        client_thread.start();
        try {
            client_thread.join();
            HALT=true;
            server_thread.join();
            
        } catch (InterruptedException e) {
            System.err.println("Main Interrupted");
        }
    }
}