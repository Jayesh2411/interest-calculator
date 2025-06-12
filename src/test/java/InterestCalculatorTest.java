import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for InterestCalculator class
 */
public class InterestCalculatorTest {

    private InterestCalculator calculator;
    private static final double DELTA = 0.01; // For floating point comparisons

    @BeforeEach
    void setUp() {
        calculator = new InterestCalculator();
    }

    @Test
    @DisplayName("Should calculate simple interest correctly")
    void testSimpleInterestCalculation() {
        // Given: Principal=1000, Rate=5%, Time=2 years
        // When: Calculate simple interest
        double result = calculator.calculateSimpleInterest(1000, 5, 2);

        // Then: Expected (1000 * 5 * 2) / 100 = 100
        assertEquals(100.0, result, DELTA);
    }

    @Test
    @DisplayName("Should calculate compound interest correctly")
    void testCompoundInterestCalculation() {
        // Given: Principal=1000, Rate=5%, Time=2 years
        // When: Calculate compound interest
        double result = calculator.calculateCompoundInterest(1000, 5, 2);

        // Then: Expected 1000 * (1.05)^2 - 1000 = 102.5
        assertEquals(102.5, result, DELTA);
    }

    @Test
    @DisplayName("Should calculate compound interest with frequency correctly")
    void testCompoundInterestWithFrequency() {
        // Given: Principal=1000, Rate=5%, Time=2 years, Quarterly compounding
        // When: Calculate compound interest with frequency
        double result = calculator.calculateCompoundInterest(1000, 5, 2, 4);

        // Then: Expected ~104.49
        assertEquals(104.49, result, DELTA);
    }

    @ParameterizedTest
    @DisplayName("Should calculate simple interest for various inputs")
    @CsvSource({
            "1000, 5, 2, 100.0",
            "5000, 10, 3, 1500.0",
            "2000, 7.5, 4, 600.0",
            "1500, 6, 1.5, 135.0",
            "0, 5, 2, 0.0",
            "1000, 0, 2, 0.0",
            "1000, 5, 0, 0.0"
    })
    void testSimpleInterestMultipleInputs(double principal, double rate, double time, double expected) {
        double result = calculator.calculateSimpleInterest(principal, rate, time);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Should calculate final amounts correctly")
    void testFinalAmountCalculations() {
        // Test simple interest amount
        double simpleAmount = calculator.calculateSimpleInterestAmount(1000, 5, 2);
        assertEquals(1100.0, simpleAmount, DELTA);

        // Test compound interest amount
        double compoundAmount = calculator.calculateCompoundInterestAmount(1000, 5, 2);
        assertEquals(1102.5, compoundAmount, DELTA);
    }

    @Test
    @DisplayName("Should calculate interest difference correctly")
    void testInterestDifference() {
        // Given: Same parameters for both calculations
        double difference = calculator.calculateInterestDifference(1000, 5, 2);

        // Then: Compound interest should be 2.5 more than simple interest
        assertEquals(2.5, difference, DELTA);
    }

    @Test
    @DisplayName("Should throw exception for negative principal")
    void testNegativePrincipalThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateSimpleInterest(-1000, 5, 2)
        );
        assertEquals("Principal amount cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative rate")
    void testNegativeRateThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateSimpleInterest(1000, -5, 2)
        );
        assertEquals("Interest rate cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative time")
    void testNegativeTimeThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateSimpleInterest(1000, 5, -2)
        );
        assertEquals("Time period cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for invalid compounding frequency")
    void testInvalidCompoundingFrequencyThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateCompoundInterest(1000, 5, 2, 0)
        );
        assertEquals("Compounding frequency must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should round amounts correctly")
    void testRounding() {
        double amount = 123.456789;

        assertEquals(123.46, calculator.roundAmount(amount, 2), DELTA);
        assertEquals(123.5, calculator.roundAmount(amount, 1), DELTA);
        assertEquals(123.0, calculator.roundAmount(amount, 0), DELTA);
    }

    @Test
    @DisplayName("Compound interest should always be greater than or equal to simple interest")
    void testCompoundVsSimpleInterest() {
        double principal = 1000;
        double rate = 5;
        double time = 3;

        double simpleInterest = calculator.calculateSimpleInterest(principal, rate, time);
        double compoundInterest = calculator.calculateCompoundInterest(principal, rate, time);

        assertTrue(compoundInterest >= simpleInterest,
                "Compound interest should be >= simple interest");
    }

    @Test
    @DisplayName("Should handle edge cases correctly")
    void testEdgeCases() {
        // Very small values
        double smallResult = calculator.calculateSimpleInterest(0.01, 0.1, 0.1);
        assertTrue(smallResult >= 0 && smallResult < 0.001);

        // Large values
        double largeResult = calculator.calculateSimpleInterest(1000000, 15, 10);
        assertEquals(1500000.0, largeResult, DELTA);
    }
}