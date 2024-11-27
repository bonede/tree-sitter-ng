package org.treesitter.tests;

import java.util.regex.Pattern;

public abstract class SExpressionUtils {
    private static final String FIELD_PTN = "\\w+: \\(";


    public static String stripSExpressionWhitespace(String expr) {
        StringBuilder result = new StringBuilder();
        boolean inString = false;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '"') {
                inString = !inString;
                result.append(c);
            }else if (Character.isWhitespace(c) && !inString) {
                if (result.length() > 0 && result.charAt(result.length() - 1) != ' ') {
                    result.append(' ');
                }
            }else if (c == '\n' || c == '\r') {
                continue;
            }else {
                result.append(c);
            }
        }
        int len = result.length();
        if (len > 0 && result.charAt(len - 1) == ' ') {
            result.setLength(len - 1);
        }
        return result.toString();
    }

    public static String stripFieldNames(String expr){
        return expr.replaceAll(FIELD_PTN, "(");
    }
}
