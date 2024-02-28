package ru.igorryb;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Node {

    private static int port;
    private static String host;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int responsePort = Integer.parseInt(getPropertyFromResource().getProperty("port.response"));
        int registeredPort = sendSignal(responsePort);
        handle(registeredPort);
    }

    public Node(int port, String host) {
        this.port = port;
        this.host = host;
    }

    private static int sendSignal(int portForSocket) {
        int port = scanner.nextInt();
        try (Socket socket = new Socket("localhost", portForSocket);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
            printWriter.println(port);
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

    private static void handle(int port) {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket socket = serverSocket.accept();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                String msg = bufferedReader.readLine();
                printWriter.println("ready for processing");
                if (!msg.equals(null)) {
                    printWriter.println(msg);
                }
            } catch (IOException e) {

            }
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

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Node.port = port;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Node.host = host;
    }
}
