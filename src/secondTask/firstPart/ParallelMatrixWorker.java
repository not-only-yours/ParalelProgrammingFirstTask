package secondTask.firstPart;

public class ParallelMatrixWorker extends Thread {
    private static int rowsPerThread;
    private final int threadIndex;
    private final int[][] matrix1;
    private final int[][] matrix2;
    private final int[][] resultMatrix;

    public static void setRowsPerThread(int rows) {
        rowsPerThread = rows;
    }

    public ParallelMatrixWorker(int threadIndex, int[][] matrix1, int[][] matrix2, int[][] resultMatrix) {
        this.threadIndex = threadIndex;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void run() {
        for (int a = threadIndex; a < threadIndex + rowsPerThread && a < resultMatrix.length; a++) {
            for (int i = 0; i < resultMatrix.length; i++) {
                for (int j = 0; j < resultMatrix[0].length; j++) {
                    resultMatrix[a][i] += matrix1[a][j] * matrix2[j][i];
                }
            }
        }

    }
}
