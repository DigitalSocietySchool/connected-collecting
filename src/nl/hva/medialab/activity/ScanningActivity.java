package nl.hva.medialab.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nl.hva.medialab.JPGFilter;
import nl.hva.medialab.PressedListener;
import nl.hva.medialab.R;
import nl.hva.medialab.StickerRowAdapter;
import nl.hva.medialab.data.DataService;
import nl.hva.medialab.model.StickerRow;
import nl.hva.medialab.util.ImageUtil;
import nl.hva.medialab.util.IntentUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.UserResult;

public class ScanningActivity extends Activity {

	private final static int SCANNING_INTENT_CODE = 1;

	private final static int IMAGE_MAX_SIZE = 640;

	private final static String SCANNER_DIR = "/StickerScanner";

	private ProgressDialog progressDialog;

	private LoadPhotosTask loadPhoto;

	private SendStickersTask sendTask;

	private String tempFilePath = "";

	private ListView mListView;

	private StickerRowAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanning);
		mListView = (ListView) findViewById(R.id.stickersListView);

		loadPhoto = new LoadPhotosTask();
		loadPhoto.execute();

		registerButtonListeners();
	}

	private void registerButtonListeners()
	{
		ImageButton trash = (ImageButton) findViewById(R.id.deleteItemButton), send = (ImageButton) findViewById(R.id.sendPictureButton), camera = (ImageButton) findViewById(R.id.takePictureButton);

		trash.setOnTouchListener(new PressedListener(trash));
		send.setOnTouchListener(new PressedListener(send));
		camera.setOnTouchListener(new PressedListener(camera));
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
		if (loadPhoto != null)
		{
			loadPhoto.cancel(true);
			loadPhoto = null;
		}
		if (sendTask != null)
		{
			sendTask.cancel(true);
			sendTask = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.scanning, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_check_stickers:
			showStickers();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 
	 */
	private void showStickers()
	{
		final Intent registerActivityIntent = new Intent(this, StickersActivity.class);
		startActivity(registerActivityIntent);
	}

	public void sendStickers(View view)
	{
		List<StickerRow> rows = adapter.getList();
		for (int i = 0; i < rows.size(); i++)
		{
			StickerRow row = rows.get(i);
			if (row.isSelected() && !row.isSending())
			{
				sendTask = new SendStickersTask(i);
				sendTask.execute(row.getFilePath());
			}
		}
	}

	public void deleteItem(View view)
	{
		List<StickerRow> rows = adapter.getList();
		int deletedCount = 0;
		for (int i = 0; i < rows.size(); i++)
		{
			StickerRow row = rows.get(i);
			if (row.isSelected() && !row.isSending())
			{
				new File(row.getFilePath()).delete();
				adapter.getList().remove(i);
				++deletedCount;
				--i;
			}
		}
		if (deletedCount > 0)
		{
			adapter.notifyDataSetChanged();
			Toast.makeText(getApplicationContext(), String.format("%d file(s) deleted.", deletedCount), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * @param view
	 */
	public void startScanning(View view)
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

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @param title
	 * @param message
	 */
	private void createAlert(String title, String message)
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.sorry_message));
		builder.setMessage(getString(R.string.error_create_file));
		builder.show();
	}

	/**
	 * 
	 * @return
	 */
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
			File file = new File(tempFilePath);
			if (resultCode == RESULT_OK)
			{
				StickerRow row = addStickerRow(file, true);
				adapter.add(row);
				adapter.notifyDataSetChanged();
				sendTask = new SendStickersTask(row);
				sendTask.execute(row.getFilePath());
			}
			else
			{
				file.delete();
			}
			break;
		}

	}

	private StickerRow addStickerRow(File file, boolean resize)
	{
		Bitmap bm = ImageUtil.decodeFile(file, IMAGE_MAX_SIZE);
		if (resize)
			ImageUtil.resizeImg(bm, file);
		return new StickerRow(file.getAbsolutePath(), bm, getString(R.string.pending_status));
	}

	private class LoadPhotosTask extends AsyncTask<String, String, List<StickerRow>> {

		@Override
		protected void onPreExecute()
		{
			progressDialog = ProgressDialog.show(ScanningActivity.this, getString(R.string.loading_images), getString(R.string.please_wait), true);
		}

		@Override
		protected List<StickerRow> doInBackground(String... arg0)
		{
			return getLastPhotos();
		}

		@Override
		protected void onPostExecute(List<StickerRow> rows)
		{
			adapter = new StickerRowAdapter(ScanningActivity.this, rows);
			mListView.setAdapter(adapter);

			progressDialog.dismiss();
			progressDialog = null;
		}

		private List<StickerRow> getLastPhotos()
		{
			List<StickerRow> list = new ArrayList<StickerRow>();
			File dir = getScannerDir();
			File[] files = dir.listFiles(new JPGFilter());
			for (File file : files)
			{
				list.add(addStickerRow(file, false));
			}
			return list;
		}

	}

	private class SendStickersTask extends AsyncTask<String, String, UserResult> {

		private final StickerRow row;

		public SendStickersTask(int position)
		{
			super();
			this.row = adapter.getItem(position);
		}
		
		public SendStickersTask(StickerRow row)
		{
			super();
			this.row = row;
		}

		@Override
		protected void onPreExecute()
		{
			row.setStatus(getString(R.string.sending_status));
			row.setSending(true);
			adapter.notifyDataSetChanged();
		}

		@Override
		protected UserResult doInBackground(String... args)
		{
			String filepath = args[0];
			return (new DataService()).addSticker(new File(filepath), 1L);
		}

		@Override
		protected void onPostExecute(UserResult result)
		{
			MediaLABResponse response = (result == null ? MediaLABResponse.UNKOWN_ERROR : result.getResponse());
			row.setStatus(response.toString());
			row.setSending(false);
			adapter.notifyDataSetChanged();
		}

	}

}
