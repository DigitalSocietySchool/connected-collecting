package nl.hva.medialab.activity;

import nl.hva.medialab.R;
import nl.hva.medialab.data.DataService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.medialab.beans.UserResult;

public class StickersActivity extends Activity {
	
	private ProgressDialog progressDialog;

	private LoadStickersTask task;
	
	//https://github.com/nostra13/Android-Universal-Image-Loader

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stickers);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
		if (task != null)
		{
			task.cancel(true);
			task = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.stickers, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_refresh:
			loadStickers();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadStickers()
	{
		task = new LoadStickersTask();
		task.execute();
	}
	
	private class LoadStickersTask extends AsyncTask<String, String, UserResult> {

		@Override
		protected void onPreExecute()
		{
			progressDialog = ProgressDialog.show(StickersActivity.this, getString(R.string.loading_images), getString(R.string.please_wait), true);
		}

		@Override
		protected UserResult doInBackground(String... arg0)
		{
			DataService service = new DataService();
			return service.getStickers(1L);
		}

		@Override
		protected void onPostExecute(UserResult result)
		{
			progressDialog.dismiss();
			progressDialog = null;
			Toast.makeText(getApplicationContext(), result.getResponse().toString(), Toast.LENGTH_SHORT).show();
		}
		
	}

}
