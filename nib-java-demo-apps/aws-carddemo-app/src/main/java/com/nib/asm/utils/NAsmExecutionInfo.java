package com.nib.asm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;
import com.nib.commons.AbstractProgram;

public class NAsmExecutionInfo {
  private static int stackLevel = -1;
  public static int traceCount = 0;
  public static String tracePrevPgm = "";
  public static final int TRACE_LIMIT = 3;
  private static Vector<HashMap<String, NAsmExecutionProgramInfo>> executionStack = null;

  public static void init() {
    stackLevel = -1;
    executionStack = null;
  }
  
  public static void addToStack(AbstractProgram program, AbstractProgram callerProgram) {
    NAsmExecutionInfo.stackLevel++;
    if (executionStack == null) {
      executionStack = new Vector<>();
    }
    HashMap<String, NAsmExecutionProgramInfo> execMap;
    if (executionStack.size() - 1 < NAsmExecutionInfo.stackLevel) {
      execMap = new HashMap<>();
      executionStack.add(executionStack.size(), execMap);
    }else {
      execMap = executionStack.get(NAsmExecutionInfo.stackLevel);
    }
    NAsmExecutionProgramInfo info = execMap.get(program.getProgramId());
    if (info == null) {
      info = new NAsmExecutionProgramInfo(program);
      execMap.put(program.getProgramId(), info);
    }else {
      info.startRun();
    }
  }

  public static void removeFromStack(AbstractProgram program) {
    HashMap<String, NAsmExecutionProgramInfo> execMap = executionStack.get(NAsmExecutionInfo.stackLevel);
    NAsmExecutionProgramInfo info = execMap.get(program.getProgramId());
    if (info != null) {
      info.endRun();
      NAsmExecutionInfo.stackLevel--;
    }
  }

  public static void execTime(NAsmField time, NAsmField date) throws NAsmException {
    SimpleDateFormat jdeSdf = new SimpleDateFormat("01yyDDD");
    int julNum = Integer.parseInt(jdeSdf.format(Calendar.getInstance().getTime()));
    byte[] julianPacked = NAsmStrings.packData(julNum, 7, 0, false);
    date.setByteArray(julianPacked);
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime());
    String timeString = timeStamp.substring(9, 17);
    byte[] timeBinary = new byte[4];
    for (int idx = 0; idx < timeString.length(); idx += 2) {
      timeBinary[idx / 2] = (byte) ((Character.digit(timeString.charAt(idx), 16) << 4) + Character.digit(timeString.charAt(idx + 1), 16));
    }
    time.setByteArray(timeBinary);
  }

  /**
   * @return the executionStack
   */
  public static Vector<HashMap<String, NAsmExecutionProgramInfo>> getExecutionStack() {
    return executionStack;
  }

  /**
   * 
   * @return
   */
  public static int getStackLevel() {
    return stackLevel;
  }
  
  public static int getTraceCount() {
    return traceCount;
  }

  public static String getTracePrevPgm() {
    return tracePrevPgm;
  }
  
}
