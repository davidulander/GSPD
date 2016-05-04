#!/usr/bin/env python
import serial
import paho.mqtt.client as paho

client = paho.Client()
client.connect("localhost", 1883)

ser = serial.Serial('/dev/tty.HC-06-DevB',9600)

while 1 :
	client.publish("myfirst/test",ser.readline())