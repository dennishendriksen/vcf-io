package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Maps;
import java.util.Map;

@AutoValue
public abstract class Format {
  public abstract String getToken();

  @Memoized
  public ImmutableList<String> getKeys() {
    String token = getToken();

    ImmutableList<String> keys;
    if (token.indexOf(':') != -1) {
      Builder<String> builder = ImmutableList.builder();
      for (String subToken : token.split(":", -1)) {
        builder.add(subToken);
      }
      keys = builder.build();
    } else {
      keys = ImmutableList.of(token);
    }
    return keys;
  }

  public static Format create(String newToken) {
    return FormatCache.get(newToken);
  }

  private static class FormatCache {
    private static final Map<String, Format> FORMAT_CACHE = Maps.newHashMapWithExpectedSize(25);

    private FormatCache() {}

    public static Format get(String newToken) {
      Format format = FORMAT_CACHE.get(newToken);
      if (format == null) {
        format = new AutoValue_Format(newToken);
        if (FORMAT_CACHE.size() < 25) {
          FORMAT_CACHE.put(newToken, format);
        }
      }
      return new AutoValue_Format(newToken);
    }
  }
}
