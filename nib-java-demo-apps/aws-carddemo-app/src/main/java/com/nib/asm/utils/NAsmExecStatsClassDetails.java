package com.nib.asm.utils;

import java.util.Map;
import java.util.TreeMap;

public class NAsmExecStatsClassDetails {
  private String className;
  private Map<String, NAsmExecStatsMethodDetails> execStatsMethodDetails = new TreeMap<>();

  /**
   * 
   * @param className
   */
  public NAsmExecStatsClassDetails(String className) {
    this.className = className;
  }

  /**
   * @return the className
   */
  public String getClassName() {
    return className;
  }

  /**
   * 
   * @param methodName
   */
  public void start(String methodName) {
    NAsmExecStatsMethodDetails esd;
    if (execStatsMethodDetails.containsKey(methodName)) {
      esd = execStatsMethodDetails.get(methodName);
    }else {
      esd = new NAsmExecStatsMethodDetails(methodName);
      execStatsMethodDetails.put(methodName, esd);
    }
    esd.start();
  }

  /**
   * 
   * @param methodName
   */
  public void end(String methodName) {
    NAsmExecStatsMethodDetails esd = execStatsMethodDetails.get(methodName);
    if (esd == null) {
      return;
    }
    esd.end();
  }

  /**
   * @return the execStatsMethodDetails
   */
  public Map<String, NAsmExecStatsMethodDetails> getExecStatsMethodDetails() {
    return execStatsMethodDetails;
  }

}
