package com.example.quanlynhatroapplication.Class;

public class Food {
    private String name;
    private String linkImage;
    private int timeProcessing;
    private double price;
    private double score;

    public Food() {
    }

    public Food(String name, String linkImage, int timeProcessing, double price, double score) {
        this.name = name;
        this.linkImage = linkImage;
        this.timeProcessing = timeProcessing;
        this.price = price;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public int getTimeProcessing() {
        return timeProcessing;
    }

    public void setTimeProcessing(int timeProcessing) {
        this.timeProcessing = timeProcessing;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
