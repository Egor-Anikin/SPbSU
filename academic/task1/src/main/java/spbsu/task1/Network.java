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
    private BufferedReader input;
    private PrintWriter output;
    private boolean server;

    /** Create Network. */
    public Network() {
        Scanner in = new Scanner(System.in);

        System.out.print("You are server or not: ");
        final String respond = in.nextLine();

        try {
            if ((respond.charAt(0) == 'Y') || (respond.charAt(0) == 'y')) {
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

    /** Synchronization data arrays. */
    public int synchronization(int send) {
        int receive = 0;

        try {
            if (isServer()) {
                receive = receiveData();
                sendData(send);
            } else {
                sendData(send);
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

    private void sendData(int send) throws IOException {
        output.write(send);
        output.flush();
    }

    private int receiveData() throws IOException {
        return input.read();
    }

    private void serverDialog(Scanner in) throws IOException {
        final int port = 35713;

        System.out.println("\nYour ip: " + getCurrentIp());
        System.out.println("Waiting for a client...");

        ServerSocket server = new ServerSocket(port);
        Socket client = server.accept();

        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

        System.out.println("Connected");
    }

    private void clientDialog(Scanner in) throws IOException {
        System.out.print("Enter server ip: ");
        String ip = in.nextLine();

        final int port = 35713;

        Socket server = new Socket(ip, port);

        input = new BufferedReader(new InputStreamReader(server.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);

        System.out.println("Connected");
    }

    private String getCurrentIp() throws IOException {
        String ip = "";

        Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();

        while (network.hasMoreElements()) {
            Enumeration<InetAddress> addresses = network.nextElement().getInetAddresses();

            while (addresses.hasMoreElements()) {
                String temp = addresses.nextElement().getHostAddress();

                if (temp.contains("192.168.")) {
                    ip = temp;
                }
            }
        }

        return ip.isEmpty() ? InetAddress.getLocalHost().getHostAddress() : ip;
    }
}





