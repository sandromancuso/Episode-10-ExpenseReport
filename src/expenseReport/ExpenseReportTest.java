package expenseReport;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ExpenseReportTest {
  private ExpenseReport report;
  private ExpenseReporter reporter;
  private MockReportPrinter printer;

  @Before
  public void setUp() {
    report = new ExpenseReport();
    reporter = new ExpenseReporter(report);
    printer = new MockReportPrinter();
  }

  @Test public void 
  should_print_empty_report() {
    reporter.printReport(printer);

    assertThat(printer.getText(), is(
    							   	  "Expenses 9/12/2002\n" +
    							   	  "\n" +
    							      "Meal expenses $0.00\n" +
    								  "Total $0.00"));
  }

  @Test public void 
  should_print_one_dinner() {
    givenReportHas(dinnerCosting(1678));
    
    reporter.printReport(printer);

    assertThat(printer.getText(), is(
								      "Expenses 9/12/2002\n" +
								      " \tDinner\t$16.78\n" +
								      "\n" +
								      "Meal expenses $16.78\n" +
								      "Total $16.78"));
  }

  @Test public void 
  should_print_two_meals() throws Exception {
	givenReportHas(dinnerCosting(1000), 
				   breakfastCosting(500));
    
	reporter.printReport(printer);

    assertThat(printer.getText(), is(
								      "Expenses 9/12/2002\n" +
								      " \tDinner\t$10.00\n" +
								      " \tBreakfast\t$5.00\n" +
								      "\n" +
								      "Meal expenses $15.00\n" +
								      "Total $15.00"));
  }

  @Test public void 
  should_print_two_meals_and_a_car_rental() throws Exception {
    givenReportHas(dinnerCosting(1000),
    		       breakfastCosting(500),
    		       carRentalCosting(50000));
    
    reporter.printReport(printer);

    assertThat(printer.getText(), is(
								      "Expenses 9/12/2002\n" +
								      " \tDinner\t$10.00\n" +
								      " \tBreakfast\t$5.00\n" +
								      " \tCar Rental\t$500.00\n" +
								      "\n" +
								      "Meal expenses $15.00\n" +
								      "Total $515.00"));
  }

  @Test public void 
  should_print_overages() throws Exception {
    givenReportHas(breakfastCosting(1000),
    		       breakfastCosting(1001),
    		       dinnerCosting(5000),
    		       dinnerCosting(5001));
    
    reporter.printReport(printer);

    assertThat(printer.getText(), is(
								      "Expenses 9/12/2002\n" +
								      " \tBreakfast\t$10.00\n" +
								      "X\tBreakfast\t$10.01\n" +
								      " \tDinner\t$50.00\n" +
								      "X\tDinner\t$50.01\n" +
								      "\n" +
								      "Meal expenses $120.02\n" +
								      "Total $120.02"));
  }

  private DinnerExpense dinnerCosting(int amount) {
	return new DinnerExpense(amount);
  }

  private BreakfastExpense breakfastCosting(int amount) {
	return new BreakfastExpense(amount);
  }
  
  private CarRentalExpense carRentalCosting(int amount) {
		return new CarRentalExpense(amount);
	}  

  private void givenReportHas(Expense... expenses) {
	for (Expense expense : expenses) {
		report.addExpense(expense);
	}
  }

}
