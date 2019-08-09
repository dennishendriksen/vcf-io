package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import org.molgenis.vcf2.VcfUtils;

@AutoValue
public abstract class Meta {
  public enum Type {
    STRUCTURED,
    UNSTRUCTURED
  }

  public abstract String getToken();

  @Memoized
  public Type getType() {
    String token = getToken();
    return token.charAt(token.indexOf('=') + 1) == '<' && token.charAt(token.length() - 1) == '>'
        ? Type.STRUCTURED
        : Type.UNSTRUCTURED;
  }

  @Memoized
  public String getKey() {
    String token = getToken();
    return token.substring(2, token.indexOf('='));
  }

  @Memoized
  public ImmutableMap<String, Value> getStructuredValue() {
    String token = getToken();
    String subToken = token.substring(token.indexOf('=') + 2, token.length());

    @SuppressWarnings("UnstableApiUsage")
    Builder<String, Value> builder = ImmutableMap.builder();

    boolean inQuotes = false;
    boolean isQuotedValue = false;
    String key = null;
    StringBuilder stringBuilder = new StringBuilder();
    final int nrChars = subToken.length();
    for (int i = 0; i < nrChars; ++i) {
      char c = subToken.charAt(i);
      switch (c) {
        case '=':
          if (inQuotes) {
            stringBuilder.append(c);
          } else {
            key = stringBuilder.toString();
            stringBuilder.setLength(0);
          }
          break;
        case ',':
        case '>':
          if (inQuotes) {
            stringBuilder.append(c);
          } else {
            builder.put(key, Value.create(stringBuilder.toString(), isQuotedValue));
            stringBuilder.setLength(0);
            isQuotedValue = false;
          }
          break;
        case '"':
          if(inQuotes) {
            inQuotes = false;
          } else {
            inQuotes = true;
            isQuotedValue = true;
          }

          break;
        case '\\':
          // TODO
          break;
        default:
          stringBuilder.append(c);
      }
    }

    return builder.build();
  }

  @Memoized
  public String getUnstructuredValue() {
    String token = getToken();
    String value = token.substring(token.indexOf('=') + 1);
    return VcfUtils.unescapeString(value);
  }

  public static Meta create(String newToken) {
    return new AutoValue_Meta(newToken);
  }

  @AutoValue
  public abstract static class Value {
    public abstract String getToken();

    @Memoized
    public String get() {
      String token = getToken();
      return VcfUtils.unescapeString(token);
    }

    public abstract boolean isQuoted();

    public static Value create(String newToken, boolean newQuoted) {
      return new AutoValue_Meta_Value(newToken, newQuoted);
    }
  }
}
