package com.rosettastone.succor.service.sms;

public class StringConverter {
  
  public static String getUnicodedString(char[] array) {
	  StringBuffer sb = new StringBuffer();
	  for (int k = 0; k < array.length; k++) {
		  sb.append(UnicodeFormatter.charToHex(array[k]));
	    } 
	  String convertedString=sb.toString();
	  System.out.println("convertedString= "+convertedString);
	  return convertedString;
	          
  }
}

class UnicodeFormatter {

  static public String byteToHex(byte b) {
    // Returns hex String representation of byte b
    char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f' };
    char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
    return new String(array);
  }

  static public String charToHex(char c) {
    // Returns hex String representation of char c
    byte hi = (byte) (c >>> 8);
    byte lo = (byte) (c & 0xff);
    return byteToHex(hi) + byteToHex(lo);
  }

} // class
