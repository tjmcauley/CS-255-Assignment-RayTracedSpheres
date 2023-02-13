package com.example.cs255assignment;

import javafx.scene.control.RadioButton;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static java.lang.Math.sqrt;

public class Sphere extends Vector {
    private double r;
    private double g;
    private double b;
    private int radius;
    private RadioButton radioButton;
    private boolean selected = false;

    private double smallestT;

    public Sphere() {

    }

    public Sphere(int x, int y, int z, double r, double g, double b, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
        this.radius = radius;
        this.selected = false;
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

    public void setSphereRadius(int radius) {
        this.radius = radius;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public void setSelect(Boolean selected) {
        this.selected = selected;
    }

    public int getSphereRadius() {
        return this.radius;
    }

    public double getSphereX() {
        return super.x;
    }

    public double getSphereY() {
        return super.y;
    }

    public double getSphereZ() {
        return super.z;
    }

    public double getSphereR() {
        return this.r / 255;
    }

    public double getSphereG() {
        return this.g / 255;
    }

    public double getSphereB() {
        return this.b / 255;
    }

    public Color getSphereColour() {
        return Color.color(this.r / 255, this.g / 255, this.b / 255);
    }

    public double getRadius() {
        return this.radius;
    }

    public RadioButton getLinkedButton() {
        return this.radioButton;
    }

    public Boolean isLinked() {
        if (this.radioButton == null) {
            return false;
        }
        return true;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public void setSmallestT(double smallestT) {
        this.smallestT = smallestT;
    }

    public double getSmallestT() {
        return this.smallestT;
    }

    public void renderSphere(WritableImage image, Vector rayOrigin, Vector rayDirection, Vector light) {
    }
}
