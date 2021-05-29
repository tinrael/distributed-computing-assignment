package ua.knu.csc;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Handler extends Thread {
    private Socket socket;

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Handler(Socket socket) throws IOException {
        this.socket = socket;

        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String query = bufferedReader.readLine();
                if (query == null) {
                    break;
                }

                String response = "123" + query + "123";

                printWriter.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            try {
                Handler handler = new Handler(socket);
                handler.start();
            } catch (IOException e) {
                e.printStackTrace();
                socket.close();
            }
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(12345);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
