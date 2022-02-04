import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JFrame;

public class Client{
	
	public BufferedReader in;
	public PrintWriter out;
	
	public final int port;
	public final String host;
	public String username;
	public int userID;
	public int userLimit;
	public int userCount;
	public Socket socket;
	
	public Game game;
	public Menus menus = new Menus(this);
	
	public Client(String host, int port) {
		
		this.port = port;
		this.host = host;
		
		menus.mainMenu(null, menus);
		
	}
	
	public void Connect(JFrame frame) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		new Thread(new ClientThreadCOMMS(this)).start();
		game = new Game(this);
		menus.play(frame);
	}
	
	public static void main(String[] args) throws IOException {
		new Client("localhost" , 1010);
	}
	
}

class ClientThreadCOMMS extends Thread {
	public Client client;
	
	public ClientThreadCOMMS(Client client) {
		this.client = client; 
	}
	
	public void run() {
		while(!Thread.interrupted()) {
			try {
				String input;
				input = client.in.readLine();
				while (input != null) {
					
					//get input
					input = client.in.readLine();
					
					StringTokenizer inputCode = new StringTokenizer(input);
					String code = inputCode.nextToken();
					
					// INITIALIZE ins
					boolean inInit = true;
					switch(code) {
						case "NC" :								
							client.userID = Integer.parseInt(inputCode.nextToken());
							break;
						case "UL" :
							client.userLimit = Integer.parseInt(inputCode.nextToken());
							break;
						default : 
							inInit = false;
					}
					if(inInit) {
						continue;
					}
					
					// SERVER ins
					boolean inServer = true;
					switch(code) {
						case "UC" :
							client.userCount = Integer.parseInt(inputCode.nextToken());
						case "RC" :
							client.out.println("Col " + client.userID + " " + client.game.colors[client.userID].getRGB());
							break;
						default : 
							inServer = false;
					}
					if(inServer) {
						continue;
					}
					
					// OTHER CLIENTS ins
					boolean inClients = true;
					switch(code) {
						case "Col":
							recieveColor(input);
						default : 
							inClients = false;
					}
					if(inClients) {
						continue;
					}
				}
			}
			catch(SocketException e1) {
				this.interrupt();
			}
			catch(Exception e2) {
				//e2.printStackTrace();
			}
		}
	}
	
	public void recieveColor(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String code = st.nextToken();
		int index = Integer.parseInt(st.nextToken());
		Color c = Color.decode(st.nextToken());
		client.game.colors[index] = c;
	}
	
}

