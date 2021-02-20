package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dataAccessLayer.DataAccessLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.Scenario;

public class Controller implements Initializable {
	private	ObservableList<Scenario> scenarioList = FXCollections.observableArrayList();
	private DataAccessLayer data = new DataAccessLayer();
	private Scene scenarioScene;
	private Scene assetScene;
	private Scenario selectedScenario;
	private ScenarioController scenarioController;
	
	public Scenario getSelectedScenario() {
		return selectedScenario;
	}

	public void setSelectedScenario(Scenario selectedScenario) {
		this.selectedScenario = selectedScenario;
	}

	public ScenarioController getScenarioController() {
		return scenarioController;
	}

	public void setScenarioController(ScenarioController scenarioController) {
		this.scenarioController = scenarioController;
	}

	
	public ObservableList<Scenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(ObservableList<Scenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public DataAccessLayer getData() {
		return data;
	}

	public void setData(DataAccessLayer data) {
		this.data = data;
	}
	public Scene getScenarioScene() {
		return scenarioScene;
	}

	public void setScenarioScene(Scene scenarioScene) {
		this.scenarioScene = scenarioScene;
	}

	public Scene getAssetScene() {
		return assetScene;
	}

	public void setAssetScene(Scene assetScene) {
		this.assetScene = assetScene;
	}


	@FXML 
	private Button editAssetAllocationButton;


	@FXML 
	void handleEditAssetAllocationButton(ActionEvent event) {
		if (!scenarioTable.getSelectionModel().isEmpty()) {
			selectedScenario = scenarioTable.getSelectionModel().getSelectedItem();
			scenarioController.setScenario(selectedScenario);
			scenarioController.updateAllData();
			Stage stage = (Stage) editAssetAllocationButton.getScene().getWindow();

			stage.setScene(scenarioScene);
			stage.show();

		} else {
			feedbackLabel.setText("Please select a scenario to delete.");
		}

	}

	@FXML 
	private Button deleteButton;

	@FXML 
	private Button addButton;

	@FXML 
	private Button calculateFinalCapitalButton;

	@FXML 
	private Button calculateAmountOfYearsButton;

	@FXML 
	private Button calculateYearlyPaymentButton;

	@FXML
	private Button calculateButton;

	@FXML 
	private Label feedbackLabel;

	@FXML 
	private TableView<Scenario> scenarioTable;

	@FXML 
	private TableColumn<Scenario, String> scenarioNameColumn;

	@FXML 
	private TableColumn<Scenario, Integer> scenarioNumberColumn;

	@FXML 
	private TableColumn<Scenario, Double> expensesColumn;

	@FXML 
	private TableColumn<Scenario, Double> initialCapitalColumn;

	@FXML 
	private TableColumn<Scenario, String> taxSettingColumn;
	
	@FXML
	private TableColumn<Scenario, Double> capitalGoalsColumn;

	@FXML 
	private Button addScenarioButton;

	@FXML 
	private TextField scenarioNameField;

	@FXML 
	private TextField annualExpensesField;

	@FXML 
	private TextField initialCapitalField;

	@FXML 
	private TextField taxSettingField;
	
	@FXML
	private TextField capitalGoalField;
	
    @FXML
    private TextField annualPaymentField;

    @FXML
    private TextField amountOfYearsField;

    @FXML 
	void handleCalculateButton(ActionEvent event) {
    	Scenario scenario;
    	if (!scenarioTable.getSelectionModel().isEmpty()) {
			scenario = scenarioTable.getSelectionModel().getSelectedItem();
    		double amountOfYears = Double.parseDouble(amountOfYearsField.getText());
    		double annualPayment = Double.parseDouble(annualPaymentField.getText());
			scenario.setAmountOfTerms(amountOfYears);
			scenario.setPaymentPerTerm(annualPayment);
			
			data.updateScenarioDoubleData("amountOfTerms", amountOfYears, scenario.getScenarioNumber());
			data.updateScenarioDoubleData("paymentPerTerm", annualPayment, scenario.getScenarioNumber());
			double finalCapital = scenario.calculateFinalCapital();
			feedbackLabel.setText("Final capital: " + String.format("%, .2f", finalCapital));


		} else {
			feedbackLabel.setText("Please select a scenario.");
		}
    }
    
	@FXML 
	void handleCalculateAmountOfYearsButton(ActionEvent event) {
		
	}

	@FXML 
	void handleCalculateFinalCapitalButton(ActionEvent event) {

	}

	@FXML 
	void handleCalculateYearlyPaymentButton(ActionEvent event) {

	}

	@FXML 
	void handleAddScenarioButton(ActionEvent event) {
		String scenarioName = scenarioNameField.getText();
		double expenses = Double.parseDouble(annualExpensesField.getText());
		double initialCapital = Double.parseDouble(initialCapitalField.getText());
		double capitalGoal = Double.parseDouble(initialCapitalField.getText());
		String taxSetting = taxSettingField.getText();
		int scenarioNumber;
		try {
			scenarioNumber = data.addScenario(expenses, taxSetting, initialCapital, scenarioName, capitalGoal);
			scenarioList.add(new Scenario(
					scenarioNumber,
					scenarioName, 
					taxSetting,
					expenses, 
					initialCapital, 
					capitalGoal));
			scenarioNameField.clear();
			annualExpensesField.clear();
			initialCapitalField.clear();
			capitalGoalField.clear();
			taxSettingField.clear();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML 
	void handleDeleteButton(ActionEvent event) {
		Scenario scenario;
		if (!scenarioTable.getSelectionModel().isEmpty()) {
			scenario = scenarioTable.getSelectionModel().getSelectedItem();
			deleteScenario(scenario);
			feedbackLabel.setText("Executed.");


		} else {
			feedbackLabel.setText("Please select a scenario to delete.");
		}
	}

	public void deleteScenario(Scenario scenario) {
		try {
			data.deleteScenario(scenario.getScenarioNumber());
			scenarioList.remove(scenario);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		updateScenarioTable();
		makeScenarioTableEditable();
	}
	//Associating the columns with properties in the Scenario class:	

	/********************
	 * Function			updateScenarioTable
	 * Description		Method to update the scenario table.
	 * Parameters 		
	 * Returns			
	 ********************/
	public void updateScenarioTable() {
		scenarioNameColumn.setCellValueFactory(new PropertyValueFactory<Scenario, String>("scenarioName"));
		scenarioNumberColumn.setCellValueFactory(new PropertyValueFactory<Scenario, Integer>("scenarioNumber"));
		expensesColumn.setCellValueFactory(new PropertyValueFactory<Scenario, Double>("expenses"));
		initialCapitalColumn.setCellValueFactory(new PropertyValueFactory<Scenario, Double>("initialCapital"));
		taxSettingColumn.setCellValueFactory(new PropertyValueFactory<Scenario, String>("taxSetting"));
		capitalGoalsColumn.setCellValueFactory(new PropertyValueFactory<Scenario, Double>("capitalGoal"));
		getScenarios();
		scenarioTable.setItems(scenarioList);
	}
	public void makeScenarioTableEditable() {
		//scenarioName
		scenarioNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		scenarioNameColumn.setOnEditCommit(event -> {
			Scenario scenario = event.getRowValue();
			scenario.setScenarioName(event.getNewValue());
			data.updateScenarioStringData("scenarioName", event.getNewValue(), scenario.getScenarioNumber());
		});
		//expensesColumn
		expensesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		expensesColumn.setOnEditCommit(event -> {
			Scenario scenario = event.getRowValue();
			scenario.setExpenses(event.getNewValue());
			data.updateScenarioDoubleData("expenses", event.getNewValue(), scenario.getScenarioNumber());
		});
		//initialCapitalColumn
		initialCapitalColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		initialCapitalColumn.setOnEditCommit(event -> {
			Scenario scenario = event.getRowValue();
			scenario.setInitialCapital(event.getNewValue());
			data.updateScenarioDoubleData("initialCapital", event.getNewValue(), scenario.getScenarioNumber());
		});
		//capitalGoalColumn
		capitalGoalsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		capitalGoalsColumn.setOnEditCommit(event -> {
			Scenario scenario = event.getRowValue();
			scenario.setCapitalGoal(event.getNewValue());
			data.updateScenarioDoubleData("capitalGoal", event.getNewValue(), scenario.getScenarioNumber());
		});
		//taxSettingColumn
		taxSettingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		taxSettingColumn.setOnEditCommit(event -> {
			Scenario scenario = event.getRowValue();
			scenario.setTaxSetting(event.getNewValue());
			data.updateScenarioStringData("taxSetting", event.getNewValue(), scenario.getScenarioNumber());
		});


	}

	/********************
	 * Function			getScenarios
	 * Description		Method to get all scenarios in a list.
	 * Parameters 		
	 * Returns			ObservableList<Scenario> with all scenarios.
	 ********************/
	public void getScenarios(){
		try {
			this.scenarioList = data.getScenarios();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
