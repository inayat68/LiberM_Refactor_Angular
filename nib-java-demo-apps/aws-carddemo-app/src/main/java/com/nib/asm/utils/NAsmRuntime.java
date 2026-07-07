package com.nib.asm.utils;

import java.util.HashMap;

/**
 * Handle NAsmRuntime runtime versions info.
 * 
 * @author nib-labs.io
 */
public class NAsmRuntime {

	private static HashMap<String, String> versions = null;

	/**
	 * @return product name
	 */
	public static String getPluginName() {
		return "Batch-Runtime";
	}

	/**
	 * @return product version
	 */
	public static String getPluginVersion() {
		return "2.6.04";
	}

	/**
	 * @return product versions history log
	 */
	public static HashMap<String, String> getPluginVersions() {
		if (versions == null) {
			versions = new HashMap<>();
			versions.put("2.0.0", "Base version of Runtime JAVA runtime");
			versions.put("2.1.0", "Fixed open of VB files");
			versions.put("2.2.0", "Improved sort performances");
			versions.put("2.3.0", "Converted project to Maven");
			versions.put("2.3.1", "Removed license end date message.\nFixed LS_UNIX-LS_DOS handling.");
			versions.put("2.3.2", "Added custom charset usage.");
			versions.put("2.4.1", "Implemented Packed fields handling.");
			versions.put("2.4.2", "Enhanced PACKED DECIMAL data handling.");
			versions.put("2.4.3", "Implemented BINARY data handling.\nFixed record variable length I/O.");
			versions.put("2.4.6", "Fixed variable lenght files write length issue.");
			versions.put("2.6.00", "Integration with CTU.");
		}
		return versions;
	}

}
