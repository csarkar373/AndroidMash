package com.westhillcs.randomspritemotion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.zip.Inflater;

/**
 * Created by Chandan on 7/26/2016.
 */
public class MashView extends SurfaceView {

    private SurfaceHolder holder;
    private Canvas canvas;

    private static Random random;
    private CountDownTimer countDownTimer;
    private Bitmap sprite;
    private int score;
    private int timeLeft;
    int x, y;

    public MashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize(context);
    }

    public MashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize(context);
    }

    public MashView(Context context) {
        super(context);
        this.initialize(context);

    }

    public void initialize(Context context) {

        score = 0;
        timeLeft=0;
        holder = getHolder();
        this.setWillNotDraw(false);

        random = new Random();
        final Context c = context;
        sprite = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                invalidate();
                timeLeft=(int)millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {

                Toast.makeText(c, "GAME OVER", Toast.LENGTH_SHORT).show();
                timeLeft=0;
                invalidate();
            }
        };

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                countDownTimer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // not used
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // not used
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // if user is touching sprite, print a TOAST message
                if ( event.getX() > x && event.getX() < (x + sprite.getWidth())
                        && event.getY() > y && event.getY() < (y + sprite.getHeight()))
                {
                   /* Toast.makeText(c, "Sprite Touched: (" + event.getX() + ","
                            + event.getY() + ")", Toast.LENGTH_SHORT).show(); */
                    incrementScore();
                }
                return false;
            }
        });

    }

    private void incrementScore() {
        ++score;
    }

    private void setScore(int newScore) {
        score = newScore;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        x = random.nextInt(canvas.getWidth()-sprite.getWidth());
        y = random.nextInt(canvas.getHeight()-sprite.getHeight());
        canvas.drawBitmap(sprite, x, y, null);
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setTextSize(45);
        canvas.drawText("Score: " + score + "        Time Left:  " + timeLeft, 30, 40, paint);
    }
}










