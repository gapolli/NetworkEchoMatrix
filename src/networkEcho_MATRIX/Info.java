package networkEcho_MATRIX;

/**
 * This class can be accessed through the program. Used to store global
 * attributes and methods.
 * 
 * @author André Franceschi de Angelis
 * @version 1.0
 * @since version 1.0
 */
public class Info {
	public static final String sysName = "Network Echo Transpose Matrix";
	public static final String sysVersion = "1.00";

	public static final int listeningPort = 5000;
	public static final int maxPackageSise = 32;
	public static final String shutDownCmd = "go down";
	public static final int loopDelay = 2500;

	private static final String decoration = "#";
	
	public static final String getUniformTitle() {
		return (Info.sysName + " - " + Info.sysVersion);
	}
	
	public static final void getDecoration(int spaces) {
				
		for (int l = 0; l < spaces * 7; l++) {
			System.out.print(decoration);
		}
		System.out.println();
	}

}
