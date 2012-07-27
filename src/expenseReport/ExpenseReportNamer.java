package expenseReport;

import java.util.HashMap;
import java.util.Map;

public class ExpenseReportNamer implements ExpenseNamer {

  static Map<Class<? extends Expense>, String> 
      ReportNameByExpenseClass = new HashMap<Class<? extends Expense>, String>();
  
  {
	  ReportNameByExpenseClass.put(DinnerExpense.class,    "Dinner");
	  ReportNameByExpenseClass.put(BreakfastExpense.class, "Breakfast");
	  ReportNameByExpenseClass.put(CarRentalExpense.class, "Car Rental");
  }
	
  public String getName(Expense expense) {
	  return ReportNameByExpenseClass.get(expense.getClass());
  }
}