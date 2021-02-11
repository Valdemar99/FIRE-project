package model;

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
	private ObservableList<AssetClass> initialAllocationList;
	private ObservableList<AssetClass> expectedAllocationList;
	
	public ObservableList<AssetClass> getInitialAllocationList() {
		return initialAllocationList;
	}
	public void setInitialAllocationList(ObservableList<AssetClass> initialAllocationList) {
		this.initialAllocationList = initialAllocationList;
	}
	public ObservableList<AssetClass> getExpectedAllocationList() {
		return expectedAllocationList;
	}
	public void setExpectedAllocationList(ObservableList<AssetClass> expectedAllocationList) {
		this.expectedAllocationList = expectedAllocationList;
	}
	public Scenario(int scenarioNumber, double expenses, String taxSetting,
			double initialCapital, double capitalGoal,
			String scenarioName, double amountOfTerms, double paymentPerTerm) {
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
	
	

}
