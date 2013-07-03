package nl.hva.medialab.util;

import nl.hva.medialab.R;
import android.content.Context;

import com.medialab.beans.MediaLABResponse;
import com.medialab.beans.UserResult;

public class ResponseUtil {
	public static String parseMessage(Context context, UserResult result)
	{
		MediaLABResponse response = result.getResponse();
		if (MediaLABResponse.ALREADY_HAVE_STICKER.equals(response) || MediaLABResponse.STICKER_ADDED.equals(response))
		{
			return String.format(context.getString(R.string.sticker_added), result.getStickerName());
		}
		if (MediaLABResponse.NO_MATCHES_FOUND.equals(response))
		{
			return context.getString(R.string.no_matches);
		}
		return context.getString(R.string.error);
	}
}
