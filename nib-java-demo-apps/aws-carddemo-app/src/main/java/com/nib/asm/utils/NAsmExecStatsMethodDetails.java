package com.nib.asm.utils;

public class NAsmExecStatsMethodDetails {
  private String methodName;
  private int execTimes = 0;
  private long executionTime = 0;
  private long startTime = 0;
  
  /**
   * 
   * @param methodName
   */
  public NAsmExecStatsMethodDetails(String methodName) {
    this.methodName = methodName;
  }

  /**
   * @return the methodName
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * 
   */
  public void start() {
    //this.startTime = System.currentTimeMillis();
    this.startTime = System.nanoTime();
    this.execTimes++;
  }

  /**
   * 
   */
  public void end() {
    //long execTime = System.currentTimeMillis() - this.startTime;
    long execTime = System.nanoTime() - this.startTime;
    this.executionTime += execTime;
  }

  /**
   * @return the execTimes
   */
  public int getExecTimes() {
    return execTimes;
  }

  /**
   * @return the executionTime
   */
  public long getExecutionTime() {
    return executionTime;
  }
  
}
