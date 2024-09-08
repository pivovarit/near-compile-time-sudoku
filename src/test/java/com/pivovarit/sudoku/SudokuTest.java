package com.pivovarit.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SudokuTest {

    // Rerun Automatically (IntelliJ IDEA)
    @Test
    void shouldBeSolved() {
        assertThat(Sudoku.SUDOKU.isSolved()).isTrue();
    }

    record Sudoku(int[][] rows) {

        public static final int SIZE = 9;
        public static final Sudoku SUDOKU = new Sudoku(new int[][]{
          {4, 3, 5, 2, 6, 9, 7, 8, 1},
          {6, 8, 2, 5, 7, 1, 4, 9, 3},
          {1, 9, 7, 8, 3, 4, 5, 6, 2},
          {8, 2, 6, 1, 9, 5, 3, 4, 7},
          {3, 7, 4, 6, 8, 2, 9, 1, 5},
          {9, 5, 1, 7, 4, 3, 6, 2, 8},
          {5, 1, 9, 3, 2, 6, 8, 7, 4},
          {2, 4, 8, 9, 5, 7, 1, 3, 6},
          {7, 6, 3, 4, 1, 8, 2, 5, 9}
        });

        public Sudoku {
            validate(rows);
        }

        public boolean isSolved() {
            return checkAll(rows) && checkAll(columns()) && checkAll(squares());
        }

        private boolean checkAll(int[][] blocks) {
            return Arrays.stream(blocks).allMatch(this::check);
        }

        private int[][] columns() {
            int[][] columns = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    columns[i][j] = rows[j][i];
                }
            }
            return columns;
        }

        private int[][] squares() {
            int[][] squares = new int[SIZE][SIZE];
            for (int block = 0; block < SIZE; block++) {
                for (int cell = 0; cell < SIZE; cell++) {
                    int row = 3 * (block / 3) + cell / 3;
                    int col = 3 * (block % 3) + cell % 3;
                    squares[block][cell] = rows[row][col];
                }
            }
            return squares;
        }

        private boolean check(int[] block) {
            boolean[] seen = new boolean[SIZE];
            return Arrays.stream(block).allMatch(n -> n > 0 && !seen[n - 1] && (seen[n - 1] = true));
        }

        private static void validate(int[][] rows) {
            if (rows.length != SIZE || Arrays.stream(rows)
              .anyMatch(row -> row.length != SIZE || Arrays.stream(row).anyMatch(n -> n < 1 || n > 9))) {
                throw new IllegalArgumentException("Invalid Sudoku board");
            }
        }
    }
}
