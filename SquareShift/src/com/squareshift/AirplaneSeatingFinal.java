package com.squareshift;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareshift.models.Seat;
import com.squareshift.models.SeatPrintSorter;
import com.squareshift.utils.Position;
import com.squareshift.utils.SeatType;
import com.squareshift.utils.Seating;

import java.util.*;

public class AirplaneSeatingFinal {
  static LinkedHashMap<Integer, Seating> seatings = null;
  static Integer totalPassenger = 0;

  public static void main(String[] args) {

    AirplaneSeatingFinal airplaneSeatingFinal = new AirplaneSeatingFinal();
    try {
      Scanner sc = new Scanner(System.in);

      System.out.println("Enter the airplane seat layout details: ");
      //      String seatLayoutString = "[[3,22],[4,13],[2,33],[3,43]]";
      String seatLayoutString = sc.nextLine();
      seatings = getZoneDetailsFromString(seatLayoutString);

      System.out.println("Enter the number of passengers: ");
      totalPassenger = sc.nextInt();
      //      totalPassenger = 530;

      List<Seat> filledSeats = airplaneSeatingFinal.fillSeatsWithPassangers();
      Collections.sort(filledSeats, new SeatPrintSorter());
      airplaneSeatingFinal.printSeatLayout(filledSeats);
      System.out.println();

    } catch (Exception e) {
      System.out.println("Given input is wrong. Please check");
      e.printStackTrace();
    }
  }

  private void printSeatLayout(List<Seat> filledSeats) {
    Seat lastSeat = null;
    for (Seat seat : filledSeats) {
      printTabs(lastSeat, seat);
      System.out.print(String.format("%03d\t", seat.value)); // Digit
      lastSeat = seat;
    }
  }

  private List<Seat> fillSeatsWithPassangers() {
    List<Seat> result = new ArrayList<>();
    int fillingRow = 0;
    int fillingvalue = 1;
    SeatType fillingSeatType = SeatType.AISLE;
    while (fillingvalue <= totalPassenger) {
      boolean atleastOneseatFilled = false;
      for (Seating seatLayout : seatings.values()) {
        int defaultSeatsCountAvailable =
            seatLayout.getDefaultSeatsCountAvailable(fillingRow, fillingSeatType);
        if (defaultSeatsCountAvailable > 0) {
          atleastOneseatFilled = true;
          List<Integer> defaultSeatsAvailable =
              seatLayout.getDefaultSeatsAvailable(fillingRow, fillingSeatType);
          for (Integer col : defaultSeatsAvailable) {
            if (fillingvalue <= totalPassenger) {
              result.add(new Seat(fillingRow, col, fillingvalue, fillingSeatType, seatLayout.zone));
              fillingvalue++;
            } else {
              break;
            }
          }
        }
      }
      fillingRow++;
      if (!atleastOneseatFilled) {
        if (fillingvalue <= totalPassenger
            && SeatType.values().length > fillingSeatType.ordinal() + 1) {
          fillingSeatType = SeatType.values()[fillingSeatType.ordinal() + 1];
          fillingRow = 0;
        } else {
          System.out.println(">>> Seats are Full for " + (fillingvalue - 1) + " Persons");
          break;
        }
      }
    }
    return result;
  }

  private void printTabs(Seat lastSeat, Seat seat) {
    if (lastSeat == null) return;
    int lastzone = lastSeat.zone;
    boolean zonereset = false;
    for (int i = lastSeat.x; i < seat.x; i++) {
      System.out.print("\n");
      lastzone = 0;
      zonereset = true;
    }

    for (int itrZone = lastzone; itrZone < seat.zone; itrZone++) {
      if (zonereset && lastzone != seat.zone) {
        for (int k = 0; k < seatings.get(itrZone).col; k++) {
          System.out.print("   \t"); // Digit
        }
      }
      System.out.print("\t|\t");
      if (!zonereset && lastzone + 1 != seat.zone) {
        for (int k = lastzone + 1; k < seat.zone; k++) {
          for (int i = 0; i < seatings.get(k).col; i++) {
            System.out.print("   \t"); // Digit
          }
        }
        lastzone++;
      }
    }

    for (int i = lastSeat.y; i < seat.y - 1; i++) {
      System.out.print("000\t"); // Digit
    }
  }

  private static LinkedHashMap<Integer, Seating> getZoneDetailsFromString(String seatLayoutString) {
    Gson gson = new GsonBuilder().create();
    Integer[][] seatlayout = gson.fromJson(seatLayoutString, Integer[][].class);
    LinkedHashMap<Integer, Seating> seatings = new LinkedHashMap<>();
    for (int i = 0; i < seatlayout.length; i++) {
      Position p = Position.MID;
      if (i == 0) {
        p = Position.START;
      }
      if (i == seatlayout.length - 1) {
        p = Position.END;
      }
      seatings.put(i, new Seating(seatlayout[i][0], seatlayout[i][1], p, i));
    }
    return seatings;
  }
}
