package networkEcho_MATRIX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class start the client end to send a matrix and receive it transposed
 * from the server.
 * 
 * @author André Franceschi de Angelis
 * @author Mateus Pim Santos
 * @author Gustavo Adrien Polli
 * @author Victor Gomes Sampaio
 * @author Max Lúcio Martins de Assis
 * @version 1.1
 * @since version 1.0
 */
public class ClientStart {
	private static final String module = "Client";
	@SuppressWarnings("unused")
	private static final Random randGen = new Random();
	private static boolean isRunning = true;

	private static Matrix matrix;

	/**
	 * The main client program.
	 * @param args permit the user to pass arguments in the execution of this program
	 */
	public static void main(String[] args) {
		System.out.println(Info.getUniformTitle());
		System.out.println(module + " running.");
		System.out.println();

		try (Socket clientSocket = new Socket("localhost", Info.listeningPort)) {
			int msgSent = 0;
			int msgRcvd = 0;
			System.out.println("Local TCP port " + clientSocket.getLocalPort());
			System.out.println("Sending bytes to TCP port " + clientSocket.getPort());
			System.out.println();

			OutputStream outputStream = clientSocket.getOutputStream();
			ObjectOutputStream objOStream = new ObjectOutputStream(outputStream);
		
			InputStream inputStream = clientSocket.getInputStream();
			ObjectInputStream objIStream = new ObjectInputStream(inputStream);

			while (isRunning) {

				matrix = getMatrix();
				System.out.println(module + " sending " + (++msgSent) + ": ");
				matrix.printMatrix();

				objOStream.writeObject((Object) matrix);
				objOStream.flush();

				matrix = (Matrix) objIStream.readObject();

				System.out.println(module + " received " + (++msgRcvd) + ": ");
				matrix.printMatrix();
				System.out.println();
				
				Thread.sleep(Info.loopDelay);
				
				if (msgSent == 1)
					isRunning = false;
			}
			
			objOStream.close();
			objIStream.close();
			clientSocket.close();
			
		} catch (IOException | InterruptedException exception) {
			System.out.println("Exception launched: " + exception.getMessage());
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println(Info.getUniformTitle());
		System.out.println(module + " stopped.");
	}
	
	/**
	 * Method to create a Matrix object
	 * @return Returns an object from Matrix Class
	 */
	private static Matrix getMatrix() {
		Matrix matrix = null;
		String choice = "R";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Do you want to insert, read or generate the values for the matrix?\n"
				+ "Press I for insert, F for File or R for random values: ");
		
		choice = sc.next();
		choice.toUpperCase();
		
		if ( choice.equals("F")) {
			sc.close();
			File file = new File("matrix.txt");
			
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
		}
				
		if ( choice.equals("F") || choice.equals("I") ) {
			int linecount = 0, rowcount = 0;
			try {
								
				System.out.print("Insert the number of lines: ");
				linecount = sc.nextInt();
				System.out.print("Insert the number of rows: ");
				rowcount = sc.nextInt();
				
				int m[][] = new int[linecount][rowcount];
				
				for (int l = 0; l < linecount; l++) {
					for (int c = 0; c < rowcount; c++) {
						System.out.printf("Insert the number for line[%d] and collum[%d]: ",l,c);
						m[l][c] = sc.nextInt();
						System.out.println("");
					}
					
				matrix = new Matrix(m);
				}
								
			}catch (InputMismatchException ex){
				System.out.println("Value inserted incorrect");
				matrix = new Matrix();
			}
		}else {
			matrix = new Matrix();
		}
		sc.close();
		return matrix;
	}

}
