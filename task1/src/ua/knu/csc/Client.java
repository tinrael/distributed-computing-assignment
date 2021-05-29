package ua.knu.csc;

import java.net.Socket;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private Socket socket;

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void initialize() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void sendQuery(String query) throws IOException {
        printWriter.println(query);
        String response = bufferedReader.readLine();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 12345);

        try {
            client.initialize();
            client.sendQuery("Hello!");
            client.sendQuery("Goodbye!");
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
