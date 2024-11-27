package org.treesitter.tests;

public abstract class SExpressionUtils {
    public static String removeSExpressionWhitespace(String expr) {

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
            }
            else if (c == '\n' || c == '\r') {
                continue;
            }
            else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
