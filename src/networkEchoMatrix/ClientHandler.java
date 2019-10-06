package networkEchoMatrix;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ClientHandler extends Thread {
	private final MultitaskServer father;
	private final Socket clientSocket;
	private final int threadId;

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
			inputStream = clientSocket.getInputStream();
			OutputStream outputStream = clientSocket.getOutputStream();

			System.out.println("Handler " + threadId + " starting.");

			int msgSent = 0;
			int msgRcvd = 0;

			while (isRunning) {
				String localTag = "Client Handler #" + threadId;
				byte[] inBuffer = new byte[Info.maxPackageSise];
				byte[] outBuffer;
				int bytesRead = inputStream.read(inBuffer, 0, inBuffer.length);

				if (bytesRead >= 0) {
					String plainText = (new String(inBuffer, 0, bytesRead));
					String reversedText = reverseString(plainText);

					System.out.println(
							localTag + " read .... " + (++msgRcvd) + ": " + plainText + " (" + bytesRead + " bytes)");

					outBuffer = reversedText.getBytes();
					outputStream.write(outBuffer);

					System.out.println(localTag + " writing . " + (++msgSent) + ": " + reversedText + " (" + bytesRead
							+ " bytes)");
					System.out.println();

					isRunning = !plainText.startsWith(Info.shutDownCmd);
				}
			}

			outputStream.close();
			inputStream.close();
			clientSocket.close();

			father.threadClosed(this.threadId);
		} catch (IOException exceptionLaunched) {
			// TODO Auto-generated catch block
			exceptionLaunched.printStackTrace();
		}
	}

	private static String reverseString(String plainText) {
		return ((new StringBuffer(plainText)).reverse().toString());
	}

}
