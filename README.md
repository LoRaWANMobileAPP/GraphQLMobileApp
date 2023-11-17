# GraphQLMobileApp
This repository contains the mobile application that presents measurements from different LoRaWAN sensors. Below is the system sketch showing the different parts of the system.
First of the LoRaWAN sensors sends the measurement data to Altibox ThingPark. From there on the measurements data are sent trough ThingParkX. 
ThingParkX has the functionality to easily route measurements to external IoT services, like Dimension Four. Here Dimension Four acts as a data storage for the measurements.
The mobile application accesses the measurements in Dimension Four using GraphQL.
![image](https://github.com/LoRaWANMobileAPP/GraphQLMobileApp/assets/33607811/53b7e967-1aa2-4ec7-9b02-80126097d42e)

A flow diagram for how the mobile application functions is given below.
![image](https://github.com/LoRaWANMobileAPP/GraphQLMobileApp/assets/33607811/d1b9ef36-3e4c-4e2d-9cf4-2b9c2bc8a5b2)

Simple instructions on how to use the software:
1. Start the app
2. Review data on home screen
3. If you want, press and sensor for extra information.
   3.1 if you want to go back swipe the screen to the right.
4. When back on the homescreen you can press the tab App Info.
5. To get back to My Sensor, just press My Sensor tab. 
