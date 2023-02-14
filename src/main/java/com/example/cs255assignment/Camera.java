package com.example.cs255assignment;

public class Camera {
    private int x;
    private int y;
    private int z;

    public Camera() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public void setCamX(int x) {
        this.x = x;
    }

    public void setCamY(int y) {
        this.y = y;
    }

    public void setCamZ(int z) {
        this.z = z;
    }

    public int getCamX() {
        return this.x;
    }

    public int getCamY() {
        return this.y;
    }

    public int getCamZ() {
        return this.z;
    }
}
