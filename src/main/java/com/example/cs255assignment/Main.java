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
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.*;
import java.lang.Math.*;

import javafx.geometry.HPos;
public class Main extends Application {
    int Width = 640;
    int Height = 640;
    int green_col = 255; //just for the test example
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
        Slider g_slider = new Slider(0, 255, green_col);
        //Add all the event handlers
        g_slider.valueProperty().addListener(
                new ChangeListener < Number > () {
                    public void changed(ObservableValue < ? extends Number >
                                                observable, Number oldValue, Number newValue) {
                        green_col = newValue.intValue();
                        Render(image);
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
        GridPane root = new GridPane();
        root.setVgap(12);
        root.setHgap(12);
        //3. (referring to the 3 things we need to display an image)
        //we need to add it to the pane
        root.add(view, 0, 0);
        root.add(g_slider, 0, 1);
        //Display to user
        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }
    public void Render(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();
        double c = green_col / 255.0;
        Vector col = new Vector(0.5, c, 0.5);
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                image_writer.setColor(i, j, Color.color(col.x, col.y, col.z, 1.0));
            } // column loop
        } // row loop
    }
    public static void main(String[] args) {
        launch();
    }
}
