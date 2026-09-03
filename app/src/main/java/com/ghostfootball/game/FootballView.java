package com.ghostfootball.game;
import android.content.Context; import android.graphics.*; import android.util.AttributeSet; import android.view.MotionEvent; import android.view.SurfaceView;
public class FootballView extends SurfaceView implements Runnable {
    Thread thread; boolean playing; Paint paint; Canvas canvas;
    float ballX=540, ballY=960, ballVX=0, ballVY=0;
    float playerX=400, playerY=960, enemyX=680, enemyY=960;
    int scoreHome=0, scoreAway=0; boolean sprint=false; float joyX=0, joyY=0;
    RectF goal1, goal2; String roomCode="OFFLINE"; CommentatorManager commentator;
    public FootballView(Context c, AttributeSet a){ super(c,a); paint=new Paint(); goal1=new RectF(0,400,30,900); goal2=new RectF(1050,400,1080,900); }
    public void setRoom(String code, CommentatorManager com){ roomCode=code; commentator=com; }
    public void setRoom(String code){ roomCode=code; }
    public void resume(){ playing=true; thread=new Thread(this); thread.start(); if(commentator!=null) commentator.onStart(); }
    public void pause(){ playing=false; try{thread.join();}catch(Exception e){} }
    public void doPass(){ ballVX=(enemyX-ballX)*0.05f; ballVY=(enemyY-ballY)*0.05f; }
    public void doShoot(){ ballVX=(goal2.centerX()-ballX)*0.08f; ballVY=(goal2.centerY()-ballY)*0.08f; }
    public void setSprint(boolean s){ sprint=s; }
    public boolean onTouchEvent(MotionEvent e){ if(e.getAction()==MotionEvent.ACTION_MOVE||e.getAction()==MotionEvent.ACTION_DOWN){ joyX=(e.getX()-200)/100f; joyY=(e.getY()-800)/100f; joyX=Math.max(-1,Math.min(1,joyX)); joyY=Math.max(-1,Math.min(1,joyY)); } return true; }
    public void run(){ while(playing){ update(); draw(); try{Thread.sleep(16);}catch(Exception ex){} } }
    void update(){
        float speed=sprint?8:4; playerX+=joyX*speed; playerY+=joyY*speed;
        if(Math.hypot(playerX-ballX, playerY-ballY)<50){ ballX=playerX+20; ballY=playerY; }
        ballX+=ballVX; ballY+=ballVY; ballVX*=0.98f; ballVY*=0.98f;
        enemyX+=(ballX-enemyX)*0.02f; enemyY+=(ballY-enemyY)*0.02f;
        if(goal1.contains(ballX,ballY)){ scoreAway++; if(commentator!=null) commentator.onGoal(); reset(); }
        if(goal2.contains(ballX,ballY)){ scoreHome++; if(commentator!=null) commentator.onGoal(); reset(); }
        if(ballX<0||ballX>1080) ballVX*=-1; if(ballY<0||ballY>1920) ballVY*=-1;
        playerX=Math.max(30,Math.min(1050,playerX)); playerY=Math.max(50,Math.min(1870,playerY));
    }
    void reset(){ ballX=540; ballY=960; ballVX=0; ballVY=0; }
    void draw(){
        if(!getHolder().getSurface().isValid()) return;
        canvas=getHolder().lockCanvas();
        canvas.drawColor(Color.rgb(34,139,34));
        paint.setColor(Color.WHITE); paint.setStrokeWidth(5); paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(50,50,1030,1870,paint); canvas.drawLine(50,960,1030,960,paint); canvas.drawCircle(540,960,100,paint);
        paint.setStyle(Paint.Style.FILL); paint.setColor(Color.WHITE); canvas.drawRect(goal1,paint); canvas.drawRect(goal2,paint);
        paint.setColor(Color.BLUE); canvas.drawCircle(playerX,playerY,28,paint);
        paint.setColor(Color.RED); canvas.drawCircle(enemyX,enemyY,28,paint);
        paint.setColor(Color.WHITE); canvas.drawCircle(ballX,ballY,16,paint);
        paint.setTextSize(55); paint.setColor(Color.WHITE); canvas.drawText(scoreHome+" - "+scoreAway, 450, 120, paint);
        paint.setTextSize(30); canvas.drawText("🇮🇹 "+roomCode, 40, 1900, paint);
        canvas.drawText("GHOST FOOTBALL ONLINE", 300, 1840, paint);
        getHolder().unlockCanvasAndPost(canvas);
    }
    }
