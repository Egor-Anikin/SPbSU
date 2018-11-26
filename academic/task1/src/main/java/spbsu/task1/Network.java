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
    //private BufferedReader input;
    //private PrintWriter output;
    private final int PORT = 35713;
    private static boolean server;
    private static String ip;

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
                Socket server = new Socket(ip, PORT);

                receive = receiveData(server);
                sendData(server,send);

                server.close();
            } else {
                ServerSocket server = new ServerSocket(PORT);
                Socket client = server.accept();

                sendData(client, send);
                receive = receiveData(client);

                client.close();
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

    private void sendData(Socket socket, int send) throws IOException {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        output.write(send);
        output.flush();
    }

    private int receiveData(Socket socket) throws IOException {
        BufferedReader  input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return input.read();
    }

    private void serverDialog(Scanner in) throws IOException {
        ip = getCurrentIp();

        System.out.println("\nYour ip: " + ip);
        System.out.println("Waiting for a client...");

        ServerSocket server = new ServerSocket(PORT);
        Socket client = server.accept();
        client.close();

        //input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

        System.out.println("Connected");
    }

    private void clientDialog(Scanner in) throws IOException {
        System.out.print("Enter server ip: 192.168.");
        ip = "192.168." + in.nextLine();

        Socket server = new Socket(ip, PORT);
        server.close();

        //input = new BufferedReader(new InputStreamReader(server.getInputStream()));
        //output = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);

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





