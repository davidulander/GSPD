import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Messages {
	private static Socket outgoing;
	private static ServerSocket incoming;
	
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		incoming = new ServerSocket(1884);
		outgoing = new Socket("169.254.21.49", 5555);
		Socket s = incoming.accept();
		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());
		out.writeUTF("Hello EV3!");
		System.out.println(in.readUTF());
	}	
}
