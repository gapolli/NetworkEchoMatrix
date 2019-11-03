package networkEcho_MATRIX;

/**
 * This class starts the multitask server.
 * 
 * @author André Franceschi de Angelis
 * @author Mateus Pim Santos
 * @author Gustavo Adrien Polli
 * @author Victor Gomes Sampaio
 * @author Max Lúcio Martins de Assis
 * @version 1.1
 * @since version 1.0
 */
public class ServerStart {
	private static final String module = "Multitask Server";

	/**
	 * The main server program.
	 * @param args permit the user to pass arguments in the execution of this program
	 */
	public static void main(String[] args) {
		System.out.println(Info.getUniformTitle());
		System.out.println(module + " running.");
		System.out.println();

		MultitaskServer server = new MultitaskServer(module);
		server.startServer();
	}
}