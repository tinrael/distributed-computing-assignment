package ua.knu.csc;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {
    private Socket socket;
    private ServerSocket serverSocket;

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            socket = serverSocket.accept();

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            while (processQuery());
        }
    }

    private boolean processQuery() {
        try {
            String query = bufferedReader.readLine();
            if (query == null) {
                return false;
            }

            String response = "123" + query + "123";

            printWriter.println(response);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
