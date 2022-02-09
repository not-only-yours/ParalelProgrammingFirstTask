package secondTask.FoxAlgorithm;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FoxAlgorithm {
    int firstMatrixLength;
    int firstMatrixHigh;
    int secondMatrixLength;
    int secondMatrixHigh;

    int[][] matrix;
    int[][] matrix2;
    private int nThread;

    public FoxAlgorithm(int firstL, int firstH, int[][] matrix, int secondL, int secondH, int[][] matrix2, int nThread) {
        this.firstMatrixHigh = firstH;
        this.firstMatrixLength = firstL;
        this.secondMatrixHigh = secondH;
        this.secondMatrixLength = secondL;
        this.matrix = matrix;
        this.matrix2 = matrix2;
        this.nThread = nThread;
    }

    public FoxAlgorithm(int[][] matrix, int[][] matrix2, int nThread) {
        this.firstMatrixHigh = 2;
        this.firstMatrixLength = 2;
        this.secondMatrixHigh = 2;
        this.secondMatrixLength = 2;
        this.matrix = matrix;
        this.matrix2 = matrix2;
        this.nThread = nThread;
    }
    private int findNearestDivider(int s, int p) {
    /*
    https://ru.stackoverflow.com/questions/434403/%D0%9F%D0%BE%D0%B8%D1%81%D0%BA-%D0%B1%D0%BB%D0%B8%D0%B6%D0%B0%D0%B9%D1%88%D0%B5%D0%B3%D0%BE-%D0%B4%D0%B5%D0%BB%D0%B8%D1%82%D0%B5%D0%BB%D1%8F
     */
        int i = s;
        while (i > 1) {
            if (p % i == 0) break;
            if (i >= s) {
                i++;
            } else {
                i--;
            }
            if (i > Math.sqrt(p)) i = Math.min(s, p / s) - 1;
        }

        return i >= s ? i : i != 0 ? p / i : p;
    }

    public int[][] multiply() {
        int[][] C = new int[firstMatrixLength][secondMatrixHigh];

        if (!(firstMatrixLength == firstMatrixHigh
                & secondMatrixLength == secondMatrixHigh
                & firstMatrixLength == secondMatrixLength)) {
            try {
                throw new Exception("Matrix A and B have different dimensions!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        this.nThread = Math.min(this.nThread, firstMatrixLength);
        this.nThread = findNearestDivider(this.nThread, firstMatrixLength);
        int step = firstMatrixLength / this.nThread;

        ExecutorService exec = Executors.newFixedThreadPool(this.nThread);
        ArrayList<Future> threads = new ArrayList<>();

        int[][] matrixOfSizesI = new int[nThread][nThread];
        int[][] matrixOfSizesJ = new int[nThread][nThread];

        int stepI = 0;
        for (int i = 0; i < nThread; i++) {
            int stepJ = 0;
            for (int j = 0; j < nThread; j++) {
                matrixOfSizesI[i][j] = stepI;
                matrixOfSizesJ[i][j] = stepJ;
                stepJ += step;
            }
            stepI += step;
        }

        for (int l = 0; l < nThread; l++) {
            for (int i = 0; i < nThread; i++) {
                for (int j = 0; j < nThread; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % nThread];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % nThread];

                    int stepI2 = matrixOfSizesI[(i + l) % nThread][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % nThread][j];

                    FoxThread t =
                            new FoxThread(
                                    copyBlock(matrix, stepI1, stepJ1, step),
                                    copyBlock(matrix2, stepI2, stepJ2, step),
                                    C,
                                    stepI0,
                                    stepJ0);
                    threads.add(exec.submit( t));
                }
            }
        }

        for (Future mapFuture : threads) {
            try {
                mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        exec.shutdown();

        return C;
    }

    private int[][] copyBlock(int[][] matrix, int i, int j, int size) {
        int[][] block = new int[size][size];
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix[k + i], j, block[k], 0, size);
        }
        return block;
    }
}
