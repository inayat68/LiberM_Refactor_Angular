package com.nib.asm.storage;

/**
 * Store a DD card info.
 */
public class NAsmDDcard {
  private String ddName;
  private String path;
  private NAsmFile.NAsmRecordFormat format;
  private int lrecl;
  private String dsnName;
  private Boolean append;
  private int keyStart;
  private int keyLength;
  private String table;
  private Boolean ascii;
  private Boolean dummy;
  private Boolean sysout;
  private Boolean temp;
  private boolean inCatalog = false; 
  private String dcb = null; 
  private Integer bulkInsertLimit = null;

  /**
   * 
   * @param ddName
   * @param path
   * @param format
   * @param lrecl
   * @param dsnName
   * @param append
   */
  public NAsmDDcard(String ddName, String path, NAsmFile.NAsmRecordFormat format, int lrecl, String dsnName, Boolean append, Boolean dummy, Boolean sysout, Boolean temp) {
    this.ddName = ddName;
    this.path = path;
    this.format = format;
    this.lrecl = lrecl;
    this.dsnName = dsnName;
    this.append = append;
    this.dummy = dummy;
    this.sysout = sysout;
    this.temp = temp;
  }

  /**
   * @return the DD name
   */
  public String getDdName() {
    return this.ddName;
  }

  /**
   * @return the file path
   */
  public String getPath() {
    return this.path;
  }

  /**
   * @return the record format
   */
  public NAsmFile.NAsmRecordFormat getFormat() {
    return this.format;
  }

  /**
   * @return the record length
   */
  public int getLrecl() {
    return this.lrecl;
  }

  /**
   * @return the append flag
   */
  public Boolean getAppend() {
    return this.append;
  }

  /**
   * @return the dsnName
   */
  public String getDsnName() {
    return dsnName;
  }

  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * @param format the format to set
   */
  public void setFormat(NAsmFile.NAsmRecordFormat format) {
    this.format = format;
  }

  /**
   * @param lrecl the lrecl to set
   */
  public void setLrecl(int lrecl) {
    this.lrecl = lrecl;
  }

  /**
   * @param dsnName the dsnName to set
   */
  public void setDsnName(String dsnName) {
    this.dsnName = dsnName;
  }

  /**
   * @param append the append to set
   */
  public void setAppend(Boolean append) {
    this.append = append;
  }

  /**
   * @return the keyStart
   */
  public int getKeyStart() {
    return keyStart;
  }

  /**
   * @param keyStart the keyStart to set
   */
  public void setKeyStart(int keyStart) {
    this.keyStart = keyStart;
  }

  /**
   * @return the keyLength
   */
  public int getKeyLength() {
    return keyLength;
  }

  /**
   * @param keyLength the keyLength to set
   */
  public void setKeyLength(int keyLength) {
    this.keyLength = keyLength;
  }

  /**
   * @return the table
   */
  public String getTable() {
    return table;
  }

  /**
   * @param table the table to set
   */
  public void setTable(String table) {
    this.table = table;
  }

  public NAsmDDcard cloneDD(String ddName) {
    NAsmDDcard clonedDD = new NAsmDDcard(ddName, this.path, this.format, this.lrecl, this.dsnName, this.append, this.dummy, this.sysout, this.temp);
    clonedDD.setKeyStart(keyStart);
    clonedDD.setKeyLength(keyLength);
    clonedDD.setTable(table);
    clonedDD.setAscii(ascii);
    clonedDD.setDummy(dummy);
    clonedDD.setSysout(sysout);
    clonedDD.setTemp(temp);
    return clonedDD;
  }

  /**
   * @return the ascii
   */
  public boolean isAscii() {
    return ascii != null && ascii;
  }

  /**
   * @param ascii the ascii to set
   */
  public void setAscii(Boolean ascii) {
    this.ascii = ascii;
  }

  /**
   * @return the dummy
   */
  public boolean isDummy() {
    return dummy;
  }

  /**
   * @param dummy the dummy to set
   */
  public void setDummy(Boolean dummy) {
    this.dummy = dummy;
  }

  /**
   * @return the sysout
   */
  public Boolean isSysout() {
    return sysout;
  }

  /**
   * @param sysout the sysout to set
   */
  public void setSysout(Boolean sysout) {
    this.sysout = sysout;
  }

  /**
   * @return the temp
   */
  public Boolean isTemp() {
    return temp;
  }

  /**
   * @param temp the temp to set
   */
  public void setTemp(Boolean temp) {
    this.temp = temp;
  }

  /**
   * @param ddName the ddName to set
   */
  public void setDdName(String ddName) {
    this.ddName = ddName;
  }

  /**
   * @return the inCatalog
   */
  public boolean isInCatalog() {
    return inCatalog;
  }

  /**
   * @param inCatalog the inCatalog to set
   */
  public void setInCatalog(boolean value) {
    this.inCatalog = value;
  }

  /**
   * @return the dcb
   */
  public String getDcb() {
    return dcb;
  }

  /**
   * @param dcb the dcb to set
   */
  public void setDcb(String dcb) {
    this.dcb = dcb;
  }

  /**
   * @return the bulkInsertLimit
   */
  public Integer getBulkInsertLimit() {
    return bulkInsertLimit;
  }

  /**
   * @param limit the bulkInsertLimit to set
   */
  public void setBulkInsertLimit(Integer limit) {
    this.bulkInsertLimit = limit;
  }

}
