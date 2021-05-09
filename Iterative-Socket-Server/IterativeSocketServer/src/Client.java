import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * This class is responsible for establishing a client connection given a valid IP and port. After
 * the connection is established between the server and client, the client will make a request to the server given a menu of options.
 * The server will not terminate until the client selects option (7). 
 * 
 * @version 5-9-2021
 */
public class Client {
	
	static ArrayList<Long> totalTimes = new ArrayList<Long>();
		
	/**
	 * This method is responsible for establishing the client connection, getting the desired command, and amount of clients
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner in = new Scanner(System.in);

		String ip = ServerClientUtilities.getIP();
		int port = ServerClientUtilities.getPort();
	
		System.out.println("Client(s) connection to the server will be established once a valid command is entered.\n");
		
		while(true){
					
			String command = ServerClientUtilities.displayMenu();
			
			if(command.equals("7")){

				ClientThread terminate = new ClientThread(ip, port, command);
				terminate.start(); //this would allow the 7 to be passed to the server, letting it know it can terminate both sockets. 
			
				terminate.join();
				in.close();
				System.exit(0);
				
				
			} else if(command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4") || command.equals("5") || command.equals("6")) {
				
				totalTimes.clear();
				
				int clientCount = ServerClientUtilities.getNumberOfClients();
				ClientThread[] clientPool = new ClientThread[clientCount];
				
				for(int i = 0; i < clientCount; i++) {
					
					clientPool[i] = new ClientThread(ip, port, command);
					System.out.println("Client number: " + (i + 1));
					System.out.println("-------------------------------");
				
					clientPool[i].start(); 
					clientPool[i].join();
						
					System.out.println();
					totalTimes.add(clientPool[i].getTurnAround());
				}
				
				int totalTime = 0;
				for(int i = 0; i < totalTimes.size(); i++) {
					totalTime += totalTimes.get(i);
				}
				
				int meanTotalTime = totalTime / totalTimes.size();
				
				System.out.println("-------------------------------");
				System.out.println("Average turn around time: " + meanTotalTime + "ms");
				System.out.println("Total turn around time: " + totalTime + "ms");
				System.out.println("-------------------------------");
				System.out.println();
				
				
			}else {
				System.out.println("Invalid entry... please enter a request between 1 - 7...");
			} 
		}
	}
}


/**
 * This class is responsible for creating and handling client threads.
 * It contains the client constructor and run method.
 */
class ClientThread extends Thread {
	
	Socket clientSocket;
	BufferedReader in; 
	PrintWriter out; 
	String command;
	long time; //reqTime
	
	
	public ClientThread(String ip, int port, String command) throws IOException {
		
		clientSocket = new Socket(ip, port);
		this.command = command;
	}
	
	@Override
	public void run() {
		try {
			
			String response;
			long startTime;

			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
						
			startTime = System.currentTimeMillis();

			out.println(command);
			while (!((response = in.readLine()).equals("[COMPLETED]")))  {
			    System.out.println(response);
			}
			
			this.time = (System.currentTimeMillis()) - startTime;
			System.out.println("Turn Around time: " + time + "ms");
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public long getTurnAround() {
		return time;
	}
} 