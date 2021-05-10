import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is responsible for establishing a server connection given a valid port. After
 * the connection is established the server will run and is capable of
 * accepting multiple clients. After a client is accepted their command will be executed.
 * After executed, the server instance will destroy itself and a new one will spawn.
 * 
 * @version 5-9-2021
 */
public class Server {
	
	/**
	 * This method establishes the server connection with the given port
	 * @throws IOException
	 */	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Welcome to our Concurrent Socket Server");
		
		int port = ServerClientUtilities.getPort();
		ServerSocket sSocket = new ServerSocket(port);
				
		while(true) {	
			Socket sock = sSocket.accept();
			System.out.println("New [SERVER] connection established");
			new Thread(new ServerThread(sock)).start();
		}
	}
}


/**
 * This class is responsible for running the server after a valid port is entered
 * The server will output the information that follows whichever command was selected. 
 */

class ServerThread extends Thread {
	
	Socket serverSocket;
	PrintWriter sendData;
	BufferedReader readData;
	
	public ServerThread(Socket sock) throws IOException {
		serverSocket = sock;
	}

	/*
	 * responsible for running the server and accepting client commands
	 */
	public void run() {
		
		System.out.println("Waiting for client connection...");
	
		try {	
			
			System.out.println("New client connection established...");
			
			sendData = new PrintWriter(serverSocket.getOutputStream(), true);
			readData = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));		
			
			String command = readData.readLine(); 
			System.out.println("Client selected command:" + command);
							
			Runtime runtime = Runtime.getRuntime();
			
			switch(command) {
				case "1": 
					
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					sendData.println(formatter.format(date));
					sendData.println("[COMPLETED]");
					sendData.flush();
					break;
						
				case "2":
					
					//Process uptimeProcess = Runtime.getRuntime().exec("wmic os get LastBootUpTime"); //use when testing on Windows
					Process uptimeProcess = Runtime.getRuntime().exec("uptime");
					readData = new BufferedReader(new InputStreamReader(uptimeProcess.getInputStream()));
					
					String s;
					while((s = readData.readLine()) != null) {
						sendData.println(s);
					}
					
					sendData.println("[COMPLETED]");
					sendData.flush();
					break;
				
				case "3":
						
					long totalMemory = runtime.maxMemory();
					long freeMemory = runtime.freeMemory();
					long memoryInUse = totalMemory - freeMemory;
					
					sendData.println("Total Memory:" + totalMemory/1000000.0 + " MB");
					sendData.println("Free Memory:" + freeMemory/1000000.0 + " MB");
					sendData.println("Memory in Use:" + memoryInUse/1000000.0 + " MB");		
					sendData.println("[COMPLETED]");
					sendData.flush();
					break;					

				case "4":

					Process procNetStat = Runtime.getRuntime().exec("netstat");
					readData = new BufferedReader(new InputStreamReader(procNetStat.getInputStream()));
					 
					String net;
					sendData.println("This might take a minute...");
					while((net = readData.readLine()) != null) {
						sendData.println(net);

					}
					
					sendData.println("[COMPLETED]");
					sendData.flush();
					break;			

				case "5":

					//Process procUsers = Runtime.getRuntime().exec("quser"); //use when testing on Windows
					Process procUsers = Runtime.getRuntime().exec("who");
					readData = new BufferedReader(new InputStreamReader(procUsers.getInputStream()));
					
					String users;
					while((users = readData.readLine()) != null) {
						sendData.println(users);
					}
					
					sendData.println("[COMPLETED]");
					sendData.flush();
					break;
					
				case "6":

					//Process procRun = Runtime.getRuntime().exec("tasklist"); //use when testing on Windows
					Process procRun = Runtime.getRuntime().exec("ps -A");
					readData = new BufferedReader(new InputStreamReader(procRun.getInputStream()));
					
					String procRunning;
					while((procRunning = readData.readLine()) != null) {
						sendData.println(procRunning);
					}
					
					sendData.println("[COMPLETED]");
					sendData.flush();
					break;
								
				case "7":

					sendData.println("Terminating server, goodbye...");
					sendData.println("[COMPLETED]");
					sendData.close();
					readData.close();
					System.exit(0);
			}
			
		} catch (IOException e){
			e.printStackTrace();
			
		} finally {
			
			System.out.println("Server terminating... Goodbye");
			
			try {
				
				serverSocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}