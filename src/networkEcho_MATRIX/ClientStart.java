package networkEcho_MATRIX;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ClientStart
   {
   private static final String module    = "Client";
   private static final Random randGen   = new Random();
   private static boolean      isRunning = true;
   
   private static Matrix matrix;
   
   public static void main(String[] args) throws ClassNotFoundException
      {
      System.out.println(Info.getUniformTitle());
      System.out.println(module + " running.");
      System.out.println();

      try (Socket clientSocket = new Socket("localhost", Info.listeningPort))
         {
         int msgSent = 0;
         int msgRcvd = 0;
         System.out.println("Local TCP port " + clientSocket.getLocalPort());
         System.out.println("Sending bytes to TCP port " + clientSocket.getPort());
         System.out.println();
         
         OutputStream outputStream = clientSocket.getOutputStream();
         ObjectOutputStream objOStream = new ObjectOutputStream(outputStream);
         
         InputStream inputStream = clientSocket.getInputStream();
         ObjectInputStream objIStream = new ObjectInputStream(inputStream);
                  
         while (isRunning)
            {
        	 
        	matrix = new Matrix();
            System.out.println(module + " sending " + (++msgSent) + ": ");
            matrix.printMatrix();
            
            objOStream.writeObject((Object) matrix);
            
            try {
            	matrix = (Matrix) objIStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

            System.out.println(module + " received " + (++msgRcvd) + ": ");
            matrix.printMatrix();
            System.out.println();

            Thread.sleep(Info.loopDelay);
            
            if (msgSent == 1)
            	isRunning = false;
            }
         clientSocket.close();
         }
      catch (IOException | InterruptedException exception)
         {
         System.out.println("Exception launched: " + exception.getMessage());
         System.exit(1);
         }

      System.out.println();
      System.out.println(Info.getUniformTitle());
      System.out.println(module + " stopped.");
      }
   }

