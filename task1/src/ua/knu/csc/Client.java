package ua.knu.csc;

import ua.knu.csc.entity.Actor;
import ua.knu.csc.entity.Operation;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    private final Socket socket;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void initialize() throws IOException {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public int sendQuery(String query) throws IOException, ClassNotFoundException {
        String[] arguments = query.split("#");

        int code = Integer.parseInt(arguments[0]);

        objectOutputStream.writeInt(code);
        objectOutputStream.flush();

        Operation operation = Operation.getOperation(code);
        switch (operation) {
            case ADD_ACTOR, UPDATE_ACTOR -> {
                int actorId = Integer.parseInt(arguments[1]);

                String forename = arguments[2];
                String surname = arguments[3];

                Actor actor = new Actor();

                actor.setActorId(actorId);
                actor.setForename(forename);
                actor.setSurname(surname);

                objectOutputStream.writeObject(actor);
                objectOutputStream.flush();

                return objectInputStream.readInt();
            }
            case DELETE_ACTOR -> {
                int actorId = Integer.parseInt(arguments[1]);

                objectOutputStream.writeInt(actorId);
                objectOutputStream.flush();

                return objectInputStream.readInt();
            }
            case GET_ACTOR -> {
                int actorId = Integer.parseInt(arguments[1]);

                objectOutputStream.writeInt(actorId);
                objectOutputStream.flush();

                int status = objectInputStream.readInt();

                if (status >= 0) {
                    Actor actor = (Actor) objectInputStream.readObject();
                    System.out.println(actor);
                }

                return status;
            }
            default -> throw new IllegalArgumentException("Undefined operation.");
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 12345);

        try {
            client.initialize();

            System.out.println("<code>(#<argument>)*");

            System.out.println();

            System.out.println("Codes: ");
            System.out.println("\t" + Operation.ADD_ACTOR.getCode() + " - ADD_ACTOR");
            System.out.println("\t" + Operation.UPDATE_ACTOR.getCode() + " - UPDATE_ACTOR");
            System.out.println("\t" + Operation.DELETE_ACTOR.getCode() + " - DELETE_ACTOR");
            System.out.println("\t" + Operation.GET_ACTOR.getCode() + " - GET_ACTOR");

            System.out.println();

            System.out.println("Examples: ");
            System.out.println("\t0#24#Tom#Cruise");
            System.out.println("\t3#22");

            System.out.println();

            System.out.println("A non-negative status indicates that an operation executed successfully.");
            System.out.println("A negative status indicates that an error occurred while executing an operation.");

            System.out.println();

            System.out.println("To disconnect from the server enter 'disconnect'.");

            System.out.println();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter a query: ");
            String query = bufferedReader.readLine();

            while (!query.equals("disconnect")) {
                int status = client.sendQuery(query);
                System.out.println("Status: " + status);

                System.out.print("Enter a query: ");
                query = bufferedReader.readLine();
            }
        } catch (IOException | ClassNotFoundException exception) {
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
