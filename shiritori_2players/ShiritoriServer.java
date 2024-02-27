package shiritori_2players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ShiritoriServer {

    public static void main(String[] args) {
    	
    	ShiritoriGame.init();
    	
        try (ServerSocket serverSocket = new ServerSocket(49152)) {
        	
            System.out.println("Waiting for a client to start...");
            System.out.println("（相手を待っています…）");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected!");

            BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            ShiritoriGame.play(serverOutput, clientInput);

            clientSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
