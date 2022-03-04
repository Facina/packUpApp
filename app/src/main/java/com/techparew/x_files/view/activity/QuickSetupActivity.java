package com.techparew.x_files.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.techparew.x_files.R;

public class QuickSetupActivity extends AppCompatActivity {

    CardView acceptButton;
    CardView declineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_setup);


        acceptButton = (CardView) findViewById(R.id.quickSetupAcceptButton);
        declineButton = (CardView) findViewById(R.id.quickSetupDeclineButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestionary();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declineQuestionary();
            }
        });







    }


    void startQuestionary(){
        Intent preferenceIntent = new Intent(this,PreferenceQuickSetup.class);
        startActivity(preferenceIntent);
    }

    void declineQuestionary(){
        finish();
    }
}
