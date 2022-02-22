package secondTask.firstPart;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println(String.format("%10s %5s %10s %5s %10s %10s %10s %10s %10s %10s %10s","Length", "|", "high", "|", "numOf threads", "|", "countingTime", "|", "typeOf algorithm", "|", "isEquals"));
        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
        printResults(new int[] {2,2}, new int[] {2,2}, 1,1);
        printResults(new int[] {2,2}, new int[] {2,2}, 2,2);
        printResults(new int[] {3,3}, new int[] {3,3}, 1,1);
        printResults(new int[] {3,3}, new int[] {3,3}, 3,3);
        printResults(new int[] {5,5}, new int[] {5,5}, 1,1);
        printResults(new int[] {5,5}, new int[] {5,5}, 5,5);
        printResults(new int[] {9,9}, new int[] {9,9}, 1,1);
        printResults(new int[] {9,9}, new int[] {9,9}, 5,5);
        printResults(new int[] {9,9}, new int[] {9,9}, 9,9);
    }

    private static void printResults(int[] fitstSize, int[] secondSize,int blockThreads, int foxThreads) {
        int[][] matrix = generateMatrix(fitstSize[0], fitstSize[1]);
        int[][] matrix2 = generateMatrix(secondSize[0], secondSize[1]);
        Result multipledMatrix = multipleMatrixParallelBlockStriped(matrix, matrix2,blockThreads);
        Result foxMatrix = multipleMatrixParallelFox(matrix, matrix2,foxThreads);
        if( isTwoMatricesEqual(multipledMatrix.getMatrix(), foxMatrix.getMatrix())) {
            //System.out.println("Matrix equals");
            //printMatrix(multipledMatrix.getMatrix());
            System.out.println(String.format("%10s %5s %10s %5s %10s %10s %10s %10s %10s %10s %10s", multipledMatrix.getLength(), "|", multipledMatrix.getHigh(),"|", blockThreads, "|", multipledMatrix.getCountingTime(), "|", multipledMatrix.getTypeofAlgorithm(), "|", isTwoMatricesEqual(multipledMatrix.getMatrix(), foxMatrix.getMatrix())));
            System.out.println(String.format("%10s %5s %10s %5s %10s %10s %10s %10s %10s %10s %10s",foxMatrix.getLength(), "|", foxMatrix.getHigh(),"|", foxThreads, "|", foxMatrix.getCountingTime(), "|", foxMatrix.getTypeofAlgorithm(), "|", isTwoMatricesEqual(multipledMatrix.getMatrix(), foxMatrix.getMatrix())));
            System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
        }
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

    public static Result multipleMatrixParallelBlockStriped(int[][] matrix1, int[][] matrix2, int numThreads) {
        int[][] result = new int[matrix1.length][matrix1.length];
        int rowsPerThread = ParallelMatrixWorker.applyRowsPerThread(numThreads,result.length);
        long start = System.currentTimeMillis();
        Thread[] parallelStringMatrixThreads=new Thread[numThreads];
        for (int threadNumber = 0; threadNumber < numThreads; threadNumber++) {
            int startIndex = threadNumber * rowsPerThread;
            parallelStringMatrixThreads[threadNumber] = new Thread(new ParallelMatrixWorker(startIndex, matrix1, matrix2, result));
            parallelStringMatrixThreads[threadNumber].start();
        }
        waitMatrixThreads(parallelStringMatrixThreads);

        //Time there

        long end = System.currentTimeMillis() - start;

        return new Result(result, result.length, result[0].length, (double) end/1000, "block");
    }

    private static void waitMatrixThreads(Thread[] threads){
        for (Thread thread:threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Result multipleMatrixParallelFox(int[][] matrix1, int[][] matrix2, int numThreads) {
        int[][] result = new int[matrix1.length][matrix1.length];
        int rowsPerThread = ParallelMatrixMultyplication.applyRowsPerThread(numThreads,result.length);
        long start = System.currentTimeMillis();
        Thread[] parallelFoxMatrixThreads=new Thread[numThreads];
        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            int startIndex = threadIndex * rowsPerThread;
            parallelFoxMatrixThreads[threadIndex] = new Thread(new FoxAlgorithm(startIndex, matrix1, matrix2, result));
            parallelFoxMatrixThreads[threadIndex].start();
        }
        waitMatrixThreads(parallelFoxMatrixThreads);
        long end = System.currentTimeMillis() - start;
        return new Result(result, result.length, result[0].length, (double) end/1000, "fox");
    }

    public static boolean isTwoMatricesEqual(int[][] matrix1, int[][] matrix2) {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                if (matrix1[i][j] != matrix2[i][j])
                    return false;
            }
        }
        return true;
    }
}
