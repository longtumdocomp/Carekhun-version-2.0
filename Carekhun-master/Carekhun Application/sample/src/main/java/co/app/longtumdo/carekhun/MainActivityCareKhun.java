package co.app.longtumdo.carekhun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

/**
 * Created by TOPPEE on 7/19/2017.
 */

public class MainActivityCareKhun extends AppCompatActivity implements TextToSpeech.OnInitListener {
    MediaPlayer ourSong;

    //Initial Text to Speech
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //***********************************************************************************************************

        //Initial Text to Speech
        tts = new TextToSpeech(this, this, "com.google.android.tts");

        ourSong = MediaPlayer.create(MainActivityCareKhun.this, R.raw.intentsuccess);               //Sound
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean music = getPrefs.getBoolean("checkbox",true);
        if(music == true)
            ourSong.start();

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openStartingPoint = new Intent(getApplicationContext(),IntroSliderCarekhun.class);
                    startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }

    //Initial Text to Speech
    @Override
    protected void onPause() {
        super.onPause();
        ourSong.release();
        finish();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            //tts.speak("สวัสดี เราแคร์คุณ", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
