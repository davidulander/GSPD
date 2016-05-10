import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.*;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Test {
    private static Socket outgoing;
    private static ServerSocket incoming;
    public static void main(String[] args) throws IOException {
        
        //declare socket on incomming port
        incoming = new ServerSocket(5555);
        //Wait for Server to connect
        Socket s = incoming.accept();
        
        //Asign the incomming data to string.
        DataInputStream in = new DataInputStream(s.getInputStream());
        String str = in.readUTF();
        
        //MQTT variables
        String topic = "myfirst/test";
        String content = str;
        int qos = 0;
        //Connect to broker which is located on my laptop, change for your IP which is know to the robot if you wnat to try out
        String broker = "tcp://169.254.33.225:1883";
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
        }
    }
}