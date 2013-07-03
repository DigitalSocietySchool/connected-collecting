package nl.hva.medialab;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class PressedListener implements OnTouchListener {
	
	private ImageButton button;
	
	public PressedListener(ImageButton button)
	{
		this.button = button;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent me)
	{
		if (me.getAction() == MotionEvent.ACTION_DOWN)
		{
			button.setBackgroundColor(Color.rgb(51, 181, 229));
		}
		else if (me.getAction() == MotionEvent.ACTION_UP)
		{
			button.setBackgroundColor(Color.TRANSPARENT);
		}
		return false;

	}

}
