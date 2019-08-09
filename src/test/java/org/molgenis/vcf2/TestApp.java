package org.molgenis.vcf2;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.collect.ImmutableList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import org.molgenis.vcf2.model.Allele;
import org.molgenis.vcf2.model.Allele.Type;
import org.molgenis.vcf2.model.Chromosome;
import org.molgenis.vcf2.model.Filter;
import org.molgenis.vcf2.model.Format;
import org.molgenis.vcf2.model.Info;
import org.molgenis.vcf2.model.Record;
import org.molgenis.vcf2.model.Sample;

public class TestApp {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    try (BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(
                new FileInputStream(
                    new File(
                        "C:\\Users\\Dennis\\Documents\\GCC\\genome_report\\data\\sensitive\\example.vcf\\example.vcf")),
                UTF_8))) {
      int count = 0;
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith("#")) continue;
        count++;
        String[] tokens = line.split("\t");
        Record vcfRecord = Record.create(tokens);
        Chromosome chromosome = vcfRecord.getChromosome();
        chromosome.getType();
        chromosome.getIdentifier();
        vcfRecord.getPosition();
        vcfRecord.getIdentifiers();
        Allele reference = vcfRecord.getReference();
        if (reference.getType() == Type.BASES) {
          reference.getBases();
        }
        ImmutableList<Allele> alternates = vcfRecord.getAlternates();
        alternates.forEach(
            allele -> {
              if (allele.getType() == Type.BASES) {
                allele.getBases();
              }
            });
        vcfRecord.getQuality();
        Optional<Filter> filtersOptional = vcfRecord.getFilters();
        filtersOptional.ifPresent(Filter::getFailedFilters);
        Info info = vcfRecord.getInfo();
        info.getFields().get("ANN");
        vcfRecord.getFormat().ifPresent(Format::getToken);
        vcfRecord.getSamples().forEach(Sample::getValues);

        vcfRecord
            .getInfo()
            .getField("ANN")
            .ifPresent(
                infoField -> {
                  infoField.getValue();
                });
      }
      long duration = System.currentTimeMillis() - start;
      System.out.println(
          count
              + " records parsed in "
              + duration
              + "ms - "
              + (count * 1000 / duration)
              + " records/s");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
