package com.squareshift.models;

import com.squareshift.utils.SeatType;

public class Seat {
  public int zone;
  public int x;
  public int y;
  public Object value;
  public SeatType type;

  public Seat(int row, int col, Object value, SeatType type, int zone) {
    this.x = row;
    this.y = col;
    this.value = value;
    this.type = type;
    this.zone = zone;
  }

  @Override
  public String toString() {
    return "Seat{"
        + "zone="
        + zone
        + ", x="
        + x
        + ", y="
        + y
        + ", value="
        + value
        + ", type="
        + type
        + '}';
  }
}
