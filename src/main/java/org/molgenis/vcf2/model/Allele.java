package org.molgenis.vcf2.model;

import static java.lang.String.format;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class Allele {
  public enum Type {
    BASES,
    MISSING,
    MISSING_OVERLAPPING_DELETION,
    SYMBOLIC,
    BREAKEND_REPLACEMENT
  }

  public abstract String getToken();

  @Memoized
  public String getBases() {
    Type type = getType();
    if (type != Type.BASES) {
      throw new UnsupportedOperationException(
          format(
              "Cannot return allele bases, because allele type is %s instead of %s",
              type, Type.BASES));
    }
    return getToken().toUpperCase();
  }

  @Memoized
  public String getIdentifier() {
    Type type = getType();
    if (type != Type.SYMBOLIC) {
      throw new UnsupportedOperationException(
          format(
              "Cannot return allele identifier, because allele type is %s instead of %s",
              type, Type.SYMBOLIC));
    }
    return getToken();
  }

  @Memoized
  public Type getType() {
    String token = getToken();
    if (token.equals(".")) {
      return Type.MISSING;
    } else if (token.equals("*")) {
      return Type.MISSING_OVERLAPPING_DELETION;
    }
    if (token.indexOf('[') != -1 || token.indexOf(']') != -1) {
      return Type.BREAKEND_REPLACEMENT;
    } else if (token.charAt(0) == '<' && token.charAt(token.length() - 1) == '>') {
      return Type.SYMBOLIC;
    } else {
      return Type.BASES;
    }
  }

  public static Allele create(String newToken) {
    return AlleleCache.get(newToken);
  }

  private static class AlleleCache {
    private static final Allele ALLELE_A = new AutoValue_Allele("A");
    private static final Allele ALLELE_C = new AutoValue_Allele("C");
    private static final Allele ALLELE_T = new AutoValue_Allele("T");
    private static final Allele ALLELE_G = new AutoValue_Allele("G");
    private static final Allele ALLELE_N = new AutoValue_Allele("N");
    private static final Allele ALLELE_MISSING = new AutoValue_Allele(".");
    private static final Allele ALLELE_MISSING_OVERLAPPING_DELETION = new AutoValue_Allele("*");

    private AlleleCache() {}

    static Allele get(String newToken) {
      switch (newToken) {
        case "A":
          return ALLELE_A;
        case "C":
          return ALLELE_C;
        case "T":
          return ALLELE_T;
        case "G":
          return ALLELE_G;
        case "N":
          return ALLELE_N;
        case ".":
          return ALLELE_MISSING;
        case "*":
          return ALLELE_MISSING_OVERLAPPING_DELETION;
        default:
          return new AutoValue_Allele(newToken);
      }
    }
  }
}
