import java.util.InputMismatchException;
import java.util.Scanner;
public class BattleshipGame {
    /* it contains a main method. In this class, you will set up the game, accept shots
       from the user as coordinates, display the results, print final scores, and ask the
       user if they want to play again. */

    public static void main (String[] args) {
        Ocean ocean = new Ocean();
        // initial display of the ocean
        ocean.placeAllShipsRandomly();
        while (!ocean.isGameOver()) {
            ocean.print();
            System.out.println("Please enter a number between 0 - 9 for row");
            Scanner scnr = new Scanner(System.in);
            int row = 0;
            while (true) {
                try {
                    row = scnr.nextInt();
                    if (row < 0 || row > 10) {
                        System.out.println("Please enter a number between 0 - 9 for row");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number between 0 - 9 for row");
                    scnr.next();
                }
            }
            System.out.println("Please enter a number between 0 - 9 for column");
            int col = 0;
            while (true) {
                try {
                    col = scnr.nextInt();
                    if (col < 0 || col > 10) {
                        System.out.println("Please enter a number between 0 - 9 for column");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number between 0 - 9 for column");
                    scnr.next();
                }
            }

            boolean shot = ocean.shootAt(row, col);
            if (shot) {
                System.out.println("hit");
            } else {
                System.out.println("miss");
            }
            if (ocean.ships[row][col].isSunk() && shot) {
                String shipType = ocean.ships[row][col].getShipType();
                System.out.println("You just sunk a " + shipType + " ");
            }
        }
        System.out.println("The game is over!");
        System.out.println("Score is " + ocean.getShotsFired());
    }
}
