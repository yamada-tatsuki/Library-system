package practice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodaysDate {
	public static void main(String args[]) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strDate = sdf.format(cal.getTime());

		System.out.println(strDate);
	}
}
