package com.aws.carddemo.common.copybook.asm.cosgn00m;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

/**
 * 
 * <b>Divergeek - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author divergeek
 */
public class Cosgn0as extends NAsmField {

  // . TIOA PREFIX
  private NAsmField dummy3 = new NAsmField(this, "DUMMY3", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY3, Len.DUMMY3, Array.DUMMY3);
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField trnnamel = new NAsmField(this, "TRNNAMEL", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEL, Len.TRNNAMEL);
  // . DATA FIELD FLAG
  private Trnnamef trnnamef = new Trnnamef(this, "TRNNAMEF", Pos.TRNNAMEF);   
  // . INPUT DATA FIELD
  private Trnnamei trnnamei = new Trnnamei(this, "TRNNAMEI", Pos.TRNNAMEI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField title01l = new NAsmField(this, "TITLE01L", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01L, Len.TITLE01L);
  // . DATA FIELD FLAG
  private Title01f title01f = new Title01f(this, "TITLE01F", Pos.TITLE01F);   
  // . INPUT DATA FIELD
  private Title01i title01i = new Title01i(this, "TITLE01I", Pos.TITLE01I);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField curdatel = new NAsmField(this, "CURDATEL", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEL, Len.CURDATEL);
  // . DATA FIELD FLAG
  private Curdatef curdatef = new Curdatef(this, "CURDATEF", Pos.CURDATEF);   
  // . INPUT DATA FIELD
  private Curdatei curdatei = new Curdatei(this, "CURDATEI", Pos.CURDATEI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField pgmnamel = new NAsmField(this, "PGMNAMEL", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEL, Len.PGMNAMEL);
  // . DATA FIELD FLAG
  private Pgmnamef pgmnamef = new Pgmnamef(this, "PGMNAMEF", Pos.PGMNAMEF);   
  // . INPUT DATA FIELD
  private Pgmnamei pgmnamei = new Pgmnamei(this, "PGMNAMEI", Pos.PGMNAMEI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField title02l = new NAsmField(this, "TITLE02L", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02L, Len.TITLE02L);
  // . DATA FIELD FLAG
  private Title02f title02f = new Title02f(this, "TITLE02F", Pos.TITLE02F);   
  // . INPUT DATA FIELD
  private Title02i title02i = new Title02i(this, "TITLE02I", Pos.TITLE02I);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField curtimel = new NAsmField(this, "CURTIMEL", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEL, Len.CURTIMEL);
  // . DATA FIELD FLAG
  private Curtimef curtimef = new Curtimef(this, "CURTIMEF", Pos.CURTIMEF);   
  // . INPUT DATA FIELD
  private Curtimei curtimei = new Curtimei(this, "CURTIMEI", Pos.CURTIMEI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField applidl = new NAsmField(this, "APPLIDL", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDL, Len.APPLIDL);
  // . DATA FIELD FLAG
  private Applidf applidf = new Applidf(this, "APPLIDF", Pos.APPLIDF);   
  // . INPUT DATA FIELD
  private Applidi applidi = new Applidi(this, "APPLIDI", Pos.APPLIDI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField sysidl = new NAsmField(this, "SYSIDL", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDL, Len.SYSIDL);
  // . DATA FIELD FLAG
  private Sysidf sysidf = new Sysidf(this, "SYSIDF", Pos.SYSIDF);   
  // . INPUT DATA FIELD
  private Sysidi sysidi = new Sysidi(this, "SYSIDI", Pos.SYSIDI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField useridl = new NAsmField(this, "USERIDL", NAsmFieldType.ALPHANUMERIC, Pos.USERIDL, Len.USERIDL);
  // . DATA FIELD FLAG
  private Useridf useridf = new Useridf(this, "USERIDF", Pos.USERIDF);   
  // . INPUT DATA FIELD
  private Useridi useridi = new Useridi(this, "USERIDI", Pos.USERIDI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField passwdl = new NAsmField(this, "PASSWDL", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDL, Len.PASSWDL);
  // . DATA FIELD FLAG
  private Passwdf passwdf = new Passwdf(this, "PASSWDF", Pos.PASSWDF);   
  // . INPUT DATA FIELD
  private Passwdi passwdi = new Passwdi(this, "PASSWDI", Pos.PASSWDI);   
  //
  // . INPUT DATA FIELD LENGTH
  private NAsmField errmsgl = new NAsmField(this, "ERRMSGL", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGL, Len.ERRMSGL);
  // . DATA FIELD FLAG
  private Errmsgf errmsgf = new Errmsgf(this, "ERRMSGF", Pos.ERRMSGF);   
  // . INPUT DATA FIELD
  private Errmsgi errmsgi = new Errmsgi(this, "ERRMSGI", Pos.ERRMSGI);   

  /**
   * @return the trnnamel
   */
  public NAsmField getTrnnamel() {
    return trnnamel;
  }

  /**
   * @return the trnnamef
   */
  public Trnnamef getTrnnamef() {
    return trnnamef;
  }

  /**
   * @return the trnnamei
   */
  public Trnnamei getTrnnamei() {
    return trnnamei;
  }

  /**
   * @return the title01l
   */
  public NAsmField getTitle01l() {
    return title01l;
  }

  /**
   * @return the title01f
   */
  public Title01f getTitle01f() {
    return title01f;
  }

  /**
   * @return the title01i
   */
  public Title01i getTitle01i() {
    return title01i;
  }

  /**
   * @return the curdatel
   */
  public NAsmField getCurdatel() {
    return curdatel;
  }

  /**
   * @return the curdatef
   */
  public Curdatef getCurdatef() {
    return curdatef;
  }

  /**
   * @return the curdatei
   */
  public Curdatei getCurdatei() {
    return curdatei;
  }

  /**
   * @return the pgmnamel
   */
  public NAsmField getPgmnamel() {
    return pgmnamel;
  }

  /**
   * @return the pgmnamef
   */
  public Pgmnamef getPgmnamef() {
    return pgmnamef;
  }

  /**
   * @return the pgmnamei
   */
  public Pgmnamei getPgmnamei() {
    return pgmnamei;
  }

  /**
   * @return the title02l
   */
  public NAsmField getTitle02l() {
    return title02l;
  }

  /**
   * @return the title02f
   */
  public Title02f getTitle02f() {
    return title02f;
  }

  /**
   * @return the title02i
   */
  public Title02i getTitle02i() {
    return title02i;
  }

  /**
   * @return the curtimel
   */
  public NAsmField getCurtimel() {
    return curtimel;
  }

  /**
   * @return the curtimef
   */
  public Curtimef getCurtimef() {
    return curtimef;
  }

  /**
   * @return the curtimei
   */
  public Curtimei getCurtimei() {
    return curtimei;
  }

  /**
   * @return the applidl
   */
  public NAsmField getApplidl() {
    return applidl;
  }

  /**
   * @return the applidf
   */
  public Applidf getApplidf() {
    return applidf;
  }

  /**
   * @return the applidi
   */
  public Applidi getApplidi() {
    return applidi;
  }

  /**
   * @return the sysidl
   */
  public NAsmField getSysidl() {
    return sysidl;
  }

  /**
   * @return the sysidf
   */
  public Sysidf getSysidf() {
    return sysidf;
  }

  /**
   * @return the sysidi
   */
  public Sysidi getSysidi() {
    return sysidi;
  }

  /**
   * @return the useridl
   */
  public NAsmField getUseridl() {
    return useridl;
  }

  /**
   * @return the useridf
   */
  public Useridf getUseridf() {
    return useridf;
  }

  /**
   * @return the useridi
   */
  public Useridi getUseridi() {
    return useridi;
  }

  /**
   * @return the passwdl
   */
  public NAsmField getPasswdl() {
    return passwdl;
  }

  /**
   * @return the passwdf
   */
  public Passwdf getPasswdf() {
    return passwdf;
  }

  /**
   * @return the passwdi
   */
  public Passwdi getPasswdi() {
    return passwdi;
  }

  /**
   * @return the errmsgl
   */
  public NAsmField getErrmsgl() {
    return errmsgl;
  }

  /**
   * @return the errmsgf
   */
  public Errmsgf getErrmsgf() {
    return errmsgf;
  }

  /**
   * @return the errmsgi
   */
  public Errmsgi getErrmsgi() {
    return errmsgi;
  }

  /**
   *
   * @throws NAsmException
   */
  public Cosgn0as(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.COSGN0AS);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY3 = 1;
    public static final int TRNNAMEL = 2;
    public static final int TITLE01L = 2;
    public static final int CURDATEL = 2;
    public static final int PGMNAMEL = 2;
    public static final int TITLE02L = 2;
    public static final int CURTIMEL = 2;
    public static final int APPLIDL = 2;
    public static final int SYSIDL = 2;
    public static final int USERIDL = 2;
    public static final int PASSWDL = 2;
    public static final int ERRMSGL = 2;

    public static final int COSGN0AS = (Len.DUMMY3 * Array.DUMMY3)
                                + Len.TRNNAMEL
                                + Trnnamef.Len.TRNNAMEF
                                + Trnnamei.Len.TRNNAMEI
                                + Len.TITLE01L
                                + Title01f.Len.TITLE01F
                                + Title01i.Len.TITLE01I
                                + Len.CURDATEL
                                + Curdatef.Len.CURDATEF
                                + Curdatei.Len.CURDATEI
                                + Len.PGMNAMEL
                                + Pgmnamef.Len.PGMNAMEF
                                + Pgmnamei.Len.PGMNAMEI
                                + Len.TITLE02L
                                + Title02f.Len.TITLE02F
                                + Title02i.Len.TITLE02I
                                + Len.CURTIMEL
                                + Curtimef.Len.CURTIMEF
                                + Curtimei.Len.CURTIMEI
                                + Len.APPLIDL
                                + Applidf.Len.APPLIDF
                                + Applidi.Len.APPLIDI
                                + Len.SYSIDL
                                + Sysidf.Len.SYSIDF
                                + Sysidi.Len.SYSIDI
                                + Len.USERIDL
                                + Useridf.Len.USERIDF
                                + Useridi.Len.USERIDI
                                + Len.PASSWDL
                                + Passwdf.Len.PASSWDF
                                + Passwdi.Len.PASSWDI
                                + Len.ERRMSGL
                                + Errmsgf.Len.ERRMSGF
                                + Errmsgi.Len.ERRMSGI;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY3 = 0;
    public static final int TRNNAMEL = Pos.DUMMY3 + (Len.DUMMY3 * Array.DUMMY3);
    public static final int TRNNAMEF = Pos.TRNNAMEL + Len.TRNNAMEL;
    public static final int TRNNAMEI = Pos.TRNNAMEF + Trnnamef.Len.TRNNAMEF;
    public static final int TITLE01L = Pos.TRNNAMEI + Trnnamei.Len.TRNNAMEI;
    public static final int TITLE01F = Pos.TITLE01L + Len.TITLE01L;
    public static final int TITLE01I = Pos.TITLE01F + Title01f.Len.TITLE01F;
    public static final int CURDATEL = Pos.TITLE01I + Title01i.Len.TITLE01I;
    public static final int CURDATEF = Pos.CURDATEL + Len.CURDATEL;
    public static final int CURDATEI = Pos.CURDATEF + Curdatef.Len.CURDATEF;
    public static final int PGMNAMEL = Pos.CURDATEI + Curdatei.Len.CURDATEI;
    public static final int PGMNAMEF = Pos.PGMNAMEL + Len.PGMNAMEL;
    public static final int PGMNAMEI = Pos.PGMNAMEF + Pgmnamef.Len.PGMNAMEF;
    public static final int TITLE02L = Pos.PGMNAMEI + Pgmnamei.Len.PGMNAMEI;
    public static final int TITLE02F = Pos.TITLE02L + Len.TITLE02L;
    public static final int TITLE02I = Pos.TITLE02F + Title02f.Len.TITLE02F;
    public static final int CURTIMEL = Pos.TITLE02I + Title02i.Len.TITLE02I;
    public static final int CURTIMEF = Pos.CURTIMEL + Len.CURTIMEL;
    public static final int CURTIMEI = Pos.CURTIMEF + Curtimef.Len.CURTIMEF;
    public static final int APPLIDL = Pos.CURTIMEI + Curtimei.Len.CURTIMEI;
    public static final int APPLIDF = Pos.APPLIDL + Len.APPLIDL;
    public static final int APPLIDI = Pos.APPLIDF + Applidf.Len.APPLIDF;
    public static final int SYSIDL = Pos.APPLIDI + Applidi.Len.APPLIDI;
    public static final int SYSIDF = Pos.SYSIDL + Len.SYSIDL;
    public static final int SYSIDI = Pos.SYSIDF + Sysidf.Len.SYSIDF;
    public static final int USERIDL = Pos.SYSIDI + Sysidi.Len.SYSIDI;
    public static final int USERIDF = Pos.USERIDL + Len.USERIDL;
    public static final int USERIDI = Pos.USERIDF + Useridf.Len.USERIDF;
    public static final int PASSWDL = Pos.USERIDI + Useridi.Len.USERIDI;
    public static final int PASSWDF = Pos.PASSWDL + Len.PASSWDL;
    public static final int PASSWDI = Pos.PASSWDF + Passwdf.Len.PASSWDF;
    public static final int ERRMSGL = Pos.PASSWDI + Passwdi.Len.PASSWDI;
    public static final int ERRMSGF = Pos.ERRMSGL + Len.ERRMSGL;
    public static final int ERRMSGI = Pos.ERRMSGF + Errmsgf.Len.ERRMSGF;

  }

  //
  //Fields ARRAY
  //
  public static class Array {

    public static final int DUMMY3 = 12;

  }

}
