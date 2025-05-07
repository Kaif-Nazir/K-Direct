package com.clientserver.clientserver.Server;

import com.clientserver.clientserver.severController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {

        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Creating Server");
            closeEverything(socket,bufferedWriter , bufferedReader);
        }
    }
    public void sendMessageToClient(String messageToClient){

        try{
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Sending message To Client");
            closeEverything(socket,bufferedWriter , bufferedReader);

        }

    }
    public void recieveMessageFromClient(VBox vBox){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(socket.isConnected()){
                        try {
                            String messageFromClient = bufferedReader.readLine();
                            severController.addLabel(messageFromClient, vBox);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error Receiving Message ");
                            closeEverything(socket , bufferedWriter , bufferedReader);
                            break;
                        }
                    }
                }
            }).start();

    }
    public void closeEverything(Socket socket , BufferedWriter bufferedWriter , BufferedReader bufferReader){
        try{
            if(bufferReader != null){
                bufferReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
