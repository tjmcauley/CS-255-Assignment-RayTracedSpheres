package com.example.cs255assignment;
/*
CS-255 Getting started code for the assignment
I do not give you permission to post this code online
Do not post your solution online
Do not copy code
Do not use JavaFX functions or other libraries to do the main parts of the
assignment (i.e. ray tracing steps 1-7)
All of those functions must be written by yourself
You may use libraries to achieve a better GUI
*/

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

public class Main extends Application {
    static int Width = 500;
    static int Height = 500;
    ArrayList<Sphere> spheres = new ArrayList<>();
    ArrayList<RadioButton> sphereSelectButtons = new ArrayList<>();
    Sphere sphere1 = new Sphere(-100, 0, 100, 255, 255, 255, 75);
    Sphere sphere2 = new Sphere(-200, -100, 100, 255, 0, 0, 75);
    Sphere sphere3 = new Sphere(-50, -100, 200, 0, 0, 255, 75);
    Sphere sphere4 = new Sphere(-200, -150, 250, 0, 255, 0, 75);
    Sphere sphere5 = new Sphere(-200, -200, -150, 255, 0, 255, 75);

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        spheres.add(sphere1);
        spheres.add(sphere2);
        spheres.add(sphere3);
        spheres.add(sphere4);
        spheres.add(sphere5);

        stage.setTitle("Ray Tracing");
        //We need 3 things to see an image
        //1. We create an image we can write to
        WritableImage image = new WritableImage(Width, Height);
        //2. We create a view of that image
        ImageView view = new ImageView(image);
        //3. Add to the pane (below)
        //Create the simple GUI
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
        root.setVgap(4);
        //3. (referring to the 3 things we need to display an image)
        //we need to add it to the pane
        Label xSliderLabel = new Label("X coord");
        Label ySliderLabel = new Label("Y coord");
        Label zSliderLabel = new Label("Z coord");
        Label rSliderLabel = new Label("R");
        Label gSliderLabel = new Label("G");
        Label bSliderLabel = new Label("B");
        Label radiusSliderLabel = new Label("radius");
        Label sphere1Label = new Label("Sphere 1");
        Label sphere2Label = new Label("Sphere 2");
        Label sphere3Label = new Label("Sphere 3");
        Label sphere4Label = new Label("Sphere 4");
        Label sphere5Label = new Label("Sphere 5");

        Button createSphereButton = new Button("Create Sphere");
        root.add(createSphereButton, 1, 0);

        FlowPane selectSphereLocation = new FlowPane();
        selectSphereLocation.setAlignment(Pos.TOP_CENTER);
        selectSphereLocation.setPrefWrapLength(70);
        selectSphereLocation.getChildren().addAll(sphereButton1, sphere1Label, sphereButton2, sphere2Label, sphereButton3, sphere3Label, sphereButton4, sphere4Label, sphereButton5, sphere5Label);
        root.add(selectSphereLocation, 2, 0);

        root.add(view, 0, 0);
        root.add(xSliderLabel, 0, 1);
        root.add(x_slider, 0, 2);

        root.add(ySliderLabel, 0, 3);
        root.add(y_slider, 0, 4);

        root.add(zSliderLabel, 0, 5);
        root.add(z_slider, 0, 6);

        root.add(rSliderLabel, 0, 7);
        root.add(r_slider, 0, 8);

        root.add(gSliderLabel, 0, 9);
        root.add(g_slider, 0, 10);

        root.add(bSliderLabel, 0, 11);
        root.add(b_slider, 0, 12);

        root.add(radiusSliderLabel, 0, 13);
        root.add(radius, 0, 14);

        tg.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle>
                                                observable, Toggle oldValue, Toggle newValue) {

                        for (Sphere elem : spheres) {
                            try {
                                if (elem.getLinkedButton().isSelected()) {

                                    x_slider.setValue(elem.getSphereX());
                                    y_slider.setValue(elem.getSphereY());
                                    z_slider.setValue(elem.getSphereZ());
                                    r_slider.setValue(elem.getSphereR());
                                    g_slider.setValue(elem.getSphereG());
                                    b_slider.setValue(elem.getSphereB());
                                    radius.setValue(elem.getRadius());
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

        //Add all the event handlers
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

        //The following is in case you want to interact with the image in any way
        //e.g., for user interaction, or you can find out the pixel position for
        //debugging
        view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(event.getX() + " " + event.getY());
            event.consume();
        });

        Render(image);

        //Display to user
        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void Render(WritableImage image) {

        //Variables for calculating which parts of the spheres to render
        Vector rayOrigin = new Vector(0, 0, 0);
        Vector rayDirection = new Vector(0, 0, 1);
        Vector light = new Vector(0, 0, -250);
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();
        int closestTIndex = 0;

        Vector points;
        double lineIntersectionWithSphere;
        Vector rayFromCenterOfSphereToOriginOfLine;

        //a, b, and c components of quadratic equation
        double a;
        double b;
        double c;
        Color col;

        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                rayOrigin.x = i - 250;
                rayOrigin.y = j - 250;
                rayOrigin.z = -200;
                double closestT = 1000000;
                image_writer.setColor(i, j, Color.color(0, 0, 0, 1));
                //Another for loop going through each sphere
                //Which sphere is closest? - index to closest sphere so far (smallest positive t value)
                for (int s = 0; s < spheres.size(); s++) {
                    rayFromCenterOfSphereToOriginOfLine = rayOrigin.sub(spheres.get(s));
                    a = rayDirection.dot(rayDirection);
                    b = rayFromCenterOfSphereToOriginOfLine.dot(rayDirection) * 2;
                    c = rayFromCenterOfSphereToOriginOfLine.dot(rayFromCenterOfSphereToOriginOfLine) - spheres.get(s).getRadius() * spheres.get(s).getRadius();

                    //Calculate if light hits sphere
                    double disc = b * b - 4 * a * c;

                    if (disc < 0) {
                        col = Color.color(0, 0, 0, 1);
                        continue;
                    } else {
                        //Calculate shading of light on sphere
                        //NEED TO KEEP TRACK OF SMALLEST T VALUE
                        lineIntersectionWithSphere = (-b - sqrt(disc)) / 2 * a;
                        if (lineIntersectionWithSphere > 0 && lineIntersectionWithSphere < closestT) {
                            closestT = lineIntersectionWithSphere;
                            closestTIndex = s;
                        }
                    }
                    points = rayOrigin.add(rayDirection.mul(lineIntersectionWithSphere));
                    Vector lv = light.sub(points);
                    lv.normalise();
                    Vector n = points.sub(spheres.get(closestTIndex));
                    n.normalise();
                    double dp = lv.dot(n);
                    if (dp < 0) {
                        col = Color.color(0, 0, 0, 1);
                    } else {
                        double sphereShadedR = dp * spheres.get(s).getSphereR();
                        double sphereShadedG = dp * spheres.get(s).getSphereG();
                        double sphereShadedB = dp * spheres.get(s).getSphereB();
                        col = Color.color(sphereShadedR, sphereShadedG, sphereShadedB, 1);
                        image_writer.setColor(i, j, col);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
