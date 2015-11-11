package com.blind.karl.blind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MenuActivity extends Activity implements OnClickListener {

	TextView tvMainMenuTitle;

	Button btnMenu1;
	Button btnMenu2;
	Button btnMenu3;
	Button btnMenu4;
	Intent intent;
	
//	Button bLangET;
//	Button bLangEN;

	Button btnLocale;

	private Locale myLocale;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu_main);

		tvMainMenuTitle = (TextView) this.findViewById(R.id.tvMainMenuTitle);

		btnMenu1 = (Button) this.findViewById(R.id.bMenu1);
		btnMenu2 = (Button) this.findViewById(R.id.bMenu2);
		btnMenu3 = (Button) this.findViewById(R.id.bMenu3);
		btnMenu4 = (Button) this.findViewById(R.id.bMenu4);
		btnMenu1.setOnClickListener(this);
		btnMenu2.setOnClickListener(this);
		btnMenu3.setOnClickListener(this);
		btnMenu4.setOnClickListener(this);

		btnLocale = (Button) this.findViewById(R.id.btnLocale);
		
//		bLangET = (Button) this.findViewById(R.id.bLangET);
//		bLangEN = (Button) this.findViewById(R.id.bLangEN);
//		bLangET.setOnClickListener(this);
//		bLangEN.setOnClickListener(this);
		
		loadLocale();	
	}
	
	private void updateTexts()
	{
		tvMainMenuTitle.setText(R.string.tvMainMenuTitle);

		btnMenu1.setText(R.string.btnMenuMain1);
		btnMenu2.setText(R.string.btnMenuMain2);
		btnMenu3.setText(R.string.btnMenuMain3);
		btnMenu4.setText(R.string.btnMenuMain4);
		//bLangET.setText(R.string.bLangET);
		//bLangEN.setText(R.string.bLangEN);

		btnLocale.setText(R.string.btnLocale);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.bMenu1:
			intent = new Intent(this, AppsActivity.class);
			this.startActivity(intent);
			break;
		case R.id.bMenu2:
			intent = new Intent(this, ListenTextActivity.class);
			this.startActivity(intent);
			break;
		case R.id.bMenu3:
			intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.bMenu4:
			// APPS 2 temporarily here
			intent = new Intent(this, SettingsActivity.class);
			this.startActivity(intent);
			break;
/*		case R.id.bLangET:
			changeLang("et");
			break;
		case R.id.bLangEN:
			changeLang("en");
			break;*/
		}
	}

	public void btnClickLocale(View v){
		Locale current = Locale.getDefault();
		Log.d("log", "btn locale clicked, current locale is " + current.toString());
		if(current.toString().equals("et")){
			changeLang("en");
		}else if(current.toString().equals("en")){
			changeLang("et");
		}
	}

/*	public void btnClickLocaleEN(View v){
		changeLang("en");
	}*/
	
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
