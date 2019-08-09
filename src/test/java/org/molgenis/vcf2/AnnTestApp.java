package org.molgenis.vcf2;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.collect.ImmutableMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.molgenis.vcf2.model.Meta;
import org.molgenis.vcf2.model.Meta.Type;

public class AnnTestApp {
  public static void main(String[] args) throws IOException {
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
        if (line.startsWith("##")) {
          Meta meta = Meta.create(line);
          Type type = meta.getType();
          switch (type) {
            case STRUCTURED:
              ImmutableMap<String, Meta.Value> structuredValue = meta.getStructuredValue();
              System.out.println(meta.getKey() + "=" + structuredValue);
              break;
            case UNSTRUCTURED:
              System.out.println(meta.getKey() + "=" + meta.getUnstructuredValue());
              break;
          }
        } else {
          // this is the header line
          break;
        }
      }
      System.out.println(new RecordParser(bufferedReader).stream().count());
    }
  }
}
