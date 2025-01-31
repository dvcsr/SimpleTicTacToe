package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String blank = "         ";
        char[] symbols = {'X', 'O'};
        String updateInput = blank;

        char[][] currentGrid = createGrid(blank);
        printGrid(currentGrid);

        while (!checkResult(updateInput, symbols) | !gridIsFull(updateInput)) {
            updateInput = getXUpdatedInput(scanner, updateInput);
            currentGrid = createGrid(updateInput);
            printGrid(currentGrid);
            if (checkResult(updateInput, symbols)){break;};
            if (gridIsFull(updateInput)) {
                break;
            }

            updateInput = getOUpdatedInput(scanner, updateInput);
            currentGrid = createGrid(updateInput);
            printGrid(currentGrid);
            if (checkResult(updateInput, symbols)){break;};
            if (gridIsFull(updateInput)) {
                break;
            }
        }
    }


    public static boolean gridIsFull(String input) {
        long xCount = input.chars().filter((int ch) -> ch == 'X').count();
        long oCount = input.chars().filter((int ch) -> ch == 'O').count();
        return xCount + oCount == 9;
    }
    public static String getXUpdatedInput(Scanner scanner, String initial) {
        while (true){
            String toCheck = getUserInput(scanner);
            String[] arr = toCheck.split(" ");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int combined = x * 10 + y;
            int charIndex = switch (combined){
                case 11 -> 0;
                case 12 -> 1;
                case 13 -> 2;
                case 21 -> 3;
                case 22 -> 4;
                case 23 -> 5;
                case 31 -> 6;
                case 32 -> 7;
                case 33 -> 8;
                default -> 9;
            };
            if (initial.charAt(charIndex) != 'X' & initial.charAt(charIndex) != 'O') {
                char[] charArr = initial.toCharArray();
                charArr[charIndex] = 'X';
                return String.valueOf(charArr);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    public static String getOUpdatedInput(Scanner scanner, String initial) {
        while (true){
            String toCheck = getUserInput(scanner);
            String[] arr = toCheck.split(" ");
            int x = Integer.parseInt(arr[0]);
            int y = Integer.parseInt(arr[1]);
            int combined = x * 10 + y;
            int charIndex = switch (combined){
                case 11 -> 0;
                case 12 -> 1;
                case 13 -> 2;
                case 21 -> 3;
                case 22 -> 4;
                case 23 -> 5;
                case 31 -> 6;
                case 32 -> 7;
                case 33 -> 8;
                default -> 9;
            };
            if (initial.charAt(charIndex) != 'X' & initial.charAt(charIndex) != 'O') {
                char[] charArr = initial.toCharArray();
                charArr[charIndex] = 'O';
                return String.valueOf(charArr);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    public static String getUserInput(Scanner scanner) {
        while (true){
            String input = scanner.nextLine();
            if (input.matches("[123]\\s[123]")){
                return input;
            }
            if (input.matches(".*[^0-9\\s].*")){
                System.out.println("You should enter numbers!");
            } else {
                System.out.println("Coordinates should be from 1 to 3!");
            }
        }
    }

    public static char[][] createGrid(String input){
        //implement mapping the char to 3x3 grid
        //check if it's possible for logic separation refactoring of grid and the input symbol
        char[][] grid = new char[5][9];
        int stringIndex = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                    if (i == 0 || i == 4){ //drawing top and bottom boundaries
                        grid[i][j] = '-';
                    }
                    else if (j == 0 || j == 8){ //drawing left and right boundaries
                        grid[i][j] = '|';
                    }
                    else if (((j % 2) == 0) & (j != 0) & (stringIndex < input.length())){
                        grid[i][j] = input.charAt(stringIndex);
                        stringIndex++;
                    }
                    else { grid[i][j] = ' ';}

                }
            }
        return grid;
            }


    public static void printGrid(char[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
                System.out.println();
            }
        }

    public static boolean checkResult(String input, char[] symbol){
        boolean isGameOver = false;
        char[][] board = new char [3][3];
        int inputIndex = 0;
        long xCount = input.chars().filter((int ch) -> ch == 'X').count();
        long oCount = input.chars().filter((int ch) -> ch == 'O').count();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (inputIndex < input.length()) {
                    board[i][j] = input.charAt(inputIndex);
                    inputIndex++;
                }
            }
        }

        if (checkWin(board, symbol[0]) & !checkWin(board, symbol[1])){
            System.out.println(symbol[0] + " wins");
            isGameOver = true;
        }
        else if (checkWin(board, symbol[1]) & !checkWin(board, symbol[0])){
            System.out.println(symbol[1] + " wins");
            isGameOver = true;
        }
        else if (xCount + oCount == 9){
            System.out.println("Draw");
            isGameOver = true;
        }
        else if ((checkWin(board, symbol[0]) & checkWin(board, symbol[0])) || Math.abs(xCount - oCount) > 1){
            System.out.println("Impossible");
        }
        else {
//            System.out.println("Game not finished");
        }
        return isGameOver;
    }


    public static boolean checkWin(char[][] board, char symbol) {

        int size = board.length;

        // Check rows and columns
        for (int i = 0; i < size; i++) {
            boolean rowWin = true;
            boolean colWin = true;
            for (int j = 0; j < size; j++) { //index swapping technique for simultaneous check of row and column in one nested loop
                if (board[i][j] != symbol) rowWin = false;
                if (board[j][i] != symbol) colWin = false;
            }
            if (rowWin || colWin) return true; // Win in row or column
        }

        // Check diagonals
        boolean diag1Win = true;
        boolean diag2Win = true;
        for (int i = 0; i < size; i++) {
            if (board[i][i] != symbol) diag1Win = false; // index is traversing to check first diagonal
            if (board[i][size - 1 - i] != symbol) diag2Win = false; // index is traversing to check second diagonal
        }
        if (diag1Win || diag2Win) return true; // Win in diagonal

        return false;
    }
}
