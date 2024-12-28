package com.example.orbit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.PointF;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class SceneView extends View{
    private Bitmap Background;
    private Bitmap Planet;
    private Rect rSrc, rDest;
    private int MaxAnimStep = 10000;
    private int CurentStep=0;
    private List<PointF> Points=new ArrayList<>();
    private Paint paint;
    private Path orbit=new Path();
    private PathMeasure pm;
    private float SegmentLength;


    public SceneView(Context context,String Start,double Distance,double Mass,double SpeedX,double SpeedY,int width,int height) {
        super(context);
        if (Background == null) {
            Background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
            rSrc = new Rect(0, 0, Background.getWidth(), Background.getHeight());
        }

        if (Planet == null) {
            Planet = BitmapFactory.decodeResource(getResources(), R.drawable.planet);
        }

        double x0 = 0, y0 = 0;
        double sqrtDistance = Math.sqrt(Distance / 2);
        int basex = width / 2;
        int basey = height / 2;
        switch (Start) {
            case "up":
                x0 = 0;
                y0 = Distance;
                break;
            case "up-right":
                x0 = sqrtDistance;
                y0 = sqrtDistance;
                break;
            case "right":
                x0 = Distance;
                y0 = 0;
                break;
            case "bottom-right":
                x0 = sqrtDistance;
                y0 = -sqrtDistance;
                break;
            case "bottom":
                x0 = 0;
                y0 = -Distance;
                break;
            case "bottom-left":
                x0 = -sqrtDistance;
                y0 = -sqrtDistance;
                break;
            case "left":
                x0 = -Distance;
                y0 = 0;
                break;
            case "up-left":
                x0 = -sqrtDistance;
                y0 = sqrtDistance;
                break;
        }
        double mu=Mass*6.67430E-11;
        double a=mu*Distance/(2*mu-Distance*(Math.pow(SpeedX,2)+Math.pow(-SpeedY,2)));
        double h = (x0*-SpeedY)-(y0*SpeedX);
        double ex=(x0/Distance)-(h*-SpeedY/mu);
        double ey=(y0/Distance)-(h*-SpeedX/mu);
        double fx =-2*a*ex;
        double fy =-2*a*ey;
        int x = 0;
        int y = 0;
        // Right: x = (4*Math.pow(a,2)*fx + 2*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) - 8*Math.pow(a,4)*Math.pow(fy,2) + 16*Math.pow(a,4)*fy*y - 16*Math.pow(a,4)*Math.pow(y,2) + Math.pow(a,2)*Math.pow(fx,4) + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) - 4*Math.pow(a,2)*Math.pow(fx,2)*fy*y + 4*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(y,2) + Math.pow(a,2)*Math.pow(fy,4) - 4*Math.pow(a,2)*Math.pow(fy,3)*y + 4*Math.pow(a,2)*Math.pow(fy,2)*Math.pow(y,2)) - Math.pow(fx,3) - fx*Math.pow(fy,2) + 2*fx*fy*y)/(2*(4*Math.pow(a,2) - Math.pow(fx,2)));
        // Left: x = (4*Math.pow(a,2)*fx - 2*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) - 8*Math.pow(a,4)*Math.pow(fy,2) + 16*Math.pow(a,4)*fy*y - 16*Math.pow(a,4)*Math.pow(y,2) + Math.pow(a,2)*Math.pow(fx,4) + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) - 4*Math.pow(a,2)*Math.pow(fx,2)*fy*y + 4*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(y,2) + Math.pow(a,2)*Math.pow(fy,4) - 4*Math.pow(a,2)*Math.pow(fy,3)*y + 4*Math.pow(a,2)*Math.pow(fy,2)*Math.pow(y,2)) - Math.pow(fx,3) - fx*Math.pow(fy,2) + 2*fx*fy*y)/(2*(4*Math.pow(a,2) - Math.pow(fx,2)));
        // Down: y = (4*Math.pow(a,2)*fy + 2*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) + 16*Math.pow(a,4)*fx*x - 8*Math.pow(a,4)*Math.pow(fy,2) - 16*Math.pow(a,4)*Math.pow(x,2) + Math.pow(a,2)*Math.pow(fx,4) - 4*Math.pow(a,2)*Math.pow(fx,3)*x + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) + 4*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(x,2) - 4*Math.pow(a,2)*fx*Math.pow(fy,2)*x + Math.pow(a,2)*Math.pow(fy,4) + 4*Math.pow(a,2)*Math.pow(fy,2)*Math.pow(x,2)) - Math.pow(fx,2)*fy + 2*fx*fy*x - Math.pow(fy,3))/(2*(4*Math.pow(a,2) - Math.pow(fy,2)));
        // Up: y = (4*Math.pow(a,2)*fy - 2*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) + 16*Math.pow(a,4)*fx*x - 8*Math.pow(a,4)*Math.pow(fy,2) - 16*Math.pow(a,4)*Math.pow(x,2) + Math.pow(a,2)*Math.pow(fx,4) - 4*Math.pow(a,2)*Math.pow(fx,3)*x + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) + 4*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(x,2) - 4*Math.pow(a,2)*fx*Math.pow(fy,2)*x + Math.pow(a,2)*Math.pow(fy,4) + 4*Math.pow(a,2)*Math.pow(fy,2)*Math.pow(x,2)) - Math.pow(fx,2)*fy + 2*fx*fy*x - Math.pow(fy,3))/(2*(4*Math.pow(a,2) - Math.pow(fy,2)));
        // Up-Right: x = (4*Math.pow(a,2)*fx + 4*Math.pow(a,2)*fy + 2*Math.sqrt(2)*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) - 8*Math.pow(a,4)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fx,4) + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fy,4)) - Math.pow(fx,3) - Math.pow(fx,2)*fy - fx*Math.pow(fy,2) - Math.pow(fy,3))/(2*(8*Math.pow(a,2) - Math.pow(fx,2) - 2*fx*fy - Math.pow(fy,2)));
        // Down-left: x = (4*Math.pow(a,2)*fx + 4*Math.pow(a,2)*fy - 2*Math.sqrt(2)*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) - 8*Math.pow(a,4)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fx,4) + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fy,4)) - Math.pow(fx,3) - Math.pow(fx,2)*fy - fx*Math.pow(fy,2) - Math.pow(fy,3))/(2*(8*Math.pow(a,2) - Math.pow(fx,2) - 2*fx*fy - Math.pow(fy,2)));
        // Down-Right*: x = (4*Math.pow(a,2)*fx - 4*Math.pow(a,2)*fy + 2*Math.sqrt(2)*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) - 8*Math.pow(a,4)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fx,4) + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fy,4)) - Math.pow(fx,3) + Math.pow(fx,2)*fy - fx*Math.pow(fy,2) + Math.pow(fy,3))/(2*(8*Math.pow(a,2) - Math.pow(fx,2) + 2*fx*fy - Math.pow(fy,2)));
        // Up-left*: x = (4*Math.pow(a,2)*fx - 4*Math.pow(a,2)*fy - 2*Math.sqrt(2)*Math.sqrt(16*Math.pow(a,6) - 8*Math.pow(a,4)*Math.pow(fx,2) - 8*Math.pow(a,4)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fx,4) + 2*Math.pow(a,2)*Math.pow(fx,2)*Math.pow(fy,2) + Math.pow(a,2)*Math.pow(fy,4)) - Math.pow(fx,3) + Math.pow(fx,2)*fy - fx*Math.pow(fy,2) + Math.pow(fy,3))/(2*(8*Math.pow(a,2) - Math.pow(fx,2) + 2*fx*fy - Math.pow(fy,2)));
        PointF up = new PointF((float) (basex), (float) (basey + (4 * Math.pow(a, 2) * fy - 2 * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) + 16 * Math.pow(a, 4) * fx * x - 8 * Math.pow(a, 4) * Math.pow(fy, 2) - 16 * Math.pow(a, 4) * Math.pow(x, 2) + Math.pow(a, 2) * Math.pow(fx, 4) - 4 * Math.pow(a, 2) * Math.pow(fx, 3) * x + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + 4 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(x, 2) - 4 * Math.pow(a, 2) * fx * Math.pow(fy, 2) * x + Math.pow(a, 2) * Math.pow(fy, 4) + 4 * Math.pow(a, 2) * Math.pow(fy, 2) * Math.pow(x, 2)) - Math.pow(fx, 2) * fy + 2 * fx * fy * x - Math.pow(fy, 3)) / (2 * (4 * Math.pow(a, 2) - Math.pow(fy, 2)))));
        PointF up_right = new PointF((float) (basex + (4 * Math.pow(a, 2) * fx - 4 * Math.pow(a, 2) * fy + 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) + Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) + Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) + 2 * fx * fy - Math.pow(fy, 2)))), (float) (basey - (4 * Math.pow(a, 2) * fx - 4 * Math.pow(a, 2) * fy + 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) + Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) + Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) + 2 * fx * fy - Math.pow(fy, 2)))));
        PointF right = new PointF((float) (basex + (4 * Math.pow(a, 2) * fx + 2 * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + 16 * Math.pow(a, 4) * fy * y - 16 * Math.pow(a, 4) * Math.pow(y, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) - 4 * Math.pow(a, 2) * Math.pow(fx, 2) * fy * y + 4 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(y, 2) + Math.pow(a, 2) * Math.pow(fy, 4) - 4 * Math.pow(a, 2) * Math.pow(fy, 3) * y + 4 * Math.pow(a, 2) * Math.pow(fy, 2) * Math.pow(y, 2)) - Math.pow(fx, 3) - fx * Math.pow(fy, 2) + 2 * fx * fy * y) / (2 * (4 * Math.pow(a, 2) - Math.pow(fx, 2)))), (float) (basey));
        PointF down_right = new PointF((float) (basex + (4 * Math.pow(a, 2) * fx + 4 * Math.pow(a, 2) * fy + 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) - Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) - Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) - 2 * fx * fy - Math.pow(fy, 2)))), (float) (basey + (4 * Math.pow(a, 2) * fx + 4 * Math.pow(a, 2) * fy + 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) - Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) - Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) - 2 * fx * fy - Math.pow(fy, 2)))));
        PointF down = new PointF((float) (basex), (float) (basey + (4 * Math.pow(a, 2) * fy + 2 * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) + 16 * Math.pow(a, 4) * fx * x - 8 * Math.pow(a, 4) * Math.pow(fy, 2) - 16 * Math.pow(a, 4) * Math.pow(x, 2) + Math.pow(a, 2) * Math.pow(fx, 4) - 4 * Math.pow(a, 2) * Math.pow(fx, 3) * x + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + 4 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(x, 2) - 4 * Math.pow(a, 2) * fx * Math.pow(fy, 2) * x + Math.pow(a, 2) * Math.pow(fy, 4) + 4 * Math.pow(a, 2) * Math.pow(fy, 2) * Math.pow(x, 2)) - Math.pow(fx, 2) * fy + 2 * fx * fy * x - Math.pow(fy, 3)) / (2 * (4 * Math.pow(a, 2) - Math.pow(fy, 2)))));
        PointF down_left = new PointF((float) (basex + (4 * Math.pow(a, 2) * fx - 4 * Math.pow(a, 2) * fy - 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) + Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) + Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) + 2 * fx * fy - Math.pow(fy, 2)))), (float) (basey - (4 * Math.pow(a, 2) * fx - 4 * Math.pow(a, 2) * fy - 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) + Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) + Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) + 2 * fx * fy - Math.pow(fy, 2)))));
        PointF left = new PointF((float) (basex + (4 * Math.pow(a, 2) * fx - 2 * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + 16 * Math.pow(a, 4) * fy * y - 16 * Math.pow(a, 4) * Math.pow(y, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) - 4 * Math.pow(a, 2) * Math.pow(fx, 2) * fy * y + 4 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(y, 2) + Math.pow(a, 2) * Math.pow(fy, 4) - 4 * Math.pow(a, 2) * Math.pow(fy, 3) * y + 4 * Math.pow(a, 2) * Math.pow(fy, 2) * Math.pow(y, 2)) - Math.pow(fx, 3) - fx * Math.pow(fy, 2) + 2 * fx * fy * y) / (2 * (4 * Math.pow(a, 2) - Math.pow(fx, 2)))), (float) (basey));
        PointF up_left = new PointF((float) (basex + (4 * Math.pow(a, 2) * fx + 4 * Math.pow(a, 2) * fy - 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) - Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) - Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) - 2 * fx * fy - Math.pow(fy, 2)))), (float) (basey + (4 * Math.pow(a, 2) * fx + 4 * Math.pow(a, 2) * fy - 2 * Math.sqrt(2) * Math.sqrt(16 * Math.pow(a, 6) - 8 * Math.pow(a, 4) * Math.pow(fx, 2) - 8 * Math.pow(a, 4) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fx, 4) + 2 * Math.pow(a, 2) * Math.pow(fx, 2) * Math.pow(fy, 2) + Math.pow(a, 2) * Math.pow(fy, 4)) - Math.pow(fx, 3) - Math.pow(fx, 2) * fy - fx * Math.pow(fy, 2) - Math.pow(fy, 3)) / (2 * (8 * Math.pow(a, 2) - Math.pow(fx, 2) - 2 * fx * fy - Math.pow(fy, 2)))));

        for (int i =0; i<5;i++)
        {
            switch (Start) {
            case "up":
                Points.add(up);
                Points.add(up_right);
                Points.add(right);
                Points.add(down_right);
                Points.add(down);
                Points.add(down_left);
                Points.add(left);
                Points.add(up_left);
                break;
            case "up-right":
                Points.add(up_right);
                Points.add(right);
                Points.add(down_right);
                Points.add(down);
                Points.add(down_left);
                Points.add(left);
                Points.add(up_left);
                Points.add(up);
                break;
            case "right":
                Points.add(right);
                Points.add(down_right);
                Points.add(down);
                Points.add(down_left);
                Points.add(left);
                Points.add(up_left);
                Points.add(up);
                Points.add(up_right);
                break;
            case "bottom-right":
                Points.add(down_right);
                Points.add(down);
                Points.add(down_left);
                Points.add(left);
                Points.add(up_left);
                Points.add(up);
                Points.add(up_right);
                Points.add(right);
                break;
            case "bottom":
                Points.add(down);
                Points.add(down_left);
                Points.add(left);
                Points.add(up_left);
                Points.add(up);
                Points.add(up_right);
                Points.add(right);
                Points.add(down_right);
                break;
            case "bottom-left":
                Points.add(down_left);
                Points.add(left);
                Points.add(up_left);
                Points.add(up);
                Points.add(up_right);
                Points.add(right);
                Points.add(down_right);
                Points.add(down);
                break;
            case "left":
                Points.add(left);
                Points.add(up_left);
                Points.add(up);
                Points.add(up_right);
                Points.add(right);
                Points.add(down_right);
                Points.add(down);
                Points.add(down_left);
                break;
            case "up-left":
                Points.add(up_left);
                Points.add(up);
                Points.add(up_right);
                Points.add(right);
                Points.add(down_right);
                Points.add(down);
                Points.add(down_left);
                Points.add(left);
                break;
        }
    }
//        Points.add(new PointF(540,1508));
//        Points.add(new PointF((float)822.8427,(float)1390.8427));
//        Points.add(new PointF(940,1108));
//        Points.add(new PointF((float)822.8427,(float)825.1573));
//        Points.add(new PointF(540,708));
//        Points.add(new PointF((float)257.1573,(float)825.1573));
//        Points.add(new PointF(140,1108));
//        Points.add(new PointF((float)257.1573,(float)1390.8427));
//        Points.add(new PointF(540,1508));
//        Points.add(new PointF((float)822.8427,(float)1390.8427));
//        Points.add(new PointF(940,1108));
//        Points.add(new PointF((float)822.8427,(float)825.1573));
//        Points.add(new PointF(540,708));
//        Points.add(new PointF((float)257.1573,(float)825.1573));
//        Points.add(new PointF(140,1108));
//        Points.add(new PointF((float)257.1573,(float)1390.8427));
//        Points.add(new PointF(540,1508));

        PointF pointF = Points.get(0);
        orbit.moveTo(pointF.x,pointF.y);
        for (int i=0;i<Points.size()-1;i++)
        {
            pointF = Points.get(i);
            PointF nextpointF = Points.get(i+1);
            orbit.quadTo(pointF.x,pointF.y,(nextpointF.x+pointF.x)/2,(nextpointF.y+pointF.y)/2);
        }

        pm=new PathMeasure(orbit,false);
        SegmentLength=pm.getLength()/MaxAnimStep;

        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.rgb(255,255,255));
    }

    @Override
    protected  void onDraw(Canvas canvas)
    {
        if (rDest==null)
        {
            rDest = new Rect(0,0,getWidth(),getHeight());
        }
        canvas.drawBitmap(Background,rSrc,rDest,null);
        canvas.drawPath(orbit,paint);
        Bitmap starbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.star);
        canvas.drawBitmap(starbitmap,540-starbitmap.getWidth()/2,1108-starbitmap.getHeight()/2,null);

        Matrix Transform = new Matrix();
        if (CurentStep<MaxAnimStep)
        {
            pm.getMatrix(SegmentLength*CurentStep,Transform,PathMeasure.POSITION_MATRIX_FLAG);
            Transform.preTranslate(-Planet.getWidth()/2,-Planet.getHeight()/2);
            canvas.drawBitmap(Planet,Transform, null);
            CurentStep++;
            invalidate();
        }
        else
        {
            CurentStep=0;
        }
    }
}
