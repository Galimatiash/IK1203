import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk {
    public static void main( String[] args) throws IOException {

    int port = Integer.parseInt(args[0]);
    ServerSocket serverSocket = new ServerSocket(port);
    String chej = "123";

    while(true){
        StringBuilder sb = new StringBuilder();
        String hostName = null;
        String serverAnswer = null;
        String clientString = null;
        int portNum = -1;

        Socket clientSocket = serverSocket.accept();
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        sb.append(inputStream.readLine());
        String[] sbSplit = sb.toString().split("[:/?&= ]");

        if(sbSplit[2].equals("ask")){ //if we do a ASK request

            for(int i = 0; i < sbSplit.length; i++){ //browse GET query for variables
               if(sbSplit[i].equals("hostname")){
                hostName = sbSplit[i+1];
                } else if (sbSplit[i].equals("port")){
                    portNum = Integer.parseInt(sbSplit[i+1]);
                } else if (sbSplit[i].equals("string")){
                    clientString = sbSplit[i+1];
                }
            }

            if(!(hostName.equals("") && !(portNum == -1))){ // if we have VALID ARGUMENTS
                try{
                    serverAnswer = TCPClient.askServer(hostName, portNum, clientString); //request TCPClient answer
                    outputStream.writeBytes("HTTP/1.1 200 OK\r\n\r\n" + serverAnswer);   //good answer
                 } catch (Exception E){
                    outputStream.writeBytes("HTTP/1.1 404 Not Found\r\n\r\n");           //null answer
                }
            } else {
                outputStream.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n");             //INVALID arguments
            }
            
            // if(!(hostName.equals("")) && !(portNum == -1)){ //if we have VALID ARGUMENTS
            //     try{
            //         serverAnswer = TCPClient.askServer(hostName, portNum, clientString); //request response from TCPClient
            //     } catch (Exception E){
            //         outputStream.writeBytes("HTTP/1.1 404 Not Found\r\n\r\n");
            //     }
            //         outputStream.writeBytes("HTTP/1.1 200 OK\r\n\r\n" + serverAnswer);
            //     } else {
            //         outputStream.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n");
            //     }

        }  else { //if we do NOT do a ASK request, i.e. BAD REQUEST;
                    outputStream.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n");
        }
        // } catch (Exception E){
        //     outputStream.writeBytes("HTTP/1.1 404 Not Found\r\n\r\n");
        // }
    
        //outputStream.writeBytes("HTTP/1.1 200 OK\r\n\r\n" + "Hostname: " + hostName + ", Port: " + portNum);
        // outputStream.writeBytes(sb.toString());

        String ez = "GET /ask hostname time.nist.gov port 13 HTTP/1.1";
        String xd = "http://localhost/ask?hostname=time.nist.gov&port=13&string=HEJ";
        String xD = "http localhost ask hostname time.nist.gov port 13 string HEJ";

        clientSocket.close();
    }

    }
}

