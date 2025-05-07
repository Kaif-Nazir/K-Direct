package com.clientmessenger.clientmessenger.Client;

import com.clientmessenger.clientmessenger.clientController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {

        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Creating Server");
            closeEverything(socket,bufferedWriter , bufferedReader);
        }
    }

    public void sendMessageToServer(String messageToServer){

        try{
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Sending message To Client");
            closeEverything(socket,bufferedWriter , bufferedReader);

        }

    }
    public void recieveMessageFromServer(VBox vBox){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        clientController.addLabel(messageFromServer, vBox);
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
