package com.ghostfootball.game;
import android.os.Bundle; import android.widget.TextView; import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    FootballView gameView; TextView txtRoom; CommentatorManager commentator;
    protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_main);
        commentator = new CommentatorManager(this);
        gameView=findViewById(R.id.gameView); txtRoom=findViewById(R.id.txtRoom);
        String room=getSharedPreferences("game",0).getString("room","OFFLINE");
        boolean isHost=getSharedPreferences("game",0).getBoolean("isHost",true);
        txtRoom.setText("Room: "+room + (room.equals("OFFLINE")?" | Offline":" | Online VS"));
        gameView.setRoom(room, commentator); // Pass commentator
        if(!room.equals("OFFLINE") && isHost) commentator.onRoomCreated(room);
        findViewById(R.id.btnPass).setOnClickListener(v->{ gameView.doPass(); commentator.onPass(); });
        findViewById(R.id.btnShoot).setOnClickListener(v->{ gameView.doShoot(); commentator.onShoot(); });
        findViewById(R.id.btnSprint).setOnTouchListener((v,e)->{ if(e.getAction()==0)gameView.setSprint(true); if(e.getAction()==1)gameView.setSprint(false); return false; });
    }
    protected void onPause(){super.onPause();gameView.pause();}
    protected void onResume(){super.onResume();gameView.resume();}
}
