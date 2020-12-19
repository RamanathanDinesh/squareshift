package com.squareshift.models;

import java.util.Comparator;

public class SeatPrintSorter implements Comparator<Seat> {

  @Override
  public int compare(Seat o1, Seat o2) {
    if (o1.x == o2.x) {
      if (o1.zone == o2.zone) {
        return Integer.compare(o1.y, o2.y);
      } else {
        return Integer.compare(o1.zone, o2.zone);
      }
    } else {
      return Integer.compare(o1.x, o2.x);
    }
  }
}
