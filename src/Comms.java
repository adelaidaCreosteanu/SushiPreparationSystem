import java.io.*;
import java.net.*;

public class Comms extends Thread {
    private static final int businessPortNumber = 1111;
    private int portNumber;             // Is businessPortNumber if this is the business application
    private ServerSocket serverSocket;
    private Socket socket;
    private int receiverPortNumber;     // Is businessPortNumber if this is a client application
    // Is last client application port number if this is the business application

    public Comms(Application application) {
        try {
            if (application instanceof ClientApplication) {     // Condition has to be tested
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
            receiveMessage();
            /* TODO: Create a method in Application (which is implemented by both applications) to be called from here when a message is received */
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

    public Object receiveMessage() {
        try {
            socket = serverSocket.accept();       // This method waits until a client connects to the server on the given port

            // Deserialize Message
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) objectInputStream.readObject();

            // Reset receiver to the sender of this message
            receiverPortNumber = msg.getSender();

            // Clean up
            objectInputStream.close();

            // TODO: use msg.getRequest()

            return msg.getContent();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
