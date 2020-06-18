package com.vitaheng.pojo;

public class Recommend {
    private int mid;
    private double score;

    public Recommend() {
    }

    public Recommend(int mid, double score) {
        this.mid = mid;
        this.score = score;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
