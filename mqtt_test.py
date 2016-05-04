#!/usr/bin/env python

import paho.mqtt.client as paho
import json

data = {

}

client = paho.Client()

def on_message(mosq, obj, msg):
	s = str(msg.payload)
	if s.startswith("Temperature: "):
		data["Temperature"] = s[13:-2]

	if s.startswith("Vis: "):
		data["Vis"] = s[5:-2]

	if s.startswith("IR: "):
		data["IR"] = s[4:-2]

	if s.startswith("UV: "):
		data["UV"] = s[4:-2]


	with open('data.json', 'w') as f:
		json.dump(data, f)

def on_publish(mosq, obj, mid):
    pass

client.on_message = on_message
client.on_publish = on_publish

client.connect("localhost", 1883)

client.subscribe("myfirst/test", 0)

while client.loop() == 0:
    pass