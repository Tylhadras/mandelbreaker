package edu.returnsvoid.main.delegator;

public class SubImage {

    private final double subImageMinCReal;
    private final double subImageMaxCReal;
    private final double subImageMinCImg;
    private final double subImageMaxCImg;
    private int[][] data;

    public SubImage(double subImageMinCReal, double subImageMaxCReal, double subImageMinCImg, double subImageMaxCImg) {
        this.subImageMinCReal = subImageMinCReal;
        this.subImageMaxCReal = subImageMaxCReal;
        this.subImageMinCImg = subImageMinCImg;
        this.subImageMaxCImg = subImageMaxCImg;
    }

    public double getSubImageMinCReal() {
        return subImageMinCReal;
    }

    public double getSubImageMaxCReal() {
        return subImageMaxCReal;
    }

    public double getSubImageMinCImg() {
        return subImageMinCImg;
    }

    public double getSubImageMaxCImg() {
        return subImageMaxCImg;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int[][] getData() {
        return this.data;
    }
}
