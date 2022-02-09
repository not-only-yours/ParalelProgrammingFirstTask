package secondTask.FoxAlgorithm;

public class FoxThread extends Thread{
    private final int firstMatrixLength;
    private final int firstMatrixHigh;
    private final int secondMatrixLength;
    private final int secondMatrixHigh;
    private final int thirdMatrixLength;
    private final int thirdMatrixHigh;
    private final int[][] matrix;
    private final int[][] matrix2;
    private final int[][] matrix3;

    private final int stepI;
    private final int stepJ;

    public FoxThread(int firstL, int firstH, int[][] matrix, int secondL, int secondH, int[][] matrix2, int thirdL, int thirdH, int[][] matrix3, int stepI, int stepJ ) {
        this.firstMatrixHigh = firstH;
        this.firstMatrixLength = firstL;
        this.secondMatrixHigh = secondH;
        this.secondMatrixLength = secondL;
        this.thirdMatrixHigh = thirdH;
        this.thirdMatrixLength = thirdL;
        this.matrix = matrix;
        this.matrix2 = matrix2;
        this.matrix3 = matrix3;
        this.stepI = stepI;
        this.stepJ = stepJ;
    }

    public FoxThread(int[][] matrix, int[][] matrix2, int[][] matrix3, int stepI, int stepJ) {
        this.firstMatrixHigh = 2;
        this.firstMatrixLength = 2;
        this.secondMatrixHigh = 2;
        this.secondMatrixLength = 2;
        this.thirdMatrixHigh = 2;
        this.thirdMatrixLength = 2;
        this.matrix = matrix;
        this.matrix2 = matrix2;
        this.matrix3 = matrix3;
        this.stepI = stepI;
        this.stepJ = stepJ;
    }

    @Override
    public void run() {
        int[][] blockRes = multiplyBlock();

        for (int i = 0; i < firstMatrixHigh; i++) {
            for (int j = 0; j < secondMatrixLength; j++) {
                matrix3[i + stepI][j + stepJ] += blockRes[i][j];
            }
        }
        // System.out.println(Thread.currentThread().getName());
    }

    private int[][] multiplyBlock() {
        int[][] blockRes = new int[firstMatrixHigh][secondMatrixLength];
        for (int i = 0; i < firstMatrixLength; i++) {
            for (int j = 0; j < secondMatrixHigh; j++) {
                for (int k = 0; k < firstMatrixHigh; k++) {
                    blockRes[i][j] += matrix[i][k] * matrix2[k][j];
                }
            }
        }
        return blockRes;
    }
}
