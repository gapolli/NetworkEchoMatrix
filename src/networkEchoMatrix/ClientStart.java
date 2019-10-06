package networkEchoMatrix;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ClientStart {
	private static final String module = "Client";
	private static final Random randGen = new Random();
	private static boolean isRunning = true;

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
			InputStream inputStream = clientSocket.getInputStream();

			while (isRunning) {
				byte[] inBuffer = new byte[Info.maxPackageSise];
				byte[] outBuffer = msgFactory();
				System.out.println(module + " sending " + (++msgSent) + ": " + (new String(outBuffer)));

				outputStream.write(outBuffer, 0, outBuffer.length);
				inputStream.read(inBuffer);

				System.out.println(module + " received " + (++msgRcvd) + ": " + (new String(inBuffer)));
				System.out.println();

				Thread.sleep(Info.loopDelay);
			}
			clientSocket.close();
		} catch (IOException | InterruptedException exception) {
			System.out.println("Exception launched: " + exception.getMessage());
			System.exit(1);
		}

		System.out.println();
		System.out.println(Info.getUniformTitle());
		System.out.println(module + " stopped.");
	}

	private static byte[] msgFactory() {
		byte[] buffer = null;

		if (randGen.nextInt(20) == 0) {
			buffer = Info.shutDownCmd.getBytes();
			isRunning = false;
		} else {
			buffer = fillArray();
		}
		return (buffer);
	}

	private static byte[] fillArray() {
		byte[] buffer;
		buffer = new byte[Info.maxPackageSise];
		for (int count = 0; count < buffer.length; count++) {
			buffer[count] = (byte) ('A' + randGen.nextInt(26));
		}
		return buffer;
	}
}
