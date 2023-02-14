import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OneDollarAlgorithmImpl {
    private final int NumPoints = 64;

    // public ArrayList<Point> resample(ArrayList<Point> points) {
    //     Point[] p = points.toArray(new Point[points.size()]);
    //     double interval = pathLength(p) / (double)(NumPoints - 1);
    //     double D = 0.0;
    //     ArrayList<Point> newPoints = new ArrayList<>();
    //     for (int i = 1; i < points.size(); i++) {
    //         Point previousPoint = points.get(i - 1);
    //         double d = currentPoint.distance(previousPoint);
    //         if (D + d >= interval) {
    //             double qx = previousPoint.xCoordinate + ((interval - D) / d);
    //             double qy = previousPoint.yCoordinate + ((interval - D) / d);
    //             Point point = new Point(qx, qy);
    //             points.add(i, point);
    //             D = 0.0;
    //         } else {
    //             D += d;
    //         }
    //     }
    //     return newPoints;
    // }
    public ArrayList<Point> resample(ArrayList<Point> points) {
        Point[] p = points.toArray(new Point[points.size()]);
        double interval = pathLength(p) / (double)(NumPoints - 1);
        double D = 0.0;
        ArrayList<Point> newPoints = new ArrayList<>();
        newPoints.add(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            Point currentPoint = points.get(i);
            Point previousPoint = points.get(i - 1);
            double d = currentPoint.distance(previousPoint);
            if (D + d >= interval) {
                double qx = previousPoint.xCoordinate + ((interval - D) / d) * (currentPoint.xCoordinate - previousPoint.xCoordinate);
                double qy = previousPoint.yCoordinate + ((interval - D) / d) * (currentPoint.yCoordinate - previousPoint.yCoordinate);
                Point point = new Point(qx, qy);
                newPoints.add(point);
                points.add(i, point);
                D = 0.0;
            } else {
                D += d;
            }
        }
        if (newPoints.size() == NumPoints - 1) {
            newPoints.add(points.get(points.size() - 1));
        }
        return newPoints;
    }
    public Point centroid(List<Point> points) {
        int newX = 0;
        int newY = 0;
        for (Point point : points) {
            newX += point.xCoordinate;
            newY += point.yCoordinate;
        }
        return new Point(newX / points.size(), newY / points.size());
    }
    
    public void scale(List<Point> points) {
        double[] dimension = candidateSquareBoxSizing(points);
        System.out.println(Arrays.toString(dimension));
        double size = 500.0;
        for (Point point : points) {
            point.xCoordinate = point.xCoordinate * size/dimension[0];
            point.yCoordinate = point.yCoordinate* size/dimension[1];
        }
    }

    public ArrayList<Point> translate(List<Point> points) {
        ArrayList<Point> listOfTranslatedPoints = new ArrayList<>();
        Point centroid = centroid(points);
        for(Point p:points) {
            listOfTranslatedPoints.add(new Point(p.xCoordinate-centroid.xCoordinate,p.yCoordinate-centroid.yCoordinate));
        }
        return listOfTranslatedPoints;
    }
    
    private double[] candidateSquareBoxSizing(List<Point> points) {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (Point point : points) {
            minX = Math.min(minX, point.xCoordinate);
            minY = Math.min(minY, point.yCoordinate);
            maxX = Math.max(maxX, point.xCoordinate);
            maxY = Math.max(maxY, point.yCoordinate);
        }
        System.out.println(maxX+" "+maxY+" "+minX+" "+minY);
        double width = maxX - minX;
        double height = maxY - minY;

        return new double[]{width, height};
    }
    public double pathLength(Point[] points) {
        double d = 0;
        for (int i = 1; i < points.length; i++) {
            d += points[i - 1].distance(points[i]);
        }
        return d;
    }
}
