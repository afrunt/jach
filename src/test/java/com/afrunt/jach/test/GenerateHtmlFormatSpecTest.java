package com.afrunt.jach.test;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
