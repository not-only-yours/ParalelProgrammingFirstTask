package secondTask.firstPart;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[][] matrix = generateMatrix(2, 2);
        int[][] matrix2 = generateMatrix(2, 2);
        int[][] multipledMatrixParallel = multipleMatrixParallelFox(matrix, matrix2);
        int[][] multipledMatrix = multipleMatrix(matrix, matrix2);


        printMatrix(matrix);
        printMatrix(matrix2);
        printMatrix(multipledMatrix);
//        printMatrix(multipledMatrixParallel);
//        printMatrix(multipledMatrixParallel);
//        multipleMatrix(matrix, matrix2);
    }

    private static int[][] multipleMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[Math.max(matrix1.length, matrix2.length)][Math.max(matrix1[0].length, matrix2[0].length)];

        long start = System.currentTimeMillis();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < result.length; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        long end = System.currentTimeMillis() - start;
        System.out.println("Simple multiplication: " + end);
        return result;
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

    private static int[][] multipleMatrixParallelFox(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][matrix1[0].length];
        Thread[][] threads = new Thread[result.length][result.length];
        int threadNum = (int) Math.pow(matrix1.length, 2);
        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                threads[i][j] = new Thread(() -> {
                    int tempI = (finalI + 1) % threads.length;
                    if (tempI == threads.length)
                        tempI = 0;
                    result[finalI][finalJ] += matrix1[finalI][tempI] * matrix2[tempI][finalJ];
                });
                threads[i][j].start();
                try {
                    threads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static int[][] multipleMatrixParallel(int[][] matrix1, int[][] matrix2, int numThreads) {
        int[][] result = new int[Math.max(matrix1.length, matrix2.length)][Math.max(matrix1[0].length, matrix2[0].length)];
        long start = System.currentTimeMillis();
        int rowsPerThread = (int) Math.round((double) result.length / numThreads);
        ParallelMatrixWorker.setRowsPerThread(rowsPerThread);
        for (int threadNumber = 0; threadNumber < numThreads; threadNumber++) {
            int startIndex = threadNumber * rowsPerThread;
            ParallelMatrixWorker parallelMatrixWorker = new ParallelMatrixWorker(startIndex, matrix1, matrix2, result);
            parallelMatrixWorker.start();
            try {
                parallelMatrixWorker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis() - start;
        System.out.println("Multithreading multiplication: " + end);
        return result;
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
}
