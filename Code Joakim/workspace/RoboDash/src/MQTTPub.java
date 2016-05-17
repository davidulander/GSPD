

import net.sf.xenqtt.client.MqttClient;
import net.sf.xenqtt.client.MqttClientListener;
import net.sf.xenqtt.client.PublishMessage;
import net.sf.xenqtt.client.SyncMqttClient;
import net.sf.xenqtt.message.ConnectReturnCode;
import net.sf.xenqtt.message.QoS;

public class MQTTPub {


	public void publish(String MQTTAddress, String topic, String message){
	

	MqttClientListener listener = new MqttClientListener() {

		@Override
		public void publishReceived(MqttClient client, PublishMessage message) {
			System.out.println("");
		}

		@Override
		public void disconnected(MqttClient client, Throwable cause, boolean reconnecting) {
			if (cause != null) {
				System.out.println("Disconnected from the broker due to an exception.");
			} else {
				System.out.println("Disconnected from the broker.");
			}

			if (reconnecting) {
				System.out.println("Attempting to reconnect to the broker.");
			}
		}
	};

	// Building client. 
	MqttClient client = new SyncMqttClient("tcp://"+MQTTAddress+":1883", listener, 5);
	//MqttClient client = new SyncMqttClient("tcp://82.181.145.144:1883", listener, 5);
	try {
		ConnectReturnCode returnCode = client.connect("RoboDash", false, "gatewaypub", "gateway-pass");
		if (returnCode != ConnectReturnCode.ACCEPTED) {
			System.out.println("Unable to connect to the broker. Reason: " );
			return;
		}

		// What to publish
		if(topic.equals("/robotCommands/target")){
		client.publish(new PublishMessage(topic, QoS.AT_MOST_ONCE, message));
		}
		else {
		client.publish(new PublishMessage(topic, QoS.AT_MOST_ONCE, message));
		}

		
	} catch (Exception ex) {
		System.out.println("An exception prevented the publishing of the full catalog.");
	} finally {
		
		if (!client.isClosed()) {
			client.disconnect();
		}
	}
	
	
}

}
