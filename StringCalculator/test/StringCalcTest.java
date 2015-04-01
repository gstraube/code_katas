import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;

public class StringCalcTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void empty_string_returns_0() {
        Assert.assertThat(StringCalculator.add(""), is(0));
    }

    @Test
    public void one_number_in_string_returns_the_number() {
        Assert.assertThat(StringCalculator.add("2"), is(2));
    }

    @Test
    public void two_numbers_separated_by_comma_returns_the_sum() {
        Assert.assertThat(StringCalculator.add("1,2"), is(3));
    }

    @Test
    public void multiple_numbers_separated_by_comma_returns_the_sum() {
        Assert.assertThat(StringCalculator.add("1,2,3,4"), is(10));
    }

    @Test
    public void multiple_numbers_separated_by_comma_or_newline_returns_the_sum() {
        Assert.assertThat(StringCalculator.add("1\n2,3\n4"), is(10));
    }

    @Test
    public void separator_can_be_specified() {
        Assert.assertThat(StringCalculator.add("//;\n1;2;3;4"), is(10));
    }

    @Test
    public void separator_spec_can_contain_multiple_chars() {
        Assert.assertThat(StringCalculator.add("//-=-\n1-=-2-=-3-=-4"), is(10));
    }

    @Test
    public void negative_numbers_cause_exception_to_be_thrown() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Negative numbers are not allowed: -3, -4");

        StringCalculator.add("1,2,-3,-4,10,2");
    }

    @Test
    public void numbers_bigger_than_1000_are_ignored() {
        Assert.assertThat(StringCalculator.add("1,1001,1000,10,3401,20"), is(1031));
    }

}
