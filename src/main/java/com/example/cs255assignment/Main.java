package com.example.cs255assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.io.*;
import java.lang.Math.*;

import javafx.geometry.HPos;

import static java.lang.Math.sqrt;

/**
 * @author Thomas McAuley, Cellan Lees
 *
 * Main class that renders 5 sphere vectors with ambient and diffuse lighting in a 3D space
 */
public class Main extends Application {
    static int Width = 600;
    static int Height = 600;
    ArrayList<Sphere> spheres = new ArrayList<>();
    Sphere sphere1 = new Sphere(0, 0, 0, 255, 255, 255, 75);
    Sphere sphere2 = new Sphere(0, 200, 0, 255, 0, 0, 75);
    Sphere sphere3 = new Sphere(0, -200, 0, 0, 0, 255, 75);
    Sphere sphere4 = new Sphere(200, 0, 0, 0, 255, 0, 75);
    Sphere sphere5 = new Sphere(-200, 0, 0, 255, 0, 255, 75);
    Camera camera = new Camera(0, 0, -200);

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        spheres.add(sphere1);
        spheres.add(sphere2);
        spheres.add(sphere3);
        spheres.add(sphere4);
        spheres.add(sphere5);

        stage.setTitle("Ray Tracing");
        WritableImage image = new WritableImage(Width, Height);
        ImageView view = new ImageView(image);

        //Build GUI
        Slider x_slider = new Slider(-250, 250, 0);
        x_slider.setShowTickLabels(true);
        x_slider.setShowTickMarks(true);
        x_slider.setMajorTickUnit(25);

        Slider y_slider = new Slider(-250, 250, 0);
        y_slider.setShowTickLabels(true);
        y_slider.setShowTickMarks(true);
        y_slider.setMajorTickUnit(25);

        Slider z_slider = new Slider(-250, 250, 0);
        z_slider.setShowTickLabels(true);
        z_slider.setShowTickMarks(true);
        z_slider.setMajorTickUnit(25);

        Slider r_slider = new Slider(0, 255, 0);
        r_slider.setShowTickLabels(true);
        r_slider.setShowTickMarks(true);

        Slider g_slider = new Slider(0, 255, 0);
        g_slider.setShowTickLabels(true);
        g_slider.setShowTickMarks(true);

        Slider b_slider = new Slider(0, 255, 0);
        b_slider.setShowTickLabels(true);
        b_slider.setShowTickMarks(true);

        Slider radius = new Slider(0, 100, 0);
        radius.setShowTickLabels(true);
        radius.setShowTickMarks(true);
        radius.setMajorTickUnit(2);
        radius.setMinorTickCount(1);

        Slider azimuthSlider = new Slider(- Math.PI,  Math.PI, 0);
        azimuthSlider.setShowTickLabels(true);
        azimuthSlider.setShowTickMarks(true);

        Slider altitudeSlider = new Slider(-360, 360, 0);
        altitudeSlider.setShowTickLabels(true);
        altitudeSlider.setShowTickMarks(true);

        ToggleGroup tg = new ToggleGroup();

        RadioButton sphereButton1 = new RadioButton();
        sphereButton1.setToggleGroup(tg);
        sphere1.setRadioButton(sphereButton1);

        RadioButton sphereButton2 = new RadioButton();
        sphereButton2.setToggleGroup(tg);
        sphere2.setRadioButton(sphereButton2);

        RadioButton sphereButton3 = new RadioButton();
        sphereButton3.setToggleGroup(tg);
        sphere3.setRadioButton(sphereButton3);

        RadioButton sphereButton4 = new RadioButton();
        sphereButton4.setToggleGroup(tg);
        sphere4.setRadioButton(sphereButton4);

        RadioButton sphereButton5 = new RadioButton();
        sphereButton5.setToggleGroup(tg);
        sphere5.setRadioButton(sphereButton5);

        GridPane root = new GridPane();

        root.setVgap(0.5);

        Label xSliderLabel = new Label("X coord");
        Label ySliderLabel = new Label("Y coord");
        Label zSliderLabel = new Label("Z coord");
        Label rSliderLabel = new Label("R");
        Label gSliderLabel = new Label("G");
        Label bSliderLabel = new Label("B");
        Label azimuthSliderLabel = new Label("Azimuth");
        Label altitudeSliderLabel = new Label("Altitude");

        Label radiusSliderLabel = new Label("radius");
        Label sphere1Label = new Label("Sphere 1");
        Label sphere2Label = new Label("Sphere 2");
        Label sphere3Label = new Label("Sphere 3");
        Label sphere4Label = new Label("Sphere 4");
        Label sphere5Label = new Label("Sphere 5");

        VBox camSliderLocation = new VBox();
        camSliderLocation.setAlignment(Pos.TOP_CENTER);
        camSliderLocation.getChildren().addAll(azimuthSliderLabel, azimuthSlider, altitudeSliderLabel, altitudeSlider);
        root.add(camSliderLocation, 1, 1);

        FlowPane selectSphereLocation = new FlowPane();
        selectSphereLocation.setAlignment(Pos.TOP_CENTER);
        selectSphereLocation.setPrefWrapLength(70);
        selectSphereLocation.getChildren().addAll(sphereButton1, sphere1Label, sphereButton2, sphere2Label, sphereButton3, sphere3Label, sphereButton4, sphere4Label, sphereButton5, sphere5Label);
        root.add(selectSphereLocation, 0, 0);

        root.add(view, 1, 0);

        GridPane sphereSliderLocation = new GridPane();
        sphereSliderLocation.add(xSliderLabel, 0, 0);
        sphereSliderLocation.add(x_slider, 0, 1);

        sphereSliderLocation.add(ySliderLabel, 0, 2);
        sphereSliderLocation.add(y_slider, 0, 3);

        sphereSliderLocation.add(zSliderLabel, 0, 4);
        sphereSliderLocation.add(z_slider, 0, 5);

        sphereSliderLocation.add(rSliderLabel, 0, 6);
        sphereSliderLocation.add(r_slider, 0, 7);

        sphereSliderLocation.add(gSliderLabel, 0, 8);
        sphereSliderLocation.add(g_slider, 0, 9);

        sphereSliderLocation.add(bSliderLabel, 0, 10);
        sphereSliderLocation.add(b_slider, 0, 11);

        sphereSliderLocation.add(radiusSliderLabel, 0, 12);
        sphereSliderLocation.add(radius, 0, 13);

        root.add(sphereSliderLocation, 2, 0);

        //Event handlers
        tg.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle>
                                                observable, Toggle oldValue, Toggle newValue) {

                        for (Sphere sphere : spheres) {
                            if (sphere.isSelected()) {
                                sphere.setSelect(false);
                            }
                        }

                        for (Sphere elem : spheres) {
                            try {
                                if (elem.getLinkedButton().isSelected()) {
                                    x_slider.setValue(elem.getSphereX());
                                    y_slider.setValue(elem.getSphereY());
                                    z_slider.setValue(elem.getSphereZ());
                                    r_slider.setValue((elem.getSphereR() * 255));
                                    g_slider.setValue((elem.getSphereG() * 255));
                                    b_slider.setValue((elem.getSphereB() * 255));
                                    radius.setValue(elem.getSphereRadius());

                                    elem.setSelect(true);
                                } else {
                                    elem.setSelect(false);
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                        }
                        Render(image);
                    }
                });

        x_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereX(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        y_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereY(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        z_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereZ(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        r_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereR(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        g_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereG(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        b_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereB(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        radius.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        for (Sphere elem : spheres) {
                            try {
                                if (elem.isSelected()) {
                                    elem.setSphereRadius(newValue.intValue());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                            Render(image);
                        }
                    }
                });

        azimuthSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        camera.setAzimuth(newValue.floatValue());
                        camera.updateCameraAzimuth();
                        Render(image);
                    }
                });

        altitudeSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        camera.setAltitude(newValue.floatValue());
                        camera.updateCameraAltitude();
                        Render(image);
                    }
                });

        //Click image for debugging
        view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event ->
        {
            System.out.println(event.getX() + " " + event.getY());
            event.consume();
        });

        Render(image);

        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void Render(WritableImage image) {
        //Variables for calculating which parts of the spheres to render
        Vector light = new Vector(0, 0, -300);
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();
        int closestTIndex = -1;

        Vector points;
        double lineIntersectionWithSphere = 0;
        Vector rayFromCenterOfSphereToOriginOfLine;
        double disc = 0;

        //a, b, and c components of quadratic equation
        double a = 0;
        double b = 0;
        double c = 0;
        Color col;
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                closestTIndex = -1;
                double u = ((w - i) - (w / 2));
                double v = ((h - j) - (h / 2));
                Vector rayOrigin = camera.getVRP().add(camera.getVRV().mul(u)).add(camera.getVUV().mul(v));
                double closestT = -1;
                camera.updateCameraVectors();

                for (int s = 0; s < spheres.size(); s++) {
                    rayFromCenterOfSphereToOriginOfLine = rayOrigin.sub(spheres.get(s));
                    a = camera.getVPN().dot(camera.getVPN());
                    b = rayFromCenterOfSphereToOriginOfLine.dot(camera.getVPN()) * 2;
                    c = rayFromCenterOfSphereToOriginOfLine.dot(rayFromCenterOfSphereToOriginOfLine) - spheres.get(s).getRadius() * spheres.get(s).getRadius();
                    disc = (b * b) - (4 * a * c);

                    //True if sphere is hit (there are 2 intersections)
                    if (disc > 0) {
                        lineIntersectionWithSphere = (-b - sqrt(disc)) / 2 * a;
                        if (lineIntersectionWithSphere > 0 && lineIntersectionWithSphere < closestT || closestT == -1) {
                            closestT = lineIntersectionWithSphere;
                            closestTIndex = s;
                        }
                    }
                }

                //True if no spheres in sphere list
                if (closestTIndex == -1) {
                    image_writer.setColor(i, j, Color.GRAY);

                //True if camera is inside sphere (there is 1 intersection)
                } else {
                    if (closestT < 0) {
                        closestT = ((-b + sqrt(disc)) / 2 * a);
                        if (closestT < 0) {
                            col = Color.GREY;
                        }
                    }

                    points = rayOrigin.add(camera.getVPN().mul(closestT));
                    Vector lv = light.sub(points);
                    lv.normalise();
                    Vector n = points.sub(spheres.get(closestTIndex).getCos());
                    n.normalise();
                    double dp = lv.dot(n);

                    if (dp < 0) {
                        dp = 0;
                    } else if (dp > 1) {
                        dp = 1;
                    }

                    Vector dc = new Vector((dp * 0.7 * spheres.get(closestTIndex).getSphereR()) + (spheres.get(closestTIndex).getSphereR() * 0.3),
                            (dp * 0.7 * spheres.get(closestTIndex).getSphereG()) + (spheres.get(closestTIndex).getSphereG() * 0.3),
                            (dp * 0.7 * spheres.get(closestTIndex).getSphereB()) + (spheres.get(closestTIndex).getSphereB() * 0.3));

                    col = Color.color(dc.x, dc.y, dc.z, 1);
                    image_writer.setColor(i, j, col);

                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
