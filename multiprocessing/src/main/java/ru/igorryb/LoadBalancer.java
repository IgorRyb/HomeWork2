package ru.igorryb;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class LoadBalancer {

    private static List<Node> nodes = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        int listeningPort = Integer.parseInt(getPropertyFromResource().getProperty("port.response"));
        int requestingPort = Integer.parseInt(getPropertyFromResource().getProperty("port.request"));
        nodeListener(listeningPort);
        responseProcess(requestingPort);
    }

    public static void nodeListener(int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    sleep();
                    try (ServerSocket serverSocket = new ServerSocket(port)) {
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

    public static void responseProcess(int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try (ServerSocket serverSocket = new ServerSocket(port)) {
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

    private static Properties getPropertyFromResource() {
        try {
            FileInputStream is = new FileInputStream("src/main/resources/application.properties");
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

