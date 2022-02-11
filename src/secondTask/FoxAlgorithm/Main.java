package secondTask.FoxAlgorithm;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int firstMatrixLength = 2;
        int firstMatrixHigh = 2;
        int secondMatrixLength = 2;
        int secondMatrixHigh = 2;
        int numberOfThreads = 1;
        int[][] matrix = generateMatrix(firstMatrixLength, firstMatrixHigh);
        int[][] matrix2 = generateMatrix(secondMatrixLength, secondMatrixHigh);

        FoxAlgorithm fa = new FoxAlgorithm(firstMatrixLength, firstMatrixHigh, matrix,secondMatrixLength, secondMatrixHigh, matrix2, numberOfThreads);

        printMatrix(matrix);
        printMatrix(matrix2);
        int currTime = (int) System.nanoTime();
        int[][] C = fa.multiply();
        currTime = (int) (System.nanoTime() - currTime);

        printMatrix(C);

        System.out.println("Time for Fox Algorithm: " + currTime / 1_000_000);
        System.out.println("\n");
    }


    private static int[][] generateMatrix(int n, int m) {
        Random r = new Random();
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = r.nextInt(20);
            }
        }
        return a;
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
