package com.example.livestreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.video.VideoCanvas;

public class AgoraBroadcaster extends AppCompatActivity {

    // Fill the App ID of your project generated on Agora Console.
    private final String appId = "ba6c1d53ce2341cead37d8db680a0711";
    // Fill the channel name.
    private String channelName = "test";
    // Fill the temp token generated on Agora Console.
    private String token = "<your access token>";
    // An integer that identifies the local user.
    private int uid = 2;
    private boolean isJoined = false;

    private RtcEngine mRtcEngine;

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            Log.i("MyAgora", "onJoinChannelSuccess");
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            Log.i("MyAgora", "onUserJoined");
            onRemoteUserJoined(uid, elapsed);
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            Log.i("MyAgora", "onUserOffline");
        }

        @Override
        public void onError(int err) {
            Log.i("Agora", "onError: " + err);
        }
    };
    //SurfaceView to render local video in a Container.
    private SurfaceView localSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_broadcaster);
        initializeAgoraEngine();
        FrameLayout container = findViewById(R.id.local_video_view_container1);
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = new SurfaceView(getBaseContext());
        container.addView(localSurfaceView);

        // Call setupLocalVideo with a VideoCanvas having uid set to 0.
        initializeAgoraEngine();
        setupLocalVideo();
        startVideoStreaming();
    }
    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), appId, mRtcEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }
    private void setupLocalVideo() {
        mRtcEngine.enableVideo();
        FrameLayout container = findViewById(R.id.local_video_view_container1);
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = new SurfaceView(getBaseContext());
        localSurfaceView.setZOrderMediaOverlay(true);
        container.addView(localSurfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }

    private void startVideoStreaming() {
        mRtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
        mRtcEngine.startPreview();
        joinChannel();
    }

    private void joinChannel() {
        mRtcEngine.joinChannel(null, "demoChannel1", "Extra Optional Data", 0);
    }

    private void onRemoteUserJoined(int uid, int elapsed) {
        // no need to add the remote video view as the app only hosts the video stream
    }

    private void stopVideoStreaming() {
        mRtcEngine.leaveChannel();
        mRtcEngine.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopVideoStreaming();
        RtcEngine.destroy();
        mRtcEngine = null;
    }
}