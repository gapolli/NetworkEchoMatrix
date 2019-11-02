package networkEcho_MATRIX;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
//import java.util.Scanner;

/**
 * This class intent to be the threads generator for the ClientStart
 * requisitions.
 * 
 * @author André Franceschi de Angelis
 * @author Mateus Pim Santos
 * @author Gustavo Adrien Polli
 * @author Victor Gomes Sampaio
 * @author Max Lúcio Martins de Assis
 * @version 1.1
 * @since 1.0
 */
class ClientHandler extends Thread {
	private final MultitaskServer father;
	private final Socket clientSocket;
	private final int threadId;

	private static Matrix matrix;

	/**
	 * ClientHandler class constructor.
	 * 
	 * @param father         the father server for the instance generated of this
	 *                       class.
	 * @param myClientSocket a local socket that permits the communication between
	 *                       client and server.
	 * @param myThreadId     the id of the thread that manipulates the instanced
	 *                       client.
	 */
	ClientHandler(MultitaskServer father, Socket myClientSocket, int myThreadId) {
		super();
		this.father = father;
		this.clientSocket = myClientSocket;
		this.threadId = myThreadId;
	}

	@Override
	public void run() {
		boolean isRunning = true;
		InputStream inputStream;
		try {
			OutputStream outputStream = clientSocket.getOutputStream();
			ObjectOutputStream objOStream = new ObjectOutputStream(outputStream);

			inputStream = clientSocket.getInputStream();
			ObjectInputStream objIStream = new ObjectInputStream(inputStream);

			System.out.println("Handler " + threadId + " starting.");

			int msgSent = 0;
			int msgRcvd = 0;

			System.out.println("Try");
			while (isRunning) {
				String localTag = "Client Handler #" + threadId;

				try {
					matrix = (Matrix) objIStream.readObject();
				} catch (ClassNotFoundException e) {
					// e.printStackTrace();
					isRunning = false;
				}

				System.out.println("" + matrix.toString());
				if (matrix != null) {

					System.out.println(localTag + " read .... " + (++msgRcvd) + ": ");
					matrix.printMatrix();

					matrix.transposeMatrix();

					try {
						objOStream.writeObject(matrix);
					} catch (IOException exceptionLaunched) {

					}

					System.out.println(localTag + " writing ... " + (++msgSent) + ": ");
					matrix.printMatrix();
					System.out.println();

					// isRunning = false;
				}
			}

			outputStream.close();
			inputStream.close();
			objIStream.close();
			objOStream.close();
			clientSocket.close();

			father.threadClosed(this.threadId);
		} catch (IOException exceptionLaunched) {
			exceptionLaunched.printStackTrace();
		}
	}
}
