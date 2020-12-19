package com.squareshift.utils;

import java.util.ArrayList;
import java.util.List;

public class Seating {
  public int col;
  public int row;
  public Position pos;
  public int zone;
  public int seatsCount = 0;
  public int aisleSeatsCount = 0;
  public int windowSeatsCount = 0;
  public int middleSeatsCount = 0;
  public int aisleSeatsPerRowCount = 0;
  public int windowSeatsPerRowCount = 0;
  public int middleSeatsPerRowCount = 0;

  public List<Integer> aisle = new ArrayList<>();
  public List<Integer> window = new ArrayList<>();
  public List<Integer> middle = new ArrayList<>();

  public Seating(int col, int row, Position pos, int zone) {
    this.col = col;
    this.row = row;
    this.pos = pos;
    this.zone = zone;
    this.seatsCount = row * col;
    if (pos == Position.START || pos == Position.END) {
      aisleSeatsPerRowCount = 1;
      aisleSeatsCount = 1 * row;
      windowSeatsPerRowCount = 1;
      windowSeatsCount = 1 * row;
    } else {
      aisleSeatsPerRowCount = 2;
      aisleSeatsCount = 2 * row;
    }
    //    middleSeatsCount = (seatsCount - windowSeatsCount - aisleSeatsCount);
    middleSeatsPerRowCount = (col - 2);
    middleSeatsCount = (col - 2) * row;

    if (this.pos == Position.START) {
      window.add(0);
    }
    if (this.pos == Position.MID || this.pos == Position.END) {
      aisle.add(0);
    }
    if (this.pos == Position.START || this.pos == Position.MID) {
      aisle.add(this.col - 1);
    }

    if (this.pos == Position.END) {
      window.add(this.col - 1);
    }
    populateCenterSeatLayouts(0, this.col - 1);
  }

  private void populateCenterSeatLayouts(int i, int i1) {
    for (int j = i + 1; j < i1; j++) {
      middle.add(j);
    }
  }

  public int getDefaultSeatsCountAvailable(int row, SeatType type) {
    switch (type) {
      case AISLE:
        if (row < this.row) {
          return aisleSeatsPerRowCount;
        } else {
          return 0;
        }
      case CENTER:
        if (row < this.row) {
          return middleSeatsPerRowCount;
        } else {
          return 0;
        }
      case WINDOW:
        if (row < this.row) {
          return windowSeatsPerRowCount;
        } else {
          return 0;
        }
    }
    return 0;
  }

  public List<Integer> getDefaultSeatsAvailable(int row, SeatType type) {
    switch (type) {
      case AISLE:
        if (row <= this.row) {
          return aisle;
        }
        break;
      case CENTER:
        if (row <= this.row) {
          return middle;
        }
        break;
      case WINDOW:
        if (row <= this.row) {
          return window;
        }
        break;
    }
    return new ArrayList<>();
  }
}
