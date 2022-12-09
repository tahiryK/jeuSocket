package main;

import socketServer.Server;

public class Main {

    public static void main(String[] args) {

        try {
            new Server(5555);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}