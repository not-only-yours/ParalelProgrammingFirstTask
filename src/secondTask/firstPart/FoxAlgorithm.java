package secondTask.firstPart;


public class FoxAlgorithm extends ParallelMatrixMultyplication implements Runnable {

    public FoxAlgorithm (int threadIndex,int[][] matrix1, int[][] matrix2, int[][] resultMatrix) {
        this.threadIndex = threadIndex;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = resultMatrix;
    }

    @Override
    public void run() {
        multiply();
    }

    @Override
    protected void multiply() {
        for (int i = threadIndex; i < threadIndex + rowsPerThread && i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix.length; j++) {
                resultMatrix[i][j] = matrix1[i][i] * matrix2[i][j];
            }
        }
        int stage = 1;
        while (stage != resultMatrix.length) {
            for (int i = threadIndex; i < threadIndex + rowsPerThread && i < resultMatrix.length; i++) {
                int temp = i + stage;
                for (int j = 0; j < resultMatrix.length; j++) {
                    resultMatrix[i][j] += matrix1[i][temp % resultMatrix.length] * matrix2[temp % resultMatrix.length][j];
                }
            }
            stage++;
        }
    }
}
