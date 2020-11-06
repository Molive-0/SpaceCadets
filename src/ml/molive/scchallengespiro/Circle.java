package ml.molive.scchallengespiro;

import java.awt.geom.Point2D;

public class Circle {
  Circle next; // The next circle attached to this one
  double rad; // The radius of the circle, in fractions of the screen
  double speed; // The speed of the rotation of the circle
  double rot; // Current rotation, in radians

  public Circle(double rad, double speed) {
    this.rad = rad;
    this.speed = speed;
    this.rot = 0;
  }

  public void setNext(Circle c) {
    next = c;
  }

  public Point2D getPosRecursive(Point2D pos) {
    Point2D myPos = new Point2D.Double();
    myPos.setLocation(Math.sin(rot) * rad + pos.getX(), Math.cos(rot) * rad + pos.getY());
    rot += speed;
    if (next == null) {
      return myPos;
    } else {
      return next.getPosRecursive(myPos);
    }
  }
}
