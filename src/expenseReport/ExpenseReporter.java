package expenseReport;

import static java.lang.String.format;

public class ExpenseReporter {
  private ReportPrinter printer;
  private ExpenseReport report;
  private ExpenseNamer namer = new ExpenseReportNamer();

  public ExpenseReporter(ExpenseReport report) {
    this.report = report;
  }

  public void printReport(ReportPrinter printer) {
    this.printer = printer;
    report.totalUpExpenses();
    printExpensesAndTotals();
  }

  private void printExpensesAndTotals() {
    printHeader();
    printExpenses();
    printTotals();
  }

  private void printHeader() {
    printer.print("Expenses " + getDate() + "\n");
  }

  private void printExpenses() {
    for (Expense expense : report.getExpenses())
      printExpense(expense);
  }

  private void printExpense(Expense expense) {
    printer.print(format("%s\t%s\t$%.02f\n",
                            overageMarkFor(expense),
                            namer.nameFor(expense),
                            penniesToDollars(expense.getAmount())));
  }

  private String overageMarkFor(Expense expense) {
	return expense.isOverage() ? "X" : " ";
  }

  private void printTotals() {
    printer.print(format("\nMeal expenses $%.02f", mealExpensesInDollars()));
    printer.print(format("\nTotal $%.02f", totalInDollars()));
  }

  private double totalInDollars() {
	return penniesToDollars(report.getTotal());
  }

  private double mealExpensesInDollars() {
	return penniesToDollars(report.getMealExpenses());
  }

  private double penniesToDollars(int pennies) {
    return pennies / 100.0;
  }

  private String getDate() {
    return "9/12/2002";
  }

}
