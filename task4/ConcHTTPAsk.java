import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class ConcHTTPAsk {

    public static void main( String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            Socket myClientSocket = serverSocket.accept();
            Runnable r = new Thread(new myRunnable(myClientSocket));
            new Thread(r).start();
        }
    }
}
