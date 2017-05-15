import java.io.*;
import java.net.*;

public class Comms {
    private int portNumber = 0;
    private int businessPort = 1111;
    private ServerSocket serverSocket;
    private Socket sock;

    public void setUpBusinessCommunication() {
        portNumber = 1111;
    }

    public void sendMessage(Object msgObject) {
        try {
            sock = new Socket("localhost", businessPort);

            // Serialize Message
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
            objectOutputStream.writeObject(new Message(msgObject, portNumber));

            // Clean up
            objectOutputStream.flush();
            objectOutputStream.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receiveMessage() {
        try {
            serverSocket = new ServerSocket(portNumber);
            sock = serverSocket.accept();

            // Deserialize Message
            ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
            Message msg = (Message) objectInputStream.readObject();

            // Clean up
            objectInputStream.close();
            sock.close();
            serverSocket.close();

            return msg.getContent();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }

        return null;
    }
}
