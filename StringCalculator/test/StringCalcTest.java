import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    StringCalculator calculator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        calculator = new StringCalculator();
    }

    @Test
    public void empty_string_reurns_0() {
        Assert.assertThat(calculator.add(""), is(0));
    }

    @Test
    public void one_number_in_string_returns_the_number() {
        Assert.assertThat(calculator.add("2"), is(2));
    }

    @Test
    public void two_numbers_separated_by_comma_returns_the_sum() {
        Assert.assertThat(calculator.add("1,2"), is(3));
    }

    @Test
    public void multiple_numbers_separated_by_comma_returns_the_sum() {
        Assert.assertThat(calculator.add("1,2,3,4"), is(10));
    }

    @Test
    public void multiple_numbers_separated_by_comma_or_newline_returns_the_sum() {
        Assert.assertThat(calculator.add("1\n2,3\n4"), is(10));
    }

    @Test
    public void separator_can_be_specified() {
        Assert.assertThat(calculator.add("//;\n1;2;3;4"), is(10));
    }

    @Test
    public void separator_spec_can_contain_multiple_chars() {
        Assert.assertThat(calculator.add("//-=-\n1-=-2-=-3-=-4"), is(10));
    }

    @Test
    public void negative_numbers_cause_exception_to_be_thrown() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Negative numbers are not allowed: -3, -4");

        calculator.add("1,2,-3,-4,10,2");
    }
}
