package com.example.awesoman.animationtest;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by Awesome on 2016/8/22.
 */
public class ShapeHolder {
    private float x = 0, y = 0;
    //图形绘制类
    private ShapeDrawable shape;
    private int color;
    //环形放射
    private RadialGradient gradient;
    public float alpha = 1f;
    private Paint paint;

    public void setPaint(Paint value) {
        paint = value;
    }
    public Paint getPaint() {
        return paint;
    }

    public void setX(float value) {
        x = value;
    }
    public float getX() {
        return x;
    }
    public void setY(float value) {
        y = value;
    }
    public float getY() {
        return y;
    }
    public void setShape(ShapeDrawable value) {
        shape = value;
    }
    public ShapeDrawable getShape() {
        return shape;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int value) {
        shape.getPaint().setColor(value);
        color = value;
    }
    public void setGradient(RadialGradient value) {
        Paint paint = getShape().getPaint();
        paint.setShader(value);
        gradient = value;
    }
    public RadialGradient getGradient() {
        return gradient;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        shape.setAlpha((int)((alpha * 255f) + .5f));
    }

    public float getWidth() {
        return shape.getShape().getWidth();
    }
    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    public float getHeight() {
        return shape.getShape().getHeight();
    }
    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }

    public ShapeHolder(ShapeDrawable s) {
        shape = s;
    }
}
