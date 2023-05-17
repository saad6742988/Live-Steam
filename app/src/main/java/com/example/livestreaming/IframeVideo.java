package com.example.livestreaming;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;

public class IframeVideo extends AppCompatActivity {

    private WebView webView;
//    String youTubeUrl = "https://www.youtube.com/embed/47yJ2XCRLZs";
    String youTubeUrl = "https://iframe.dacast.com/live/b0516811-9b8d-598a-cbb2-2cf8f3e3363c/909e6d46-f73b-d514-f2cb-9caecefe135b";

    String frameVideo = "<html><body>" +
            "<iframe width=\"420\" height=\"315\" " +
            "src='" + youTubeUrl + "' frameborder=\"5\" allowfullscreen>" +
            "</iframe></body></html>";


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iframe_video);
        webView = (WebView) findViewById(R.id.webView);

        String regexYoutUbe = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (true||youTubeUrl.matches(regexYoutUbe)) {

            //setting web client
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            //web settings for JavaScript Mode
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webView.loadData(frameVideo, "text/html", "utf-8");


        } else {
            Toast.makeText(IframeVideo.this, "This is other video",
                    Toast.LENGTH_SHORT).show();
        }
    }
}