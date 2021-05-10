import java.util.Scanner;

/**
 * This class is responsible for setting up utilities to make the process of collecting necessary data from the server and client
 * more efficient. For example, both (server and client) will need to provide a valid port, so the method getPort is stored in this class. 
 * 
 * @version 5-9-21
 */
public class ServerClientUtilities {
	
	/**
	 * This method is responsible for getting the port number from the user
	 * It runs until a valid port is entered
	 * @return port
	 */
	public static int getPort() {
		
		int port;	
				
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please enter a valid port number to begin establishing the server...");
		System.out.println("Valid entry between 1025 - 4998");
		
		do {
			
			System.out.println("Waiting for valid port number...");
			port = in.nextInt();
			
		} while(port < 1025 || port > 4998);
	
		System.out.println("Valid port number accepted... Server listening on port: " + port);
		System.out.println();
		return port;
	}
	
	
	/**
	 * This method is responsible for getting the server's IP address from the user
	 * It runs when any IP is entered
	 * @return ip
	 */
	public static String getIP() {
		
		Scanner in = new Scanner(System.in);
		String IP = null;
		System.out.println("Please enter the server's IP address...");
		
		do {
			
			IP = in.nextLine();
			
		}while(IP == null);
		
		System.out.println("IP Address Entered: " + IP);
		System.out.println();
		return IP;
	}
	
	
	/**
	 * This method is responsible for getting the number of clients from the user
	 * Valid between 1 - 25
	 * @return clientCount
	 */
	public static int getNumberOfClients() {
		
		Scanner in = new Scanner(System.in);

		int clientCount;	
		System.out.println("Please enter the number of clients you would like to test...");
		System.out.println("Valid between the range 1 - 25");
		
		do {
			
			System.out.println("Please enter a number within the valid range...");
			clientCount = in.nextInt();
				
		} while(clientCount <= 0 || clientCount > 100);
		
		System.out.println();
		return clientCount;
	}
	
	
	/**
	 * This method is responsible for displaying the request menu to the user
	 */
	public static String displayMenu() {
		Scanner in = new Scanner(System.in);
		String command;
		
		System.out.println("Review and select from list of commands using the associated number:");
		System.out.println("1. Date and Time - the date and time on the server ");
		System.out.println("2. Uptime - how long the server has been running since last boot-up");
		System.out.println("3. Memory Use - the current memory usage on the server");
		System.out.println("4. Netstat - lists network connections on the server");
		System.out.println("5. Current Users - list of users currently connected to the server");
		System.out.println("6. Running Processes - list of programs currently running on the server");
		System.out.print("7. To Terminate Server\n > ");
		
		command = in.nextLine();
		return command;
	}
}