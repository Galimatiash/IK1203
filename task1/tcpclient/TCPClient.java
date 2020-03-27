package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        if (ToServer == null){
            return(askServer(hostname, port));
        }

        Socket clientSocket = new Socket(hostname, port);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
        StringBuilder sb = new StringBuilder();
        String sString;
        int maxSizeBuffer = 0;
        clientSocket.setSoTimeout(1000);

        toServer.writeBytes(ToServer + '\n');
        //System.out.println("lol1");
        
        try{
            while(((sString = fromServer.readLine()) != null) && maxSizeBuffer < 8192){
            sb.append(sString + "\n");
            maxSizeBuffer++;
            }
        } catch (Exception SocketTimeoutException) {
            
        }
        clientSocket.close();
        return sb.toString();
    }

    public static String askServer(String hostname, int port) throws  IOException {

        Socket clientSocket = new Socket(hostname, port);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String sString;
        int maxSizeBuffer = 0;
        clientSocket.setSoTimeout(5000);
        //System.out.println("hej2");

        try{
            while(((sString = fromServer.readLine()) != null) && maxSizeBuffer < 8192){
            sb.append(sString + "\n");
            maxSizeBuffer++;
            }
        } catch (Exception SocketTimeoutException) {
            
        }

        clientSocket.close();
        return sb.toString();
    }
}

