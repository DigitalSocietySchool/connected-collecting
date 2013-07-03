package nl.hva.medialab;

import java.util.ArrayList;
import java.util.List;

import nl.hva.medialab.model.StickerRow;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StickerRowAdapter extends BaseAdapter {

	private Activity activity;

	private List<StickerRow> list;

	public StickerRowAdapter(Activity activity, List<StickerRow> list)
	{
		this.activity = activity;
		this.list = list;
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public StickerRow getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public void add(StickerRow row)
	{
		list.add(row);
	}

	public void remove(StickerRow row)
	{
		list.remove(row);
	}

	public List<StickerRow> getList()
	{
		if (list == null)
		{
			list = new ArrayList<StickerRow>();
		}
		return list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		StickerRow row = list.get(position);
		View view = null;
		if (convertView == null)
		{
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.sticker_row, null);

			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.checkBox = (CheckBox) view.findViewById(R.id.stickerImgCheckBox);
			viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					StickerRow element = (StickerRow) viewHolder.checkBox.getTag();
					element.setSelected(buttonView.isChecked());
				}
			});

			viewHolder.imageView = (ImageView) view.findViewById(R.id.stickerImageView);
			viewHolder.textView = (TextView) view.findViewById(R.id.statusTextView);
			viewHolder.progressBar = (ProgressBar) view.findViewById(R.id.imageProgressBar);

			view.setTag(viewHolder);
			viewHolder.checkBox.setTag(row);
		}
		else
		{
			view = convertView;
			((ViewHolder) view.getTag()).checkBox.setTag(row);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.checkBox.setText(row.getFilePath().substring(row.getFilePath().lastIndexOf('/') + 1));
		holder.checkBox.setChecked(row.isSelected());
		holder.imageView.setImageBitmap(row.getBitmap());
		setAnimation(holder, row);
		holder.textView.setText(row.getStatus());

		return view;
	}
	
	private void setAnimation(ViewHolder holder, StickerRow row)
	{
		if (row.isSending())
		{
			holder.imageView.setAlpha(25);
			holder.progressBar.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.imageView.setAlpha(255);
			holder.progressBar.setVisibility(View.GONE);
		}
	}

	static class ViewHolder {
		protected ImageView imageView;
		protected CheckBox checkBox;
		protected TextView textView;
		protected ProgressBar progressBar;
	}

}
