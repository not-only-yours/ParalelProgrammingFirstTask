package secondTask.firstPart;

public class Result {
    int[][] matrix;
    int length;
    int high;
    double countingTime;
    String typeofAlgorithm;

    public int[][] getMatrix() {
        return matrix;
    }

    public int getLength() {
        return length;
    }

    public int getHigh() {
        return high;
    }

    public double getCountingTime() {
        return countingTime;
    }

    public String getTypeofAlgorithm() {
        return typeofAlgorithm;
    }


    public Result(int[][] matrix, int length, int high, double countingTime, String typeofAlgorithm) {
        this.length = length;
        this.high = high;
        this.countingTime = countingTime;
        this.typeofAlgorithm = typeofAlgorithm;
        this.matrix = matrix;
    }
}
