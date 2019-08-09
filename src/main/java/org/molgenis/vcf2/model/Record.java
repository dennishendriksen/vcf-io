package org.molgenis.vcf2.model;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import org.molgenis.vcf2.VcfUtils;

@AutoValue
public abstract class Record {
  public abstract ImmutableList<String> getTokens();

  @Memoized
  public Chromosome getChromosome() {
    String token = getTokens().get(0);
    return Chromosome.create(token);
  }

  @Memoized
  public int getPosition() {
    String token = getTokens().get(1);
    return Integer.parseInt(token);
  }

  @Memoized
  public ImmutableList<String> getIdentifiers() {
    String token = getTokens().get(2);

    ImmutableList<String> identifiers;
    if (token.equals(".")) {
      identifiers = ImmutableList.of();
    } else if (token.indexOf(';') == -1) {
      identifiers = ImmutableList.of(token);
    } else {
      identifiers = ImmutableList.copyOf(token.split(";", -1));
    }
    return identifiers;
  }

  @Memoized
  public Allele getReference() {
    String token = getTokens().get(3);
    return Allele.create(token);
  }

  @Memoized
  public ImmutableList<Allele> getAlternates() {
    String token = getTokens().get(4);

    ImmutableList<Allele> alleles;
    if (token.indexOf(',') == -1) {
      alleles = ImmutableList.of(Allele.create(token));
    } else {
      String[] subTokens = token.split(",", -1);
      @SuppressWarnings("UnstableApiUsage")
      Builder<Allele> builder = ImmutableList.builderWithExpectedSize(subTokens.length);
      for (String subToken : subTokens) {
        builder.add(Allele.create(subToken));
      }
      alleles = builder.build();
    }
    return alleles;
  }

  @Memoized
  public Optional<Float> getQuality() {
    String token = getTokens().get(5);
    return token.equals(".") ? Optional.empty() : Optional.of(VcfUtils.parseFloat(token));
  }

  @Memoized
  public Optional<Filter> getFilters() {
    String token = getTokens().get(6);
    return token.equals(".") ? Optional.empty() : Optional.of(Filter.create(token));
  }

  @Memoized
  public Info getInfo() {
    String token = getTokens().get(7);
    return Info.create(token);
  }

  @Memoized
  public Optional<Format> getFormat() {
    ImmutableList<String> tokens = getTokens();
    return tokens.size() > 8 ? Optional.of(Format.create(tokens.get(8))) : Optional.empty();
  }

  @Memoized
  public ImmutableList<Sample> getSamples() {
    ImmutableList<String> tokens = getTokens();
    int nrTokens = tokens.size();

    ImmutableList<Sample> samples;
    if (nrTokens > 9) {
      @SuppressWarnings("UnstableApiUsage")
      Builder<Sample> builder = ImmutableList.builderWithExpectedSize(nrTokens - 9);
      for (int i = 9; i < nrTokens; ++i) {
        builder.add(Sample.create(tokens.get(i)));
      }
      samples = builder.build();
    } else {
      samples = ImmutableList.of();
    }
    return samples;
  }

  public static Record create(String[] newTokens) {
    return new AutoValue_Record(ImmutableList.copyOf(newTokens));
  }
}
