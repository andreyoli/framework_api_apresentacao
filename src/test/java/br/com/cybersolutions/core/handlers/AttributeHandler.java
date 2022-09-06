package br.com.cybersolutions.core.handlers;

import br.com.cybersolutions.core.utils.StringSyntax;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributeHandler {

    @SuppressWarnings("unchecked")
    public static void setAttribute(Object rootObject, Map<String, String> values) {

        Map<String, Object> cleanedValues = StringSyntax.setSyntax(values);

        cleanedValues.keySet().forEach(key -> {
            String[] fields = key.split("\\.");
            final int fieldsLastIndex = fields.length - 1;

            try {
                Object actualObject = rootObject;
                Field actualField;

                if (fieldHasListSyntax(fields[0])) {
                    actualField = getListField(fields[0], actualObject);
                } else {
                    actualField = actualObject.getClass().getDeclaredField(fields[0]);
                }

                actualField.setAccessible(true);

                for (int i = 1; i < fields.length; i++) {
                    actualObject = actualField.get(actualObject);

                    if (fieldHasListSyntax(fields[i - 1])) {
                        actualObject = getListObjectFromIndex(actualObject, fields[i - 1]);
                    }

                    if (fieldHasListSyntax(fields[i])) {
                        actualField = getListField(fields[i], actualObject);
                    } else {
                        actualField = actualObject.getClass().getDeclaredField(fields[i]);
                    }

                    actualField.setAccessible(true);
                }

                if (fieldHasListSyntax(fields[fieldsLastIndex])) {
                    int index = getIndexFromListFieldSyntax(fields[fieldsLastIndex]);
                    actualObject = actualField.get(actualObject);
                    List<Object> actualList = (List<Object>) actualObject;

                    if (isToClearTheList(cleanedValues.get(key))) {
                        actualList.clear();
                    } else {
                        actualList.set(index, cleanedValues.get(key));
                    }

                } else {

                    if (isToClearTheList(cleanedValues.get(key))) {
                        List<Object> actualList = (List<Object>) actualField.get(actualObject);
                        actualList.clear();

                    } else {
                        actualField.setAccessible(true);
                        actualField.set(actualObject, cleanedValues.get(key));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static Object getListObjectFromIndex(Object actualObject, String field) {
        int index = getIndexFromListFieldSyntax(field);
        return ((List<?>) actualObject).get(index);
    }

    private static boolean isToClearTheList(Object value) {
        return String.valueOf(value).equalsIgnoreCase("clear");
    }

    private static Field getListField(String field, Object actualObject) throws NoSuchFieldException {
        Field actualField = actualObject.getClass().getDeclaredField(field.split("\\[")[0]);
        actualField.setAccessible(true);
        return actualField;
    }

    private static boolean fieldHasListSyntax(String field) {
        return field.endsWith("]");
    }

    private static int getIndexFromListFieldSyntax(String field) {
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(field);

        int index = -1;

        if (matcher.find()) {
            index = Integer.parseInt(matcher.group(1));
        }

        return index;
    }

}
