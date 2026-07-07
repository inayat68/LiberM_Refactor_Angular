package com.nib.asm.storage;

import java.nio.charset.Charset;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmStrings;
import com.nib.asm.storage.NAsmField.Condition;
import com.nib.asm.storage.NAsmField.NAsmFieldType;

public final class NAsmString {
  private final String value;

  public NAsmString(String value) {
      this.value = value;
  }

  public NAsmString(byte[] byteBuffer, Charset charset) {
    this.value = new String(byteBuffer, charset);
  }

  @Override
  public String toString() {
    return value;
  }

  public int compareTo(String string) throws NAsmException {
    NAsmField fld1 = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.ALPHANUMERIC, 0, string.length());
    byte[] constantValue;
    if (NAsmEnvironment.RUN_EBCDIC()) {
      constantValue = NAsmStrings.convertBytesToEBCDIC(string.getBytes(NAsmEnvironment.ENV_CHARSET()));
    } else {
      constantValue = string.getBytes(NAsmEnvironment.ENV_CHARSET());
    }
    fld1.setValue(constantValue, 0);
    NAsmField fld2 = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.ALPHANUMERIC, 0, this.value.length());
    if (NAsmEnvironment.RUN_EBCDIC()) {
      constantValue = NAsmStrings.convertBytesToEBCDIC(this.value.getBytes(NAsmEnvironment.ENV_CHARSET()));
    } else {
      constantValue = this.value.getBytes(NAsmEnvironment.ENV_CHARSET());
    }
    fld2.setValue(constantValue, 0);
    NAsmCompareInfo compareFields = fld2.compareFields(fld2, fld1, Condition.EQUAL, null);
    return compareFields.getValue();
  }

  public int length() {
    return this.value.length();
  }

  public String substring(int beginIndex, int endIndex) {
    return this.value.substring(beginIndex, endIndex);
  }

  public String trim() {
    return this.value.trim();
  }

  public boolean contains(String string) {
    return this.value.contains(string);
  }

  public char charAt(int index) {
    return this.value.charAt(index);
  }        

  public boolean equals(String string) {
    //Limit compare to the left length
    //i.e.: CLC   0(22,R6),=C'E15 GROUPING FACTOR=AUTOSELECT'
    if (this.value.length() < string.length()) {
      return string.startsWith(this.value);
    }else {
      return this.value.equals(string);
    }
  }        

  public boolean equals(NAsmField field) throws NAsmException {
    String s = field.getString().toString();
    if (s.length() > this.value.length())  {
      s = s.substring(0, this.value.length());
    }else if (s.length() < this.value.length())  {
      s = NAsmStrings.formatSpaces(s, this.value.length());
    }
    return this.value.equals(field.getString().toString());
  }        
}
