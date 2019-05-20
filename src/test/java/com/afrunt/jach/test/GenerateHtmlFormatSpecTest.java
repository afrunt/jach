package com.afrunt.jach.test;

import com.afrunt.beanmetadata.Typed;
import com.afrunt.jach.ACH;
import com.afrunt.jach.metadata.ACHBeanMetadata;
import com.afrunt.jach.metadata.ACHMetadata;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class GenerateHtmlFormatSpecTest {

    @Test
    public void testGenerateSpec() {
        NACHASpecRenderer renderer = new NACHASpecRenderer();

        renderer.renderSingleSpecs()
                .forEach(this::storeToFile);

        storeToFile("nacha-spec.htm", renderer.renderFullSpec());
    }

    @Test
    public void testIdenticalPatterns() {
        ACHMetadata metadata = new ACH().getMetadata();
        metadata
                .getACHBeansMetadata()
                .stream()
                .collect(Collectors.groupingBy(ACHBeanMetadata::getPattern))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .forEach(e -> System.out.println(String.format("%s: %s", e.getKey(), e.getValue().stream().map(Typed::getSimpleTypeName).collect(Collectors.joining(", ")))));
    }

    private void storeToFile(String fileName, String contents) {
        try {
            Path achDirPath = Paths.get(System.getProperty("user.dir")).resolve("target/ach");
            Files.createDirectories(achDirPath);
            Path filePath = achDirPath.resolve(fileName);
            PrintWriter writer = new PrintWriter(new FileOutputStream(filePath.toFile()));
            writer.write(contents);
            writer.flush();
            writer.close();
            System.out.println("File " + filePath.toAbsolutePath() + " stored successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
