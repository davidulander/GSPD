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

public class Position {
	private static ServerSocket incoming;
	
	public static void main(String[] args) throws UnknownHostException, IOException, ParseException{
		
		JSONParser meassureParser = new JSONParser();
		
		MqqtPub m = new MqqtPub();
		incoming = new ServerSocket(1886);
		
		while(true){
			Object meassureObj = meassureParser.parse(new FileReader("/Users/jocke/Desktop/GSPD/data.json"));
			JSONObject meassure = (JSONObject) meassureObj;
			
			String BCt = "/brightAndCold/temperature";
			String DCt = "/darkAndCold/temperature";
			String BWt = "/brightAndWarm/temperature";
			String DWt = "/darkAndWarm/temperature";
			String BCb = "/brightAndCold/brightness";
			String DCb = "/darkAndCold/brightness";
			String BWb = "/brightAndWarm/brightness";
			String DWb = "/darkAndWarm/brightness";
			
		Socket s = incoming.accept();
		DataInputStream in = new DataInputStream(s.getInputStream());
		String position = in.readUTF();
		
		String temperature = (String) meassure.get("Temperature");
		String uv = (String) meassure.get("UV");
		
		if(position.equals("DW")){
			m.publish(temperature,DWt);
			m.publish(uv, DWb);
		}
		if(position.equals("DC")){
			m.publish(temperature,DCt);
			m.publish(uv, DCb);
		}
		if(position.equals("BW")){
			m.publish(temperature,BWt);
			m.publish(uv, BWb);
		}
		if(position.equals("BC")){
			m.publish(temperature,BCt);
			m.publish(uv, BCb);
		}
		}
	}
}
