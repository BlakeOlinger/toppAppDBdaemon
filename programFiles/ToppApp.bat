@echo off

cd "C:\Users\bolinger\Desktop\test install\"

start toppAppMaster.jar

timeout 1

start toppApp.jar

start toppAppDBdaemon.jar

start toppAppUpdater.jar

cd "C:\Users\bolinger\Desktop\test install\programFiles\sw\"

start sw-part-auto-test.exe

exit