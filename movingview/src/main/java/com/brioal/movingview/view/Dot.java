package com.brioal.movingview.view;

import java.util.Random;

/**小点的实体类,封装小点信息及其移动方法
 * Created by Brioal on 2016/8/12.
 */

public class Dot {
    private float mX; //x坐标
    private float mY; //y坐标
    private float mRadius; //小点半径

    public static int WIDTH = 0; //父组件宽度
    public static int SPEED = 0; //当前速度
    public Random sRandom;
    public static int sMaxDotRadius; //最大半径
    public static int sMinDotRadius; //最小半径

    public Dot() {
        setRightPosition();
    }

    public Dot(float x, float y, float radius) {
        mX = x;
        mY = y;
        mRadius = radius;
    }

    public float getX() {

        return mX;
    }

    public void setX(float x) {
        mX = x;
    }

    public float getY() {

        return mY;
    }

    public void setY(float y) {
        mY = y;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public double getZ() {
        return Math.sqrt(mX * mX + mY * mY);
    }

    public void checkAndChange() {
        if (getZ() + getRadius() < WIDTH / 4) {
            setRightPosition();
        } else {
            adjustPosition();
        }
    }

    public void adjustPosition() {
        float xOffset = (float) (mX * SPEED / getZ());
        mX -= xOffset;
        float yOffset = (float) (mY * SPEED / getZ());
        mY -= yOffset;
    }

    public void setRightPosition() {
        if (sRandom == null) {
            sRandom = new Random();
        }
        mRadius = (int) (sRandom.nextFloat() * (sMaxDotRadius - sMinDotRadius) + sMinDotRadius);
        int angle = sRandom.nextInt(360); //获取角度值
        if (angle < 90) {
            mY = -WIDTH / 2 - mRadius - sRandom.nextInt(50);
            angle = angle - 45;
            mX = (int) (angle < 0 ? (-Math.tan(angle * Math.PI / 360) * WIDTH / 2) : (Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        } else if (angle < 180) {
            mX = WIDTH / 2 + mRadius + sRandom.nextInt(50);
            angle = angle - 135;
            mY = (int) (angle < 0 ? (-Math.tan(angle * Math.PI / 180) * WIDTH / 2) : (Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        } else if (angle < 270) {
            mY = WIDTH / 2 + mRadius + sRandom.nextInt(50);
            angle = angle - 225;
            mX = (int) (angle < 0 ? (Math.tan(angle * Math.PI / 180) * WIDTH / 2) : (-Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        } else {
            mX = -WIDTH / 2 - mRadius - sRandom.nextInt(50);
            angle = angle - 315;
            mY = (int) (angle > 0 ? (-Math.tan(angle * Math.PI / 180) * WIDTH / 2) : (Math.tan(angle * Math.PI / 180) * WIDTH / 2));
        }
    }
}
