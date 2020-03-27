import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

class myRunnable implements Runnable{

    Socket threadSocket;

    public myRunnable(Socket socketSocket){
        threadSocket = socketSocket;
    }

    public void run(){

        try{

            StringBuilder sb = new StringBuilder();
            String hostName = null;
            String serverAnswer = null;
            String clientString = null;
            int portNum = -1;

            DataOutputStream outputStream = new DataOutputStream(this.threadSocket.getOutputStream());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(this.threadSocket.getInputStream()));

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
                        outputStream.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n");         //INVALID arguments
                }
            }  else { //if we do NOT do a ASK request, i.e. BAD REQUEST;
                outputStream.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n");
            }

        this.threadSocket.close();
        } catch(Exception exceptionz){
            System.out.println(exceptionz);
            System.exit(1);
        }
    }
}