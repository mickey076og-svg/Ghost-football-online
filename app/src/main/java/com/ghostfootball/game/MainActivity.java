package com.ghostfootball.game;
import android.os.Bundle; import android.widget.TextView; import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    FootballView gameView; TextView txtRoom;
    protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
        gameView=findViewById(R.id.gameView); txtRoom=findViewById(R.id.txtRoom);
        String room=getSharedPreferences("game",0).getString("room","OFFLINE");
        txtRoom.setText("Room: "+room);
        gameView.setRoom(room);
        findViewById(R.id.btnPass).setOnClickListener(v->gameView.doPass());
        findViewById(R.id.btnShoot).setOnClickListener(v->gameView.doShoot());
        findViewById(R.id.btnSprint).setOnTouchListener((v,e)->{ if(e.getAction()==0)gameView.setSprint(true); if(e.getAction()==1)gameView.setSprint(false); return false; });
    }
    protected void onPause(){super.onPause();gameView.pause();} protected void onResume(){super.onResume();gameView.resume();}
      }
