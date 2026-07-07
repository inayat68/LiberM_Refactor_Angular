package com.nib.asm.storage;

public class NAsmCompareInfo implements INAsmClonable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private int value;
  private boolean result;
  
  /**
   * 
   * @param result
   * @param value
   */
  public NAsmCompareInfo(double value1, double value2, boolean result) {
    this.value = (int)(value1 - value2);
    this.result = result;
  }
  
  /**
   * 
   * @param result
   * @param value
   */
  public NAsmCompareInfo(int value1, int value2, boolean result) {
    this.value = value1 - value2;
    this.result = result;
  }
  
  /**
   * 
   * @param result
   * @param value
   */
  public NAsmCompareInfo(int value, boolean result) {
    this.value = value;
    this.result = result;
  }

  /**
   * @return the value
   */
  public int getValue() {
    return value;
  }

  /**
   * @return the result
   */
  public boolean getResult() {
    return result;
  }

}
