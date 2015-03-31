import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class StringCalculator {

    public static final String NEGATIVE_NUMBERS_EX_MESSAGE = "Negative numbers are not allowed: ";

    static int add(String numbersInput) {

        String delimiter = ",";
        if ("".equals(numbersInput)) {
            return 0;
        }

        if (numbersInput.startsWith("//")) {
            delimiter = getDelimiter(numbersInput);
            numbersInput = getNumbers(numbersInput);
        }

        numbersInput = numbersInput.replace("\n", ",");
        List<String> separatedNumbers = Arrays.asList(numbersInput.split(delimiter));

        List<Integer> numbers = separatedNumbers.stream().map(Integer::parseInt).collect(toList());

        List<Integer> negativeNumbers = numbers.stream().filter((f) -> Math.signum(f) < 0).collect(toList());

        if (negativeNumbers.size() > 0) {
            String negativeNumbersOutput = negativeNumbers.stream().map(Object::toString)
                    .collect(joining(", "));
            StringBuilder exceptionMessage = new StringBuilder();
            exceptionMessage.append(NEGATIVE_NUMBERS_EX_MESSAGE);
            exceptionMessage.append(negativeNumbersOutput);

            throw new IllegalArgumentException(exceptionMessage.toString());
        }

        return numbers.stream().reduce(Math::addExact).get();
    }

    private static String getNumbers(String numbers) {
        final int endOfDelimiterSpec = numbers.indexOf("\n");
        final int beginOfNumbers = endOfDelimiterSpec + 1;

        return numbers.substring(beginOfNumbers);
    }

    private static String getDelimiter(String numbers) {
        final int beginOfDelimiter = 2;
        final int endOfDelimiterSpec = numbers.indexOf("\n");

        return numbers.substring(beginOfDelimiter, endOfDelimiterSpec);
    }
}
