import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

// Class to create a blank ‘canvas’ on the screen using java GUI elements
// listen to mouse events on the canvas and record them as the user gesture
// allow the user to clear the canvas
class OneDollarUiImpl extends JFrame implements MouseListener, MouseMotionListener, ActionListener{

    JButton button;
    Canvas newCanvas;
    int previousXCoordinate, previousYCoordinate;
    // ArrayList<Double> xcordinate = new ArrayList<>();
    // ArrayList<Double> ycordinate = new ArrayList<>();
    ArrayList<Point> listPoints = new ArrayList<>();
   // double[] xcordinate= new double[500];
   // double[] ycordinate= new double[500];
    int i=0;
    
	// constructor
	OneDollarUiImpl()
	{
		// Create a empty canvas
		newCanvas = new Canvas(){

            // public void paint(Graphics g) {
			// 	g.clearRect(0, 0, 400, 300);
			// }
            public void paint(Graphics gr)
            {  
            }
        };
        
        // set background for the canvas
		newCanvas.setBackground(Color.gray);
        // add mouse listener
        newCanvas.addMouseListener(this);
        newCanvas.addMouseMotionListener(this);
        // set size of the canvas in the frame
        newCanvas.setSize(500, 400);
		add(newCanvas);
        // Create a clear button. 
	    button = new JButton ("Clear");
	    button.setBackground (Color.white);
        button.setBounds(180 , 400, 100, 50);
        add(button);
        // Add a listener for the button
        button.addActionListener (this);
        // sets the frame to put canvas as a component
        setLayout(null);
        // sets size of the frame
		setSize(500, 500);
        setVisible(true);
	}
   // Method to add action listener to perform canvas clear action through action
	// button
    public void actionPerformed (ActionEvent e)
    {
	    String buttonString = e.getActionCommand();
        listPoints.clear();
        if (buttonString.equalsIgnoreCase ("Clear")) {
	    newCanvas.setBackground (Color.gray);
	    newCanvas.repaint ();
	    }
    }

    public void mousePressed(MouseEvent e)
    {
        Graphics gr = newCanvas.getGraphics();
 
        gr.setColor(Color.red);
 
        // get X and y position
        previousXCoordinate = e.getX();
        previousYCoordinate = e.getY();
        listPoints.add(new Point((double)previousXCoordinate,(double)previousYCoordinate));
        // draw a Oval at the point
        // where mouse is moved
        gr.fillOval(previousXCoordinate, previousYCoordinate, 10, 10);
    }
    
    // public void mouseDragged(MouseEvent event) {
	// 	Graphics canvasGraphics = newCanvas.getGraphics();
	// 	Graphics2D canvasGraphics2 = (Graphics2D) canvasGraphics;

	// 	canvasGraphics.setColor(Color.blue);
	// 	canvasGraphics2.setStroke(new BasicStroke(5));
	// 	int newXCoordinate, newYCoordinate;
	// 	newXCoordinate = event.getX();
	// 	newYCoordinate = event.getY();

	// 	// draw a line with the points where mouse is moved
	// 	canvasGraphics.drawLine(previousXCoordinate, previousYCoordinate, newXCoordinate, newYCoordinate);
	// 	previousXCoordinate = newXCoordinate;
	// 	previousYCoordinate = newYCoordinate;

	// }
    public void mouseDragged(MouseEvent e)
    {
        Graphics graphics = newCanvas.getGraphics();
        Graphics2D newGraphics= (Graphics2D)graphics;
        graphics.setColor(Color.red);
        newGraphics.setStroke(new BasicStroke(5));
        int x, y;
        x = e.getX();
        y = e.getY();
        listPoints.add(new Point((double)x,(double)y));
        // draw a line with the points where mouse is moved
        graphics.drawLine(previousXCoordinate, previousYCoordinate, x, y);
        previousXCoordinate=x;
        previousYCoordinate=y;
        // System.out.println(xcordinate[i]+" "+ycordinate[i]);
    }
    
    // Method which comes into action when the mouse button is released
    // This method consist the steps of resampling, rotation,scaling, translate, matching
    public void mouseReleased(MouseEvent e)
    {
        //long startTime = System.currentTimeMillis();
        OneDollarAlgorithmImpl pointProcessor = new OneDollarAlgorithmImpl();
        OneDollarRecognitionAlgorithm dollarOneRecognizer = new OneDollarRecognitionAlgorithm();

        // Resampling
        ArrayList<Point> resampledPoints = pointProcessor.resample(listPoints);

        //Rotate
        Point centroid = pointProcessor.centroid(resampledPoints);
        Point firstPoint = resampledPoints.get(0);
        double slope = Math.atan2((firstPoint.yCoordinate-centroid.yCoordinate),(firstPoint.xCoordinate-centroid.xCoordinate));
        ArrayList<Point> rotatedPoints = dollarOneRecognizer.rotateBy(resampledPoints, -1*slope,centroid);
        
        //Scale
        pointProcessor.scale(rotatedPoints);

        //Translate
        ArrayList<Point> translatedPoints = pointProcessor.translate(rotatedPoints);
    
        //Matching
        String result = dollarOneRecognizer.recognize(translatedPoints);
        String[] str = result.split("-");
        System.out.println(result);
        String templateName = str[0];
        double score = Double.parseDouble(str[1]);
       // long endTime = System.currentTimeMillis();
        this.setTitle("Result: "+templateName+" ("+String.format("%.2f", score)+")");
        //Draw the points
        Graphics g = newCanvas.getGraphics();
        g.setColor(Color.blue);        

    }
 
    public static void main (String[] argv)
    {
        new OneDollarUiImpl();
            
    }

    public void mouseClicked(MouseEvent e)
    {
    }
 
    public void mouseMoved(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }
 
    public void mouseEntered(MouseEvent e)
    {
    }
 
}
