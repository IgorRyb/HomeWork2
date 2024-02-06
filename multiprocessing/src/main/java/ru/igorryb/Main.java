package ru.igorryb;

public class Main {

    public static void main(String[] args) {
        LoadBalancer loadBalancer = new LoadBalancer();
        Node nodeOne = new Node(8081, loadBalancer);
        Node nodeTwo = new Node(8989, loadBalancer);
        Node nodeThree = new Node(9091, loadBalancer);
    }
}
