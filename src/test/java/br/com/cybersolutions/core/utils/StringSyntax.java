package br.com.cybersolutions.core.utils;

import br.com.cybersolutions.core.enums.StringSyntaxTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class StringSyntax {

    public static Map<String, Object> setSyntax(Map<String, String> values) {
        Map<String, Object> cleanedValues = new HashMap<>();

        for (String key : values.keySet()) {
            Object valueToSave;
            Predicate<String> valuesStartsWith = string -> values.get(key).startsWith(string);
            Predicate<String> valuesEquals = string -> values.get(key).equalsIgnoreCase(string);

            try {
                if (valuesStartsWith.test("{")) {
                    String stringSyntaxType = getSubstringBetweenCurlyBrackets(values.get(key)).toUpperCase();
                    String value = removeStringSyntaxTypeFromValue(values.get(key));
                    StringSyntaxTypes stringSyntaxEnum = StringSyntaxTypes.valueOf(stringSyntaxType);
                    valueToSave = stringSyntaxEnum.getValue(value);

                } else if (valuesEquals.test("null")) {
                    valueToSave = null;
                } else {
                    valueToSave = values.get(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ocorrido no StringSyntax");
            }

            cleanedValues.put(key, valueToSave);
        }

        return cleanedValues;
    }

    private static String getSubstringBetweenCurlyBrackets(String text) {
        int firstCurlyBracketIndex = text.indexOf("{");
        int lastCurlyBracketIndex = text.indexOf("}");
        String substring = text.substring(firstCurlyBracketIndex + 1, lastCurlyBracketIndex);
        return substring;
    }

    private static String removeStringSyntaxTypeFromValue(String value) {
        final String getBetweenCurlyBrackets = "\\{(.*?)}";
        String cleanedValue = value.replaceAll(getBetweenCurlyBrackets, "");
        return cleanedValue;
    }
}
