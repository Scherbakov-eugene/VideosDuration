package lunasoft.applications.videos_duration.core;

import java.io.File;

@SuppressWarnings("rawtypes")
public class VideoInfo implements Comparable
{
	public File file = null;
	public String path = null;

	public String duration = null;

	public long hours = 0;
	public long minutes = 0;
	public long seconds = 0;
	public long milliseconds = 0;

	public long time = 0;

	public boolean selected = false;

	@Override
	public int compareTo(Object arg)
	{
		return path.compareTo(((VideoInfo) arg).path);
	}
}
