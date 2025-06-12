import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculator for simple and compound interest calculations
 */
public class InterestCalculator {

    /**
     * Calculate simple interest
     * Formula: SI = (P * R * T) / 100
     *
     * @param principal The principal amount
     * @param rate The annual interest rate (as percentage)
     * @param time Time period in years
     * @return Simple interest amount
     * @throws IllegalArgumentException if any parameter is negative
     */
    public double calculateSimpleInterest(double principal, double rate, double time) {
        validateInputs(principal, rate, time);
        return (principal * rate * time) / 100.0;
    }

    /**
     * Calculate compound interest
     * Formula: CI = P * (1 + R/100)^T - P
     *
     * @param principal The principal amount
     * @param rate The annual interest rate (as percentage)
     * @param time Time period in years
     * @return Compound interest amount
     * @throws IllegalArgumentException if any parameter is negative
     */
    public double calculateCompoundInterest(double principal, double rate, double time) {
        validateInputs(principal, rate, time);
        double amount = principal * Math.pow(1 + (rate / 100.0), time);
        return amount - principal;
    }

    /**
     * Calculate compound interest with compounding frequency
     * Formula: CI = P * (1 + R/(n*100))^(n*T) - P
     *
     * @param principal The principal amount
     * @param rate The annual interest rate (as percentage)
     * @param time Time period in years
     * @param frequency Number of times interest is compounded per year
     * @return Compound interest amount
     * @throws IllegalArgumentException if any parameter is negative or frequency is zero
     */
    public double calculateCompoundInterest(double principal, double rate, double time, int frequency) {
        validateInputs(principal, rate, time);
        if (frequency <= 0) {
            throw new IllegalArgumentException("Compounding frequency must be positive");
        }

        double ratePerPeriod = rate / (frequency * 100.0);
        double totalPeriods = frequency * time;
        double amount = principal * Math.pow(1 + ratePerPeriod, totalPeriods);
        return amount - principal;
    }

    /**
     * Calculate final amount for simple interest
     *
     * @param principal The principal amount
     * @param rate The annual interest rate (as percentage)
     * @param time Time period in years
     * @return Final amount (principal + simple interest)
     */
    public double calculateSimpleInterestAmount(double principal, double rate, double time) {
        return principal + calculateSimpleInterest(principal, rate, time);
    }

    /**
     * Calculate final amount for compound interest
     *
     * @param principal The principal amount
     * @param rate The annual interest rate (as percentage)
     * @param time Time period in years
     * @return Final amount (principal + compound interest)
     */
    public double calculateCompoundInterestAmount(double principal, double rate, double time) {
        return principal + calculateCompoundInterest(principal, rate, time);
    }

    /**
     * Calculate difference between compound and simple interest
     *
     * @param principal The principal amount
     * @param rate The annual interest rate (as percentage)
     * @param time Time period in years
     * @return Difference (compound interest - simple interest)
     */
    public double calculateInterestDifference(double principal, double rate, double time) {
        double simpleInterest = calculateSimpleInterest(principal, rate, time);
        double compoundInterest = calculateCompoundInterest(principal, rate, time);
        return compoundInterest - simpleInterest;
    }

    /**
     * Round amount to specified decimal places
     *
     * @param amount The amount to round
     * @param decimalPlaces Number of decimal places
     * @return Rounded amount
     */
    public double roundAmount(double amount, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places cannot be negative");
        }

        BigDecimal bd = new BigDecimal(amount);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Validate input parameters
     *
     * @param principal The principal amount
     * @param rate The interest rate
     * @param time The time period
     * @throws IllegalArgumentException if any parameter is negative
     */
    private void validateInputs(double principal, double rate, double time) {
        if (principal < 0) {
            throw new IllegalArgumentException("Principal amount cannot be negative");
        }
        if (rate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (time < 0) {
            throw new IllegalArgumentException("Time period cannot be negative");
        }
    }
}