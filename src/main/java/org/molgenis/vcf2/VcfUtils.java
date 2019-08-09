package org.molgenis.vcf2;

public class VcfUtils {
  private VcfUtils() {}

  /**
   * Variant Call Format Specification VCFv4.3: Float (32-bit, formatted to match the regular
   * expression ^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?$, NaN, or +/-Inf)
   */
  public static Float parseFloat(String str) {
    if (str.equals("NaN")) {
      return Float.NaN;
    } else if (str.equals("+Inf")) {
      return Float.POSITIVE_INFINITY;
    } else if (str.equals("-Inf")) {
      return Float.NEGATIVE_INFINITY;
    } else {
      return Float.parseFloat(str);
    }
  }

  /**
   * Characters with special meaning (such as field delimiters ‘;’ in INFO or ‘:’ FORMAT fields)
   * must be represented using the capitalized percent encoding: %3A : (colon) %3B ; (semicolon) %3D
   * = (equal sign) %25 % (percent sign) %2C , (comma) %0D CR %0A LF %09 TAB
   */
  public static String unescapeString(String escapedString) {
    if (escapedString.indexOf('%') == -1) {
      return escapedString;
    }
    return escapedString
        .replace("%3A", ":")
        .replace("%3B", ";")
        .replace("%3D", "=")
        .replace("%25", "%")
        .replace("%2C", ",")
        .replace("%0D", "\r")
        .replace("%0A", "\n")
        .replace("%09", "\t");
  }
}
