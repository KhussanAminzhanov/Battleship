package battleship;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final char[][] field = new char[10][10];
    private final char[][] fog = new char[10][10];
    private final List<String> ships = new ArrayList<>();

    public Field() {
        initiate(field);
        initiate(fog);
    }

    public State examine(String s, String e, int length) {
        if (!isValid(s).equals(State.CORRECT)) return State.LOCATION;
        if (!isValid(e).equals(State.CORRECT)) return State.LOCATION;

        int[] start = new int[]{s.charAt(0) - 65, Integer.parseInt(s.substring(1))};
        int[] end = new int[]{e.charAt(0) - 65, Integer.parseInt(e.substring(1))};
        if (start[0] == end[0]) {
            int max = Math.max(start[1], end[1]);
            int min = Math.min(start[1], end[1]);
            if (max - min + 1 != length) return State.LENGTH;
            for (int i = min - 1; i < max; i++) {
                if (field[start[0]][i] != '~') return State.LOCATION;
                if (isClose(start[0], i)) return State.CLOSE;
            }
        } else if (start[1] == end[1]) {
            int max = Math.max(start[0], end[0]);
            int min = Math.min(start[0], end[0]);
            if (max - min + 1 != length) return State.LENGTH;
            for (int i = min; i < max + 1; i++) {
                if (field[i][start[1] - 1] != '~') return State.LOCATION;
                if (isClose(i, start[1] - 1)) return State.CLOSE;
            }
        } else {
            return State.LOCATION;
        }
        return State.CORRECT;
    }

    public State isValid(String coordinate) {
        if (!coordinate.substring(0, 1).matches("(?i)[a-j]")) return State.LOCATION;
        try {
            int col = Integer.parseInt(coordinate.substring(1));
            if (col < 0 || col > 10) return State.LOCATION;
        } catch (NumberFormatException e) {
            return State.LOCATION;
        }
        return State.CORRECT;
    }

    private boolean isClose(int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row + i >= 0 && row + i <= 9 && col + j >= 0 && col + j <= 9) {
                    if (field[row+i][col+j] == 'O') return true;
                }
            }
        }
        return false;
    }

    private boolean isShipSank() {
        for (String ship : ships) {
            String s = ship.split(" ")[0];
            String e = ship.split(" ")[1];
            int[] start = new int[]{s.charAt(0) - 65, Integer.parseInt(s.substring(1))};
            int[] end = new int[]{e.charAt(0) - 65, Integer.parseInt(e.substring(1))};
            boolean isSank = true;

            if (start[0] == end[0]) {
                int max = Math.max(start[1], end[1]);
                int min = Math.min(start[1], end[1]);
                for (int i = min - 1; i < max; i++) {
                    if (field[start[0]][i] == 'O') {
                        isSank = false;
                        break;
                    }
                }
            } else if (start[1] == end[1]) {
                int max = Math.max(start[0], end[0]);
                int min = Math.min(start[0], end[0]);
                for (int i = min; i < max + 1; i++) {
                    if (field[i][start[1] - 1] == 'O') {
                        isSank = false;
                        break;
                    }
                }
            }
            if (isSank) {
                ships.remove(ship);
                return true;
            }
        }
        return false;
    }

    public boolean check() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 'O' && fog[i][j] != 'X') return false;
            }
        }
        return true;
    }

    public State shot(String c) {
        int[] coordinate = new int[]{c.charAt(0) - 65, Integer.parseInt(c.substring(1)) - 1};
        char cell = field[coordinate[0]][coordinate[1]];
        char mark = cell == 'O' ? 'X' : 'M';
        fog[coordinate[0]][coordinate[1]] = mark;
        field[coordinate[0]][coordinate[1]] = mark;
        if (isShipSank()) return State.SANK;
        return mark == 'X' ? State.HIT : State.MISSED;
    }

    public void putShip(String s, String e) {
        int[] start = new int[]{s.charAt(0) - 65, Integer.parseInt(s.substring(1))};
        int[] end = new int[]{e.charAt(0) - 65, Integer.parseInt(e.substring(1))};
        ships.add(s + " " + e);

        if (start[0] == end[0]) {
            int max = Math.max(start[1], end[1]);
            int min = Math.min(start[1], end[1]);
            for (int i = min - 1; i < max; i++) {
                field[start[0]][i] = 'O';
            }
        } else if (start[1] == end[1]) {
            int max = Math.max(start[0], end[0]);
            int min = Math.min(start[0], end[0]);
            for (int i = min; i < max + 1; i++) {
                field[i][start[1] - 1] = 'O';
            }
        }
    }

    private void initiate(char[][] arr) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) arr[i][j] = '~';
        }
    }

    @Override
    public String toString() {
        return convert(field);
    }

    public String getFog() {
        return convert(fog);
    }

    private String convert(char[][] arr) {
        StringBuilder str = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < 10; i++) {
            str.append((char) (i + 65));
            for (int j = 0; j < 10; j++) {
                str.append(" ").append(arr[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

}
