import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import lejos.hardware.*;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;


public class Communication {
	private static Socket outgoing;
	private static ServerSocket incoming;
	private static GraphicsLCD display;
	
	public int getBox(int a) throws UnknownHostException, IOException{
		
	outgoing = new Socket("169.254.227.186", 1885);
	DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());
	out.writeInt(a);
	
	incoming = new ServerSocket(5556);
	Socket s = incoming.accept();
	DataInputStream in = new DataInputStream(s.getInputStream());
	int foo = in.readInt();
	incoming.close();
	return foo;
    
	}
	public void sendMeassure(String m) throws UnknownHostException, IOException{
		outgoing = new Socket("169.254.227.186", 1886);
		DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());
		out.writeUTF(m);
	}
}
