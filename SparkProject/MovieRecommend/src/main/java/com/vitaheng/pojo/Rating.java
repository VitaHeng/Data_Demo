package com.vitaheng.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Rating {

    @JsonIgnore
    private String _id;
    private int uid;
    private int mid;
    private double score;
    private long timestamp;

    public Rating() {
    }

    public Rating(String _id, int uid, int mid, double score, long timestamp) {
        this._id = _id;
        this.uid = uid;
        this.mid = mid;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
