package application;
import java.sql.SQLException;
import java.text.DecimalFormat;

import dataAccessLayer.DataAccessLayer;
import javafx.collections.ObservableList;
import model.Scenario;

public class testClass {
	public static int generateScenarioNumber() {
			return + 1;
		}
	static DecimalFormat df = new DecimalFormat("#.##");
	
	public static void main(String[] args) {
		DataAccessLayer data = new DataAccessLayer();
//		
//		Scanner input = new Scanner(System.in);
//		String answer;
//		do {
//		System.out.println("Which country are you investing in, Denmark or Sweden?");
//		String taxSetting = input.nextLine();
//		System.out.println("What is your initial capital (kr)?");
//		double initialCapital = Double.parseDouble(input.nextLine());
//		System.out.println("What real rate of return is expected (inflation-adjusted)?");
//		double rateOfReturn = Double.parseDouble(input.nextLine());
//		System.out.println("For how many years do you intend to save up money?");
//		double amountOfTerms = Double.parseDouble((input.nextLine()));
//		System.out.println("What is your expected annual payment (kr)?");
//		double paymentPerTerm = Double.parseDouble((input.nextLine()));
//		Scenario savingScenario = new Scenario(generateScenarioNumber(), initialCapital, rateOfReturn, taxSetting);
		try {
//			data.addScenario(200000, "SE", 300000.33, "First Scenario", 5151);
//			data.editScenario(1, 32000, "DK", 25, "First Scene", 15456);
//			data.editScenarioAmountOfTerms(1, 30.333);
//			data.editScenarioPaymentPerTerm(1, 9879);
			ObservableList<Scenario> set = data.getScenarios();
			Scenario scenario = set.get(2);
    		double amountOfYears = 23;
    		double annualPayment = 90000;
			scenario.setAmountOfTerms(amountOfYears);
			scenario.setPaymentPerTerm(annualPayment);
			
			data.updateScenarioDoubleData("amountOfTerms", amountOfYears, scenario.getScenarioNumber());
			data.updateScenarioDoubleData("paymentPerTerm", annualPayment, scenario.getScenarioNumber());
			double finalCapital = scenario.calculateFinalCapital();
			System.out.println("Final capital " + String.format("%, .2f", finalCapital));
//			data.addExpectedAssetClass("Dogecoin", 0.234, 1);
//			data.addInitialAssetClass("Dogecoin", 0.45, 1);
//			data.editAssetClass("Bitcoin", "Initial Asset", 0.15, 0.8);
//			data.deleteScenario(2);
//			data.deleteAssetClass("Bitcoin", "Expected Asset");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		System.out.println("If you would like to make another simulation, please write \"y\" to repeat. Otherwise press any other key and then enter to exit the program.");
//		answer = input.nextLine();
//		} while (answer.equalsIgnoreCase("y"));
//		System.out.println("The program has shut down.");
 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
