package com.example.cs255assignment;

import static java.lang.Math.*;

public class Camera {
    double azimuth;
    double altitude;
    double distance;
    Vector vrp = new Vector(0, 0, 0);
    Vector vuv = new Vector(0, 1, 0);
    Vector lookAt = new Vector(0, 0, 0);
    Vector vpn;
    Vector vrv;

    public Camera (double azimuth, double altitude, double distance) {
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

    //View reference point
    public void updateCameraVRP() {
        //Calc vrp.x vrp.y vrp.z with distance
        this.vrp.x = this.distance * cos(this.azimuth);
        this.vrp.y = this.distance * sin(this.azimuth) * cos(this.altitude);
        this.vrp.z = this.distance * sin(this.azimuth) * sin(this.altitude);
    }

    //Camera vectors that change the swivel of the camera (the C++ code)
    public void updateCameraVectors() {
        vpn = lookAt.sub(vrp);
        vpn.normalise();
        vrv = vpn.crossProduct(vuv);
        vrv.normalise();
        vuv = vrv.crossProduct(vpn);
        vuv.normalise();
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
}
