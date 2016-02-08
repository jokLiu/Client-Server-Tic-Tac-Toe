
// java Client user-nickname hostname

//imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Main client class
 *
 */
class Client {

	/**
	 * main method of the class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 2) {
			System.err.println("Usage: java Client user-nickname hostname");
			System.exit(1); // Give up.
		}

		// Initialize information:
		String nickname = args[0];
		String hostname = args[1];

		// Open sockets:
		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;

		try {
			server = new Socket(hostname, Port.number);
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}

		// create a game board and game model
		NoughtsCrosses board = new NoughtsCrosses();
		NoughtsCrossesModel model = new NoughtsCrossesModel(board, toServer);

		// create a class which keeps the board
		Game game = new Game(board,  model);

		// Create a thread to recieve information from server and act
		// accordingly
		ClientReceiver receiver = new ClientReceiver(fromServer, game, toServer, nickname);

		// run the thread
		receiver.start();

		// print information to server to start the game
		toServer.println(nickname);
		toServer.println("info");
		toServer.println("info");

		// Wait for the thread to end and close sockets.
		try {
			// waiting for thread to end
			receiver.join();

			// sending a message to Server to delete the player data
			toServer.println("me");
			toServer.println("remove");
			toServer.println("Quit");

			// close the sockets
			toServer.close();
			fromServer.close();
			server.close();
			
		} catch (IOException e) {
			System.err.println("Something wrong " + e.getMessage());
			System.exit(1); // Give up.
		} catch (InterruptedException e) {
			System.err.println("Unexpected interruption " + e.getMessage());
			System.exit(1); // Give up.
		}
	}
}
