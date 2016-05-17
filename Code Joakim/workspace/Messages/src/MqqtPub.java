import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
public class MqqtPub {
	public void publish(String data, String topic){
		//String topic = "/storage/boxPriority";
	    String content = data;
	    int qos = 0;
	    //Connect to broker which is located on my laptop, change for your IP which is know to the robot if you wnat to try out
	    String broker = "tcp://127.0.0.1:1883";
	    String clientId = "Server";
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
