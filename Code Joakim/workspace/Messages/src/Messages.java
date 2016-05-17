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
		
		incoming = new ServerSocket(1885);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("/Users/jocke/Desktop/GSPD/objects.json"));
		JSONObject jsonObject = (JSONObject) obj;
		
		int current = 0;
		
		int green = Integer.parseInt((String) jsonObject.get("Green"));
		int red = Integer.parseInt((String) jsonObject.get("Red"));	
		int yellow = Integer.parseInt((String) jsonObject.get("Yellow"));
		
		MqqtPub m = new MqqtPub();
		m.publish((String)jsonObject.get("Red")+(String)jsonObject.get("Green")+(String)jsonObject.get("Yellow")+"0"+"0", "/storage/boxPriority");

		while(true){
		Socket s = incoming.accept();
		DataInputStream in = new DataInputStream(s.getInputStream());
		int question = in.readInt();
		
		outgoing = new Socket("169.254.21.49", 5556);
		DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());
		
		if(question==0){	
			System.out.println("Entered question 0");
			if(green==1) {
				out.writeInt(1);
				current = 1;
			}
			
			if(red==1) {
				out.writeInt(0);
				current = 0;
			}
			if(yellow==1) {
				out.writeInt(3);
				current = 3;
			}
			if(green==0 && red ==0 && yellow ==0) {
				out.writeInt(-10);
				current=-10;
			}
			if(green!=0){
				green--;
				jsonObject.put("Green",  Integer.toString(green));
			}
			if(red!=0){
				red--;
				jsonObject.put("Red", Integer.toString(red));
			}
			if(yellow!=0){
				yellow--;
				jsonObject.put("Yellow", Integer.toString(yellow));
			}
			m.publish(Integer.toString(red)+Integer.toString(green)+Integer.toString(yellow)+"0"+"0","/storage/boxPriority");
		}
		
		if(question==1){ 
			if(current==1) out.writeInt(3);
			if(current==0) out.writeInt(4);
			if(current==3) out.writeInt(1);
			else out.writeInt(-10);
			}
		}
	}
}