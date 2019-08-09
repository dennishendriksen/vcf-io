package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Optional;

@AutoValue
public abstract class Info {
  public abstract String getToken();

  @Memoized
  public ImmutableMap<String, InfoField> getFields() {
    String token = getToken();
    String[] subTokens = token.split(";", -1);

    @SuppressWarnings("UnstableApiUsage")
    Builder<String, InfoField> builder = ImmutableMap.builderWithExpectedSize(subTokens.length);
    for (String subToken : subTokens) {
      int index = subToken.indexOf('=');
      String key = subToken.substring(0, index);
      String value = subToken.substring(index + 1);
      builder.put(key, InfoField.create(key, value));
    }
    return builder.build();
  }

  public Optional<InfoField> getField(String key) {
    InfoField infoField = getFields().get(key);
    return Optional.ofNullable(infoField);
  }

  public static Info create(String newToken) {
    return new AutoValue_Info(newToken);
  }

  @AutoValue
  public abstract static class InfoField {
    public abstract String getKey();

    public abstract String getValue();

    public static InfoField create(String newKey, String newValue) {
      return new AutoValue_Info_InfoField(newKey, newValue);
    }
  }
}
