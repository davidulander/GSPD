import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import lejos.hardware.*;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
/*
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
*/
public class Communication {
	private static Socket outgoing;
	private static ServerSocket incoming;
	private static GraphicsLCD display;
	
	public int getBox(int a) throws UnknownHostException, IOException{
		
	outgoing = new Socket("169.254.240.125", 1885);
	DataOutputStream out = new DataOutputStream(outgoing.getOutputStream());
	out.writeInt(a);
	
	incoming = new ServerSocket(5556);
	Socket s = incoming.accept();
	DataInputStream in = new DataInputStream(s.getInputStream());
	int foo = in.readInt();
	incoming.close();
	return foo;
    
    
	
	
	
	//MQTT variables
	/*String topic = "myfirst/test";
    String content = str;
    int qos = 0;
    //Connect to broker which is located on my laptop, change for your IP which is know to the robot if you wnat to try out
    String broker = "tcp://169.254.136.26:1883";
    String clientId = "Robot";
    MemoryPersistence persistence = new MemoryPersistence();

    try {
        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        sampleClient.connect(connOpts);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        sampleClient.publish(topic, message);
        sampleClient.disconnect();
    } catch(MqttException me) {
        System.out.println("reason "+me.getReasonCode());
        System.out.println("msg "+me.getMessage());
        System.out.println("loc "+me.getLocalizedMessage());
        System.out.println("cause "+me.getCause());
        System.out.println("excep "+me);
        me.printStackTrace();
    }*/
	}
}