package battleship;

import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);

    public static void putShips(Field field) {
        for (Ship ship : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                    ship.getName(),
                    ship.getCells());
            String[] coordinates = scanner.nextLine().toUpperCase().split("\\s");
            System.out.println();
            State state;

            while (true) {
                if (coordinates.length < 2) state = State.LOCATION;
                else state = field.examine(coordinates[0], coordinates[1], ship.getCells());
                if (state.equals(State.CORRECT)) break;
                for (State s : State.values()) {
                    if (state.equals(s))
                        System.out.printf("Error! %s Try again:\n", s.getText());
                }
                coordinates = scanner.nextLine().toUpperCase().split("\\s");
                System.out.println();
            }

            field.putShip(coordinates[0], coordinates[1]);
            System.out.println(field);
        }
    }

    public static void enterCoordinates(Field field, String player) {
        System.out.println(player + ", place your ships on the game field\n");
        System.out.println(field);
        putShips(field);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

    public static void play() {
        Field playerOne = new Field();
        Field playerTwo = new Field();

        enterCoordinates(playerOne, "Player One");
        enterCoordinates(playerTwo, "Player Two");

        System.out.println("The game starts!\n");
        while(!playerOne.check() && !playerTwo.check()) {
            move(playerOne, playerTwo, "Player One");
            move(playerTwo, playerOne, "Player Two");
        }

    }

    public static void move(Field playerOne, Field playerTwo, String player) {
        System.out.printf("%s\n%s\n%s\n",playerTwo.getFog().trim(), "-".repeat(21), playerOne);
        System.out.println(player + ", it's your turn:");
        String coordinate = scanner.nextLine().toUpperCase();

        while (!playerTwo.isValid(coordinate).equals(State.CORRECT)) {
            System.out.println("Error! You entered the wrong coordinates Try again:");
            coordinate = scanner.nextLine().toUpperCase();
        }

        String state = playerTwo.shot(coordinate.toUpperCase()).getText();
        if (playerTwo.check()) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        } else {
            System.out.println(state);
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        play();
    }
}
