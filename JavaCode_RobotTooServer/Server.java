import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Messages {
    //Declare a new socket for the outgoing data to be sent
    private static Socket outgoing;
    
    public static void main(String[] args) throws UnknownHostException, IOException{
        
        //The IP to the robot. This may change over time, so be sure to check if it is correct.
        //5555 means port number and this port has a socket asigned in the code for the robot.
        outgoing = new Socket("169.254.21.49", 5555);
        DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());
        //message to write, will in future be a command probably
        out.writeUTF("Hello EV3!");
    }	
}
