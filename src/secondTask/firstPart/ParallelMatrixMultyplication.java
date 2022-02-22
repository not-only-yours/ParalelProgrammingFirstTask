package secondTask.firstPart;
public abstract class ParallelMatrixMultyplication {
    protected static int rowsPerThread;
    protected  int threadIndex;
    protected  int[][] matrix1;
    protected int[][] matrix2;
    protected int[][] resultMatrix;

    public static int applyRowsPerThread(int numThreads,int resultLength) {
        rowsPerThread = resultLength > numThreads ? (int) Math.round((double) resultLength / numThreads) : 1;
        while (rowsPerThread*numThreads<resultLength){
            rowsPerThread++;
        }
        return rowsPerThread;
    }

    protected abstract void multiply();
}
