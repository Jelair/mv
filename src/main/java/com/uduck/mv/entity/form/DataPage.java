package com.uduck.mv.entity.form;

public class DataPage {

    private int start;
    private int length;

    private String direction;
    private String orderBy;

    public DataPage() {
        this.start = 0;
        this.length = 6;
        this.direction = "desc";
        this.orderBy = "id";
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
