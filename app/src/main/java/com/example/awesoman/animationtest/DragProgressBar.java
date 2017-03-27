package com.example.awesoman.animationtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Awesome on 2017/2/23.
 */

public class DragProgressBar extends View {

    private float xPointStart, xPointEnd,//播放点的x起点,x终点
            lengthStartToEnd,//起点到终点的长度
            xPoint, yPoint,//当前播放点的坐标, xPoint变动,yPoint不变
            xTouchStart, yTouchStart,//初点击的坐标
            radius = 8,//播放点的半径
            progressCurrent//当前进度 百分比
                    ;

    private Paint paintHasPlay = null;

    private Paint paintNoPlay = null;

    private boolean isTouched = false;

    private int color = 0;

    private IPlayerPregressBar listener;

    public void setListener(IPlayerPregressBar listener) {
        this.listener = listener;
    }

    public DragProgressBar(Context context) {
        super(context);
        init();
    }

    public DragProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        paintHasPlay = new Paint();
        paintHasPlay.setColor(getResources().getColor(R.color.colorAccent));

        paintNoPlay = new Paint();
        paintNoPlay.setColor(getResources().getColor(R.color.gray));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        xPointStart = 30;
        xPointEnd = getWidth() - 30;
        xPoint = xPointStart;
        yPoint = getHeight() / 2;
        lengthStartToEnd = xPointEnd - xPointStart;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawRect(xPoint, yPoint - 3, xPointEnd, yPoint + 3, paintNoPlay);
        canvas.drawRect(xPointStart, yPoint - 3, xPoint, yPoint + 3, paintHasPlay);
        canvas.drawCircle(xPoint, yPoint, radius, paintHasPlay);
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("dispatch", "ACTION_DOWN");
                xTouchStart = event.getX();
                yTouchStart = event.getY();
                if (xTouchStart <= xPoint + radius && xTouchStart >= xPoint - radius
                        && yTouchStart <= yPoint + 10 && yTouchStart >= yPoint - 10) {
                    isTouched = true;
                }
                if (listener != null)
                    listener.onDown();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("dispatch", "ACTION_MOVE");
                if (isTouched) {
                    xPoint = event.getX();
                    if (xPoint <= xPointEnd && xPoint >= xPointStart) {
                        invalidate();
                        progressCurrent = (xPoint - xPointStart) / (lengthStartToEnd);
                    }
                    if (listener != null)
                        listener.onMove(progressCurrent);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("dispatch", "ACTION_UP");
                if (isTouched) {
                    isTouched = false;
                    if (listener != null)
                        listener.onUp(progressCurrent);
                }
                break;
        }
        return true;
    }

    public interface IPlayerPregressBar {
        void onDown();

        void onMove(float progressCurrent);

        void onUp(float progressCurrent);
    }

}
