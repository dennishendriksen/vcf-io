package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class Chromosome {
  public enum Type {
    REFERENCE_GENOME,
    ASSEMBLY
  }

  public abstract String getToken();

  @Memoized
  public String getIdentifier() {
    String token = getToken();
    return getType() == Type.ASSEMBLY ? token.substring(1, token.length() - 1) : token;
  }

  @Memoized
  public Type getType() {
    String token = getToken();
    return token.charAt(0) == '<' && token.charAt(token.length() - 1) == '>'
        ? Type.ASSEMBLY
        : Type.REFERENCE_GENOME;
  }

  public static Chromosome create(String newToken) {
    return ChromosomeCache.get(newToken);
  }

  private static class ChromosomeCache {
    private static final Chromosome CHROMOSOME_1 = new AutoValue_Chromosome("1");
    private static final Chromosome CHROMOSOME_2 = new AutoValue_Chromosome("2");
    private static final Chromosome CHROMOSOME_3 = new AutoValue_Chromosome("3");
    private static final Chromosome CHROMOSOME_4 = new AutoValue_Chromosome("4");
    private static final Chromosome CHROMOSOME_5 = new AutoValue_Chromosome("5");
    private static final Chromosome CHROMOSOME_6 = new AutoValue_Chromosome("6");
    private static final Chromosome CHROMOSOME_7 = new AutoValue_Chromosome("7");
    private static final Chromosome CHROMOSOME_8 = new AutoValue_Chromosome("8");
    private static final Chromosome CHROMOSOME_9 = new AutoValue_Chromosome("9");
    private static final Chromosome CHROMOSOME_10 = new AutoValue_Chromosome("10");
    private static final Chromosome CHROMOSOME_11 = new AutoValue_Chromosome("11");
    private static final Chromosome CHROMOSOME_12 = new AutoValue_Chromosome("12");
    private static final Chromosome CHROMOSOME_13 = new AutoValue_Chromosome("13");
    private static final Chromosome CHROMOSOME_14 = new AutoValue_Chromosome("14");
    private static final Chromosome CHROMOSOME_15 = new AutoValue_Chromosome("15");
    private static final Chromosome CHROMOSOME_16 = new AutoValue_Chromosome("16");
    private static final Chromosome CHROMOSOME_17 = new AutoValue_Chromosome("17");
    private static final Chromosome CHROMOSOME_18 = new AutoValue_Chromosome("18");
    private static final Chromosome CHROMOSOME_19 = new AutoValue_Chromosome("19");
    private static final Chromosome CHROMOSOME_20 = new AutoValue_Chromosome("20");
    private static final Chromosome CHROMOSOME_21 = new AutoValue_Chromosome("21");
    private static final Chromosome CHROMOSOME_22 = new AutoValue_Chromosome("22");
    private static final Chromosome CHROMOSOME_X = new AutoValue_Chromosome("X");
    private static final Chromosome CHROMOSOME_Y = new AutoValue_Chromosome("Y");
    private static final Chromosome CHROMOSOME_MT = new AutoValue_Chromosome("MT");

    private ChromosomeCache() {}

    public static Chromosome get(String newToken) {
      switch (newToken) {
        case "1":
          return CHROMOSOME_1;
        case "2":
          return CHROMOSOME_2;
        case "3":
          return CHROMOSOME_3;
        case "4":
          return CHROMOSOME_4;
        case "5":
          return CHROMOSOME_5;
        case "6":
          return CHROMOSOME_6;
        case "7":
          return CHROMOSOME_7;
        case "8":
          return CHROMOSOME_8;
        case "9":
          return CHROMOSOME_9;
        case "10":
          return CHROMOSOME_10;
        case "11":
          return CHROMOSOME_11;
        case "12":
          return CHROMOSOME_12;
        case "13":
          return CHROMOSOME_13;
        case "14":
          return CHROMOSOME_14;
        case "15":
          return CHROMOSOME_15;
        case "16":
          return CHROMOSOME_16;
        case "17":
          return CHROMOSOME_17;
        case "18":
          return CHROMOSOME_18;
        case "19":
          return CHROMOSOME_19;
        case "20":
          return CHROMOSOME_20;
        case "21":
          return CHROMOSOME_21;
        case "22":
          return CHROMOSOME_22;
        case "X":
          return CHROMOSOME_X;
        case "Y":
          return CHROMOSOME_Y;
        case "MT":
          return CHROMOSOME_MT;
        default:
          return new AutoValue_Chromosome(newToken);
      }
    }
  }
}
