package com.nib.asm.utils;

import com.nib.commons.AbstractProgram;

public class NAsmExecutionProgramInfo {

  private AbstractProgram program;
  private long starTime = 0;
  private int runNumber = 0;
  private long totalTime = 0;

  /**
   * 
   * @param program
   * @param starTime
   */
  public NAsmExecutionProgramInfo(AbstractProgram program) {
    this.program = program;
    this.startRun();
  }

  public void startRun() {
    this.starTime = System.currentTimeMillis();
    this.runNumber++;
  }
  
  public long endRun() {
    //double seconds = result / 1000.0;
    long executionTime = System.currentTimeMillis() - this.starTime;
    this.totalTime += executionTime;
    return executionTime;
  }

  /**
   * @return the program
   */
  public AbstractProgram getProgram() {
    return program;
  }

  /**
   * @return the starTime
   */
  public long getStarTime() {
    return starTime;
  }

  /**
   * @return the runNumber
   */
  public int getRunNumber() {
    return runNumber;
  }

  /**
   * @return the totalTime
   */
  public long getTotalTime() {
    return totalTime;
  }

}
