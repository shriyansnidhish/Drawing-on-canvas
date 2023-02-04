import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Project1Part1 extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	JButton newButton;
	Canvas newCanvas;
	int currentXCoordinate = 0, currentYCoordinate = 0;
	int previousXCoordinate, previousYCoordinate;

	// Default Constructor
	Project1Part1() {

		// Create a empty canvas
		newCanvas = new Canvas() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.clearRect(0, 0, 400, 300);

			}
		};
		// set canvas background
		newCanvas.setBackground(Color.gray);
		// add mouse listener
		newCanvas.addMouseListener(this);
		newCanvas.addMouseMotionListener(this);
		// set canvas size
		newCanvas.setSize(500, 400);
		add(newCanvas);
		setLayout(null);
		// set frame size
		setSize(500, 500);
		// set frame visibility
		setVisible(true);

		// Create a button to clear the drawing
		newButton = new JButton("Clear");
		newButton.addActionListener(this);
		newButton.setBackground(Color.white);
		newButton.setBounds(180, 400, 100, 50);
		add(newButton);

	}

	// Method to add action listener to perform canvas clear action through action
	// button
	public void actionPerformed(ActionEvent action) {
		String buttonString = action.getActionCommand();
		if (buttonString.equalsIgnoreCase("Clear")) {
			newCanvas.setBackground(Color.gray);
			newCanvas.repaint();
			currentXCoordinate = currentYCoordinate = 0;
		}
	}

	public void mouseDragged(MouseEvent event) {
		Graphics canvasGraphics = newCanvas.getGraphics();
		Graphics2D canvasGraphics2 = (Graphics2D) canvasGraphics;

		canvasGraphics.setColor(Color.blue);
		canvasGraphics2.setStroke(new BasicStroke(5));
		int newXCoordinate, newYCoordinate;
		newXCoordinate = event.getX();
		newYCoordinate = event.getY();

		// draw a line with the points where mouse is moved
		canvasGraphics.drawLine(previousXCoordinate, previousYCoordinate, newXCoordinate, newYCoordinate);
		previousXCoordinate = newXCoordinate;
		previousYCoordinate = newYCoordinate;

	}

	public void mousePressed(MouseEvent event) {
		Graphics canvasGraphics = newCanvas.getGraphics();
		canvasGraphics.setColor(Color.blue);
		previousXCoordinate = event.getX();
		previousYCoordinate = event.getY();
		canvasGraphics.fillOval(previousXCoordinate, previousYCoordinate, 5, 5);
	}

	public static void main(String[] argv) {
		Project1Part1 c = new Project1Part1();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}