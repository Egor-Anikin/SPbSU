package spbsu.task1;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Scanner;

/** Network class. */
public class Network {
    private final int PORT = 35671;
    private final int CHECK = 108481;
    private boolean server;
    private BufferedReader input;
    private PrintWriter output;
    private Socket socket;

    /** Create Network. */
    public Network() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter:\n 1 - if you are a server\n 0 - if not ");
        final int respond = in.nextLine().charAt(0);

        try {
            if (respond == '1') {
                server = true;
                serverDialog(in);
            } else {
                server = false;
                clientDialog(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Message information. */
    public int message(int info) {
        int receive = 0;

        try {
            if (isServer()) {
                receive = receiveData();
                sendData(info);
            } else {
                sendData(info);
                receive = receiveData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return receive;
    }

    /** Check current computer type. */
    public boolean isServer() {
        return server;
    }

    /** Close socket. */
    public void closeSocket() throws IOException {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendData(int send) throws IOException {
        output.write(send);
        output.flush();
    }

    private int receiveData() throws IOException {
        return input.read();
    }

    private void serverDialog(Scanner in) throws IOException {

        System.out.println("\nYour ip: " + getIp());
        System.out.println("Waiting for a client...");

        ServerSocket server = new ServerSocket(PORT);
        socket = server.accept();

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        output.write(CHECK);
        output.flush();
        if (input.read() == CHECK)
            System.out.println("Connected");
    }

    private void clientDialog(Scanner in) throws IOException {
        System.out.print("Enter server ip: ");
        String ip = in.nextLine();

        socket = new Socket(ip, PORT);

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        if (input.read() == CHECK)
            System.out.println("Connected");
        output.write(CHECK);
        output.flush();
    }

    private String getIp() throws IOException {
        String ip = "";

        Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();

        while (network.hasMoreElements()) {
            Enumeration<InetAddress> addresses = network.nextElement().getInetAddresses();

            while (addresses.hasMoreElements()) {
                String temp = addresses.nextElement().getHostAddress();

                if (!temp.contains(":") && !temp.contains("127.0.0.1")) {
                    ip = temp;
                }
            }
        }

        return ip.isEmpty() ? InetAddress.getLocalHost().getHostAddress() : ip;
    }
}



