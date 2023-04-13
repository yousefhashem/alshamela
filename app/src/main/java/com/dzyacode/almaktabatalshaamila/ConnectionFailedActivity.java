package com.dzyacode.almaktabatalshaamila;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class ConnectionFailedActivity extends AppCompatActivity {

    Handler handler;

    GifImageView net;
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_failed);
        net = findViewById(R.id.error_img);
        t1 = findViewById(R.id.error_text);
        t2 = findViewById(R.id.error_disc);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run() {
                if (isNetworkConnected()) {
                    net.setImageResource(R.drawable.ic_internet);
                    t1.setText("Internet Connected");
                    t1.setTextColor(Color.parseColor("#FF34DF27"));
                    t2.setVisibility(View.INVISIBLE);
                    handler.postDelayed(() -> finish(), 1000);
                } else {
                    isNetworkConnected();
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        closeFull();
    }

    private void closeFull() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View customView = this.getLayoutInflater().inflate(R.layout.close_dialog, null);
        builder.setView(customView);

        Button yesBtn = customView.findViewById(R.id.yes_btn);
        yesBtn.setOnClickListener(task -> finishAffinity());

        Button noBtn = customView.findViewById(R.id.no_btn);
        noBtn.setOnClickListener(task -> builder.create().cancel());

        builder.create().show();
    }

}