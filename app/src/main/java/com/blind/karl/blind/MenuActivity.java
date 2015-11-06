package com.blind.karl.blind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Locale;

public class MenuActivity extends Activity implements OnClickListener {

	Button bMenu1;
	Button bMenu2;
	Button bMenu3;
	Button bMenu4;
	Intent intent;
	
	Button bLangET;
	Button bLangEN;
	
	private Locale myLocale;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu_main);
		bMenu1 = (Button) this.findViewById(R.id.bMenu1);
		bMenu2 = (Button) this.findViewById(R.id.bMenu2);
		bMenu3 = (Button) this.findViewById(R.id.bMenu3);
		bMenu4 = (Button) this.findViewById(R.id.bMenu4);
		bMenu1.setOnClickListener(this);
		bMenu2.setOnClickListener(this);
		bMenu3.setOnClickListener(this);
		bMenu4.setOnClickListener(this);
		
//		bLangET = (Button) this.findViewById(R.id.bLangET);
//		bLangEN = (Button) this.findViewById(R.id.bLangEN);
//		bLangET.setOnClickListener(this);
//		bLangEN.setOnClickListener(this);
		
		loadLocale();	
	}
	
	private void updateTexts()
	{
		bMenu1.setText(R.string.bMenuMain1);
		bMenu2.setText(R.string.bMenuMain2);
		bMenu3.setText(R.string.bMenuMain3);
		bMenu4.setText(R.string.bMenuMain4);
		//bLangET.setText(R.string.bLangET);
		//bLangEN.setText(R.string.bLangEN);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.bMenu1:
			intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.bMenu2:
			//intent = new Intent(this, MenuStudent.class);
			//intent = new Intent(this, .class);
			//this.startActivity(intent);
			break;
		case R.id.bMenu3:
			//intent = new Intent(this, .class);
			//this.startActivity(intent);
			break;
		case R.id.bMenu4:
			//intent = new Intent(this, .class);
			//this.startActivity(intent);
			break;
/*		case R.id.bLangET:
			changeLang("et");
			break;
		case R.id.bLangEN:
			changeLang("en");
			break;*/
		}
	}
	
	public void changeLang(String lang)
	{
		if(lang.equalsIgnoreCase("")){
			return;
		}else{
			myLocale = new Locale(lang);
			saveLocale(lang);
			Locale.setDefault(myLocale);
			android.content.res.Configuration config = new android.content.res.Configuration();
			config.locale = myLocale;
			getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
			updateTexts();
		}
	}

	public void saveLocale(String lang)
	{
		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
	}
	
	public void loadLocale()
	{
		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");
		changeLang(language);
	}

}
