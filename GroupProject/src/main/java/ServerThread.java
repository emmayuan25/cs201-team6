
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	private PrintWriter ps;
	private BufferedReader br;
	private Socket ser;
	private InputStream in;
	private Server server;
	public ServerThread(Socket s, Server server) {
		try {
			
			this.server = server;
		

			// to do --> store them somewhere, you will need them later 
			ps = new PrintWriter(s.getOutputStream());
			br= new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.ser=s;
		
			
			// to do --> complete the implementation for the constructor
			
			this.start();
			
			
			
			
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}
	public void run() {
	
	}
}
