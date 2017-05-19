import java.io.*;
import java.net.*;

public class Comms extends Thread {
    private Application application;

    private static final int businessPortNumber = 1111;
    private int portNumber;             // Is businessPortNumber if this is the business application
    private ServerSocket serverSocket;
    private Socket socket;
    private int receiverPortNumber;     // Is businessPortNumber if this is a client application
    // Is last client application port number if this is the business application

    public Comms(Application application) {
        this.application = application;

        try {
            if (application instanceof ClientApplication) {
                serverSocket = new ServerSocket(0);     // Let the server find a port number for the client application
                portNumber = serverSocket.getLocalPort();    // And store it in this variable
                receiverPortNumber = businessPortNumber;
            } else {
                portNumber = businessPortNumber;
                serverSocket = new ServerSocket(portNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            listen();
        }
    }

    public void sendMessage(int request) {
        sendMessage(request, null);
    }

    public void sendMessage(int request, Object msgObject) {
        try {
            socket = new Socket("localhost", receiverPortNumber);

            // Serialize Message
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new Message(request, msgObject, portNumber));     // Give portNumber so receiver will know who to respond to

            // Clean up
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receiveMessage() {    // Returns only the content of the message. Assumes the caller of this method know the request/purpose of communication
        try {
            socket = serverSocket.accept();       // This method waits until a client connects to the server on the given port

            // Deserialize Message
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) objectInputStream.readObject();

            // Reset receiver to the sender of this message
            receiverPortNumber = msg.getSender();

            // Clean up
            objectInputStream.close();

            return msg.getContent();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void listen() {
        try {
            socket = serverSocket.accept();        // This method waits until a client connects to the server on the given port

            // Deserialize Message
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) objectInputStream.readObject();

            // Reset receiver to the sender of this message
            receiverPortNumber = msg.getSender();

            // Clean up
            objectInputStream.close();

            if (msg.getContent() == null) {
                application.receivedMessage(msg.getRequest());
            } else {
                application.receivedMessage(msg.getRequest(), msg.getContent());
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
