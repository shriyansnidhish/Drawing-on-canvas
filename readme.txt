Human-Centered Input Recognition Algorithms - Project 1 Part 1

Submission info:-
- This submission contains readme.txt, Project1Part1.java, Project1Part1.mp4

Group members:-
- Shriyans Nidhish
- Smridhi Bhat

Steps to run the project:-
- Compile the Project1Part1.java file
  javac Project1Part1.java
- Run the Project1Part1.java class file
  java Project1Part1

Goals achieved
a)Set up a development environment
	- In this project, a development environment was set up using Java programming language. 
	- The JDK 17 and VScode IDE were used for the development and execution of the project.

b)Instantiating a canvas
	- A class named Project1Part1 was instantiated, with a constructor present at line 13. Upon 	  creation of an object for the class, a canvas with a size of 500 * 400 was created, and its 	  title was set to "newCanvas".

c)Listening for mouse or touch events
	- To listen for mouse or touch events, the MouseMotionListener class from the java.awt 		  package was implemented.
	- When the mouse was clicked for the first time, mousePressed function at line 73 was called 	  and the X, Y coordinate of that point was stored in variables. Then the cursor, which was 	  in an oval shape, filled the line using ovals.
	- The mouseDragged function at line 58 was also called when the mouse was dragged on the 	  screen, which kept drawing the line from initially stored X,Y coordinate to newly stored X, 	  Y coordinate.
	- The mouse events were functioning as intended.

d)Clearing the canvas
	- A button called "Clear" was added at line 39 which, upon clicking, called the 		  actionPerformed function at line 49 linked to the button, and cleared the canvas by 		  repainting it with color defined.
