/*******************************************************************************
 * 
 * Copyright (c) 2022-2026, eModernize, www.emodernize.eu
 * 
 *******************************************************************************/
package com.nib.asm;

public interface INAsmProgramWrapper {

	public static final String BATCH_RECORD_HANDLER = "batch.recordhandler";
	public static final String BATCH_VSAM_HANDLER = "batch.vsamhandler";
	public static final String BATCH_FILES_CATALOG = "batch.filescatalog";
	public static final String BATCH_JOB_LISTENER = "batch.joblistener";

	public String getParm(String parmName);

  public String getProgramsPackage();

}
