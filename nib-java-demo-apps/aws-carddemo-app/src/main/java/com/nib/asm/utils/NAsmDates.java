package com.nib.asm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nib.asm.exceptions.NAsmException;

/**
 * Handle dates operations and formatting.
 * 
 * @author nib-labs.io
 */
public class NAsmDates {

	/**
	 * Replacement for PAPLDATE utility, the original Runtime date format
	 * masks have to be changed to match Java compliant date formats.
	 * 
	 * @param dateIn
	 *            the date to be converted
	 * @param formatIn
	 *            the input format, accepts any Java compliant date pattern for
	 *            example "CCYYDDD" can be used for Julian dates or "CCYYMMDD"
	 *            for Gregorian dates
	 * @param formatOut
	 *            the output format, accepts any Java compliant date pattern for
	 *            example "CCYYDDD" can be used for Julian dates or "CCYYMMDD"
	 *            for Gregorian dates
	 * @return the date formatted according to formatOut pattern
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public static String dateConv(String dateIn, String formatIn, String formatOut) throws NAsmException {
		Date date;
		String out = "";
		try {
			date = new SimpleDateFormat(formatIn).parse(dateIn);
			out = new SimpleDateFormat(formatOut).format(date);
		} catch (Exception ex) {
			NAsmException eex = new NAsmException(
					String.format("Error converting dates, dateIn %1$s formatIn %2$s formatOut %3$s Error==>%4$s", dateIn, formatIn,
							formatOut, ex.getMessage()));
			eex.setJavaStack(ex);
			throw eex;
		}
		return out;
	}

	/**
	 * Generates a timestamp string to be used into log files.
	 * 
	 * @return the timestamp for log files
	 * @throws Exception
	 *             if a problem occurs
	 */
	public static String getTimestampForLog() throws Exception {
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance(); // today
		return sdf.format(c1.getTime());
	}

}
