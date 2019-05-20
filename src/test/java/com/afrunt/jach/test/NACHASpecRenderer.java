package com.afrunt.jach.test;

import com.afrunt.jach.ACH;
import com.afrunt.jach.metadata.ACHBeanMetadata;
import com.afrunt.jach.metadata.ACHFieldMetadata;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.afrunt.jach.logic.StringUtil.letter;

/**
 * @author Andrii Frunt
 */
public class NACHASpecRenderer {
    private ACH ach = new ACH();
    private List<String> notes = new ArrayList<>();
    
    public String renderFullSpec() {
        return ach.getMetadata().getACHBeansMetadata().stream()
                .sorted(Comparator.comparing(ACHBeanMetadata::getRecordTypeCode))
                .peek(bm -> notes = new ArrayList<>())
                .map(this::generateSpecBody)
                .reduce((s1, s2) -> s1 + s2)
                .map(this::generateHtmlBeanSpec)
                .orElse("");
    }

    public Map<String, String> renderSingleSpecs() {
        notes = new ArrayList<>();
        return ach.getMetadata().getACHBeansMetadata().stream()
                .sorted(Comparator.comparing(ACHBeanMetadata::getRecordTypeCode))
                .peek(bm -> notes = new ArrayList<>())
                .collect(Collectors.toMap(bm -> bm.getRecordTypeCode() + "-" + bm.getSimpleTypeName() + ".htm", this::generateHtmlBeanSpec));
    }
    
    private String generateHtmlBeanSpec(ACHBeanMetadata bm) {
        return generateHtmlBeanSpec(generateSpecBody(bm));
    }

    private String generateHtmlBeanSpec(String body) {
        return formatHtmlContainer("NACHA Records Specification", body);
    }

    private String generateSpecBody(ACHBeanMetadata bm) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>(")
                .append(bm.getRecordTypeCode())
                .append(") ")
                .append(bm.getACHRecordName())
                .append("</h1>")
                .append("<table><tbody>");

        sb
                .append(row("FIELD", bm, (fm, i) -> String.valueOf(i + 1)))
                .append(row("Data Element Name", bm, (fm, i) -> fm.getAchFieldName()))
                .append(row("Field Inclusion Requirement", bm, (fm, i) -> fm.getInclusionRequirement().name()))
                .append(renderContentsRow(bm))
                .append(row("Length", bm, (fm, i) -> String.valueOf(fm.getLength())))
                .append(row("Position", bm, (fm, i) -> (fm.getStart() + 1) + "-" + fm.getEnd()))
                .append(row("Pattern key", bm, (fm, i) -> letter(i)));

        sb.append("</tbody></table>");

        return sb
                .append(renderPattern(bm))
                .append(renderNotes(bm))
                .append("<hr/>")
                .toString();
    }

    private String renderNotes(ACHBeanMetadata bm) {
        if (!notes.isEmpty()) {
            return "<h2>NOTES</h2><ul>"
                    + IntStream
                    .range(0, notes.size())
                    .boxed()
                    .map(this::renderNoteLineItem)
                    .collect(Collectors.joining())
                    + "</ul>";
        } else {
            return "";
        }
    }

    private String renderNoteLineItem(int i) {
        return "<li><sup>" + (i + 1) + "</sup> " + notes.get(i) + "</li>" + ACH.LINE_SEPARATOR;
    }

    private String renderPattern(ACHBeanMetadata bm) {
        return "<h2>Pattern</h2><pre>"
                + bm.getPattern()
                + "</pre>";
    }

    private String row(String firstColumnValue, ACHBeanMetadata bm, BiFunction<ACHFieldMetadata, Integer, String> fmFn) {
        List<ACHFieldMetadata> achFieldsMetadata = bm.getACHFieldsMetadata();

        StringBuilder sb = new StringBuilder()
                .append("<tr><td>")
                .append(firstColumnValue)
                .append("</td>");

        IntStream.range(0, achFieldsMetadata.size())
                .boxed()
                .forEach(i -> sb
                        .append("<td>")
                        .append(fmFn.apply(achFieldsMetadata.get(i), i))
                        .append("</td>")
                );

        return sb.append(ACH.LINE_SEPARATOR)
                .toString();
    }

    private String renderContentsRow(ACHBeanMetadata bm) {
        return row("Contents", bm,
                (fm, i) -> renderContentCell(fm)
        );
    }

    private String renderContentCell(ACHFieldMetadata fm) {
        if (fm.hasConstantValues() && requiresNote(fm)) {

            notes.add("May contain " + fm.getValues().stream().map(s -> "'" + s + "'").collect(Collectors.joining(", ")));

            return fm.getSimpleTypeName() + "<sup>" + notes.size() + "</sup> ";
        } else if (fm.hasConstantValues() && !requiresNote(fm)) {
            return "'" + fm.getValues().iterator().next() + "'";
        } else {
            return fm.getSimpleTypeName() + (!"".equals(fm.getDateFormat().trim()) ? ". " + fm.getDateFormat() : "");
        }
    }

    private boolean requiresNote(ACHFieldMetadata fm) {
        return fm.hasConstantValues() && fm.getValues().size() > 1;
    }


    private String formatHtmlContainer(String title, String content) {
        return String.format(readFile("template/container.html"), title, content);
    }

    private String readFile(String classPath) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(classPath).toURI());
            return Files.readAllLines(path).stream().collect(Collectors.joining(ACH.LINE_SEPARATOR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
