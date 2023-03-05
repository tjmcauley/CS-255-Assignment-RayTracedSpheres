package com.example.cs255assignment;

import javafx.scene.control.RadioButton;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static java.lang.Math.sqrt;

/**
 * @author Thomas McAuley, Cellan Lees
 *
 * Class that models a sphere vector
 */
public class Sphere extends Vector {
    private double r;
    private double g;
    private double b;
    private int radius;
    private RadioButton radioButton;
    private boolean selected;
    private Vector cos;

    /**
     *
     * @param x x coord of sphere
     * @param y y coord of sphere
     * @param z z coord of sphere
     * @param r red colour of sphere between 0 and 255
     * @param g green colour of sphere between 0 and 255
     * @param b blue colour of sphere between 0 and 255
     * @param radius radius of the sphere
     */
    public Sphere(int x, int y, int z, double r, double g, double b, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
        this.radius = radius;
        this.selected = false;
        this.cos = new Vector(x,y,z);
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

    public double getRadius() {
        return this.radius;
    }

    public RadioButton getLinkedButton() {
        return this.radioButton;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public Vector getCos(){
        return this.cos;
    }
}
