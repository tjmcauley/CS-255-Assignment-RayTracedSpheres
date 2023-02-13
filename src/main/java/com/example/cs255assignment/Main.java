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
    static int Width = 250;
    static int Height = 250;
    ArrayList<Sphere> spheres = new ArrayList<>();
    ArrayList<RadioButton> sphereSelectButtons = new ArrayList<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
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

        Button createSphereButton = new Button("Create Sphere");
        root.add(createSphereButton, 1, 0);

        FlowPane selectSphereLocation = new FlowPane();
        selectSphereLocation.setAlignment(Pos.TOP_CENTER);
        selectSphereLocation.setPrefWrapLength(340);
        ToggleGroup tg = new ToggleGroup();
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

        createSphereButton.setOnAction(e -> {
            sphereSelectButtons.add(new RadioButton());
            spheres.add(new Sphere(0, 0, 0, 1, 1, 1, 75));
            spheres.get(spheres.size() - 1).setRadioButton(sphereSelectButtons.get(sphereSelectButtons.size() - 1));
            selectSphereLocation.getChildren().addAll(sphereSelectButtons.get(sphereSelectButtons.size() - 1), new TextField("New Sphere"));
            sphereSelectButtons.get(sphereSelectButtons.size() - 1).setToggleGroup(tg);
            r_slider.setValue(122.5);
            g_slider.setValue(122.5);
            b_slider.setValue(122.5);
            x_slider.setValue(0);
            y_slider.setValue(0);
            z_slider.setValue(0);
            radius.setValue(0);

            Render(image);
        });

        tg.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle>
                                                observable, Toggle oldValue, Toggle newValue) {

                        for (Sphere elem : spheres) {
                            try {
                                if (elem.getLinkedButton().isSelected()) {
                                    elem.setSelect(true);
                                } else {
                                    elem.setSelect(false);
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Select a sphere to manipulate");
                            }
                        }
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
        int smallestTIndex = 0;

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
                double smallestDisc = 0;
                int closestSphere = 0;
                //Another for loop going through each sphere
                //Which sphere is closest? - index to closest sphere so far (smallest positive t value)
                for (int s = 0; s < spheres.size(); s++) {
                    rayOrigin.x = i - 250;
                    rayOrigin.y = j - 250;
                    rayOrigin.z = -200;
                    rayFromCenterOfSphereToOriginOfLine = rayOrigin.sub(spheres.get(s));
                    a = rayDirection.dot(rayDirection);
                    b = rayFromCenterOfSphereToOriginOfLine.dot(rayDirection) * 2;
                    c = rayFromCenterOfSphereToOriginOfLine.dot(rayFromCenterOfSphereToOriginOfLine) - spheres.get(s).getRadius() * spheres.get(s).getRadius();

                    //Calculate if light hits sphere
                    double disc = b * b - 4 * a * c;

                    if (disc < smallestDisc) {
                        smallestDisc = disc;
                        closestSphere = s;
                    }
                }

                if (smallestDisc < 0) {
                    col = Color.color(0, 0, 0, 1);
                } else {
                    col = spheres.get(closestSphere).getSphereColour();
                }

                image_writer.setColor(i, j, col);
            }
        }

        //Calculate shading of light on sphere
        //NEED TO KEEP TRACK OF SMALLEST T VALUE
//                    double smallestLineIntersectionWithSphere = 0;
//                    lineIntersectionWithSphere = (-b - sqrt(disc)) / 2 * a;
//                    if (lineIntersectionWithSphere > 0 && lineIntersectionWithSphere < smallestLineIntersectionWithSphere || smallestLineIntersectionWithSphere == 0 && lineIntersectionWithSphere > 0) {
//                        smallestLineIntersectionWithSphere = lineIntersectionWithSphere;
//                        smallestTIndex = s;
//                    }

//                    points = rayOrigin.add(rayDirection.mul(smallestLineIntersectionWithSphere));
//                    Vector lv = light.sub(points);
//                    lv.normalise();
//                    Vector n = points.sub(spheres.get(s));
//                    n.normalise();
//                    double dp = lv.dot(n);
//                    if (dp < 0) {
//                        col = Color.color(0, 0, 0, 1);
//                    } else {
//                        double sphereShadedR;
//                        double sphereShadedG;
//                        double sphereShadedB;
//                        if (smallestLineIntersectionWithSphere == spheres.get(s).getSmallestT()) {
//                            sphereShadedR = dp * spheres.get(smallestTIndex).getSphereR();
//                            sphereShadedG = dp * spheres.get(smallestTIndex).getSphereG();
//                            sphereShadedB = dp * spheres.get(smallestTIndex).getSphereB();
//                        } else {
//                            sphereShadedR = dp * spheres.get(s).getSphereR();
//                            sphereShadedG = dp * spheres.get(s).getSphereG();
//                            sphereShadedB = dp * spheres.get(s).getSphereB();
//                        }
//                        col = Color.color(sphereShadedR, sphereShadedG, sphereShadedB, 1);
//                    }

    }

    public static void main(String[] args) {
        launch();
    }
}
