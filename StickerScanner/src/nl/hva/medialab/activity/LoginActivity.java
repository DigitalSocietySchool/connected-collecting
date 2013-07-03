package nl.hva.medialab.activity;

import nl.hva.medialab.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
//		http://www.ezzylearning.com/tutorial.aspx?tid=1763429
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void sync(View view)
	{
		final Intent registerActivityIntent = new Intent(this, ScanningActivity.class);
//		final Intent registerActivityIntent = new Intent(this, ScanActivity.class);
		startActivity(registerActivityIntent);
	}

}
