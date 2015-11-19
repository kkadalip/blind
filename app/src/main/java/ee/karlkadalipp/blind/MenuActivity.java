package ee.karlkadalipp.blind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ee.karlkadalipp.blind.Apps.AppsActivity;

import com.blind.karl.blind.R;

public class MenuActivity extends Activity {

	TextView tvMainMenuTitle;

//	Button btnMenu1;
//	Button btnMenu2;
//	Button btnMenu3;
//	Button btnMenu4;
	Intent intent;
	
//	Button bLangET;
//	Button bLangEN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu_main);

		tvMainMenuTitle = (TextView) this.findViewById(R.id.tvMainMenuTitle);

//		btnMenu1 = (Button) this.findViewById(R.id.bMenu1);
//		btnMenu2 = (Button) this.findViewById(R.id.bMenu2);
//		btnMenu3 = (Button) this.findViewById(R.id.bMenu3);
//		btnMenu4 = (Button) this.findViewById(R.id.bMenu4);


		
//		bLangET = (Button) this.findViewById(R.id.bLangET);
//		bLangEN = (Button) this.findViewById(R.id.bLangEN);
//		bLangET.setOnClickListener(this);
//		bLangEN.setOnClickListener(this);
		

	}
	
	//private void updateTexts()
	//{
		//tvMainMenuTitle.setText(R.string.tvMainMenuTitle);

//		btnMenu1.setText(R.string.btnMenuMain1);
//		btnMenu2.setText(R.string.btnMenuMain2);
//		btnMenu3.setText(R.string.btnMenuMain3);
//		btnMenu4.setText(R.string.btnMenuMain4);
		//bLangET.setText(R.string.bLangET);
		//bLangEN.setText(R.string.bLangEN);

		//btnLocale.setText(R.string.btnLocale);
	//}

	public void btnMenu1Click(View v){
		intent = new Intent(this, AppsActivity.class);
		//this.startActivity(intent);
		this.finish(); // GOING BACK TO APPS
	}

	public void btnMenu2Click(View v){
		intent = new Intent(this, ListenTextActivity.class);
		this.startActivity(intent);
	}

	public void btnMenu3Click(View v){
		intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
	}

	public void btnMenu4Click(View v){
		intent = new Intent(this, SettingsActivity.class);
		this.startActivity(intent);
	}

}
