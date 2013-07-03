package nl.hva.medialab.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionService {
 
	private ConnectionService()
	{
	}

	/**
	 * Check if there's internet connection
	 * */
	public static boolean isOnline(ConnectivityManager cm)
	{
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return (netInfo != null && netInfo.isConnectedOrConnecting());
	}
}
