package br.com.cybersolutions.core.enums;

import br.com.cybersolutions.core.handlers.ResponseHandler;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public enum StringSyntaxTypes {

    STORAGE {
        @Override
        public Object getValue(String value) throws Exception {
            if (ResponseHandler.data.containsKey(value)) {
                return ResponseHandler.data.get(value);
            } else {
                throw new IllegalArgumentException("Chave não encontrada no storage");
            }
        }
    },

    NUMBER {
        @Override
        public Object getValue(String value) throws Exception {
            Number number = NumberFormat.getInstance(Locale.US).parse(value);
            if (value.contains(".")) {
                return number.floatValue();
            } else {
                return number.intValue();
            }
        }

    },

    MATCHER {
        @Override
        public Object getValue(String value) throws Exception {
            return Matchers.class.getMethod(value).invoke(null);
        }

    },

    DATE {
        @Override
        public Object getValue(String value) {
            LocalDate finalDate = null;
            ZoneId brasiliaTimeZone = ZoneId.of("GMT-3");
            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateNow = LocalDate.now(brasiliaTimeZone);
            char lastChar = value.charAt(value.length() - 1);

            if (value.startsWith("now")) {
                finalDate = dateNow;

            } else if (value.startsWith("+") || value.startsWith("-")) {
                String firstChar = String.valueOf(value.charAt(0));
                int dateToCalc = Integer.parseInt(StringUtils.chop(value.substring(1)));
                
                switch (lastChar) {
                    case 'd':
                        finalDate = firstChar.equals("+") ? dateNow.plusDays(dateToCalc) : dateNow.minusDays(dateToCalc);
                        break;

                    case 'm':
                        finalDate = firstChar.equals("+") ? dateNow.plusMonths(dateToCalc) : dateNow.minusMonths(dateToCalc);
                        break;

                    case 'y':
                        finalDate = firstChar.equals("+") ? dateNow.plusYears(dateToCalc) : dateNow.minusYears(dateToCalc);
                        break;

                    default:
                        throw new IllegalArgumentException();
                }

            }

            if (finalDate == null) {
                throw new NullPointerException();
            }

            return finalDate.format(datePattern);
        }
    },

    BOOL {
        @Override
        public Object getValue(String value) throws Exception {
            return Boolean.parseBoolean(value);
        }

    };

    public abstract Object getValue(String value) throws Exception;
}
