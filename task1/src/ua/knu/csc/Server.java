package ua.knu.csc;

import ua.knu.csc.core.Manager;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.SQLException;

class Handler extends Thread {
    private final Socket socket;

    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;

    private final Manager manager;

    public Handler(Socket socket) throws IOException, SQLException {
        this.socket = socket;

        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);

        String url = "jdbc:postgresql://localhost/films";
        String user = "postgres";
        String password = "postgres";

        manager = new Manager(url, user, password);
    }

    private String processQuery(String query) {
        return "";
    }

    @Override
    public void run() {
        try {
            while (true) {
                String query = bufferedReader.readLine();
                if (query == null) {
                    break;
                }

                String response = processQuery(query);

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
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            try {
                Handler handler = new Handler(socket);
                handler.start();
            } catch (IOException | SQLException e) {
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
