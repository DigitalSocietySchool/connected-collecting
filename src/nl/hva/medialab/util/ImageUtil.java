package nl.hva.medialab.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
	public static Bitmap decodeFile(File f, int maxSize){
	    Bitmap b = null;
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;

	        FileInputStream fis = new FileInputStream(f);
	        BitmapFactory.decodeStream(fis, null, o);
	        fis.close();
	        
	        int scale = 1;
	        if (o.outHeight > maxSize || o.outWidth > maxSize) {
	            scale = (int)Math.pow(2, (int) Math.round(Math.log(maxSize / 
	               (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
	        }

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        o2.inPurgeable = true;
	        o2.inInputShareable = true;
	        fis = new FileInputStream(f);
	        b = BitmapFactory.decodeStream(fis, null, o2);
	        fis.close();
	    } catch (IOException e) {
	    }
	    return b;
	}
	
	/**
	 * 
	 * @param bitmap
	 * @param filePath
	 * @return
	 */
	public static void resizeImg(Bitmap bitmap, File file)
	{
	    try{
	        OutputStream out = new FileOutputStream(file);
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
	        out.flush();
	        out.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
}
