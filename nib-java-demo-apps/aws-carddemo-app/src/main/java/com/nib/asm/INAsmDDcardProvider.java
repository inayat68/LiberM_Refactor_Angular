/*******************************************************************************
 * 
 * Copyright (c) 2022-2026, eModernize, www.emodernize.eu
 * 
 *******************************************************************************/
package com.nib.asm;

import com.nib.asm.storage.NAsmDDcard;


public interface INAsmDDcardProvider {
	
	  public NAsmDDcard getDDcard(String ddName);

	  public NAsmDDcard getFileInfo(String dsnName);
		
//	  public IDdCardInfoProvider ddCardInfoProvider();

}
