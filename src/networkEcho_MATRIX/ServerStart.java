package networkEcho_MATRIX;

/**
 * This Class starts the multitask server
 * @author
 * @version
 * @since
 */
public class ServerStart {
	private static final String module = "Multitask Server";

	public static void main(String[] args) {
		System.out.println(Info.getUniformTitle());
		System.out.println(module + " running.");
		System.out.println();

		MultitaskServer server = new MultitaskServer(module);
		server.startServer();
	}
}