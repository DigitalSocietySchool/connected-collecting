package com.medialab.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileUtil {
	
	public static java.io.File inputStreamToFile(InputStream is)
	{
		try {
			java.io.File f = java.io.File.createTempFile(UUID.randomUUID().toString() , ".tmp");
			OutputStream out = new FileOutputStream(f);
			byte buf[] = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			is.close();
			return f;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
