import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Comms {
    private static final int PORTNO = 1111;
    private InetSocketAddress socketAddress;
    private ServerSocket serverSocket;
    private Socket sock;

    public Comms(String hostName) {
        socketAddress = new InetSocketAddress(hostName, PORTNO);
    }

    public void sendMessage(Message msg) {
        try {
            sock = new Socket(socketAddress.getAddress(), PORTNO);
            PrintStream printStream = new PrintStream(sock.getOutputStream());
            printStream.println(msg.getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() {
        try {
            serverSocket = new ServerSocket(PORTNO);
            sock = serverSocket.accept();

            InputStreamReader inputStreamReader = new InputStreamReader(sock.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            return bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
