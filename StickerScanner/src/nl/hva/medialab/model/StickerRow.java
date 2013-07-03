package nl.hva.medialab.model;

import android.graphics.Bitmap;

public class StickerRow {
	private String filePath;

	private boolean selected;

	private Bitmap bitmap;

	private String status;

	private boolean sending;

	public StickerRow(String filePath, Bitmap bitmap, String status)
	{
		this.filePath = filePath;
		this.bitmap = bitmap;
		this.status = status;
	}

	public boolean isSending()
	{
		return sending;
	}

	public void setSending(boolean sending)
	{
		this.sending = sending;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}

}
