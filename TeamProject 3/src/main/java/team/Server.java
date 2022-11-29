package team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Server {
	
	private List<ServerThread> serverThreads;

	private BufferedReader br;
	
	public Server(int port) {
		try {
			ServerSocket ss= new ServerSocket(port);
			serverThreads= Collections.synchronizedList(new ArrayList<ServerThread>());
			
			while(true) {
				Socket s = ss.accept();
				System.out.println("Accepted connection");
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
		} catch (IOException e) {
			System.out.println("IO exception in server constructor "+ e);
		}
	}
	
	public static void main(String [] args) {
		Server s = new Server(1023);
		
	}
}
