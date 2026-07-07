package com.nib.asm.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmString;
import jakarta.xml.bind.DatatypeConverter;

/**
 * Handle strings operations.
 * 
 * @author nib-labs.io
 */
public class NAsmStrings {
	
	public static boolean isUnsignedPacked = false; //SQ PACK Vs ZAP

  /**
   * Check if a string is numeric.
   *
   * @param inputString
   *            string to be checked
   *
   * @return <b>true</b> if the string is numeric <br>
   *         <b>false</b> if the string is not numeric <br>
   *
   */
  public static boolean isNumeric(String inputString) {
    if (inputString == null) {
			return false;
		}
    String validChars = "0123456789";
    boolean isNumeric = true;
    boolean atLeastADigit = false;
    for (int idxI = 0; idxI < inputString.length() && isNumeric; idxI++) {
      char c = inputString.charAt(idxI);
      if (validChars.indexOf(c) == -1) {
        if (inputString.charAt(idxI) == '.' || inputString.charAt(idxI) == ',') {
        } else if (idxI == 0 && (inputString.charAt(idxI) == '+' || inputString.charAt(idxI) == '-')) {

        } else {
          return false;
        }
      } else {
        atLeastADigit = true;
      }
    }
    return atLeastADigit;
  }

  /**
   * Formats a line adding a fixed number of leading zeroes, if the line is
   * longer than positions it is trunked at positions length.
   *
   * @param line
   *            line to be formatted
   * @param positions
   *            number of positions
   * @return the formatted line
   */
  public static String formatZeroes(NAsmString line, int positions) {
    return formatZeroes(line.toString(), positions);
  }

  /**
   * Formats a line adding a fixed number of leading zeroes, if the line is
   * longer than positions it is trunked at positions length.
   *
   * @param line
   *            line to be formatted
   * @param positions
   *            number of positions
   * @return the formatted line
   */
  public static String formatZeroes(String line, int positions) {
    if (line == null) {
			line = "";
		}
    if (positions <= 0) {
			return "";
		}
    String sign = line.substring(0, 1);
    if (sign.equals("+") || sign.equals("-")) {
			line = line.substring(1);
		} else {
			sign = "";
		}
    if (line.length() > positions) {
      line = line.substring(0, positions);
      return line;
    }
    int zeroes = positions - line.length();
    for (int idxI = 0; idxI < zeroes; idxI++) {
      line = "0" + line;
    }
    return sign + line;
  }

  /**
   * Formats a line adding a fixed number of leading zeroes, if the line is
   * longer than positions it is trunked at positions length.
   *
   * @param value
   *            value to be formatted
   * @param positions
   *            number of positions
   * @return the formatted line
   */
  public static String formatZeroes(double value, int positions) {
    return formatZeroes((long) value + "", positions);
  }

  /**
   * Formats a line with a fixed number of spaces. If the line is longer than
   * positions it is trucked at position length.
   *
   * @param line
   *            line to be formatted
   * @param positions
   *            number of positions
   * @return the formatted line
   */
  public static String formatSpaces(String line, int positions) {
    if (line == null) {
			line = "";
		}
    if (positions <= 0) {
			return "";
		}
    if (line.length() > positions) {
      line = line.substring(0, positions);
      return line;
    }
    int spaces = positions - line.length();
    for (int idxI = 0; idxI < spaces; idxI++) {
      line += " ";
    }
    return line;
  }

  /**
   * Formats a line positioning the input string in the middle
   * 
   * @param string
   *            input string
   * @param size
   *            line size
   * @return the formatted line
   */
  public static String center(String string, int size) {
    return center(string, size, ' ');
  }

  /**
   * Formats a line using the padding character positioning the input string
   * in the middle
   * 
   * @param string
   *            input string
   * @param size
   *            line size
   * @param paddingChar
   *            character to be used for padding
   * @return the formatted line
   */
  public static String center(String string, int size, char paddingChar) {
    if (string == null || size <= string.length()) {
			return string;
		}

    StringBuilder sb = new StringBuilder(size);
    for (int i = 0; i < (size - string.length()) / 2; i++) {
      sb.append(paddingChar);
    }
    sb.append(string);
    while (sb.length() < size) {
      sb.append(paddingChar);
    }
    return sb.toString();
  }

  /**
   * Creates a string with a repetition of the same character.
   *
   * @param character
   *            character to be repeated
   * @param count
   *            number of times of the repetition
   * @return a string with a repetition of the same character
   */
  public static String repeatCharacter(final char character, final int count) {
    final char[] outputString = new char[count];
    Arrays.fill(outputString, character); 
    return new String(outputString);
  }

  /**
   * Creates a string with a repetition of the same bytes.
   *
   * @param characters
   *            characters (bytes to be repeated)
   * @param count
   *            number of times of the repetition
   * @return a string with a repetition of the same character
   */
  public static String repeatBytes(final String characters, final int count) {
    int len = characters.length();
    byte[] outputString = new byte[count * len];
    for (int idx = 0; idx < count; idx++) {
      System.arraycopy(characters.getBytes(), 0, outputString, idx * len, len);
    }
    return new String(outputString);
  }

  /**
   * Replace leading zeros with space
   * 
   * @param value
   *            value to replace
   * @param groupSeparator
   *            numeric group separator
   * @return
   */
  public static String padLeadingZeroes(String value, String groupSeparator) {
    if (value.equals("0")) {
			return value;
		}
    String rc = "";
    for (int index = 0; index < value.length(); index++) {
      String c = value.substring(index, index + 1);
      if (c.equals("0") || !NAsmStrings.isNumeric(c)) {
        rc += " ";
      } else {
        rc += value.substring(index);
        return rc;
      }
    }
    return rc;
  }

  /**
   * Convert an EBCDIC hex character to its value representation.
   * 
   * @param hexString
   *            a valid hex string
   * @return the equivalent EBCDIC sequence of characters
   * @throws UnsupportedEncodingException
   *             if a problem occurs.
   */
  public static String convertEBCDIC(String hexString) throws UnsupportedEncodingException {
    String codepage = NAsmEnvironment.EBCDIC_CODEPAGE();
    if (hexString.toUpperCase(Locale.US).startsWith("X'")) {
      hexString = hexString.substring(2, hexString.length() - 1);
    }
    byte[] bytes = DatatypeConverter.parseHexBinary(hexString);
    String ascii;
    ascii = new String(bytes, codepage);
    return ascii;
  }

  /**
   * Convert an ASCII byte array to EBCDIC.
   * 
   * @param asciiValue
   *            an ASCII byte array
   * @return the equivalent EBCDIC sequence of characters
   */
  public static byte[] convertBytesToEBCDIC(byte[] asciiValue) {
    byte[] ebcdic = new String(asciiValue, NAsmEnvironment.ENV_CHARSET()).getBytes(NAsmEnvironment.EBCDIC_CHARSET());
    return ebcdic;
  }

  /**
   * Convert an EBCDIC byte array to ASCII.
   * 
   * @param ebcdicValue
   *            an EBCDIC byte array
   * @return the equivalent ASCII sequence of characters
   */
  public static byte[] convertBytesToASCII(byte[] ebcdicValue) {
    byte[] ebcdic = new String(ebcdicValue, NAsmEnvironment.EBCDIC_CHARSET()).getBytes(NAsmEnvironment.ENV_CHARSET());
    return ebcdic;
  }

  /**
   * Format a numeric value to a legacy binary sequence Big Endian, i.e.:<br>
   * <br>
   * a numeric value 12345 will be converted to 0x3039 if the length in bytes
   * is 2<br>
   * or to 0x00003039 if the length in bytes is 4.
   * 
   * @param value
   *            the numeric value to format
   * @param length
   *            the length in bytes
   * @return the output binary string
   * @throws Exception
   *             if a problem occurs.
   */
  public static byte[] valueToBinaryBigEndian(double value, int length) throws NAsmException {
    byte[] rc;
    if (length == 8) {
      rc = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
      byte[] bl = ByteBuffer.allocate(8).putLong((long) value).array();
      rc[0] = bl[0];
      rc[1] = bl[1];
      rc[2] = bl[2];
      rc[3] = bl[3];
      rc[4] = bl[4];
      rc[5] = bl[5];
      rc[6] = bl[6];
      rc[7] = bl[7];
    } else if (length == 4) {
      rc = new byte[] { 0x00, 0x00, 0x00, 0x00 };
      byte[] bl = ByteBuffer.allocate(4).putInt((int) value).array();
      rc[0] = bl[0];
      rc[1] = bl[1];
      rc[2] = bl[2];
      rc[3] = bl[3];
    } else if (length == 2) {
      rc = new byte[] { 0x00, 0x00 };
      byte[] bl = ByteBuffer.allocate(4).putInt((int) value).array();
      rc[0] = bl[2];
      rc[1] = bl[3];
    } else {
      if (length > 8) {
				throw new NAsmException("valueToBinaryBigEndian, invalide binary length " + length);
			}
      rc = new byte[length];
      for (int idx = 0; idx < length; idx++) {
        rc[idx] = 0x00;
      }
      byte[] bl = ByteBuffer.allocate(4).putInt((int) value).array();
      int pos = 0;
      for (int idx = 3; idx >= 0 && pos < length; idx--) {
        rc[length - pos - 1] = bl[idx];
        pos++;
      }
    }
    return rc;
  }

  /**
   * Convert a legacy binary sequence Big Endian to its numeric value,
   * i.e.:<br>
   * <br>
   * a binary sequence 0x3039 will be converted to 12345<br>
   * <br>
   * .
   * 
   * @param binaryData
   *            the binary sequence to convert
   * @param length
   *            the length in bytes
   * @return the output value
   * @throws Exception
   *             if a problem occurs.
   */
  public static int binaryBigEndianToValue(byte[] binaryData, int length) throws NAsmException {
    //		if (NAsmEnvironment.iAsmWrapper != null) {
    //			binaryData = NAsmEnvironment.iAsmWrapper.convertAscii2Ebcdic(Arrays.copyOfRange(binaryData, 0, length));
    //		}
    int rc;
    byte[] bytes = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    //Used for CLI
    if (length == 1) {
      bytes[3] = binaryData[0];
      rc = ByteBuffer.wrap(bytes, 0, 4).getInt();
      
    } else if (length == 2) {
      bytes[2] = binaryData[0];
      bytes[3] = binaryData[1];
      rc = ByteBuffer.wrap(bytes, 0, 4).getInt();
    } else if (length == 3) {
      bytes[1] = binaryData[0];
      bytes[2] = binaryData[1];
      bytes[3] = binaryData[2];
      rc = ByteBuffer.wrap(bytes, 0, 4).getInt();
    } else if (length == 4) {
      bytes[0] = binaryData[0];
      bytes[1] = binaryData[1];
      bytes[2] = binaryData[2];
      bytes[3] = binaryData[3];
      rc = ByteBuffer.wrap(bytes, 0, 4).getInt();
    } else if (length == 8) {
      bytes[0] = binaryData[0];
      bytes[1] = binaryData[1];
      bytes[2] = binaryData[2];
      bytes[3] = binaryData[3];
      bytes[4] = binaryData[4];
      bytes[5] = binaryData[5];
      bytes[6] = binaryData[6];
      bytes[7] = binaryData[7];
      rc = (int) ByteBuffer.wrap(bytes).getLong();
    } else {
      throw new NAsmException("binaryBigEndianToValue, invalid binary length " + length);
    }
    return rc;
  }

  public static long binaryBigEndianToLongValue(byte[] binaryData, int length) throws NAsmException {
    long rc;
    byte[] bytes = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    //Used for CLI
    if (length == 1) {
      bytes[7] = binaryData[0];
      rc = ByteBuffer.wrap(bytes, 0, 4).getLong();
      
    } else if (length == 2) {
      bytes[6] = binaryData[0];
      bytes[7] = binaryData[1];
      rc = ByteBuffer.wrap(bytes, 0, 4).getLong();
    } else if (length == 3) {
      bytes[5] = binaryData[0];
      bytes[6] = binaryData[1];
      bytes[7] = binaryData[2];
      rc = ByteBuffer.wrap(bytes, 0, 4).getLong();
    } else if (length == 4) {
      bytes[4] = binaryData[0];
      bytes[5] = binaryData[1];
      bytes[6] = binaryData[2];
      bytes[7] = binaryData[3];
      rc = ByteBuffer.wrap(bytes).getLong();
    } else if (length == 8) {
      bytes[0] = binaryData[0];
      bytes[1] = binaryData[1];
      bytes[2] = binaryData[2];
      bytes[3] = binaryData[3];
      bytes[4] = binaryData[4];
      bytes[5] = binaryData[5];
      bytes[6] = binaryData[6];
      bytes[7] = binaryData[7];
      rc = ByteBuffer.wrap(bytes).getLong();
    } else {
      throw new NAsmException("binaryBigEndianToLongValue, invalid binary length " + length);
    }
    return rc;
  }

  /**
   * Format a numeric value to a legacy packed sequence, i.e.:<br>
   * <br>
   * if isSigned is true a numeric value 12345 will be converted to 0x12345c
   * while -12345 will be converted to 0x12345d<br>
   * <br>
   * if isSigned is false both values 12345 and -12345 will be converted to
   * 0x12345f.
   * 
   * @param value
   *            the numeric value to format
   * @param lengthInDigits
   *            the length in digits
   * @param decimals
   *            number of decimals
   * @param isSigned
   *            packed format is signed
   * @return the output packed string
   */
  public static byte[] packData(double value, int lengthInDigits, int decimals, boolean isSigned) {
    //String unpackedData = Double.toString(value);
    //String unpackedData = new BigDecimal(value).toPlainString();
    //Assembler packed are always signed 
    isSigned = true;
    String unpackedData = new BigDecimal(value).setScale(decimals, RoundingMode.HALF_UP).toPlainString();
    //SQtmp very big numbers are getting changed?!
    String sqTmp = String.format("%.0f", value);
    if (!unpackedData.contentEquals(sqTmp)) {
      //SQtmp
      //logger.warn("[WARNING] SQ TMP - packData corrupted? " + unpackedData + " instead of " + sqTmp + "?!");
      //unpackedData = sqTmp;
    }
    if (decimals == 0) {
      int posDot = unpackedData.indexOf(".");
      if (posDot > -1) {
        unpackedData = unpackedData.substring(0, posDot);
      }
    } else {
      int posDot = unpackedData.indexOf(".");
      int numDec = 0;
      if (posDot == -1) {
        unpackedData += ".";
      } else {
        String decs = unpackedData.substring(posDot + 1);
        numDec = decs.length();
        if (numDec > decimals) {
          unpackedData = unpackedData.substring(0, posDot + 1) + NAsmStrings.formatZeroes(decs, decimals);
          numDec = decimals;
        }
      }
      while (numDec < decimals) {
        unpackedData += "0";
        numDec++;
      }
    }

    // Fill the field length leading with zeroes
    boolean itsNegative = false;
    if (unpackedData.charAt(0) == '-') {
      itsNegative = true;
      unpackedData = unpackedData.substring(1);
    }
    int currentLength = unpackedData.length();
    if (unpackedData.indexOf(".") > -1) {
      currentLength--;
    }
    // Too long truncate it!
    if (currentLength > lengthInDigits) {
      int gap = currentLength - lengthInDigits;
      unpackedData = unpackedData.substring(gap);
    } else {
      while (currentLength < lengthInDigits) {
        unpackedData = "0" + unpackedData;
        currentLength++;
      }
    }
    if (itsNegative) {
      unpackedData = "-" + unpackedData;
    }

    int unpackedDataLength = unpackedData.length();
    final int notSignedSign = 15; //0xF
    final int negativeSign = 13; //0xD
    final int positiveSign = 12; //0xC
    if (unpackedData.charAt(0) == '-') {
      unpackedDataLength--;
    }

    if (unpackedData.contains(".")) {
      unpackedDataLength--;
    }
    int packedLength = unpackedDataLength / 2 + 1;

    byte[] packed = new byte[packedLength];
    int countPacked = 0;
    boolean firstHex = (packedLength * 2 - 1 == unpackedDataLength);
    for (int i = 0; i < unpackedData.length(); i++) {
      if (unpackedData.charAt(i) != '-' && unpackedData.charAt(i) != '.') {
        byte digit = Byte.valueOf(unpackedData.substring(i, i + 1));
        if (firstHex) {
          packed[countPacked] = (byte) (digit << 4);
        } else {
          packed[countPacked] = (byte) (packed[countPacked] | digit);
          countPacked++;
        }
        firstHex = !firstHex;
      }
    }
    if (isSigned && !isUnsignedPacked) { //SQ PACK Vs ZAP) {
      if (unpackedData.charAt(0) == '-') {
        packed[countPacked] = (byte) (packed[countPacked] | negativeSign);
      } else {
				packed[countPacked] = (byte) (packed[countPacked] | positiveSign);
      }
    } else {
      packed[countPacked] = (byte) (packed[countPacked] | notSignedSign);
    }

    //		if (NAsmEnvironment.iAsmWrapper != null) {
    //			packed = NAsmEnvironment.iAsmWrapper.convertEbcdic2Ascii(packed);
    //		}
    return packed;
  }
  
  //SQtmp version using long
  public static byte[] packData(long value, int lengthInDigits, int decimals, boolean isSigned) {
    //Assembler packed are always signed 
    isSigned = true;
    String unpackedData = new BigDecimal(value).setScale(decimals, RoundingMode.HALF_UP).toPlainString();
    if (decimals == 0) {
      int posDot = unpackedData.indexOf(".");
      if (posDot > -1) {
        unpackedData = unpackedData.substring(0, posDot);
      }
    } else {
      int posDot = unpackedData.indexOf(".");
      int numDec = 0;
      if (posDot == -1) {
        unpackedData += ".";
      } else {
        String decs = unpackedData.substring(posDot + 1);
        numDec = decs.length();
        if (numDec > decimals) {
          unpackedData = unpackedData.substring(0, posDot + 1) + NAsmStrings.formatZeroes(decs, decimals);
          numDec = decimals;
        }
      }
      while (numDec < decimals) {
        unpackedData += "0";
        numDec++;
      }
    }

    // Fill the field length leading with zeroes
    boolean itsNegative = false;
    if (unpackedData.charAt(0) == '-') {
      itsNegative = true;
      unpackedData = unpackedData.substring(1);
    }
    int currentLength = unpackedData.length();
    if (unpackedData.indexOf(".") > -1) {
      currentLength--;
    }
    // Too long truncate it!
    if (currentLength > lengthInDigits) {
      int gap = currentLength - lengthInDigits;
      unpackedData = unpackedData.substring(gap);
    } else {
      while (currentLength < lengthInDigits) {
        unpackedData = "0" + unpackedData;
        currentLength++;
      }
    }
    if (itsNegative) {
      unpackedData = "-" + unpackedData;
    }

    int unpackedDataLength = unpackedData.length();
    final int notSignedSign = 15; //0xF
    final int negativeSign = 13; //0xD
    final int positiveSign = 12; //0xC
    if (unpackedData.charAt(0) == '-') {
      unpackedDataLength--;
    }

    if (unpackedData.contains(".")) {
      unpackedDataLength--;
    }
    int packedLength = unpackedDataLength / 2 + 1;

    byte[] packed = new byte[packedLength];
    int countPacked = 0;
    boolean firstHex = (packedLength * 2 - 1 == unpackedDataLength);
    for (int i = 0; i < unpackedData.length(); i++) {
      if (unpackedData.charAt(i) != '-' && unpackedData.charAt(i) != '.') {
        byte digit = Byte.valueOf(unpackedData.substring(i, i + 1));
        if (firstHex) {
          packed[countPacked] = (byte) (digit << 4);
        } else {
          packed[countPacked] = (byte) (packed[countPacked] | digit);
          countPacked++;
        }
        firstHex = !firstHex;
      }
    }
    if (isSigned && !isUnsignedPacked) { //SQ PACK Vs ZAP) {
      if (unpackedData.charAt(0) == '-') {
        packed[countPacked] = (byte) (packed[countPacked] | negativeSign);
      } else {
				packed[countPacked] = (byte) (packed[countPacked] | positiveSign);
      }
    } else {
      packed[countPacked] = (byte) (packed[countPacked] | notSignedSign);
    }

    //		if (NAsmEnvironment.iAsmWrapper != null) {
    //			packed = NAsmEnvironment.iAsmWrapper.convertEbcdic2Ascii(packed);
    //		}
    return packed;
  }



  /**
   * Convert a legacy packed sequence to its numeric value, i.e.:<br>
   * <br>
   * if isSigned is true a packed sequence 0x12345c will be converted to 12345
   * while 0x12345d will be converted to -12345<br>
   * <br>
   * if isSigned is false both sequences 0x12345c and 0x12345d will be
   * converted to 12345.
   * 
   * @param packedData
   *            the packed sequence to convert
   * @param decimals
   *            number of decimals
   * @return the output packed value
   * @throws Exception
   *             if a problem occurs.
   */
  public static double unpackData(byte[] packedData, int decimals) throws NAsmException {
    //		if (NAsmEnvironment.iAsmWrapper != null) {
    //			packedData = NAsmEnvironment.iAsmWrapper.convertAscii2Ebcdic(packedData);
    //		}
    String unpackedData = "";
    StringBuilder unpackedDataBuffer = new StringBuilder();
    final int notSignedSign = 15;
    final int negativeSign = 13;
    final int positiveSign = 12;
    int lengthPack = packedData.length;
    int numDigits = lengthPack * 2 - 1;

    int raw = (packedData[lengthPack - 1] & 0xFF);
    int firstDigit = (raw >> 4);
    int secondDigit = (packedData[lengthPack - 1] & 0x0F);
    //Sign can be C D or F
    if (secondDigit != positiveSign //
        && secondDigit != negativeSign //
        && secondDigit != notSignedSign) {
      throw new NAsmException("Packed value invalid format");
    }

    boolean negative = (secondDigit == negativeSign);
    int lastDigit = firstDigit;
    for (int i = 0; i < lengthPack - 1; i++) {
      raw = (packedData[i] & 0xff);
      firstDigit = (raw >> 4);
      secondDigit = (packedData[i] & 0x0f);
      unpackedDataBuffer.append(firstDigit);
      unpackedDataBuffer.append(secondDigit);

    }
    unpackedDataBuffer.append(lastDigit);
    unpackedData = unpackedDataBuffer.toString();

    if (decimals > 0) {
      unpackedData = unpackedData.substring(0, numDigits - decimals) + "." + unpackedData.substring(numDigits - decimals);
    }
    if (negative) {
      unpackedData = '-' + unpackedData;
    }
    return Double.valueOf(unpackedData);
  }
  
  //SQtmp long version
  public static long unpackData_L(byte[] packedData, int decimals) throws NAsmException {
    //		if (NAsmEnvironment.iAsmWrapper != null) {
    //			packedData = NAsmEnvironment.iAsmWrapper.convertAscii2Ebcdic(packedData);
    //		}
    String unpackedData = "";
    StringBuilder unpackedDataBuffer = new StringBuilder();
    final int notSignedSign = 15;
    final int negativeSign = 13;
    final int positiveSign = 12;
    int lengthPack = packedData.length;
    int numDigits = lengthPack * 2 - 1;

    int raw = (packedData[lengthPack - 1] & 0xFF);
    int firstDigit = (raw >> 4);
    int secondDigit = (packedData[lengthPack - 1] & 0x0F);
    //Sign can be C D or F
    if (secondDigit != positiveSign //
        && secondDigit != negativeSign //
        && secondDigit != notSignedSign) {
      throw new NAsmException("Packed value invalid format");
    }

    boolean negative = (secondDigit == negativeSign);
    int lastDigit = firstDigit;
    for (int i = 0; i < lengthPack - 1; i++) {
      raw = (packedData[i] & 0xff);
      firstDigit = (raw >> 4);
      secondDigit = (packedData[i] & 0x0f);
      unpackedDataBuffer.append(firstDigit);
      unpackedDataBuffer.append(secondDigit);

    }
    unpackedDataBuffer.append(lastDigit);
    unpackedData = unpackedDataBuffer.toString();

    if (decimals > 0) {
      unpackedData = unpackedData.substring(0, numDigits - decimals) + "." + unpackedData.substring(numDigits - decimals);
    }
    if (negative) {
      unpackedData = '-' + unpackedData;
    }
    return Long.valueOf(unpackedData); //Double.valueOf(unpackedData);
  }

  /**
   * 
   * @return a timestamp string with format "yyyy-MM-dd HH:mm:ss.SSS"
   * @throws Exception
   *             if a problem occurs.
   */
  public static String getTimestamp() {
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    Calendar c1 = Calendar.getInstance(); // today
    return sdf.format(c1.getTime());
  }

  /**
   * Convert a buffer to its hex representation.
   * 
   * @param buffer
   *            the buffer to represents as hex dump
   * @return the output hex dump
   */
  public static String formatHexDump(byte[] buffer, boolean displayToAscii) {
    return formatHexDump(buffer, displayToAscii, false);
  }

  /**
   * Convert a buffer to its hex representation, optionally print the result
   * to NAsmEnvironment defined console.
   * 
   * @param buffer
   *            the buffer to represents as hex dump
   * @param sendToDebugConsole
   *            <b>true</b> if the dump has to be redirect to the user console
   *            <br>
   *            <b>false</b> if the dump has not to be redirect to the user
   *            console
   * @return the output hex dump
   */
  public static String formatHexDump(byte[] buffer, boolean displayToAscii, boolean sendToDebugConsole) {
    final int width = 16;
    final int offset = 0;
    final int length = buffer.length;
    StringBuilder builder = new StringBuilder();
    byte[] bufferAscii;
    if (displayToAscii) {
      bufferAscii = NAsmStrings.convertBytesToASCII(buffer);
    } else {
      bufferAscii = buffer;
    }
    for (int rowOffset = offset; rowOffset < offset + length; rowOffset += width) {
      if (sendToDebugConsole) {
        builder = new StringBuilder();
      }
      builder.append(String.format("%06x: ", rowOffset)); //SQ 

      for (int index = 0; index < width; index++) {
        if (rowOffset + index < buffer.length) {
          builder.append(String.format("%02x ", buffer[rowOffset + index]));
        } else {
          builder.append("   ");
        }
      }

      if (rowOffset < buffer.length) {
        int asciiWidth = Math.min(width, buffer.length - rowOffset);
        builder.append(" | ");
        try {
          String textLine = new String(bufferAscii, rowOffset, asciiWidth, NAsmEnvironment.ENV_CHARSET()).replaceAll("\r\n", " ").replaceAll("\n", " ").replaceAll("[^\\p{Print}]", ".");
          builder.append(textLine);
        } catch (Exception ignored) {
          builder.append(new String("__hex_conv_error__"));
        }
      }
      if (sendToDebugConsole) {
        NAsmEnvironment.debug(builder.toString());
      } else {
        builder.append(String.format("%n"));
      }
    }
    return builder.toString();
  }

  /**
   * Convert a string with its hex sequence.
   * 
   * @param text
   *            the text to be represented as hex sequence
   * @return the hex sequence of the text passed as parameter
   */
  public static String toHex(String text) {
    return toHex(text.getBytes(NAsmEnvironment.ENV_CHARSET()));
  }

  /**
   * Convert a byte buffer to its hex sequence.
   * 
   * @param buffer
   *            the byte array to be represented as hex sequence
   * @return the byte array of the hex sequence passed as parameter
   */
  public static String toHex(byte[] buffer) {
    if (buffer == null) {
			return "";
		}
    StringBuffer result = new StringBuffer(2 * buffer.length);
    for (int i = 0; i < buffer.length; i++) {
      appendHex(result, buffer[i]);
    }
    return result.toString();
  }

  private static final String HEX = "0123456789ABCDEF";

  private static void appendHex(StringBuffer sb, byte b) {
    sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
  }

  public static final boolean isUTF8(final byte[] inputBytes) {
    final String converted = new String(inputBytes, StandardCharsets.UTF_8);
    final byte[] outputBytes = converted.getBytes(StandardCharsets.UTF_8);
    return Arrays.equals(inputBytes, outputBytes);
  }

  /**
   * Capitalize the first letter of a token
   * 
   * @param token
   *            token to capitalize
   * @return the input token with the first letter capitalized
   */
  public static String capitalizeOnlyFirstLetter(String token) {
    if (token == null || token.length() == 0) {
      return token;
    }
    return token.substring(0, 1).toUpperCase(Locale.US) + token.substring(1).toLowerCase();
  }

  /**
   * Convert a string representation of a hex dump to a byte array
   * 
   * @param hexString
   * @return
   */
  public static byte[] fromHex(String hexString) {
    int len = hexString.length();
    byte[] rc = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      rc[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
    }
    return rc;
  }

  public static String fromHexToString(String hexString) {
    int len = hexString.length();
    byte[] rc = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      rc[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
    }
    return new String(rc, NAsmEnvironment.ENV_CHARSET());
  }

  public static String fromIntToString(int value, int length) {
    String hexString = Integer.toHexString(value);
    hexString = NAsmStrings.formatZeroes(hexString, length * 2);
    int len = hexString.length();
    byte[] rc = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      rc[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
    }
    return new String(rc, NAsmEnvironment.ENV_CHARSET());
  }

  public static String SPACE(int length) {
    return NAsmStrings.repeatCharacter(' ', length);
  }

  public static String HIGH(int length) {
    return NAsmStrings.repeatCharacter((char) 0xff, length);
  }

  public static String LOW(int length) {
    return NAsmStrings.repeatCharacter((char) 0x00, length);
  }

  public static String overlay(String sourceString, int offset, int length, NAsmString overlayString) {
    return overlay(sourceString, offset, length, overlayString.toString());
  }

  public static String overlay(NAsmString sourceString, int offset, int length, NAsmString overlayString) {
    return overlay(sourceString.toString(), offset, length, overlayString.toString());
  }

  public static String overlay(String sourceString, int offset, int length, String overlayString) {
    if (overlayString.length() != length) {
			overlayString = NAsmStrings.formatSpaces(overlayString, length);
		}
    String rc = sourceString.substring(0, offset) + overlayString + sourceString.substring(offset + length);
    return rc;
  }

  public static String leftTrim(NAsmString string, int length) {
    return leftTrim(string.toString(), length);
  }

  public static String leftTrim(String string, int length) {
    if (string == null || string.length() < length) {
			return string;
		}
    int pos = string.length() - length;
    return string.substring(pos);
  }

  public static String rightTrim(String string) {
    int pos = string.length();
    for (int idx = pos - 1; idx >= 0; idx--) {
      if (string.charAt(idx) == 0x20  || string.charAt(idx) == 0x40) {
        pos--;
      } else {
        idx = 0;
      }
    }
    if (pos == 0) {
      return "";
    }else {
      return string.substring(0, pos);
    }
  }
  
  public static int getEbcdicSequence(char value) {
    byte[] ebcdic = new String(new byte[] { (byte) value }, NAsmEnvironment.ENV_CHARSET()).getBytes(NAsmEnvironment.EBCDIC_CHARSET());
    return ebcdic[0] & 0xff;
  }

  //SQ 
  public static int getLeftSemibyte(int byteValue) {
    return (byteValue & 0xF0) >> 4;
  }

  //SQ 
  public static int getRightSemibyte(int byteValue) {
    return byteValue & 0x0F;
  }

  /**
   * 
   * @param fileInput
   * @param fileOutput
   * @return
   * @throws NAsmException
   */
  public static boolean compare(String fileInput, String fileOutput) throws Exception {
    File fileIn = new File(fileInput);
    File fileOut = new File(fileOutput);
    if (!fileIn.exists()) {
      return false;
    }
    if (!fileOut.exists()) {
      return false;
    }
    if (fileIn.length() != fileOut.length()) {
      return false;
    }
    InputStream in1 = null;
    InputStream in2 = null;
    try {
      in1 = new BufferedInputStream(new FileInputStream(fileIn));
      in2 = new BufferedInputStream(new FileInputStream(fileOut));
      int value1, value2;
      do {
        value1 = in1.read();
        value2 = in2.read();
        if (value1 != value2) {
          return false;
        }
      } while (value1 >= 0);
      in1.close();
      in2.close();
      return true;
    } catch (Exception e) {
      throw e;
    }finally {
      if (in1 != null) {
        try {
          in1.close();
        } catch (Exception e) {
        }
      }
      if (in2 != null) {
        try {
          in2.close();
        } catch (Exception e) {
        }
      }
    }

  }
  
  //SQ to cover missing java11 stuff in jdk8
  public static boolean isBlank(String value) {
    return value.trim().length() == 0;
  }
  
}
