package com.ghostfootball.game;
import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import java.util.Random;
public class MenuActivity extends AppCompatActivity {
    EditText inputCode; TextView txtMyCode;
    protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_menu);
        Button btnCreate=findViewById(R.id.btnCreate); Button btnJoin=findViewById(R.id.btnJoin); Button btnOffline=findViewById(R.id.btnOffline);
        inputCode=findViewById(R.id.inputCode); txtMyCode=findViewById(R.id.txtMyCode);
        btnCreate.setOnClickListener(v->{ String code="GHOST-"+(1000+new Random().nextInt(9000)); txtMyCode.setText("Your Code:\n"+code+"\nShare on WhatsApp!"); getSharedPreferences("game",0).edit().putString("room",code).putBoolean("isHost",true).apply(); Toast.makeText(this,"Room "+code+" Created! Open on 2nd phone too",Toast.LENGTH_LONG).show(); });
        btnJoin.setOnClickListener(v->{ String code=inputCode.getText().toString().trim().toUpperCase(); if(code.length()<6){Toast.makeText(this,"Enter GHOST-XXXX",0).show();return;} getSharedPreferences("game",0).edit().putString("room",code).putBoolean("isHost",false).apply(); startActivity(new Intent(this,MainActivity.class)); });
        btnOffline.setOnClickListener(v->{ getSharedPreferences("game",0).edit().putString("room","OFFLINE").apply(); startActivity(new Intent(this,MainActivity.class)); });
        // Long press Create to actually start as host
        txtMyCode.setOnClickListener(v->{ if(!txtMyCode.getText().toString().isEmpty()) startActivity(new Intent(this,MainActivity.class)); });
    }
          }
