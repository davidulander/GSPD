import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.Iterator;  
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  
import org.json.simple.parser.JSONParser;  
import org.json.simple.parser.ParseException; 


public class Messages {

	private static Socket outgoing;
	private static ServerSocket incoming;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ParseException{
		/*outgoing = new Socket("169.254.21.49", 5555);
		DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());*/
		
		incoming = new ServerSocket(1885);
		Socket s = incoming.accept();
		DataInputStream in = new DataInputStream(s.getInputStream());
		String question = in.readUTF();
		
		System.out.println(question);
		 
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("/Users/jocke/Desktop/GSPD/data.json"));
		JSONObject jsonObject = (JSONObject) obj;  
		String temperature = (String) jsonObject.get("Temperature");
		String uv = (String) jsonObject.get("UV");
		
		//out.writeUTF("Temperature: " + temperature + " Light: " + uv);
		incoming.close();
	}	
}
