package lib;

public class TaxFunction {

    private static final double TAX_RATE = 0.05;
    private static final int MAX_CHILDREN_FOR_TAX_FREE = 3;
    private static final int BASE_TAX_FREE_INCOME = 54000000;
    private static final int ADDITIONAL_TAX_FREE_INCOME_MARRIED = 4500000;
    private static final int ADDITIONAL_TAX_FREE_INCOME_PER_CHILD = 1500000;

    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        
        validateInput(numberOfMonthWorking, numberOfChildren);
        
        int taxFreeIncome = calculateTaxFreeIncome(isMarried, numberOfChildren);
        int taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, taxFreeIncome);

        int tax = (int) Math.round(TAX_RATE * taxableIncome);

        return Math.max(0, tax);
    }
	
	
	
	private static void validateInput(int numberOfMonthWorking, int numberOfChildren) {
		if (numberOfMonthWorking > 12) {
			throw new IllegalArgumentException("More than 12 month working per year");
		}
		if (numberOfChildren > MAX_CHILDREN_FOR_TAX_FREE) {
			throw new IllegalArgumentException("More than maximum allowed children");
		}
	}
	
	private static int calculateTaxFreeIncome(boolean isMarried, int numberOfChildren) {
		int taxFreeIncome = BASE_TAX_FREE_INCOME;
		if (isMarried) {
			taxFreeIncome += ADDITIONAL_TAX_FREE_INCOME_MARRIED;
		}
		taxFreeIncome += numberOfChildren * ADDITIONAL_TAX_FREE_INCOME_PER_CHILD;
	
		return taxFreeIncome;
	}
	
	private static int calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, int taxFreeIncome) {
		return ((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible - taxFreeIncome;
	}
	
	
}
