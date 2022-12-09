package main;
import socketClient.ClientSocket;
public class Main {
    
    public static void main(String[] args) {
        
        try {
           new ClientSocket("localhost",5555);
        } catch (Exception e) {
           System.out.println(e);
        }
    }
}
