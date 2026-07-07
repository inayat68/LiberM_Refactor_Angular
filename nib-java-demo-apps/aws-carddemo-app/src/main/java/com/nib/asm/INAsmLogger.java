package com.nib.asm;

import java.io.PrintStream;

import org.slf4j.Logger;

public interface INAsmLogger extends Logger {

  public void info(String message);

  public void warn(String message);
 
  public void error(String message);
  
  public void debug(String message);
  
  public void error(Exception e);
  
  public void error(String message, Exception e);  
 
  public void close();
  
  public PrintStream getPrintStream();

}
