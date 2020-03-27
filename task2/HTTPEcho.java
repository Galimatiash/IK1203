import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {

    int port = Integer.parseInt(args[0]);
    ServerSocket serverSocket = new ServerSocket(port);

    while(true){
        String clientString = "123";
        String header = "HTTP/1.1 200 OK\r\n\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        //System.out.println("Awaiting client");
        Socket clientSocket = serverSocket.accept();
        //System.out.println("Client connected");
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        while(!(clientString = inputStream.readLine()).equals("")){
            //clientString = inputStream.readLine();
            sb.append(clientString + "\n");
            //System.out.println(clientString);
        }
        //byte[] stringBytes = sb.toString().getBytes();
        //outputStream.write(stringBytes);
        outputStream.writeBytes(sb.toString());
        //outputStream.writeBytes(sb.toString());
        //outputStream.writeBytes("End of Stream\n");
        clientSocket.close();
    }




    }

}

