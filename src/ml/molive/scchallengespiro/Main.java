package ml.molive.scchallengespiro;

import edu.princeton.cs.algs4.StdDraw;
import java.awt.geom.Point2D;
import java.util.Scanner;

/*
Input format is radius,speed;radius,speed;radius,speed;...
 */

public class Main {
  public static void main(String[] args) {
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(StdDraw.BLUE);
    StdDraw.setScale(-1.05, 1.05); // Make the screen cartesian
    Circle mainCircle = new Circle(0, 0);
    String[] input = new Scanner(System.in).next().split(";");

    Circle currentCircle = mainCircle;
    double speed = 0;
    for (String cir : input) {
      speed += Double.parseDouble(cir.split(",")[1]);
      Circle c = new Circle(Double.parseDouble(cir.split(",")[0]), speed);
      currentCircle.setNext(c);
      currentCircle = c;
    }

    Point2D lastPos = mainCircle.getPosRecursive(new Point2D.Double(0, 0));
    while (true) {
      Point2D p = mainCircle.getPosRecursive(new Point2D.Double(0, 0));
      StdDraw.line(lastPos.getX(), lastPos.getY(), p.getX(), p.getY());
      lastPos = p;
      StdDraw.pause(16);
    }
  }
}
