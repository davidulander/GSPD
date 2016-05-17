

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.xenqtt.client.MqttClient;
import net.sf.xenqtt.client.MqttClientListener;
import net.sf.xenqtt.client.PublishMessage;
import net.sf.xenqtt.client.Subscription;
import net.sf.xenqtt.client.SyncMqttClient;
import net.sf.xenqtt.message.ConnectReturnCode;
import net.sf.xenqtt.message.QoS;

public class MQTTSub {
String serverIP;
String address;
String address2;
String address3;
String address4;
String address5;
String address6;
String address7;
String address8;
String address9;
String address10;
String address11;
String value = "0";
String value2 = "0";
String value3 = "0";
String value4 = "0";
String value5 = "0";
String value6 = "0";
String value7 = "0";
String value8 = "0";
String value9 = "0";
String value10 = "0";
String value11 = "00000";


public String getValue(){
	return value;
}

public String getValue2(){
	return value2;
}

public String getValue3(){
	return value3;
}

public String getValue4(){
	return value4;
}

public String getValue5(){
	return value5;
}

public String getValue6(){
	return value6;
}

public String getValue7(){
	return value7;
}

public String getValue8(){
	return value8;
}

public String getValue9(){
	return value9;
}

public String getValue10(){
	return value10;
}

public char[] getArrayValue11(){
	char[] arrayValue11 = value11.toCharArray();
	return arrayValue11;
}



	




public void mqttRef(String server, String topic, String topic2, String topic3, String topic4, String topic5,String topic6, String topic7, String topic8, String topic9, String topic10, String topic11){
	
	serverIP=server;
	address =topic;
	address2 =topic2;
	address3 = topic3;
	address4 = topic4;
	address5 = topic5;
	address6 = topic6;
	address7 = topic7;
	address8 = topic8;
	address9 = topic9;
	address10 = topic10;
	address11 = topic11;
	
	final List<String> catalog = Collections.synchronizedList(new ArrayList<String>());
	MqttClientListener listener = new MqttClientListener() {

		@Override
		public void publishReceived(MqttClient client, PublishMessage message) {
			catalog.add(message.getPayloadString());
			if(message.getTopic().equals("/darkAndCold/temperature")){
				value = message.getPayloadString();}
			else if(message.getTopic().equals("/darkAndCold/brightness")){
				value2 = message.getPayloadString();
			}
			else if(message.getTopic().equals("/darkAndWarm/temperature")){
				value3 = message.getPayloadString();
			}
			else if(message.getTopic().equals("/darkAndWarm/brightness")){
				value4 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/brightAndWarm/temperature")){
				value5 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/brightAndWarm/brightness")){
				value6 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/brightAndCold/temperature")){
				value7 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/brightAndCold/brightness")){
				value8 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/robotCoord/Xvalue")){
				value9 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/robotCoord/Yvalue")){
				value10 = message.getPayloadString();
				}
			else if(message.getTopic().equals("/storage/boxPriority")){
				value11 = message.getPayloadString();
				}
			
			else{
				System.out.println("Error1: MQTT Read Error");
			}
			
			System.out.println("FromMQTT: "+message.getTopic()+"--->"+message.getPayloadString());
			message.ack();
		}

		@Override
		public void disconnected(MqttClient client, Throwable cause, boolean reconnecting) {
			if (cause != null) {
				System.out.println("Disconnected from the broker due to an exception.");
			} else {
				System.out.println("Disconnecting from the broker.");
			}

			if (reconnecting) {
				System.out.println("Attempting to reconnect to the broker.");
			}
		}

	};

	//SyncMqttClient client = new SyncMqttClient("tcp://82.181.145.144:1883", listener, 5);
	SyncMqttClient client = new SyncMqttClient("tcp://"+serverIP+":1883", listener, 5);
	try {
		ConnectReturnCode returnCode = client.connect("RoboDash", true);
		if (returnCode != ConnectReturnCode.ACCEPTED) {
			System.out.println("No route to MQTT Broker. Reason: "+ returnCode );
			return;
		}

		List<Subscription> subscriptions = new ArrayList<Subscription>();
		subscriptions.add(new Subscription(address, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address2, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address3, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address4, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address5, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address6, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address7, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address8, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address9, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address10, QoS.AT_MOST_ONCE));
		subscriptions.add(new Subscription(address11, QoS.AT_MOST_ONCE));
		System.out.println("Subscribed to: "+ address+"&&" + address2 + "&&" + address3+"&&" + address4 + "&&" + address5 + "&&" + address6+"&&" + address7 + "&&" + address8 + address9+"&&" + address10 + "&&" + address11);
		
		client.subscribe(subscriptions);
		

		while(true){
			Thread.sleep(1500);
			
		}
	
	} catch (Exception ex) {
		System.out.println("Exceptions happen, don't trust buggy software.");
	}
	
	
}
}
