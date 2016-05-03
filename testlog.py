#!/usr/bin/env python
import serial

ser = serial.Serial('/dev/tty.HC-06-DevB',9600)

f = open('buffer.txt','a')

while 1 :
    f.write(ser.readline())
    f.close()
    f = open('buffer.txt','a')