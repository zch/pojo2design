package com.vaadin.pojo2design;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Created by jonatan on 15/09/15.
 */
public class Generator {

    private String sourceCode;
    private StringBuilder result;

    public Generator(File sourceFile) throws IOException {
        sourceCode = new String(Files.readAllBytes(sourceFile.toPath()),
                StandardCharsets.UTF_8);
    }

    public Generator(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String generate() throws ParseException {
        result = new StringBuilder();

        CompilationUnit cu = JavaParser.parse(new ByteArrayInputStream(sourceCode.getBytes(StandardCharsets.UTF_8)));
        new MethodVisitor().visit(cu, result);

        return result.toString();
    }

    private static class MethodVisitor extends VoidVisitorAdapter<StringBuilder> {

        @Override
        public void visit(MethodDeclaration method, StringBuilder result) {
            if (method.getName().startsWith("get")) {
                String caption = splitCamelCase(method.getName().substring(3));
                if (isTextualInput(method)) {
                    appendTag(result, "v-text-field", caption);
                } else if (isType(method, "boolean")) {
                    appendTag(result, "v-check-box", caption);
                } else if (isType(method, "date")) {
                    appendTag(result, "v-date-field", caption);
                }
            } else if (method.getName().startsWith("is")) {
                String caption = splitCamelCase(method.getName().substring(2));
                if (isType(method, "boolean")) {
                    appendTag(result, "v-check-box", caption);
                }
            }
        }

        private void appendTag(StringBuilder result, final String tagName, String caption) {
            result.append(result.length() > 0 ? "\n" : "").
                    append("<").append(tagName).append(" caption=\"").
                    append(caption).append("\"></").append(tagName).append(">");
        }

        private boolean isTextualInput(MethodDeclaration method) {
            return isType(method, "string")
                    || isType(method, "int")
                    || isType(method, "number")
                    || isType(method, "float")
                    || isType(method, "double")
                    ;
        }

        private boolean isType(MethodDeclaration method, String type) {
            return type.equalsIgnoreCase(method.getType().toString());
        }

        static String splitCamelCase(String s) {
            return s.replaceAll(
                    String.format("%s|%s|%s",
                            "(?<=[A-Z])(?=[A-Z][a-z])",
                            "(?<=[^A-Z])(?=[A-Z])",
                            "(?<=[A-Za-z])(?=[^A-Za-z])"
                    ),
                    " "
            );
        }
    }

}
