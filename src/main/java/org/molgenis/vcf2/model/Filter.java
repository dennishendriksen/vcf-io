package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Filter {
  public abstract String getToken();

  public boolean hasPassedAllFilters() {
    return getToken().equals("PASS");
  }

  @Memoized
  public ImmutableList<String> getFailedFilters() {
    String token = getToken();
    return token.equals("PASS") ? ImmutableList.of() : ImmutableList.copyOf(token.split(";", -1));
  }

  public static Filter create(String newToken) {
    return FiltersCache.get(newToken);
  }

  private static class FiltersCache {
    private static final Filter FILTERS_PASS = new AutoValue_Filter("PASS");

    private FiltersCache() {}

    public static Filter get(String newToken) {
      return newToken.equals("PASS") ? FILTERS_PASS : new AutoValue_Filter(newToken);
    }
  }
}
