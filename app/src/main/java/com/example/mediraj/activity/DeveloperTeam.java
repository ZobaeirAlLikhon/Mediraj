package com.example.mediraj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mediraj.R;

public class DeveloperTeam extends AppCompatActivity {

    TextView toolbarText;
    ImageView toolbarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_team);

        toolbarText = findViewById(R.id.toolbarText);
        toolbarBtn = findViewById(R.id.toolbarBtn);
        toolbarText.setText(getString(R.string.dev));
        toolbarBtn.setOnClickListener(v -> finish());
    }
}