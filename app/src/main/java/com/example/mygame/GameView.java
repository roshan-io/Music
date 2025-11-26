package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread;
    private boolean isPlaying = true;
    private float ballX = 200, ballY = 200;
    private float velocityY = 0;
    private float gravity = 1.0f;
    private int score = 0;
    private Paint paint = new Paint();

    public GameView(Context context) {
        super(context);
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        velocityY += gravity;
        ballY += velocityY;

        // bounce from bottom
        if (ballY > getHeight() - 100) {
            ballY = getHeight() - 100;
            velocityY = -25;
        }

        score++;
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);

            // draw ball
            paint.setColor(Color.CYAN);
            canvas.drawCircle(ballX, ballY, 50, paint);

            // draw score
            paint.setColor(Color.WHITE);
            canvas.drawText("Score: " + score, 50, 100, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(16); // ~60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityY = -30;
        return true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (Exception e) {}
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new Thread(this);
        gameThread.start();
    }
          }
