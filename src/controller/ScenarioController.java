package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dataAccessLayer.DataAccessLayer;
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
import javafx.stage.Stage;
import model.AssetClass;
import model.AssetClass.AssetClassTypeEnum;
import model.Scenario;

public class ScenarioController implements Initializable {
	private DataAccessLayer data;
	private Scene mainScene;
	private Scenario scenario;

	public Scenario getScenario() {
		return scenario;
	}
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public DataAccessLayer getData() {
		return data;
	}
	public void setData(DataAccessLayer data) {
		this.data = data;
	}
	public Scene getMainScene() {
		return mainScene;
	}
	public void setMainScene(Scene mainScene) {
		this.mainScene = mainScene;
	}

    @FXML  
    private Button clearInitialAllocationsButton;

    @FXML  
    private Button deleteInitialAllocationButton;

    @FXML  
    private Button addInitialAllocationButton;

    @FXML  
    private TextField initialAssetNameField;

    @FXML  
    private TextField initialAssetRateOfReturnField;

    @FXML  
    private TextField initialAssetAllocationField;

    @FXML  
    private TableView<AssetClass> initialAllocationTable;

    @FXML  
    private TableColumn<AssetClass, String> initialAllocationNameColumn;

    @FXML  
    private TableColumn<AssetClass, Double> initialAllocationRateOfReturnColumn;

    @FXML  
    private TableColumn<AssetClass, Double> initialAllocationColumn;

    @FXML 
    private Label assetLabel;

    @FXML  
    private Label taxSettingLabel;

    @FXML  
    private Label initialCapitalLabel;

    @FXML  
    private Label expensesLabel;

    @FXML  
    private Label scenarioNumberLabel;
    
    @FXML 
    private Label capitalGoalLabel;

    @FXML  
    private Button clearExpectedAllocationsButton;

    @FXML  
    private Button deleteExpectedAllocationButton;

    @FXML  
    private Button addExpectedAllocationButton;

    @FXML  
    private TextField expectedAssetNameField;

    @FXML  
    private TextField expectedAssetRateOfReturnField;

    @FXML  
    private TextField expectedAssetAllocationField;

    @FXML  
    private TableView<AssetClass> expectedAllocationTable;

    @FXML  
    private TableColumn<AssetClass, String> expectedAllocationNameColumn;

    @FXML  
    private TableColumn<AssetClass, Double> expectedAllocationRateOfReturnColumn;

    @FXML  
    private TableColumn<AssetClass, Double> expectedAllocationColumn;
    
    @FXML  
    private Button goBackButton;

    @FXML  
    void addExpectedAllocation(ActionEvent event) {
    	String assetClassName = expectedAssetNameField.getText();
		double rateOfReturn = Double.parseDouble(expectedAssetRateOfReturnField.getText());
		double allocation = Double.parseDouble(expectedAssetAllocationField.getText());
		try {
			data.addAssetClass(assetClassName, rateOfReturn);
			scenario.getExpectedAllocationList().add(new AssetClass(assetClassName, rateOfReturn, allocation,
					AssetClassTypeEnum.EXPECTED_ASSET, scenario.getScenarioNumber()));
			expectedAssetNameField.clear();
			expectedAssetRateOfReturnField.clear();
			expectedAssetAllocationField.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML  
    void addInitialAllocation(ActionEvent event) {
    	String assetClassName = initialAssetNameField.getText();
		double rateOfReturn = Double.parseDouble(initialAssetRateOfReturnField.getText());
		double allocation = Double.parseDouble(initialAssetAllocationField.getText());
		try {
			data.addAssetClass(assetClassName, rateOfReturn);
			data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocation, true);
			scenario.getInitialAllocationList().add(new AssetClass(assetClassName, rateOfReturn, allocation,
					AssetClassTypeEnum.INITIAL_ASSET, scenario.getScenarioNumber()));
			initialAssetNameField.clear();
			initialAssetRateOfReturnField.clear();
			initialAssetAllocationField.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML  
    void clearExpectedAllocations(ActionEvent event) {

    }

    @FXML  
    void clearInitialAllocations(ActionEvent event) {

    }

    @FXML  
    void deleteExpectedAllocation(ActionEvent event) {

    }

    @FXML  
    void deleteInitialAllocation(ActionEvent event) {

    }
    @FXML  
    void goBack(ActionEvent event) {
    	Stage stage = (Stage) initialAssetNameField.getScene().getWindow();

		stage.setScene(mainScene);
		stage.show();
    }
    
    public void updateLabelsWithScenario() {
    	if (assetLabel == null) {
    		System.out.println("Label was null!");
    	} else if (scenario == null) {
    		System.out.println("Scenario was null!");
    	} else {
    	assetLabel.setText(scenario.getScenarioName());
    	taxSettingLabel.setText(scenario.getTaxSetting());
    	initialCapitalLabel.setText("" + scenario.getInitialCapital());
    	expensesLabel.setText("" + scenario.getExpenses());
    	scenarioNumberLabel.setText("" + scenario.getScenarioNumber());
    	capitalGoalLabel.setText("" + scenario.getCapitalGoal());
    	}
    }

	public void updateInitialAllocationTable() {
		initialAllocationNameColumn.setCellValueFactory(new PropertyValueFactory<AssetClass, String>("assetClassName"));
		initialAllocationRateOfReturnColumn.setCellValueFactory(new PropertyValueFactory<AssetClass, Double>("rateOfReturn"));
		initialAllocationColumn.setCellValueFactory(new PropertyValueFactory<AssetClass, Double>("allocation"));
		getInitialAssetClasses();
		initialAllocationTable.setItems(scenario.getInitialAllocationList());
	}
	public void updateExpectedAllocationTable() {
		expectedAllocationNameColumn.setCellValueFactory(new PropertyValueFactory<AssetClass, String>("assetClassName"));
		expectedAllocationRateOfReturnColumn.setCellValueFactory(new PropertyValueFactory<AssetClass, Double>("rateOfReturn"));
		expectedAllocationColumn.setCellValueFactory(new PropertyValueFactory<AssetClass, Double>("allocation"));
		getExpectedAssetClasses();
		initialAllocationTable.setItems(scenario.getExpectedAllocationList());
	}

	/********************
	 * Function			getInitialAssetClasses
	 * Description		Method to get all initial assets from the current scenario.
	 * Parameters 		
	 * Returns			
	 ********************/

	public void getInitialAssetClasses() {
		if (scenario.getInitialAllocationList().isEmpty()) {
			try {
				ResultSet initialAssetSet = data.getInitialAssetClasses(scenario.getScenarioNumber());
				while (initialAssetSet.next()) {
					String assetClassName = initialAssetSet.getString("assetClassName");
					double rateOfReturn = initialAssetSet.getDouble("rateOfReturn");
					double allocation = initialAssetSet.getDouble("allocation");

					AssetClass thisRow = new AssetClass(assetClassName, rateOfReturn, allocation,
							AssetClassTypeEnum.INITIAL_ASSET, scenario.getScenarioNumber());
					scenario.getInitialAllocationList().add(thisRow);
				}
				data.closeConnection();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/********************
	 * Function			getExpectedAssetClasses
	 * Description		Method to get all expected assets from the current scenario in the database.
	 * 					Does not run if DB query has been run more than once.
	 * Parameters 		
	 * Returns			
	 ********************/

	public void getExpectedAssetClasses() {
		if (scenario.getExpectedAllocationList().isEmpty()) {
			try {
				ResultSet expectedAssetSet = data.getExpectedAssetClasses(scenario.getScenarioNumber());
				while (expectedAssetSet.next()) {
					String assetClassName = expectedAssetSet.getString("assetClassName");
					double rateOfReturn = expectedAssetSet.getDouble("rateOfReturn");
					double allocation = expectedAssetSet.getDouble("allocation");

					AssetClass thisRow = new AssetClass(assetClassName, rateOfReturn, allocation,
							AssetClassTypeEnum.EXPECTED_ASSET, scenario.getScenarioNumber());
					scenario.getExpectedAllocationList().add(thisRow);
				}
				data.closeConnection();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void updateAllData() {
		this.updateExpectedAllocationTable();
		this.updateInitialAllocationTable();
		this.updateLabelsWithScenario();
	}
}


