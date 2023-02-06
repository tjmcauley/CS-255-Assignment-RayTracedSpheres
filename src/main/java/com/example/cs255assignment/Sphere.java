package com.example.cs255assignment;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static java.lang.Math.sqrt;

public class Sphere {

    private int x;
    private int y;
    private int z;
    private double r;
    private double g;
    private double b;

    public Sphere() {

    }

    public Sphere(int x, int y, int z, double r, double g, double b) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setSphereX(int x) {
        this.x = x;
    }

    public void setSphereY(int y) {
        this.y = y;
    }

    public void setSphereZ(int z) {
        this.z = z;
    }

    public void setSphereR(double r) {
        this.r = r;
    }

    public void setSphereG(double g) {
        this.g = g;
    }

    public void setSphereB(double b) {
        this.b = b;
    }

    public int getSphereX() {
        return this.x;
    }

    public int getSphereY() {
        return this.y;
    }

    public int getSphereZ() {
        return this.z;
    }

    public Color getSphereColour() {
        return Color.color(r, g, b);
    }
}
