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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.Allocation;
import model.Allocation.AllocationTypeTypeEnum;
import model.AssetClass;
import model.AssetClassAllocationWrapper;
import model.Scenario;

public class ScenarioController implements Initializable {
	private DataAccessLayer data;
	private Scene mainScene;
	private Scenario scenario;
	private	ObservableList<String> assetClassList = FXCollections.observableArrayList();
	private ObservableList<AssetClassAllocationWrapper> initialAllocationList = FXCollections.observableArrayList();
	private ObservableList<AssetClassAllocationWrapper> expectedAllocationList = FXCollections.observableArrayList();
 

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

    public ObservableList<String> getAssetClassList() {
		return assetClassList;
	}
	public void setAssetClassList(ObservableList<String> assetClassList) {
		this.assetClassList = assetClassList;
	}

	@FXML  
    private Button clearInitialAllocationsButton;

    @FXML  
    private Button addInitialAllocationButton;

    @FXML  
    private ComboBox<String> initialAssetNameBox;


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
    private SplitMenuButton deleteExpectedAllocationButton;

    @FXML
    private MenuItem deleteExpectedAssetAltogetherMenuItem;
    
    @FXML
    private SplitMenuButton deleteInitialAllocationButton;

    @FXML
    private MenuItem deleteInitialAssetAltogetherMenuItem;

    @FXML  
    private Button addExpectedAllocationButton;

    @FXML  
    private ComboBox<String> expectedAssetNameBox;

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
    private Label expectedAllocationFeedback;
    
    @FXML
    private Label initialAllocationFeedback;
    
    @FXML
    void deleteExpectedAssetAltogether(ActionEvent event) {
    	AssetClassAllocationWrapper allocation;
    	String assetClassName;
		assetClassName = expectedAssetNameBox.getValue();
		if (!expectedAllocationTable.getSelectionModel().isEmpty()) {
			allocation = expectedAllocationTable.getSelectionModel().getSelectedItem();
			try {
				data.deleteAssetClass(allocation.getAssetClassName());
				this.updateAllData();
				expectedAllocationFeedback.setText("Executed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (!assetClassName.trim().equals("")) {
			AssetClass assetClassToDelete = data.getAssetClass(assetClassName);
			if(assetClassToDelete != null) {
				try {
					data.deleteAssetClass(assetClassToDelete.getAssetClassName());
					this.updateAllData();
					expectedAllocationFeedback.setText("Executed.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				this.expectedAllocationFeedback.setText("The asset does not exist. Please choose another one.");
			}
		} else if (assetClassName.trim().equals("")) {
			this.expectedAllocationFeedback.setText("Please enter a name.");
		} else {
			expectedAllocationFeedback.setText("Please select a scenario to delete.");
		}
    }

    @FXML
    void deleteInitialAssetAltogether(ActionEvent event) {
    	AssetClassAllocationWrapper allocation;
    	String assetClassName;
		assetClassName = initialAssetNameBox.getValue();
		if (!initialAllocationTable.getSelectionModel().isEmpty()) {
			allocation = initialAllocationTable.getSelectionModel().getSelectedItem();
			try {
				data.deleteAssetClass(allocation.getAssetClassName());
				this.updateAllData();
				initialAllocationFeedback.setText("Executed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (!assetClassName.trim().equals("")) {
			AssetClass assetClassToDelete = data.getAssetClass(assetClassName);
			if(assetClassToDelete != null) {
				try {
					data.deleteAssetClass(assetClassToDelete.getAssetClassName());
					this.updateAllData();
					initialAllocationFeedback.setText("Executed.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				this.expectedAllocationFeedback.setText("The asset does not exist. Please choose another one.");
			}
		} else if (assetClassName.trim().equals("")) {
			this.expectedAllocationFeedback.setText("Please enter a name.");
		} else {
			expectedAllocationFeedback.setText("Please select a scenario to delete.");
		}
    }

    @FXML  
    void addExpectedAllocation(ActionEvent event) {
		double rateOfReturn = Double.parseDouble(expectedAssetRateOfReturnField.getText());
		double allocationRate = Double.parseDouble(expectedAssetAllocationField.getText());
		String assetClassName;
		assetClassName = expectedAssetNameBox.getValue();

		try {
			//Check if any value is in the field besides blank spaces
			if (assetClassName.trim() == "") {
				this.expectedAllocationFeedback.setText("Please enter a name.");
			}
			//Check if it exists already
			else if (data.getAssetClass(assetClassName) != null) {
				if (rateOfReturn != data.findRateOfReturnForAsset(assetClassName)) {
					data.editAssetClass(assetClassName, rateOfReturn);
				}
				data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocationRate, false);
				clearExpectedAllocationSelection();
			}
			else {
				data.addAssetClass(assetClassName, rateOfReturn);
				data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocationRate, false);
				clearExpectedAllocationSelection();
			}
			this.updateAllData();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void clearExpectedAllocationSelection() {
		expectedAssetNameBox.getSelectionModel().clearSelection();
		expectedAssetRateOfReturnField.clear();
		expectedAssetAllocationField.clear();
    }

    @FXML  
    void addInitialAllocation(ActionEvent event) { //Add listener to check if a clicked item is selected or a manually typed one is selected.
    	//That way, the app can determine whether or not to add an already existing asset or create a new one to add.
    	String assetClassName = initialAssetNameBox.getValue();
		double rateOfReturn = Double.parseDouble(initialAssetRateOfReturnField.getText());
		double allocationRate = Double.parseDouble(initialAssetAllocationField.getText());
		
		try {
			//Check if any value is in the field besides blank spaces
			if (assetClassName.trim() == "") {
				this.initialAllocationFeedback.setText("Please enter a name.");
			}
			//Check if it exists already
			else if (data.getAssetClass(assetClassName) != null) {
				if (rateOfReturn != data.findRateOfReturnForAsset(assetClassName)) {
					data.editAssetClass(assetClassName, rateOfReturn);
				}
				data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocationRate, true);
				this.clearInitialAllocationSelection();
			}
			else {
				data.addAssetClass(assetClassName, rateOfReturn);
				data.linkAssetToScenario(assetClassName, scenario.getScenarioNumber(), allocationRate, true);
				this.clearInitialAllocationSelection();
			}
			this.updateAllData();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void clearInitialAllocationSelection() {
    	initialAssetNameBox.getSelectionModel().clearSelection();
		initialAssetRateOfReturnField.clear();
		initialAssetAllocationField.clear();
    }

    @FXML  
    void clearExpectedAllocations(ActionEvent event) {
    	if (!this.expectedAllocationList.isEmpty()) {
    		data.clearExpectedAllocationsFromScenario(scenario.getScenarioNumber());
    		this.updateAllData();
    	} else {
			initialAllocationFeedback.setText("There are no allocations to clear.");
    	}
    }

    @FXML  
    void clearInitialAllocations(ActionEvent event) {
    	if (!this.initialAllocationList.isEmpty()) {
    		data.clearInitialAllocationsFromScenario(scenario.getScenarioNumber());
    		this.updateAllData();
    	} else {
			initialAllocationFeedback.setText("There are no allocations to clear.");
    	}
    }

    @FXML  
    void deleteExpectedAllocation(ActionEvent event) {
    	AssetClassAllocationWrapper allocation;
		if (!initialAllocationTable.getSelectionModel().isEmpty()) {
			allocation = initialAllocationTable.getSelectionModel().getSelectedItem();
			try {
				data.unlinkAssetFromScenario(allocation.getAssetClassName(), scenario.getScenarioNumber(), "Expected Asset");
				this.updateAllData();
				expectedAllocationFeedback.setText("Executed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} else {
			expectedAllocationFeedback.setText("Please select a scenario to delete.");
		}
	}
    

    @FXML  
    void deleteInitialAllocation(ActionEvent event) {
    	AssetClassAllocationWrapper allocation;
		if (!initialAllocationTable.getSelectionModel().isEmpty()) {
			allocation = initialAllocationTable.getSelectionModel().getSelectedItem();
			try {
				data.unlinkAssetFromScenario(allocation.getAssetClassName(), scenario.getScenarioNumber(), "Initial Asset");
				this.updateAllData();
				initialAllocationFeedback.setText("Executed.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} else {
			initialAllocationFeedback.setText("Please select a scenario to delete.");
		}
	}

	public void deleteScenario(Scenario scenario) {
		

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
		initialAllocationTable.setItems(initialAllocationList);
	}
	public void updateExpectedAllocationTable() {
		expectedAllocationNameColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, String>("assetClassName"));
		expectedAllocationRateOfReturnColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, Double>("rateOfReturn"));
		expectedAllocationColumn.setCellValueFactory(new PropertyValueFactory<AssetClassAllocationWrapper, Double>("allocationRate"));
		getExpectedAssetClasses();
		expectedAllocationTable.setItems(expectedAllocationList);
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
		if (initialAllocationList.isEmpty()) {
			try {
				ResultSet initialAssetSet = data.getInitialAssetClasses(scenario.getScenarioNumber());
				while (initialAssetSet.next()) {
					String assetClassName = initialAssetSet.getString("assetClassName");
					double rateOfReturn = initialAssetSet.getDouble("rateOfReturn");
					double allocationRate = initialAssetSet.getDouble("allocation");

					AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
					Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.INITIAL_ASSET, scenario.getScenarioNumber(), assetClassName);
					
					AssetClassAllocationWrapper thisRow = new AssetClassAllocationWrapper(assetClass, allocation);
					initialAllocationList.add(thisRow);
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
		if (expectedAllocationList.isEmpty()) {
			try {
				ResultSet expectedAssetSet = data.getExpectedAssetClasses(scenario.getScenarioNumber());
				while (expectedAssetSet.next()) {
					String assetClassName = expectedAssetSet.getString("assetClassName");
					double rateOfReturn = expectedAssetSet.getDouble("rateOfReturn");
					double allocationRate = expectedAssetSet.getDouble("allocation");

					AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
					Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.EXPECTED_ASSET, scenario.getScenarioNumber(), assetClassName);
					
					AssetClassAllocationWrapper thisRow = new AssetClassAllocationWrapper(assetClass, allocation);
					expectedAllocationList.add(thisRow);
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
					
					assetClassList.add(assetClass.getAssetClassName());
				}
				data.closeConnection();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void updateTextFieldsOnSelectedAsset(ComboBox<String> assetBox, TextField rateOfReturnField) {
		AssetClass selectedAsset = data.getAssetClass(assetBox.getValue());
		if (selectedAsset != null) {
			rateOfReturnField.setText("" + selectedAsset.getRateOfReturn());
		} else {
			rateOfReturnField.setText("");
		}
	}
	public void clearData() {
		this.assetClassList.clear();
		this.expectedAllocationList.clear();
		this.initialAllocationList.clear();
	}
	public void makeInitialAllocationTableEditable() {
		//assetClassName
		initialAllocationNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
				initialAllocationNameColumn.setOnEditCommit(event -> {
					try {
						data.editAssetClassName(event.getOldValue(), event.getNewValue());
						this.updateAllData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				//assetClassRateOfReturn
				initialAllocationRateOfReturnColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
				initialAllocationRateOfReturnColumn.setOnEditCommit(event -> {
					AssetClassAllocationWrapper allocation = event.getRowValue();
					allocation.setRateOfReturn(event.getNewValue());
					
					allocation.getAssetClass().setRateOfReturn(event.getNewValue());
					try {
						data.editAssetClass(allocation.getAssetClassName(), event.getNewValue());
						this.updateAllData();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				//allocationColumn
				initialAllocationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
				initialAllocationColumn.setOnEditCommit(event -> {
					AssetClassAllocationWrapper allocation = event.getRowValue();
					allocation.setAllocationRate(event.getNewValue());
					data.editAllocationRate(scenario.getScenarioNumber(), allocation.getAssetClassName(), "Initial Asset", event.getNewValue());
					updateAllData();
				});
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		makeInitialAllocationTableEditable();
	}
	public void updateAllData() {
		this.clearData();
		this.updateExpectedAllocationTable();
		this.updateInitialAllocationTable();
		this.updateLabelsWithScenario();
		this.updateAssetComboBoxes();
	}
}


