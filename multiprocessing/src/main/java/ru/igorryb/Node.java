package ru.igorryb;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Node extends Thread {

    private int port;
    private LoadBalancer loadBalancer;

    public Node(int port, LoadBalancer loadBalancer) {
        this.port = port;
        this.loadBalancer = loadBalancer;
        loadBalancer.registerNode(this);
        start();
    }

    @Override
    public void run() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket socket = serverSocket.accept();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                printWriter.println("check");
                printWriter.flush();
                String msg = bufferedReader.readLine();
                printWriter.println(port);
            } catch (IOException e) {}
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
