package lib;

public class TaxFunction {

	
	private static final int TAX_RATE = 0;
	private static final int MAX_CHILDREN_FOR_TAX_FREE = 0;
	private static final int BASE_TAX_FREE_INCOME = 0;
	private static final int ADDITIONAL_TAX_FREE_INCOME_MARRIED = 0;
	private static final int ADDITIONAL_TAX_FREE_INCOME_PER_CHILD = 0;

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	
	
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
