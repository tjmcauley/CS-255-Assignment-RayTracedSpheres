package com.example.cs255assignment;

import static java.lang.Math.*;

public class Camera {
    double azimuth;
    double altitude;
    double distance;
    Vector vrp = new Vector(0, 0, 0);
    Vector vuv = new Vector(0, 1, 0);
    Vector lookAt = new Vector(0, 0, 0);
    Vector vrv = new Vector(1, 0, 0);
    Vector vpn;

    public Camera(double azimuth, double altitude, double distance) {
        this.azimuth = azimuth;
        this.altitude = altitude;
        this.distance = distance;
        updateCameraVRP();
        updateCameraVectors();
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void updateCameraVRP() {
        //Calc vrp.x vrp.y vrp.z with distance
        double calcAzimuth = this.distance * sin(this.azimuth) * cos(this.altitude);
        double calcAltitude = this.distance * sin(this.azimuth) * sin(this.altitude);
        double calcDistance = this.distance * cos(this.azimuth);

        this.vrp.x = calcAzimuth;
        this.vrp.y = calcAltitude;
        this.vrp.z = calcDistance;
    }

    //Camera vectors that change the swivel of the camera (the C++ code)
    public void updateCameraVectors() {
        this.vpn = this.lookAt.sub(this.vrp);
        this.vpn.normalise();
        this.vrv = this.vpn.crossProduct(this.vuv);
        this.vrv.normalise();
        this.vuv = this.vrv.crossProduct(this.vpn);
        this.vuv.normalise();
    }

    public Vector getVRP() {
        return this.vrp;
    }

    public Vector getVRV() {
        return this.vrv;
    }

    public Vector getVUV() {
        return this.vuv;
    }

    public Vector getLookAt() {
        return this.lookAt;
    }

    public Vector getVPN() {
        return this.vpn;
    }
}
