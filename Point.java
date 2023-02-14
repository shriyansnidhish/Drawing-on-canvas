// Class to store the candidate and template points
// Also calculates the distance between two points
public class Point
{
	public double xCoordinate, yCoordinate;
	// Default constructor
	public Point(){
		this.xCoordinate = 0; this.yCoordinate = 0;
	}
	
	public Point(double x, double y)
	{	this.xCoordinate = x; this.yCoordinate = y;	}
	public double distance( Point other)
  {
    return Math.sqrt(Math.pow(other.xCoordinate-xCoordinate,2)+Math.pow(other.yCoordinate-yCoordinate ,2));
  }
}