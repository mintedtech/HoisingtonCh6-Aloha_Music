package com.example.alohamusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnUkelele, btnDrums;
    private MediaPlayer mpUkelele, mpDrums;

    private MediaPlayer mpActive;   // will be set to either of the above
    private boolean isPlaying;

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlaying && mpActive!=null)
            mpActive.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying && mpActive!=null)
            mpActive.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get a reference to the button
        btnUkelele = findViewById(R.id.btnUkulele);
        btnDrums = findViewById(R.id.btnDrums);

        // set the listeners for each button to the single listener object below
        btnUkelele.setOnClickListener(listener);
        btnDrums.setOnClickListener(listener);

        // set the Media Players
        mpUkelele = MediaPlayer.create(this, R.raw.ukulele);
        mpDrums = MediaPlayer.create(this, R.raw.drums);

        // set isPlaying to false
        isPlaying = false;
    }

    Button.OnClickListener listener = new Button.OnClickListener() {
        Button btnActive, btnInactive;
        String strPlay, strPause;

        @Override
        public void onClick(View v) {
            setActiveInstrument(v);
            playPauseMusicAndUpdateUI();
        }

        private void setActiveInstrument(View v) {
            switch (v.getId()) {
                case R.id.btnUkulele:
                    btnActive = btnUkelele;
                    btnInactive = btnDrums;
                    mpActive = mpUkelele;
                    strPlay = getString(R.string.btnUkulele);
                    strPause = getString(R.string.btnUkulelePause);
                    break;
                case R.id.btnDrums:
                    btnActive = btnDrums;
                    btnInactive = btnUkelele;
                    mpActive = mpDrums;
                    strPlay = getString(R.string.btnDrums);
                    strPause = getString(R.string.btnDrumsPause);
                    break;
            }
        }

        private void playPauseMusicAndUpdateUI() {
            if (isPlaying)             // music is currently playing
            {
                mpActive.pause();
                isPlaying = false;

                btnActive.setText(strPlay);
                btnInactive.setVisibility(View.VISIBLE);
            } else {                    // music is currently paused
                mpActive.start();
                isPlaying = true;

                btnActive.setText(strPause);
                btnInactive.setVisibility(View.INVISIBLE);
            }
        }
    };
}
