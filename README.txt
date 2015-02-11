========================================================================================================================
============================================= AUTOR : Calina Cristian Florin ===========================================
=================================================== Tema 2: Selfie =====================================================
========================================================================================================================


Simulation Manager:

I built the network using the network configuration file recieved as a parameter (args[0]) and returned the first center.
In order to build the network I used a scanner to read the first line of the file (which had the number of lines) and 
then for the first n lines I read the center name and it's components and added them to it's message center. For the 
next n lines I read the center name and it's neighbors and added them to it's neighbor list.

In the start function , I used two strings for the image input and output and three arrays of string for pre-capture , 
capture and post-capture. First I checked if the line said "exit" in order to close the program , and if it did not , 
I used the delimiter " " to have a vector with every task , than I set the image input variable to be tasks[0] , 
image output tasks[1] , pre capture tasks[2] with "()=;," delimiters , capture tasks[3] with "(=)" delimiters and
post capture tasks[4] with "(;)" delimiters. After that I solved the tasks using these variables. 




Components:

Every component used the formulas required in the task. They are explained in the java documentation.
In the Component class I added the function pixelsOverMax which receives an image and checks if it's pixels are over 255 
value and sets them to 255 in case they are. I used the function here because it is needed in more than 1 extentions.




Messaging:

MessageCenter:

For every center , I have a center name , a list of neighbors, a list of components and a list of message IDs. 
The constructor recieves a vector with the name on the first position and the name of it's neighbors on the next 
positions (if there are any). It sets the name to be the value of the first position and it's neighbors are added to 
the neighbor list using switch/case. 
I also have a function which adds a neighbor ( given as a prameter ) to the neighbor list.
The publish algorithm respects the pseudocode and doesn't need any explanation.

Message:

For generating the message ID , i used a static variable "count" which was initialised with 0 and everytime generateId 
is called I incremented it and set the message ID to it's value.

MessageFlash and MessageZoom:

MessageFlash is the same as MessageImage but it has a new parameter which represents the flashtype (ON/OFF/AUTO)
MessageZoom is the same as MessageImage but it has 4 more parameters which represent the coordinates of th up-left 
and the down-right corners. 