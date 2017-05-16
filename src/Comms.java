import java.io.*;
import java.net.*;

public class Comms extends Thread {
    private static final int businessPortNumber = 1111;
    private int portNumber;             // Is businessPortNumber if this is the business application
    private ServerSocket serverSocket;
    private Socket socket;
    private int receiverPortNumber;     // Is businessPortNumber if this is a client application
    // Is last client application port number if this is the business application

    public Comms(boolean businessApplication) {
        try {
            if (businessApplication) {
                portNumber = businessPortNumber;
                serverSocket = new ServerSocket(portNumber);
                serverSocket.setSoTimeout(10000);
            } else {
                serverSocket = new ServerSocket(0);     // Let the server find a port number for the client application
                portNumber = serverSocket.getLocalPort();    // And store it in this variable
                serverSocket.setSoTimeout(10000);
                receiverPortNumber = businessPortNumber;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            receiveMessage();
            /* TODO: Maybe think about a way to get the return value to the object that instantiated this class
            Maybe pass "this" as a parameter for the constructor of this class. The boolean could be replaced with an if using instanceof
            Both applications could have a method which would be called from here to give them the message. */
        }
    }

    public void sendMessage(Object msgObject) {
        try {
            socket = new Socket("localhost", receiverPortNumber);

            // Serialize Message
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new Message(msgObject, portNumber));     // Give portNumber so receiver will know who to respond to

            // Clean up
            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receiveMessage() {
        try {
            socket = serverSocket.accept();       // This method waits until a client connects to the server on the given port or there is a timeout

            // Deserialize Message
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) objectInputStream.readObject();

            // Reset receiver to the sender of this message
            receiverPortNumber = msg.getSender();

            // Clean up
            objectInputStream.close();

            return msg.getContent();

        } catch (SocketTimeoutException e1) {
            // Do nothing as it will loop through this method again.
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
        }

        return null;
    }
}
