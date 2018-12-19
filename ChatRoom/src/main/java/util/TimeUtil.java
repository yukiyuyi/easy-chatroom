package util;
import java.util.Date;
import java.text.SimpleDateFormat;
public class TimeUtil {
	public static String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

}
