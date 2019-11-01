package networkEcho_MATRIX;

public class Info
   {
   public static final String sysName        = "Network Echo Transpose Matrix";
   public static final String sysVersion     = "1.00";
   
   public static final int    listeningPort  = 5001;
   public static final int    maxPackageSise = 32;
   public static final String shutDownCmd    = "go down";
   public static final int    loopDelay      = 2500;

   public static final String getUniformTitle()
      {
      return (Info.sysName + " - " + Info.sysVersion);
      }
   }
