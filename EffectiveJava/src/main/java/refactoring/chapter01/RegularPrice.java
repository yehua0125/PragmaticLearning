package refactoring.chapter01;

public class RegularPrice extends Price {

    /**
     */

    @Override
    public PriceCode getPriceCode() {
        return PriceCode.REGULAR;
    }

    @Override
    public double getCharge(int daysRented) {
        double result = 2;
        if (daysRented > 2) {
            result += (daysRented - 2) * 1.5;
        }
        return result;
    }
}
