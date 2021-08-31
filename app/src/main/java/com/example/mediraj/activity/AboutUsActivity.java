package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mediraj.R;

public class AboutUsActivity extends AppCompatActivity {
    TextView toolbarText;
    ImageView toolbarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbarText = findViewById(R.id.toolbarText);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        toolbarText.setText(getString(R.string.about_us));
        toolbarBtn.setOnClickListener(v -> finish());
    }
}