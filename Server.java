import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {

	public static final int userLimit = 4;
	public PrintWriter[] outs = new PrintWriter[userLimit];
    public BufferedReader[] ins = new BufferedReader[userLimit];
    public static int userID = 0;
    public static int userCount = 0;

    public Server(int port) throws IOException {

    	ServerSocket serverSocket = new ServerSocket(port);
    	serverSocket.setReuseAddress(true);
    	while(userID < userLimit) {
    		System.out.println("Waiting...");
    		Socket client = serverSocket.accept();
    		System.out.println("Connected to: " + client);

    		outs[userID] = new PrintWriter(client.getOutputStream(), true);
    		ins[userID] = new BufferedReader(new InputStreamReader(client.getInputStream()));

    		new Thread(new ServerThread(this, userID)).start();

    		userID++;
    		userCount++;

    	}

    }

    public static void main(String[] args) {
    	try {
			new Server(1010);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}

class ServerThread extends Thread {

	public Server server;
	public int user;
	public boolean running = true;

	public ServerThread(Server server, int user) {
		this.server = server;
		this.user = user;
	}

	public void run(){
		for (int i = 0; i < server.outs.length; i++) {
			if(server.outs[i] != null) {
				server.outs[i].println("NC " + user);
				server.outs[i].println("UL " + Server.userLimit);
				server.outs[i].println("UC " + Server.userCount);
			}
	    }
		System.out.print("Start");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {

		}
		System.out.println("END");
		for (int i = 0; i < server.outs.length; i++) {
			if(server.outs[i] != null) {
				server.outs[i].println("RC"); //RC = requesting colors
			}
	    }
		while(running) {
			String inputLine;
            try {
				while ((inputLine = server.ins[user].readLine()) != null) {
					if(server.outs[user] == null) {
						running = false;
						--Server.userCount;
						return;
					}
					StringTokenizer st = new StringTokenizer(inputLine);
					switch(st.nextToken()) {
						case "Col":
							//String s = "Col " + st.nextToken() + " " + st.nextToken();
							String s = inputLine;
							for (int i = 0; i < server.outs.length; i++) {
								server.outs[i].println(s);
							}
			    	}
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

}