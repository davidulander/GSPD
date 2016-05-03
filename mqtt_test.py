#!/usr/bin/env python

import paho.mqtt.client as paho

client = paho.Client()

def on_message(mosq, obj, msg):
	s = msg.payload
	#This is the location of the output file on my computer, you will have to update to yours.
	fo = open("/Users/jocke/Desktop/output.txt", "a")
	fo.write( "%s" % (s) + "\n")
	fo.close()

def on_publish(mosq, obj, mid):
    pass

client.on_message = on_message
client.on_publish = on_publish

client.connect("localhost", 1883)

client.subscribe("test3maj", 0)

while client.loop() == 0:
    pass