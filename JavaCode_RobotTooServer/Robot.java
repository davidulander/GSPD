import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.*;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
public class Test {
	private static Socket outgoing;
	private static ServerSocket incoming;
	public static void main(String[] args) throws IOException {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
		
		incoming = new ServerSocket(5555);
		try {
		    Thread.sleep(3000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	    outgoing = new Socket("169.254.33.225", 1884);
		Socket s = incoming.accept(); //Wait for Laptop to connect
		DataInputStream in = new DataInputStream(s.getInputStream());
		DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());

		g.drawString(in.readUTF(), 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(2000);
		out.writeUTF("I got the message!");
	}
}