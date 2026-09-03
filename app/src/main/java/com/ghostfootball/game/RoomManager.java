package com.ghostfootball.game;
import com.google.firebase.database.*;

public class RoomManager {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public interface RoomListener {
        void onJoined();
        void onError(String msg);
        void onOpponentJoined();
    }

    // CREATE ROOM - Generate GHOST-XXXX code
    public String createRoom(RoomListener listener){
        String code = "GHOST-" + (1000 + (int)(Math.random()*9000));
        db.child("rooms").child(code).child("status").setValue("waiting");
        db.child("rooms").child(code).child("createdAt").setValue(System.currentTimeMillis());

        // Listen if someone joins my room
        db.child("rooms").child(code).child("status").addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot s){
                if(s.exists() && s.getValue().toString().equals("playing")){
                    listener.onOpponentJoined();
                }
            }
            public void onCancelled(DatabaseError e){}
        });

        listener.onJoined();
        return code;
    }

    // JOIN ROOM - Enter GHOST-XXXX
    public void joinRoom(String code, RoomListener listener){
        code = code.trim().toUpperCase();
        db.child("rooms").child(code).addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(DataSnapshot snap){
                if(snap.exists()){
                    String status = snap.child("status").getValue()!=null ? snap.child("status").getValue().toString() : "waiting";
                    if(status.equals("playing")){
                        listener.onError("Room full! Already playing");
                    } else {
                        db.child("rooms").child(code).child("status").setValue("playing");
                        listener.onJoined();
                    }
                } else {
                    listener.onError("Room "+code+" not found! Check code");
                }
            }
            public void onCancelled(DatabaseError e){
                listener.onError(e.getMessage());
            }
        });
    }

    // DELETE ROOM when game ends
    public void deleteRoom(String code){
        db.child("rooms").child(code).removeValue();
    }
  }
