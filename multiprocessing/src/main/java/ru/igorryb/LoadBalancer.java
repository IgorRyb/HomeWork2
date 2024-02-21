package ru.igorryb;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LoadBalancer {

    private static List<Node> nodes = Collections.synchronizedList(new ArrayList<>());

    private static final int LISTENING_PORT = 8989;
    private static final int REQUESTING_PORT = 9091;

    public static void main(String[] args) {
        nodeListener();
        responseProcess();
    }

    public static void nodeListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    sleep();
                    try (ServerSocket serverSocket = new ServerSocket(LISTENING_PORT)) {
                        Socket socket = serverSocket.accept();
                        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                            int raisedNode = Integer.parseInt(bufferedReader.readLine());
                            Node regitsteredNode = registerNode(new Node(raisedNode, socket.getInetAddress().getHostAddress()));
                            printWriter.println("Node registered: " + regitsteredNode);
                        }
                    } catch (IOException e) {}
                }
            }
        }).start();
    }

    public static void responseProcess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try (ServerSocket serverSocket = new ServerSocket(REQUESTING_PORT)) {
                        Socket socket = serverSocket.accept();
                        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                            String msg = bufferedReader.readLine();
                            if (nodes.size() != 0) {
                                String result = sendMessage(msg);
                                printWriter.println(result);
                            } else {
                                throw new RuntimeException("Nodes is empty!");
                            }
                        }
                    } catch (IOException e) {}
                }
            }
        }).start();
    }

    private static String sendMessage(String msg) {
        int randomNumber = getRandomNumber();
        Node node = nodes.get(randomNumber);
        String nodeMessage = null;
        try (Socket socket = new Socket(node.getHost(), node.getPort());
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            printWriter.println(msg);
            nodeMessage = bufferedReader.readLine();
        } catch (IOException e) {}
        return nodeMessage;
    }

    private static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(nodes.size());
    }

    private static Node registerNode(Node node) {
        nodes.add(node);
        return node;
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

