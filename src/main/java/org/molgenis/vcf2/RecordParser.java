package org.molgenis.vcf2;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.Streams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.molgenis.vcf2.model.Record;

public class RecordParser {
  private final BufferedReader bufferedReader;

  public RecordParser(BufferedReader bufferedReader) {
    this.bufferedReader = requireNonNull(bufferedReader);
  }

  @SuppressWarnings("UnstableApiUsage")
  public Stream<Record> stream() {
    return Streams.stream(new RecordIterator(bufferedReader));
  }

  private static class RecordIterator implements Iterator<Record> {
    private final BufferedReader bufferedReader;
    private Record record;

    private RecordIterator(BufferedReader bufferedReader) {
      this.bufferedReader = requireNonNull(bufferedReader);
    }

    @Override
    public boolean hasNext() {
      if (record != null) {
        return true;
      }

      String line;
      try {
        line = bufferedReader.readLine();
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }

      if (line == null) {
        return false;
      } else {
        record = createRecord(line);
        return true;
      }
    }

    @Override
    public Record next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      Record nextRecord = record;
      record = null;
      return nextRecord;
    }

    private static Record createRecord(String line) {
      return Record.create(line.split("\t", -1));
    }
  }
}
