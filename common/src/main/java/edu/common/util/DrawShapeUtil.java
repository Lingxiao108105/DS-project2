package edu.common.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawShapeUtil {

    public static void drawLine(GraphicsContext graphicsContext,
                           Color strokeColor, Color fillColor,
                           double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }

    public static void drawCircle(GraphicsContext graphicsContext,
                                Color strokeColor, Color fillColor,
                                double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        double upperLeftX = Math.min(x1,x2);
        double upperLeftY = Math.min(y1,y2);
        double w = Math.abs(x1-x2);
        double y = Math.abs(y1-y2);
        double r = Math.max(w,y);
        graphicsContext.fillOval(upperLeftX, upperLeftY, r, r);
        graphicsContext.strokeOval(upperLeftX, upperLeftY, r, r);
    }

    public static void drawOval(GraphicsContext graphicsContext,
                                Color strokeColor, Color fillColor,
                                double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        double upperLeftX = Math.min(x1,x2);
        double upperLeftY = Math.min(y1,y2);
        double w = Math.abs(x1-x2);
        double y = Math.abs(y1-y2);
        graphicsContext.fillOval(upperLeftX, upperLeftY, w, y);
        graphicsContext.strokeOval(upperLeftX, upperLeftY, w, y);
    }

    public static void drawTriangle(GraphicsContext graphicsContext,
                                Color strokeColor, Color fillColor,
                                double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        double upperX = (x1+x2)/2.0;
        double upperY = Math.min(y1,y2);
        double lowerY = Math.max(y1,y2);
        graphicsContext.fillPolygon(new double[]{upperX,x1,x2},new double[]{upperY,lowerY,lowerY},3);
        graphicsContext.strokePolygon(new double[]{upperX,x1,x2},new double[]{upperY,lowerY,lowerY},3);
    }

    public static void drawRectangle(GraphicsContext graphicsContext,
                                    Color strokeColor, Color fillColor,
                                    double x1, double y1, double x2, double y2){
        graphicsContext.setFill(fillColor);
        graphicsContext.setStroke(strokeColor);
        double upperLeftX = Math.min(x1,x2);
        double upperLeftY = Math.min(y1,y2);
        double w = Math.abs(x1-x2);
        double y = Math.abs(y1-y2);
        graphicsContext.fillRect(upperLeftX, upperLeftY, w, y);
        graphicsContext.strokeRect(upperLeftX, upperLeftY, w, y);
    }




}
