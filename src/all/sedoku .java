package all;

import java.util.Scanner;
import java.util.Random;

public class arrays {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        int[][] board = new int[9][9];
        int[][] puzzle = new int[9][9];
        boolean[][] isFixed = new boolean[9][9];
        int lives = 3;

        System.out.println("--- SUDOKU WITH LIVES ---");
        System.out.println("1. Easy\n2. Medium\n3. Hard");
        System.out.print("Select Difficulty: ");
        int level = scan.hasNextInt() ? scan.nextInt() : 1;
        int clues = (level == 2) ? 30 : (level == 3 ? 20 : 40);

        for (int i = 0; i < 9; i++) board[0][i] = i + 1;
        for (int i = 0; i < 9; i++) {
            int r = rand.nextInt(9);
            int temp = board[0][i];
            board[0][i] = board[0][r];
            board[0][r] = temp;
        }

        int current = 9;
        while (current < 81) {
            int row = current / 9;
            int col = current % 9;

            if (current < 9) {
                current = 9;
                for (int i = 0; i < 9; i++) {
                    int r = rand.nextInt(9);
                    int temp = board[0][i];
                    board[0][i] = board[0][r];
                    board[0][r] = temp;
                }
                for (int r = 1; r < 9; r++) {
                    for (int c = 0; c < 9; c++) board[r][c] = 0;
                }
                continue;
            }

            board[row][col]++;

            if (board[row][col] > 9) {
                board[row][col] = 0;
                current--;
                continue;
            }

            boolean valid = true;
            for (int i = 0; i < 9; i++) {
                if (i != col && board[row][i] == board[row][col]) valid = false;
                if (i != row && board[i][col] == board[row][col]) valid = false;
            }
            int boxRow = (row / 3) * 3;
            int boxCol = (col / 3) * 3;
            for (int r = boxRow; r < boxRow + 3; r++) {
                for (int c = boxCol; c < boxCol + 3; c++) {
                    if ((r != row || c != col) && board[r][c] == board[row][col]) valid = false;
                }
            }

            if (valid) current++;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) puzzle[i][j] = board[i][j];
        }

        int holes = 81 - clues;
        while (holes > 0) {
            int r = rand.nextInt(9);
            int c = rand.nextInt(9);
            if (puzzle[r][c] != 0) {
                puzzle[r][c] = 0;
                holes--;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != 0) isFixed[i][j] = true;
            }
        }

        while (lives > 0) {
            System.out.println("\n  LIVES: " + lives);
            System.out.println("   1 2 3   4 5 6   7 8 9");
            System.out.println(" +-------+-------+-------+");
            for (int r = 0; r < 9; r++) {
                System.out.print((r + 1) + "| ");
                for (int c = 0; c < 9; c++) {
                    if (puzzle[r][c] == 0) System.out.print(". ");
                    else System.out.print(puzzle[r][c] + " ");
                    
                    if ((c + 1) % 3 == 0) System.out.print("| ");
                }
                System.out.println();
                if ((r + 1) % 3 == 0) System.out.println(" +-------+-------+-------+");
            }

            boolean won = true;
            for(int r=0; r<9; r++) {
                for(int c=0; c<9; c++) {
                    if(puzzle[r][c] == 0) won = false;
                }
            }
            if(won) {
                System.out.println("\n*** YOU WON! ***");
                break;
            }

            System.out.print("Enter Row Col Value (e.g., 1 1 5): ");
            try {
                int r = scan.nextInt() - 1;
                int c = scan.nextInt() - 1;
                int val = scan.nextInt();

                if (r < 0 || r > 8 || c < 0 || c > 8 || val < 1 || val > 9) {
                    System.out.println(">> ERROR: Numbers must be 1-9.");
                    continue;
                }

                if (isFixed[r][c]) {
                    System.out.println(">> ERROR: You can't change the starting numbers!");
                } else if (val == board[r][c]) {
                    System.out.println(">> Correct!");
                    puzzle[r][c] = val;
                } else {
                    lives--;
                    System.out.println(">> WRONG! That is not the correct number.");
                    
                    if(lives == 0) {
                        System.out.println("\n!!! GAME OVER !!!");
                        System.out.println("Here is the solution:");
                        System.out.println("   1 2 3   4 5 6   7 8 9");
                        System.out.println(" +-------+-------+-------+");
                        for (int rr = 0; rr < 9; rr++) {
                            System.out.print((rr + 1) + "| ");
                            for (int cc = 0; cc < 9; cc++) {
                                System.out.print(board[rr][cc] + " ");
                                if ((cc + 1) % 3 == 0) System.out.print("| ");
                            }
                            System.out.println();
                            if ((rr + 1) % 3 == 0) System.out.println(" +-------+-------+-------+");
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(">> ERROR: Please enter valid numbers.");
                scan.next();
            }
        }
    }
}