package com.example.cs255assignment;

import static java.lang.Math.*;

/**
 * @author Thomas McAuley, Cellan Lees
 *
 * Class that models a camera vector
 */
public class Camera {
    float azimuth;
    float altitude;
    float distance;
    Vector vrp = new Vector(0, 0, 0);
    Vector vuv = new Vector(0, 1, 0);
    Vector lookAt = new Vector(0, 0, 0);
    Vector vrv = new Vector(1, 0, 0);
    Vector vpn;

    /**
     *
     * @param azimuth azimuth slider value
     * @param altitude altitude slider value
     * @param distance distance of the camera from center point
     */
    public Camera(float azimuth, float altitude, float distance) {
        this.azimuth = azimuth;
        this.altitude = altitude;
        this.distance = distance;
        updateCameraAzimuth();
        updateCameraAltitude();
        updateCameraVectors();

    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    //Change vrp to behave as if adjusting azimuth
    public void updateCameraAzimuth(){
        double calcAzimuth = this.distance * sin(this.azimuth) * cos(this.altitude);
        double calcDistance = this.distance * cos(this.azimuth);
        this.vrp.z = calcDistance;
        this.vrp.x = calcAzimuth;
    }

    //Change vrp to behave as if adjusting altitude
    public void updateCameraAltitude() {
        double calcAltitude = this.altitude;
        double calcDistance = this.distance * cos(this.azimuth);
        this.vrp.y = calcAltitude;
        this.vrp.z = calcDistance;
    }

    //Camera vectors that change the swivel of the camera
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

    public Vector getVPN() {
        return this.vpn;
    }
}
