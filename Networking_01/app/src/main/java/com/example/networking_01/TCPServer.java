package com.example.networking_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("Đang chờ kết nối...");
        Socket socket = serverSocket.accept();
        System.out.println("Đã kết nối!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        String clientMessage;
        while ((clientMessage = in.readLine()) != null) {
            System.out.println("Client: " + clientMessage);
            System.out.print("Server: ");
            String serverResponse = consoleInput.readLine();
            out.println(serverResponse);
        }

        socket.close();
        serverSocket.close();
    }
}
