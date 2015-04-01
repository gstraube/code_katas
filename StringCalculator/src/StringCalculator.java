import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class StringCalculator {

    private static final String NEGATIVE_NUMBERS_EXCEPTION_MESSAGE = "Negative numbers are not allowed: %s";

    private static final String DELIMITER_SPECIFICATION_REGEX = "\\[([^\\[\\]]*)\\]";
    public static final String DEFAULT_DELIMITER = ",";

    public static int add(String numbersInput) {

        if ("".equals(numbersInput)) {
            return 0;
        }

        List<String> separatedNumbers;
        if (numbersInput.startsWith("//")) {
            separatedNumbers = getSeparatedNumbers(numbersInput);
        } else {
            separatedNumbers = getSeparatedNumbersWithDefaultDelimiter(numbersInput);
        }

        List<Integer> numbers = separatedNumbers.stream()
                .map(Integer::parseInt)
                .filter(number -> number <= 1000)
                .collect(toList());

        List<Integer> negativeNumbers = numbers.stream()
                .filter((f) -> Math.signum(f) < 0)
                .collect(toList());

        if (negativeNumbers.size() > 0) {
            String negativeNumbersOutput = negativeNumbers.stream()
                    .map(Object::toString)
                    .collect(joining(", "));

            String exceptionMessage = String.format(NEGATIVE_NUMBERS_EXCEPTION_MESSAGE,
                    negativeNumbersOutput);

            throw new IllegalArgumentException(exceptionMessage);
        }

        return numbers.stream()
                .reduce(Math::addExact)
                .get();
    }

    private static List<String> getSeparatedNumbers(String numbersInput) {
        Pattern delimiterPattern = Pattern.compile(DELIMITER_SPECIFICATION_REGEX);

        int endOfDelimiter = numbersInput.indexOf("\n");

        String delimiterSpecification = numbersInput.substring(2, endOfDelimiter);
        String numbers = numbersInput.substring(endOfDelimiter + 1);

        Matcher matcher = delimiterPattern.matcher(delimiterSpecification);
        List<String> delimiters = new ArrayList<>();

        while (matcher.find()) {
            delimiters.add(matcher.group(1));
        }

        if (delimiters.size() == 0) {
            delimiters.add(delimiterSpecification);
        }

        for (String delimiter : delimiters) {
            numbers = numbers.replace(delimiter, DEFAULT_DELIMITER);
        }
        numbers = numbers.replace("\n", DEFAULT_DELIMITER);

        return Arrays.asList(numbers.split(DEFAULT_DELIMITER));
    }

    private static List<String> getSeparatedNumbersWithDefaultDelimiter(String numbers) {
        String numbersWithoutNewLine = numbers.replace("\n", DEFAULT_DELIMITER);
        return Arrays.asList(numbersWithoutNewLine.split(DEFAULT_DELIMITER));
    }

}
