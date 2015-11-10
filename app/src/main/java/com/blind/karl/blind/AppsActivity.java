package com.blind.karl.blind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_main);
    }

    public void gridButtonClick(View v){
        Intent intent = new Intent(this, AppsExtraActivity.class);
        this.startActivity(intent);
    }
}
