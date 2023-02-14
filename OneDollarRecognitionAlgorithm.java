import java.util.ArrayList;
import java.util.List;

public class OneDollarRecognitionAlgorithm {
    // Total number of templates
    private final int totalTemplates = 16;
    // 2D array of type "Point" for storing templates and their respective points
    private final Point[][] templates = new Point[totalTemplates][];
    // 1D array of type string to store the name of the templates
    private final String[] templateNames = new String[totalTemplates];
    
    // Default constructor for initializing the gesture and storing templates in 2D array
    OneDollarRecognitionAlgorithm() {
        OneDollarStrokes stroke = new OneDollarStrokes();
        // importing all the templates
        List<Candidate> template = stroke.template;
        int i = 0;
        for(Candidate g:template) {
            // Creating list of points
            List<Point> points = g.points;
            int j = 0;
            // Defining the number of rows of 2D array that will be equal to size of list of points
            templates[i] = new Point[points.size()];
            // Storing the template name and their corresponding points
            // first element of every row being the name of the template
            // second elements onwards of every row will be corresponding points
            for(Point p:points)templates[i][j++] = p;
            templateNames[i] = g.name;
            i++;
        }
    }

     // Values and name of variables are taken directly from paper
     private double pathDistance(List<Point> a, Point[] b) {
        double d = 0;
        for (int i = 0; i < Math.min(b.length,a.size()); i++) {
            d += distance(a.get(i), b[i]);
        }
        return d / Math.min(b.length,a.size());
    }

    // Values and name of variables are taken directly from paper
    public ArrayList<Point> rotateBy(List<Point> points, double angle,Point centroid) {
        ArrayList<Point> newPoints = new ArrayList<>();
        
        for (Point point : points) {
            int x = (int) ((point.xCoordinate-centroid.xCoordinate) * Math.cos(angle) - (point.yCoordinate-centroid.yCoordinate) * Math.sin(angle)) + (int)centroid.xCoordinate;
            int y = (int) ((point.xCoordinate-centroid.xCoordinate) * Math.sin(angle) + (point.yCoordinate-centroid.yCoordinate) * Math.cos(angle)) + (int)centroid.yCoordinate;
            newPoints.add(new Point(x, y));
        }
        return newPoints;
    }

    // Values and name of variables are taken directly from paper
    private double distance(Point a, Point b) {
        double dx = (a.xCoordinate - b.xCoordinate);
        double dy = (a.yCoordinate - b.yCoordinate);
        return Math.sqrt(dx * dx + dy * dy);
    }
    // Values and name of variables are taken directly from paper
    private double distanceAtAngle(double angle, List<Point> points, Point[] T) {
        
        List<Point> newPoints = rotateBy(points, angle,new OneDollarAlgorithmImpl().centroid(points));
        return pathDistance(newPoints, T);
    }

    // Method to recognize the drawn template on canvas 
    // Prints name of the template as well as the accuracy score
    // @return: templateName +" " +score
    public String recognize(List<Point> points) {
        double b = Double.POSITIVE_INFINITY;
        int temp = 0;

        for (int i = 0; i < totalTemplates; i++) {
            double distance = distanceAtBestAngle(points, templates[i]);
            if (distance < b) {
                b = distance;
                temp = i;
            }
            System.out.println(distance+" "+templateNames[i]);
        }
        System.out.println();
        int size = 500;
        double score = 1 - (b/(0.5*Math.sqrt(size*size + size*size)));
        String templateName = templateNames[temp];
        return templateName+"-"+score;
    }

    // Method to find the distance of points of candidate from points of templates
    // Values and name of variables are taken directly from paper
    private double distanceAtBestAngle(List<Point> points, Point[] T) {
        double a = -0.25 * Math.PI;
        double b = 0.25 * Math.PI;
        double delta = 0.5*(-1+Math.sqrt(5));
        double threshold = Math.toRadians(2);

        double x1 = delta*a +(1 - delta)*b;
        double x2 = (1-delta)*a + delta*b;

        double f1 = distanceAtAngle(x1,points,T);
        double f2 = distanceAtAngle(x2, points, T);
        // double x1 = phi(a, points, T);
        // double x2 = phi(b, points, T);
        while (Math.abs(b - a) > threshold) {
            if(f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = delta*a +(1 - delta)*b;
                f1 = distanceAtAngle(x1, points, T);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = (1-delta)*a + delta*b;
                f2 = distanceAtAngle(x2, points, T);
            }
        }
        return Math.min(f1, f2);
    }
    
}
