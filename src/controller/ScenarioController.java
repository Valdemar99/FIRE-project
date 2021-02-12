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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Allocation;
import model.Allocation.AllocationTypeTypeEnum;
import model.AssetClass;
import model.AssetClassAllocationWrapper;
import model.Scenario;

public class ScenarioController implements Initializable {
	private DataAccessLayer data;
	private Scene mainScene;
	private Scenario scenario;
	private	ObservableList<AssetClass> assetClassList = FXCollections.observableArrayList();
 

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

    public ObservableList<AssetClass> getAssetClassList() {
		return assetClassList;
	}
	public void setAssetClassList(ObservableList<AssetClass> assetClassList) {
		this.assetClassList = assetClassList;
	}

	@FXML  
    private Button clearInitialAllocationsButton;

    @FXML  
    private Button deleteInitialAllocationButton;

    @FXML  
    private Button addInitialAllocationButton;

    @FXML  
    private ComboBox<AssetClass> initialAssetNameBox;


    @FXML  
    private TextField initialAssetRateOfReturnField;

    @FXML  
    private TextField initialAssetAllocationField;

    @FXML  
    private TableView<AssetClassAllocationWrapper> initialAllocationTable;

    @FXML  
    private TableColumn<AssetClassAllocationWrapper, String> initialAllocationNameColumn;

    @FXML  
    private TableColumn<AssetClassAllocationWrapper, Double> initialAllocationRateOfReturnColumn;

    @FXML  
    private TableColumn<AssetClassAllocationWrapper, Double> initialAllocationColumn;

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
    private ComboBox<AssetClass> expectedAssetNameBox;

    @FXML  
    private TextField expectedAssetRateOfReturnField;

    @FXML  
    private TextField expectedAssetAllocationField;

    @FXML  
    private TableView<AssetClassAllocationWrapper> expectedAllocationTable;

    @FXML  
    private TableColumn<AssetClassAllocationWrapper, String> expectedAllocationNameColumn;

    @FXML  
    private TableColumn<AssetClassAllocationWrapper, Double> expectedAllocationRateOfReturnColumn;

    @FXML  
    private TableColumn<AssetClassAllocationWrapper, Double> expectedAllocationColumn;
    
    @FXML  
    private Button goBackButton;

    @FXML  
    void addExpectedAllocation(ActionEvent event) {
    	String assetClassName = expectedAssetNameBox.getSelectionModel().toString();
		double rateOfReturn = Double.parseDouble(expectedAssetRateOfReturnField.getText());
		double allocationRate = Double.parseDouble(expectedAssetAllocationField.getText());
		try {
			data.addAssetClass(assetClassName, rateOfReturn);
			data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocationRate, false);
			AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
			Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.EXPECTED_ASSET, scenario.getScenarioNumber(), assetClassName);
			scenario.getExpectedAllocationList().add(new AssetClassAllocationWrapper(assetClass, allocation));
			expectedAssetNameBox.getSelectionModel().clearSelection();
			expectedAssetRateOfReturnField.clear();
			expectedAssetAllocationField.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML  
    void addInitialAllocation(ActionEvent event) {
    	String assetClassName = initialAssetNameBox.getSelectionModel().toString();
		double rateOfReturn = Double.parseDouble(initialAssetRateOfReturnField.getText());
		double allocationRate = Double.parseDouble(initialAssetAllocationField.getText());
		try {
			data.addAssetClass(assetClassName, rateOfReturn);
			data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocationRate, false);
			AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
			Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.INITIAL_ASSET, scenario.getScenarioNumber(), assetClassName);
			scenario.getExpectedAllocationList().add(new AssetClassAllocationWrapper(assetClass, allocation));
			initialAssetNameBox.getSelectionModel().clearSelection();
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
    	Stage stage = (Stage) expectedAssetRateOfReturnField.getScene().getWindow();

		stage.setScene(mainScene);
		stage.show();
    }
    
    @FXML
    void expectedAssetSelected(ActionEvent event) {
    	this.updateTextFieldsOnSelectedAsset(expectedAssetNameBox, expectedAssetRateOfReturnField);
    }

    @FXML
    void initialAssetSelected(ActionEvent event) {
    	this.updateTextFieldsOnSelectedAsset(initialAssetNameBox, initialAssetRateOfReturnField);
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
		initialAllocationNameColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, String>("assetClassName"));
		initialAllocationRateOfReturnColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, Double>("rateOfReturn"));
		initialAllocationColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, Double>("allocationRate"));
		getInitialAssetClasses();
		initialAllocationTable.setItems(scenario.getInitialAllocationList());
	}
	public void updateExpectedAllocationTable() {
		expectedAllocationNameColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, String>("assetClassName"));
		expectedAllocationRateOfReturnColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, Double>("rateOfReturn"));
		expectedAllocationColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, Double>("allocationRate"));
		getExpectedAssetClasses();
		initialAllocationTable.setItems(scenario.getExpectedAllocationList());
	}
	public void updateAssetComboBoxes() {
		getAssetClasses();
		initialAssetNameBox.setItems(assetClassList);
		expectedAssetNameBox.setItems(assetClassList);
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
					double allocationRate = initialAssetSet.getDouble("allocation");
					
					AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
					Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.INITIAL_ASSET, scenario.getScenarioNumber(), assetClassName);
					
					AssetClassAllocationWrapper thisRow = new AssetClassAllocationWrapper(assetClass, allocation);
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
					double allocationRate = expectedAssetSet.getDouble("allocation");

					AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
					Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.EXPECTED_ASSET, scenario.getScenarioNumber(), assetClassName);
					
					AssetClassAllocationWrapper thisRow = new AssetClassAllocationWrapper(assetClass, allocation);
					scenario.getExpectedAllocationList().add(thisRow);
				}
				data.closeConnection();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/********************
	 * Function			getAssetClasses
	 * Description		Method to get all expected assets from the current scenario in the database.
	 * 					Does not run if DB query has been run more than once.
	 * Parameters 		
	 * Returns			
	 ********************/
	public void getAssetClasses() {
		if (assetClassList.isEmpty()) {
			try {
				ResultSet assetSet = data.getAssetClasses();
				while (assetSet.next()) {
					String assetClassName = assetSet.getString("assetClassName");
					double rateOfReturn = assetSet.getDouble("rateOfReturn");

					AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
					
					assetClassList.add(assetClass);
				}
				data.closeConnection();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void updateTextFieldsOnSelectedAsset(ComboBox<AssetClass> assetBox, TextField rateOfReturnField) {
		try {
			AssetClass selectedAsset = assetBox.getSelectionModel().getSelectedItem();
			rateOfReturnField.setText("" + selectedAsset.getRateOfReturn());
		}
		catch (ClassCastException e) {
			rateOfReturnField.setText("");
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void updateAllData() {
		this.updateExpectedAllocationTable();
		this.updateInitialAllocationTable();
		this.updateLabelsWithScenario();
		this.updateAssetComboBoxes();
	}
}


