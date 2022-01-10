package com.prisma.librarymanagement.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvUtils {
    private static final CsvMapper mapper = new CsvMapper().enable(CsvParser.Feature.SKIP_EMPTY_LINES);

    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY).setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
}
