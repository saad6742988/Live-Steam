package com.example.livestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class Oplayer1 extends AppCompatActivity {

    DacastPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oplayer1);
//        b0516811-9b8d-598a-cbb2-2cf8f3e3363c-live-909e6d46-f73b-d514-f2cb-9caecefe135b

        player = new DacastPlayer(this, "b0516811-9b8d-598a-cbb2-2cf8f3e3363c-live-909e6d46-f73b-d514-f2cb-9caecefe135b"/*, "https://cdn.theoplayer.com/demos/preroll.xml"*/);
        ConstraintLayout layout = findViewById(R.id.mainLayout);
        player.getView().setLayoutParams(
                new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT
                )
        );
        layout.addView(player.getView());
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.onDestroy();
    }
}