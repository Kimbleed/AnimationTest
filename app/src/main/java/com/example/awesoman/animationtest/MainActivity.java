package com.example.awesoman.animationtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_container;
    List<Hit> hitList =new ArrayList<>();
    List<Bitmap> hitBitmap;

    int Height;
    int Width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_container = (LinearLayout) findViewById(R.id.LinearLayout);
//        MyAnimView myAnim = new MyAnimView(this);
//        PlayerProgressBar bar = new PlayerProgressBar(this);
        MusicPlayingView mpv = new MusicPlayingView(getBaseContext());
        //把动画控件加入到控件盒子中
        ll_container.addView(mpv);
//        ll_container.addView(myAnim);
//        Height = myAnim.getHeight();
//        hitBitmap =split(BitmapFactory.decodeResource(getResources(),R.mipmap.button),2,1);
//        createHit();
    }

    class MyAnimView2 extends View{
        ShapeHolder ball = null;
        int RED = 0xffff8080;
        int BLUE = 0xff8080ff;
        public MyAnimView2(Context context) {
            super(context);
//            ValueAnimator anim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
//            //动画的播放时间为3000ms
//            anim.setDuration(3000);
//            anim.setInterpolator(new BounceInterpolator());
//            //设置动画的重复次数
//            anim.setRepeatCount(ValueAnimator.INFINITE);
//            //设置动画重复模式
//            anim.setRepeatMode(ValueAnimator.REVERSE);
//            //设置颜色计算器
//            anim.setEvaluator(new ArgbEvaluator());
//            //开始动画
//            anim.start();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            ball = addBalls(0,getHeight()/2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(ball.getX() - 50f, ball.getY() - 50f);
            ball.getShape().draw(canvas);
            canvas.restore();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            ValueAnimator squash1Animation = ObjectAnimator.ofFloat(ball, "x", 100f, 200f);
            squash1Animation.setDuration(1000);
            squash1Animation.setInterpolator(new DecelerateInterpolator());
            squash1Animation.setRepeatCount(1);
            squash1Animation.setRepeatMode(ValueAnimator.REVERSE);
            squash1Animation.start();
            return true;
        }
    }

    class MyAnimView extends View {
        int RED = 0xffff8080;
        int BLUE = 0xff8080ff;
        List<ShapeHolder> balls = new ArrayList<ShapeHolder>();

        public MyAnimView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        //实现一个简单的默认构造器（重写View必须实现一个构造器）
        public MyAnimView(Context context) {
            super(context);
            //创建动画，改变对象的background属性，值会在RED和BLUE之间计算
            ValueAnimator anim = ObjectAnimator.ofInt(this, "backgroundColor", RED, RED+3);
            //动画的播放时间为3000ms
            anim.setDuration(3000);
            anim.setInterpolator(new BounceInterpolator());
            //设置动画的重复次数
            anim.setRepeatCount(ValueAnimator.INFINITE);
            //设置动画重复模式
            anim.setRepeatMode(ValueAnimator.REVERSE);
            //设置颜色计算器
            anim.setEvaluator(new ArgbEvaluator());
            //开始动画
            anim.start();
        }



        //绘制自定义的内容只需要重写onDraw方法
        @Override
        protected void onDraw(Canvas canvas) {
            //移动画布至指定的区域
            for(ShapeHolder ball : balls){
                //canvas.save();与canvas.retore();之间的代码为一帧的绘制
                canvas.save();
                //把画布移动到小球的坐标，默认坐标点是左上角，所以要加偏移量挪到中心
                canvas.translate(ball.getX() - 50f, ball.getY() - 50f);
                Log.i("ball.getY",ball.getY()-50f+"");
                ball.getShape().draw(canvas);
                canvas.restore();
                //canvas.save();与canvas.retore();之间的代码为一帧的绘制
            }

//            for(Hit hit:hitList){
//                canvas.drawBitmap(hit.getBitmap(),hit.getX(),hit.getY(),null);
//            }
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                ShapeHolder ball = addBalls(event.getX(), event.getY());
                balls.add(ball);
                int h = getHeight();
                float currY = event.getY();
                float currX = event.getX();
                float currWidth = ball.getWidth();
                float currHeight = ball.getHeight();


                float endY = h - 50f;
                long duration = 800;
                double g = 2*h/Math.pow((float)duration,2);
//                long realDuration = (long) ((h - ball.getY()) / h * duration);
                long realDuration = (long) (Math.sqrt(2*h/g));
                //创建小球下落的动画
                ValueAnimator downAnim = ObjectAnimator.ofFloat(ball, "y" , currY, endY);
                downAnim.setDuration(realDuration);
                downAnim.setInterpolator(new AccelerateInterpolator());

                //小球压缩动画
                //1.左移小球x坐标
                ValueAnimator squash1Animation = ObjectAnimator.ofFloat(ball, "x", currX, currX - 50f);
                squash1Animation.setDuration(realDuration / 4);
                squash1Animation.setInterpolator(new DecelerateInterpolator());
                squash1Animation.setRepeatCount(1);
                squash1Animation.setRepeatMode(ValueAnimator.REVERSE);




                //小球压缩动画
                //2.增加小球宽度
                ValueAnimator squash2Animation = ObjectAnimator.ofFloat(ball, "width", currWidth, currWidth + 100f);
                squash2Animation.setDuration(realDuration / 4);
                squash2Animation.setInterpolator(new DecelerateInterpolator());
                squash2Animation.setRepeatCount(1);
                squash2Animation.setRepeatMode(ValueAnimator.REVERSE);


                //小球压缩动画
                //3.降低小球
                ValueAnimator strech1Anim = ObjectAnimator.ofFloat(ball,
                        "y", endY, endY + 50f);//创建控制y属性的属性动画,增加50像素
                strech1Anim.setDuration(realDuration / 4);//设置整个动画的持续时间,比掉落时间要短
                strech1Anim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值（越来越难压）
                //小球回弹
                strech1Anim.setRepeatCount(1);//让它循环播放一次
                strech1Anim.setRepeatMode(ValueAnimator.REVERSE);//反序播放\


                //小球压缩的动画
                //4.减少小球的高度
                ValueAnimator strech2Anim = ObjectAnimator.ofFloat(ball,
                        "height", currHeight, currHeight - 50f);//创建控制height属性的属性动画,增加50像素
                strech2Anim.setDuration(realDuration / 4);//设置整个动画的持续时间,比掉落时间要短
                strech2Anim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值（越来越难压）
                //小球回弹
                strech2Anim.setRepeatCount(1);//让它循环播放一次
                strech2Anim.setRepeatMode(ValueAnimator.REVERSE);//反序播放

                //小球上升的动画
                ValueAnimator upAnim = ObjectAnimator.ofFloat(ball,
                        "y", endY, currY);//创建控制y属性的属性动画
                upAnim.setDuration(realDuration);//设置整个动画的持续时间
                upAnim.setInterpolator(new DecelerateInterpolator());//设置一个减速的插值
                //小球消失的动画
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(ball,
                        "alpha", 1.0f, 0.0f);//创建控制透明度属性的属性动画
                fadeAnim.setDuration(realDuration / 4);//设置整个动画的持续时间
                fadeAnim.setInterpolator(new LinearInterpolator());//设置一个线性的插值
                //添加一个动画播放结束后的监听
                fadeAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //把动画完成后的小球移出容器
                        balls.remove(((ObjectAnimator) animation).getTarget());
                    }
                });
                //创建第一组弹球运动的动画
                AnimatorSet bouncerSet = new AnimatorSet();
                bouncerSet.play(downAnim).before(squash1Animation);//在squash1Anim之前播放downAnim
                bouncerSet.play(squash1Animation).with(squash2Animation);//在播放squash1Anim时播放squash2Anim
                bouncerSet.play(squash1Animation).with(strech1Anim);//在播放squash1Anim时播放strech1Anim
                bouncerSet.play(squash1Animation).with(strech2Anim);//在播放squash1Anim时播放strech2Anim
                bouncerSet.play(upAnim).after(strech2Anim);//在播放strech2Anim之后播放upAnim
                //练习多个Animator嵌套
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(fadeAnim).after(bouncerSet);//在bouncerSet之后播放fadeAnim
                animSet.start();//启动动画
            }
            return true;
        }

    }

    class Hit {
        Bitmap bitmap;
        float x;
        float y;
        public Hit(Bitmap bitmap,float pathWay) {
            this.bitmap = bitmap;
            x = pathWay;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public void createHit(){
        Hit h = new Hit(hitBitmap.get(0),100);
        hitList.add(h);
        ValueAnimator downAnim = ObjectAnimator.ofFloat(h, "y" , (float)0,(float)100);
        downAnim.setDuration(3000);
        downAnim.setInterpolator(new DecelerateInterpolator());
        downAnim.setRepeatCount(100);
        downAnim.setRepeatMode(ValueAnimator.REVERSE);
        downAnim.start();
    }

    private ShapeHolder addBalls(float x ,float y){
        int RED=(int)(Math.random()*255)<<16;
        int GREEN=(int)(Math.random()*255)<<8;
        int BLUE=(int)(Math.random()*255);
        int COLOR = 0xff000000|RED|GREEN|BLUE;
        int DARKCOLOR = 0xff000000 | (RED / 4) | (GREEN / 4) | (BLUE / 4);
        OvalShape circle = new OvalShape();
//        设置圆的长高
        circle.resize(100f,100f);
//        创建ShapeDrawable
        ShapeDrawable shapeDrawable = new ShapeDrawable(circle);
//        创建自己的Drawable
        ShapeHolder shapeHolder = new ShapeHolder(shapeDrawable);
//      设置绘制图形的颜色
        shapeHolder.setColor(COLOR);
//      设置图形的坐标
        shapeHolder.setX(x);
        shapeHolder.setY(y);
//      设置图形的环形放射颜色
        RadialGradient gradient = new RadialGradient(
                75f, 25f, 100, COLOR, DARKCOLOR, Shader.TileMode.CLAMP);
        shapeHolder.setGradient(gradient);
        //返回设置好的图形
        return shapeHolder;
    }

    public List<Bitmap> split(Bitmap bitmap, int xPiece, int yPiece){
        List<Bitmap> pieces = new ArrayList<Bitmap>(xPiece * yPiece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / xPiece;
        int pieceHeight = height / yPiece;
        for (int i = 0; i < yPiece; i++) {
            for (int j = 0; j < xPiece; j++) {
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                Bitmap b;
                if(i!=0)
                    b= Bitmap.createBitmap(bitmap, xValue, yValue+10,
                            pieceWidth, pieceHeight-10);
                else
                    b= Bitmap.createBitmap(bitmap, xValue, yValue,
                            pieceWidth, pieceHeight);

                pieces.add(compressImg(b,b.getWidth(),100));
            }
        }
        return pieces;
    }

    public Bitmap compressImg(Bitmap bitmap , double newWidth,double newHeight){
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        //创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        float scaleWidth = ((float)newWidth)/width;
        float scaleHeight = ((float)newHeight)/height;
        matrix.postScale(scaleWidth,scaleHeight);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,0,(int)width,(int)height,matrix,true);
        return newBitmap;
    }

    /**
     *压缩图片
     * @param list  图片List
     * @param newWidth  新的宽
     * @param newHeight 新的高
     */
    public void compressImg(List<Bitmap> list,double newWidth,double newHeight){
        for(int i = 0;i<list.size();i++){
            if(newWidth ==0)
                list.set(i,compressImg(list.get(i),list.get(i).getWidth(),newHeight));
            else if(newHeight == 0)
                list.set(i,compressImg(list.get(i),newWidth,list.get(i).getHeight()));
            else
                list.set(i,compressImg(list.get(i),newWidth,newHeight));
        }
    }
}
