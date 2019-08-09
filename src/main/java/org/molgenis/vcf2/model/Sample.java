package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Sample {
  public abstract String getToken();

  @Memoized
  public ImmutableList<String> getValues() {
    String token = getToken();
    return ImmutableList.copyOf(token.split(",", -1));
  }

  public static Sample create(String newToken) {
    return new AutoValue_Sample(newToken);
  }
}
