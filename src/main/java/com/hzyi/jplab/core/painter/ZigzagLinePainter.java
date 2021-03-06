package com.hzyi.jplab.core.painter;

import com.hzyi.jplab.core.model.shape.Appearance;
import com.hzyi.jplab.core.model.shape.ZigzagLine;
import com.hzyi.jplab.core.util.Coordinate;
import com.hzyi.jplab.core.util.Coordinates;

public class ZigzagLinePainter {

  private final JavaFxPainter painter;

  ZigzagLinePainter(JavaFxPainter painter) {
    this.painter = painter;
  }

  public void paint(
      Coordinate pointU, Coordinate pointV, double theta, ZigzagLine line, Appearance appearance) {
    double x = pointU.x();
    double y = pointU.y();
    double length = Coordinates.distance(pointU, pointV);
    double zigzagLength = length / (line.zigzagCount() + 1);
    Coordinate previous = pointU;
    boolean clockwise = true;

    // the first zigzag only takes up half the length
    Coordinate next =
        getZigzagBoundaryPoint(pointU, line.width() / 2, theta, zigzagLength / 2, clockwise);
    for (int i = 0; i <= line.zigzagCount(); i++) {
      painter.drawLine(previous, next, appearance);
      previous = next;
      clockwise = !clockwise;
      next = getZigzagBoundaryPoint(previous, line.width(), theta, zigzagLength, clockwise);
    }
    painter.drawLine(previous, pointV, appearance);
  }

  private static Coordinate getZigzagBoundaryPoint(
      Coordinate start, double width, double theta, double lengthFromStart, boolean clockwise) {
    // First, move from A along the zigzag line by lengthFromStart
    Coordinate destination =
        new Coordinate(lengthFromStart * Math.cos(theta), lengthFromStart * Math.sin(theta));
    destination.x(destination.x() + start.x());
    destination.y(destination.y() + start.y());

    // Secondly, we move to the edge of the line
    theta = theta + Math.PI / 2.0 * (clockwise ? 1 : -1);
    destination.x(destination.x() + width * Math.cos(theta));
    destination.y(destination.y() + width * Math.sin(theta));

    return destination;
  }
}
