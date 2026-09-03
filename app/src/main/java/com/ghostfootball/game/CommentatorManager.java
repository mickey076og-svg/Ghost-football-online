package com.ghostfootball.game;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import java.util.Random;

public class CommentatorManager {
    TextToSpeech tts;
    Random rand = new Random();

    String[] goalCalls = {"GOOOOOL! CHE GOLAZO!", "MAMMA MIA! CHE GOL FANTASTICO!", "RETE! GOL BELLISSIMO!", "GOL! GOL! GOL! INCREDIBILE!"};
    String[] passCalls = {"Passaggio!", "Palla buona!", "Bel passaggio!"};
    String[] shootCalls = {"Tiro! Tiro!...", "Va a tirare!", "Conclusione!"};
    String[] saveCalls = {"Parata! Grande parata!", "Salvataggio miracoloso!"};
    String[] startCalls = {"Si parte! Forza ragazzi!", "Inizia la partita! Che spettacolo!"};

    public CommentatorManager(Context ctx){
        tts = new TextToSpeech(ctx, status -> {
            if(status == TextToSpeech.SUCCESS){
                tts.setLanguage(new Locale("it", "IT"));
                tts.setSpeechRate(1.1f);
                tts.setPitch(1.0f);
                speak(startCalls[0]);
            }
        });
    }

    void speak(String text){ if(tts!=null) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null); }
    void onGoal(){ speak(goalCalls[rand.nextInt(goalCalls.length)]); }
    void onPass(){ if(rand.nextInt(3)==0) speak(passCalls[rand.nextInt(passCalls.length)]); }
    void onShoot(){ speak(shootCalls[rand.nextInt(shootCalls.length)]); }
    void onSave(){ speak(saveCalls[rand.nextInt(saveCalls.length)]); }
    void onStart(){ speak(startCalls[rand.nextInt(startCalls.length)]); }
    void onRoomCreated(String code){ speak("Stanza creata! Codice " + code.replace("GHOST-","")); }
               }
