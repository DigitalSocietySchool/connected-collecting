package nl.hva.medialab.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nl.hva.medialab.R;
import nl.hva.medialab.data.DataService;
import nl.hva.medialab.util.ImageUtil;
import nl.hva.medialab.util.IntentUtil;
import nl.hva.medialab.util.ResponseUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.medialab.beans.UserResult;

public class ScanActivity extends Activity {

	private ProgressDialog progressDialog;
	
	private static final long USER_ID = 2L;

	private SendStickersTask sendTask;
	
	private String tempFilePath = "";
	
	private static final String TEMP_KEY = "tempKey";
	
	private final static int SCANNING_INTENT_CODE = 1;
	
	private final static int IMAGE_MAX_SIZE = 640;
	
	private final static String SCANNER_DIR = "/StickerScanner";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null)
		{
			tempFilePath = savedInstanceState.getString(TEMP_KEY);
		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_scan);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString(TEMP_KEY, tempFilePath);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
		if (sendTask != null)
		{
			sendTask.cancel(true);
			sendTask = null;
		}
	}

	public void scanSticker(View view)
	{
		if (IntentUtil.isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE))
		{

			// Create file for the image
			File image = createImgFile();
			tempFilePath = image.getAbsolutePath();

			// Make the intent and put the path to write the image
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));

			// Start activity
			startActivityForResult(takePictureIntent, SCANNING_INTENT_CODE);
		}
		else
		{
			createAlert(getString(R.string.sorry_message), getString(R.string.no_camera_app));
		}
	}
	
	private File createImgFile()
	{
		String fileName = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.ENGLISH).format(new Date());

		try
		{
			File image = File.createTempFile(fileName, ".jpg", getScannerDir());
			tempFilePath = image.getAbsolutePath();
			return image;
		} catch (IOException e)
		{
			e.printStackTrace();
			createAlert(getString(R.string.sorry_message), getString(R.string.error_create_file));
		}
		return null;
	}
	
	private File getScannerDir()
	{
		File dir = new File(Environment.getExternalStorageDirectory() + SCANNER_DIR);
		if (!dir.exists())
			dir.mkdir();
		return dir;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode)
		{

		case SCANNING_INTENT_CODE:
			if (resultCode == RESULT_OK)
			{
				File file = new File(tempFilePath);
				
				Bitmap bm = ImageUtil.decodeFile(file, IMAGE_MAX_SIZE);
				ImageUtil.resizeImg(bm, file);
				
				sendTask = new SendStickersTask();
				sendTask.execute(tempFilePath);
			}
			else
			{
				new File(tempFilePath).delete();
			}
			break;
		}

	}
	
	private void createAlert(String title, String message)
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		builder.show();
	}	

	private class SendStickersTask extends AsyncTask<String, String, UserResult> {

		@Override
		protected void onPreExecute()
		{
			progressDialog = ProgressDialog.show(ScanActivity.this, getString(R.string.sending_status), getString(R.string.please_wait), true);
		}

		@Override
		protected UserResult doInBackground(String... args)
		{
			String filepath = args[0];
			return (new DataService()).addSticker(new File(filepath), USER_ID);
		}

		@Override
		protected void onPostExecute(UserResult result)
		{
			progressDialog.dismiss();
			progressDialog = null;
			
			String message = ResponseUtil.parseMessage(ScanActivity.this, result);
			createAlert("Sticker Album", message);
			new File(tempFilePath).delete();
		}

	}

}
