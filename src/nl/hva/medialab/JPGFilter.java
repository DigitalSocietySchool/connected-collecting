package nl.hva.medialab;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

public class JPGFilter implements FileFilter {
	
	private static final String JPG_EXT = ".jpg";

	@Override
	public boolean accept(File pathname)
	{
		return (pathname.getName().toLowerCase(Locale.US).endsWith(JPG_EXT));
	}

}
