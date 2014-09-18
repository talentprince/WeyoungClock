package org.weyoung.clock.utils;

import android.util.Log;

/**
 * LogUtils
 * 
 * @author chenchen
 */
public class LogUtils {
	private static final boolean DEBUG = true;

	public static void i(String tag, String msg) {
		if (DEBUG)
			Log.i(tag, msg);
	}
}
