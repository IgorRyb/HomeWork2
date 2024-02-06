package ru.igorryb;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadBalancer extends Thread {

    private List<Node> nodes;
    private int index;

    public LoadBalancer() {
        index = 0;
        this.nodes = Collections.synchronizedList(new ArrayList<>());
        start();
    }

    @Override
    public void run() {
        while (true) {
            sleep();
            Node node = getNextNode();
            System.out.println("_____");
            try (Socket socket = new Socket("localhost", node.getPort());
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                String message = bufferedReader.readLine();
                if (message.equals("check")) {
                    registerNode(new Node(socket.getPort(), this));
                }
                String msg = "processed";
                printWriter.println(msg + "\n");
                int port = Integer.parseInt(bufferedReader.readLine());
                System.out.println("Node with port: " + port + " registered");
            } catch (IOException e) {}
        }
    }


    public void registerNode(Node node) {
        nodes.add(node);
    }

    public Node getNextNode() {
        Node node = nodes.get(index);
        if (index != nodes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
        return node;
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

