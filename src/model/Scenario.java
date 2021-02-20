package model;

import dataAccessLayer.DataAccessLayer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Scenario {
	private IntegerProperty scenarioNumber;
	private DoubleProperty expenses;
	private StringProperty taxSetting;
	private DoubleProperty initialCapital;
	private DoubleProperty capitalGoal;
	private StringProperty scenarioName;
	private DoubleProperty amountOfTerms;
	private DoubleProperty paymentPerTerm;
	private ObservableList<AssetClassAllocationWrapper> initialAllocationList;
	private ObservableList<AssetClassAllocationWrapper> expectedAllocationList;
	private static double swedishCapitalTax = 1.25;
	private static DataAccessLayer data = new DataAccessLayer();
	
	public ObservableList<AssetClassAllocationWrapper> getInitialAllocationList() {
		return initialAllocationList;
	}
	public void setInitialAllocationList(ObservableList<AssetClassAllocationWrapper> initialAllocationList) {
		this.initialAllocationList = initialAllocationList;
	}
	public ObservableList<AssetClassAllocationWrapper> getExpectedAllocationList() {
		return expectedAllocationList;
	}
	public void setExpectedAllocationList(ObservableList<AssetClassAllocationWrapper> expectedAllocationList) {
		this.expectedAllocationList = expectedAllocationList;
	}
	public Scenario(int scenarioNumber, String scenarioName, String taxSetting,
			double expenses, double initialCapital, double capitalGoal,
			double amountOfTerms, double paymentPerTerm) {
		this.scenarioNumber = new SimpleIntegerProperty(scenarioNumber);
		this.expenses = new SimpleDoubleProperty(expenses);
		this.taxSetting = new SimpleStringProperty(taxSetting);
		this.initialCapital = new SimpleDoubleProperty(initialCapital);
		this.capitalGoal = new SimpleDoubleProperty(capitalGoal);
		this.scenarioName = new SimpleStringProperty(scenarioName);
		this.amountOfTerms = new SimpleDoubleProperty(amountOfTerms);
		this.paymentPerTerm = new SimpleDoubleProperty(paymentPerTerm);
		
		expectedAllocationList = FXCollections.observableArrayList();
		initialAllocationList = FXCollections.observableArrayList();
	}
	public Scenario(int scenarioNumber, String scenarioName, String taxSetting,
			double expenses, double initialCapital, double capitalGoal) {
		this.scenarioNumber = new SimpleIntegerProperty(scenarioNumber);
		this.expenses = new SimpleDoubleProperty(expenses);
		this.taxSetting = new SimpleStringProperty(taxSetting);
		this.initialCapital = new SimpleDoubleProperty(initialCapital);
		this.scenarioName = new SimpleStringProperty(scenarioName);
		this.capitalGoal = new SimpleDoubleProperty(capitalGoal);
		
		this.amountOfTerms = new SimpleDoubleProperty(0);
		this.paymentPerTerm = new SimpleDoubleProperty(0);
		
		expectedAllocationList = FXCollections.observableArrayList();
		initialAllocationList = FXCollections.observableArrayList();
	}
	public final IntegerProperty scenarioNumberProperty() {
		return this.scenarioNumber;
	}
	
	public final int getScenarioNumber() {
		return this.scenarioNumberProperty().get();
	}
	
	public final void setScenarioNumber(final int scenarioNumber) {
		this.scenarioNumberProperty().set(scenarioNumber);
	}
	
	public final DoubleProperty expensesProperty() {
		return this.expenses;
	}
	
	public final double getExpenses() {
		return this.expensesProperty().get();
	}
	
	public final void setExpenses(final double expenses) {
		this.expensesProperty().set(expenses);
	}
	
	public final StringProperty taxSettingProperty() {
		return this.taxSetting;
	}
	
	public final String getTaxSetting() {
		return this.taxSettingProperty().get();
	}
	
	public final void setTaxSetting(final String taxSetting) {
		this.taxSettingProperty().set(taxSetting);
	}
	
	public final DoubleProperty initialCapitalProperty() {
		return this.initialCapital;
	}
	
	public final double getInitialCapital() {
		return this.initialCapitalProperty().get();
	}
	
	public final void setInitialCapital(final double initialCapital) {
		this.initialCapitalProperty().set(initialCapital);
	}
	
	public final DoubleProperty capitalGoalProperty() {
		return this.capitalGoal;
	}
	
	public final double getCapitalGoal() {
		return this.capitalGoalProperty().get();
	}
	
	public final void setCapitalGoal(final double capitalGoal) {
		this.capitalGoalProperty().set(capitalGoal);
	}

	public final StringProperty scenarioNameProperty() {
		return this.scenarioName;
	}
	
	public final String getScenarioName() {
		return this.scenarioNameProperty().get();
	}
	
	public final void setScenarioName(final String scenarioName) {
		this.scenarioNameProperty().set(scenarioName);
	}
	
	public final DoubleProperty amountOfTermsProperty() {
		return this.amountOfTerms;
	}
	
	public final double getAmountOfTerms() {
		return this.amountOfTermsProperty().get();
	}
	
	public final void setAmountOfTerms(final double amountOfTerms) {
		this.amountOfTermsProperty().set(amountOfTerms);
	}
	
	public final DoubleProperty paymentPerTermProperty() {
		return this.paymentPerTerm;
	}
	
	public final double getPaymentPerTerm() {
		return this.paymentPerTermProperty().get();
	}
	
	public final void setPaymentPerTerm(final double paymentPerTerm) {
		this.paymentPerTermProperty().set(paymentPerTerm);
	}
	public double calculateFinalCapital() {
		ObservableList<AssetClassAllocationWrapper> initialAssets = data.getInitialAssetClassList(getScenarioNumber());
		ObservableList<AssetClassAllocationWrapper> expectedAssets = data.getExpectedAssetClassList(getScenarioNumber());

		if (this.getTaxSetting() == "SE") {
			//Swedish tax setting.
			//Calculating how much the initial capital contributes to the final capital
			double sumFromInitialCapital = 0;
			for (AssetClassAllocationWrapper allocation : initialAssets) {
				double initialAmount = allocation.getAllocationRate() * this.getInitialCapital()/100;
				double netRateOfReturn = 1 + (allocation.getRateOfReturn() - swedishCapitalTax)/100;
				sumFromInitialCapital += initialAmount * Math.pow(netRateOfReturn, this.getAmountOfTerms());
			}
			
			//Calculating how the expected payments and savings will contribute to the final capital.
			double sumFromExpectedSavings = 0;
			//Calculate weighted average rate of return by calculating the sum of products of values and weights and the sum of weights.
			double sumProductOfWeightAndIndividualRateOfReturn = 0;
			double sumOfWeights = 0;
			for (AssetClassAllocationWrapper allocation : expectedAssets) {
				double thisWeight = allocation.getAllocationRate();
				double thisProductOfWeightAndIndividualRateOfReturn = thisWeight * (allocation.getRateOfReturn());
				sumOfWeights += thisWeight;
				sumProductOfWeightAndIndividualRateOfReturn += thisProductOfWeightAndIndividualRateOfReturn;
			}
			double netWeightedAverageRateOfReturn = 1 + (sumProductOfWeightAndIndividualRateOfReturn / sumOfWeights - swedishCapitalTax)/100;
			sumFromExpectedSavings = this.getPaymentPerTerm()
					*(Math.pow(netWeightedAverageRateOfReturn, this.getAmountOfTerms()) - 1)
					/(netWeightedAverageRateOfReturn-1);
			return sumFromInitialCapital + sumFromExpectedSavings;
		} else {
			//Danish tax setting.
			//Calculating how much the initial capital contributes to the final capital
			double sumFromInitialCapital = 0;
			for (AssetClassAllocationWrapper allocation : initialAssets) {
				double initialAmount = allocation.getAllocationRate() * this.getInitialCapital()/100;
				double netRateOfReturn = 1 + (allocation.getRateOfReturn())/100;
				sumFromInitialCapital += initialAmount * Math.pow(netRateOfReturn, this.getAmountOfTerms());
			}
			
			//Calculating how the expected payments and savings will contribute to the final capital.
			double sumFromExpectedSavings = 0;
			//Calculate weighted average rate of return by calculating the sum of products of values and weights and the sum of weights.
			double sumProductOfWeightAndIndividualRateOfReturn = 0;
			double sumOfWeights = 0;
			for (AssetClassAllocationWrapper allocation : expectedAssets) {
				double thisWeight = allocation.getAllocationRate();
				double thisProductOfWeightAndIndividualRateOfReturn = thisWeight * (allocation.getRateOfReturn());
				sumOfWeights += thisWeight;
				sumProductOfWeightAndIndividualRateOfReturn += thisProductOfWeightAndIndividualRateOfReturn;
			}
			double netWeightedAverageRateOfReturn = 1 + (sumProductOfWeightAndIndividualRateOfReturn / sumOfWeights)/100;
			sumFromExpectedSavings = this.getPaymentPerTerm()
					*(Math.pow(netWeightedAverageRateOfReturn, this.getAmountOfTerms()) - 1)
					/(netWeightedAverageRateOfReturn-1);
			return sumFromInitialCapital + sumFromExpectedSavings;
		}
	}
	

}
