package shiritori_2players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ShiritoriClient {

    public static void main(String[] args) {
    	
    	ShiritoriGame.init();
    	
        try (Socket socket = new Socket("localhost", 49152)) {
        	
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String message = serverInput.readLine();
                System.out.println("Server: " + message);

                if (message.contains("負け")) {
                	System.out.println("\n-------ゲーム終了-------");
                    break;
                }
                String userWord = scanner.nextLine();
                clientOutput.println(userWord);
            }
            scanner.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
