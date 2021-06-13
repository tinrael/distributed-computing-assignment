package ua.knu.csc;

import ua.knu.csc.core.Manager;

import ua.knu.csc.entity.Actor;
import ua.knu.csc.entity.Operation;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.SQLException;

class Handler extends Thread {
    private final Socket socket;

    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    private final Manager manager;

    private final int STATUS_CODE_SUCCESS = 0;

    public Handler(Socket socket) throws IOException, SQLException {
        this.socket = socket;

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        String url = "jdbc:postgresql://localhost/films";
        String user = "postgres";
        String password = "postgres";

        manager = new Manager(url, user, password);
    }

    private void processQuery(Operation operation) throws IOException, ClassNotFoundException {
        switch (operation) {
            case ADD_ACTOR -> {
                Actor actor = (Actor) objectInputStream.readObject();
                try {
                    manager.addActor(actor);
                    objectOutputStream.writeInt(STATUS_CODE_SUCCESS);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                    objectOutputStream.writeInt(-1);
                }
            }
            case UPDATE_ACTOR -> {
                Actor actor = (Actor) objectInputStream.readObject();
                try {
                    manager.updateActor(actor);
                    objectOutputStream.writeInt(STATUS_CODE_SUCCESS);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                    objectOutputStream.writeInt(-1);
                }
            }
            case DELETE_ACTOR -> {
                int actorId = objectInputStream.readInt();
                try {
                    manager.deleteActor(actorId);
                    objectOutputStream.writeInt(STATUS_CODE_SUCCESS);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                    objectOutputStream.writeInt(-1);
                }
            }
            case GET_ACTOR -> {
                int actorId = objectInputStream.readInt();
                try {
                    Actor actor = manager.getActor(actorId);
                    objectOutputStream.writeInt(STATUS_CODE_SUCCESS);
                    objectOutputStream.writeObject(actor);
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                    objectOutputStream.writeInt(-1);
                }
            }
            default -> objectOutputStream.writeInt(-1);
        }
        objectOutputStream.flush();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                int code = objectInputStream.readInt();
                Operation operation = Operation.getOperation(code);
                processQuery(operation);
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
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
