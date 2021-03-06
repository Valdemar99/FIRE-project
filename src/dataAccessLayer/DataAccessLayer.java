package dataAccessLayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Allocation;
import model.Allocation.AllocationTypeTypeEnum;
import model.AssetClass;
import model.AssetClassAllocationWrapper;
import model.Scenario;

public class DataAccessLayer {
	private Connection con;
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	/********************
	 * Function			connect
	 * Description		Method to connect to database to avoid repeating code, returns Connection if successful, otherwise returns null.
	 * Parameters 
	 * Returns			Connection to database
	 ********************/
	//Method to connect to database to avoid repeating code, returns Connection if successful, otherwise returns null.
	public void connect() {
		//Creating null Connection which will be assigned a value in try/catch block (if successful)
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//Connecting to my local database
			String url = "jdbc:sqlserver://localhost:1433;database=SmartSavers";
			
			//credentials
			String user = "user";
			String password = "losen";
			
			//Assigning the null variable the value of the (hopefully) successful login
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		//Creating null Connection which will be assigned a value in try/catch block (if successful)
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//Connecting to my local database
			String url = "jdbc:sqlserver://localhost:1433;database=SmartSavers";
			
			//credentials
			String user = "user";
			String password = "losen";
			
			//Assigning the null variable the value of the (hopefully) successful login
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/********************
	 * Function			closeConnection
	 * Description		Method to close the database connection.
	 * Parameters 
	 * Returns			
	 * @throws SQLException 
	 ********************/
	public void closeConnection() throws SQLException {
		//Creating null Connection which will be assigned a value in try/catch block (if successful)
		con.close();
	}
	/********************
	 * Function			read
	 * Description		Reads and executes query. Returns and saves result in resultSet
	 * Parameters 		String query		The inserted query to be executed
	 * Returns			ResultSet

	 ********************/
	public ResultSet query(String query) throws SQLException, ClassNotFoundException {
		connect();
		PreparedStatement ps = con.prepareStatement(query);	//preparing query
		ResultSet resultSet = ps.executeQuery(); //executing query and saving result to resultSet
		return resultSet; //returning resultSet
	}
	
	/********************
	 * Function			update
	 * Description		Uses a query to update information in database
	 * Parameters 		String update		The inserted query to be executed
	 * Returns			Returns 0 if there are no rows to update

	 ********************/
	//Below returns 0 if there are no rows to delete.
	public int update (String update) throws SQLException {
		connect();
		PreparedStatement ps = con.prepareStatement(update);
		int updatedRows = ps.executeUpdate();
		return updatedRows;
	}
	
	//Here are the methods for scenarios:
	
	/********************
	 * Function			addScenario
	 * Description		Method to add a scenario.
	 * Parameters 		double expenses, String taxSetting, double initialCapital, String scenarioName, double capitalGoal
	 * Returns			
	 * @throws ClassNotFoundException 
	 ********************/
	public int addScenario(double expenses, String taxSetting, double initialCapital, String scenarioName, double capitalGoal) throws SQLException, ClassNotFoundException {
		String query = "INSERT INTO Scenario (expenses, taxSetting, initialCapital, scenarioName, capitalGoal)";
		query += " VALUES (" + expenses + ", '"
		+ taxSetting + "', " 
		+ initialCapital + ", '" 
		+ scenarioName + "', "
		+ capitalGoal + ")";
		connect();
		PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int affectedRows = statement.executeUpdate();
		if (affectedRows == 0) {
            throw new SQLException("Creating scenario failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int scenarioNumber = generatedKeys.getInt(1);
                return scenarioNumber;
            }
            else {
                throw new SQLException("Creating scenario failed, no ID obtained.");
            }
        }
		
	}
	
	/********************
	 * Function			editScenario
	 * Description		Method to edit a scenario with its important attributes that are needed for all use cases. Useful for correcting a saving scenario.
	 * Parameters 		int scenarioNumber, double expenses, String taxSetting, double initialCapital, double capitalGoal
	 * Returns			
	 ********************/
	public void editScenario(int scenarioNumber, double expenses, String taxSetting, double initialCapital, String scenarioName, double capitalGoal) throws SQLException {
		String query = "UPDATE Scenario"
				+ " SET expenses = " + expenses + ", "
				+ " taxSetting = '" + taxSetting + "', " 
				+ " initialCapital = " + initialCapital + ", " 
				+ " scenarioName = '" + scenarioName + "', "
				+ " capitalGoal = " + scenarioName + " "
				+ " WHERE scenarioNumber = " + scenarioNumber;
		this.update(query);
		
	}
	/********************
	 * Function			editScenarioPaymentPerTerm
	 * Description		Method to edit the payment per term of a scenario.
	 * Parameters 		int scenarioNumber, double paymentPerTerm
	 * Returns			
	 ********************/
	public void editScenarioPaymentPerTerm(int scenarioNumber, double paymentPerTerm) throws SQLException {
		String query = "UPDATE Scenario "
				+ "SET paymentPerTerm = " + paymentPerTerm + " " 
				+ "WHERE scenarioNumber = " + scenarioNumber;
		this.update(query);
		
	}
	/********************
	 * Function			editScenarioAmountOfTerms
	 * Description		Method to edit the amount of terms of a scenario.
	 * Parameters 		int scenarioNumber, double amountOfTerms
	 * Returns			
	 ********************/
	public void editScenarioAmountOfTerms(int scenarioNumber, double amountOfTerms) throws SQLException {
		String query = "UPDATE Scenario "
				+ "SET amountOfTerms = " + amountOfTerms + " " 
				+ "WHERE scenarioNumber = " + scenarioNumber;
		this.update(query);
		
	}
	/********************
	 * Function			deleteScenario
	 * Description		Method to delete a scenario.
	 * Parameters 		int scenarioNumber
	 * Returns			
	 * @throws SQLException 
	 ********************/
	public void deleteScenario(int scenarioNumber) throws SQLException {
		String query = "DELETE FROM Scenario WHERE scenarioNumber = " + scenarioNumber;
		this.update(query);
		
	}
	/********************
	 * Function			getScenarios
	 * Description		Method to retrieve all scenarios.
	 * Parameters 		
	 * Returns			ResultSet
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 ********************/
	public ObservableList<Scenario> getScenarios() throws ClassNotFoundException, SQLException {
		String selectSql = "SELECT * FROM Scenario";
		
		ObservableList<Scenario> scenarioList = FXCollections.observableArrayList();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
			ResultSet scenarioSet = preparedStatement.executeQuery();
			while (scenarioSet.next()) {
				String scenarioName = scenarioSet.getString("scenarioName");
				int scenarioNumber = scenarioSet.getInt("scenarioNumber");
				double expenses = scenarioSet.getDouble("expenses");
				double initialCapital = scenarioSet.getDouble("initialCapital");
				double capitalGoal = scenarioSet.getDouble("capitalGoal");
				String taxSetting = scenarioSet.getString("taxSetting");
				double amountOfTerms = scenarioSet.getDouble("amountOfTerms");
				double paymentPerTerm = scenarioSet.getDouble("paymentPerTerm");
				
				Scenario thisRow = new Scenario(scenarioNumber, scenarioName, taxSetting,
						expenses, initialCapital, capitalGoal, amountOfTerms, paymentPerTerm);
				scenarioList.add(thisRow);
			}
		} catch (SQLException e) {
			System.out.println("An exception occured while Selecting records from Table. Exception is :: "	+ e);
		}
		return scenarioList;

	}
	//Here are the methods for asset classes:
	/********************
	 * Function			linkAssetToScenario
	 * Description		Method to link an asset class with a given scenario, but it still needs to
	 * 					check that the sum of allocations never exceeds 1.
	 * Parameters 		String assetClassName, double allocation, boolean isInitial
	 * Returns			
	 * @param scenarioNumber 
	 ********************/
	public void linkAssetToScenario(String assetClassName, int scenarioNumber, double allocation, boolean isInitial) throws SQLException {
		String allocationType = "Expected Asset";
		if (isInitial) {
			allocationType = "Initial Asset";
		}
		String query = "INSERT INTO Allocation(assetClassName, scenarioNumber, allocation, allocationType) " + 
				"VALUES ('" + assetClassName + "', " + scenarioNumber + ", " + allocation + ", '" + allocationType + "');";
		this.update(query);
		
	}
	/********************
	 * Function			addInitialAssetClass
	 * Description		Method to add an expected asset class with a given type, but it still needs to
	 * 					check that the sum of allocations never exceeds 1.
	 * Parameters 		String assetClassName, double rateOfReturn, double allocation
	 * Returns			
	 * @param i 
	 ********************/
	public void addAssetClass(String assetClassName, double rateOfReturn) throws SQLException {
		String query = "INSERT INTO AssetClass (assetClassName, rateOfReturn)";
		query += " VALUES ('" + assetClassName + "', "
		+ rateOfReturn + ")";
		this.update(query);
		
	}
	/********************
	 * Function			editAssetClass
	 * Description		Method to edit an asset class. Missing parts, see the above method.
	 * Parameters 		String assetClassName, String assetClassType, double rateOfReturn, double allocation
	 * Returns			
	 * @throws SQLException 
	 ********************/
	public void editAssetClass(String assetClassName, double rateOfReturn) throws SQLException {
		String query = "UPDATE AssetClass "
				+ "SET rateOfReturn = " + rateOfReturn
				+ " WHERE assetClassName = '" + assetClassName + "'";
		this.update(query);
		
	}
	/********************
	 * Function			editAssetClassName
	 * Description		Method to edit an asset class. Missing parts, see the above method.
	 * Parameters 		String assetClassName, String assetClassType, double rateOfReturn, double allocation
	 * Returns			
	 * @throws SQLException 
	 ********************/
	public void editAssetClassName(String assetClassName, String newAssetClassName) throws SQLException {
		String query = "UPDATE AssetClass "
				+ "SET assetClassName = '" + newAssetClassName
				+ "' WHERE assetClassName = '" + assetClassName + "'";
		this.update(query);
		
	}
	/********************
	 * Function			deleteAssetClass
	 * Description		Method to delete an asset class.
	 * Parameters 		String assetClassName, String assetClassType
	 * Returns			
	 * @throws SQLException 
	 ********************/
	public void deleteAssetClass(String assetClassName) throws SQLException {
		String query = "DELETE FROM AssetClass"
				+ " WHERE assetClassName = '" + assetClassName
				+ "'";
		this.update(query);
		
	}
	
	/********************
	 * Function			getAssetClasses
	 * Description		Method to retrieve all asset classes.
	 * Parameters 		
	 * Returns			ResultSet
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 ********************/
	public ResultSet getAssetClasses() throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM AssetClass";
		ResultSet assetClasses = this.query(query);
		
		return assetClasses;
	}
	
	/********************
	 * Function			getInitialAssetClasses
	 * Description		Method to retrieve all initial asset classes for a scenario.
	 * Parameters 		int scenarioNumber
	 * Returns			ResultSet
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 ********************/
	public ResultSet getInitialAssetClasses(int scenarioNumber) throws ClassNotFoundException, SQLException {
		String query = "SELECT Allocation.assetClassName, Allocation.allocation, AssetClass.rateOfReturn " + 
				"FROM Allocation " + 
				"INNER JOIN AssetClass ON AssetClass.assetClassName = Allocation.assetClassName " + 
				"WHERE Allocation.scenarioNumber = " + scenarioNumber +  
				" AND Allocation.allocationType = 'Initial Asset';";
		ResultSet assetClasses = this.query(query);
		
		return assetClasses;
	}
	
	public ObservableList<AssetClassAllocationWrapper> getInitialAssetClassList(int scenarioNumber){
		String selectSql = "SELECT Allocation.assetClassName, Allocation.allocation, AssetClass.rateOfReturn " + 
				"FROM Allocation " + 
				"INNER JOIN AssetClass ON AssetClass.assetClassName = Allocation.assetClassName " + 
				"WHERE Allocation.scenarioNumber = ? " +  
				" AND Allocation.allocationType = 'Initial Asset';";
		
		ObservableList<AssetClassAllocationWrapper> allocationList = FXCollections.observableArrayList();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
			preparedStatement.setInt(1, scenarioNumber);
			ResultSet initialAssetSet = preparedStatement.executeQuery();
			while (initialAssetSet.next()) {
				String assetClassName = initialAssetSet.getString("assetClassName");
				double rateOfReturn = initialAssetSet.getDouble("rateOfReturn");
				double allocationRate = initialAssetSet.getDouble("allocation");

				AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
				Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.INITIAL_ASSET, scenarioNumber, assetClassName);
				
				AssetClassAllocationWrapper thisRow = new AssetClassAllocationWrapper(assetClass, allocation);
				allocationList.add(thisRow);
			}
		} catch (SQLException e) {
			System.out.println("An exception occured while Selecting records from Table. Exception is :: "	+ e);
		}
		return allocationList;

	}
	
	/********************
	 * Function			getExpectedAssetClasses
	 * Description		Method to retrieve all expected asset classes for a scenario.
	 * Parameters 		int scenarioNumber
	 * Returns			ResultSet
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 ********************/
	
	public ResultSet getExpectedAssetClasses(int scenarioNumber) throws ClassNotFoundException, SQLException {
		String query = "SELECT Allocation.assetClassName, Allocation.allocation, AssetClass.rateOfReturn " + 
				"FROM Allocation " + 
				"INNER JOIN AssetClass ON AssetClass.assetClassName = Allocation.assetClassName " + 
				"WHERE Allocation.scenarioNumber = " + scenarioNumber +  
				" AND Allocation.allocationType = 'Expected Asset';";
		ResultSet assetClasses = this.query(query);
		
		return assetClasses;
	}
	
	public ObservableList<AssetClassAllocationWrapper> getExpectedAssetClassList(int scenarioNumber){
		String selectSql = "SELECT Allocation.assetClassName, Allocation.allocation, AssetClass.rateOfReturn " + 
				"FROM Allocation " + 
				"INNER JOIN AssetClass ON AssetClass.assetClassName = Allocation.assetClassName " + 
				"WHERE Allocation.scenarioNumber = ? " +  
				" AND Allocation.allocationType = 'Expected Asset';";
		
		ObservableList<AssetClassAllocationWrapper> allocationList = FXCollections.observableArrayList();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
			preparedStatement.setInt(1, scenarioNumber);
			ResultSet initialAssetSet = preparedStatement.executeQuery();
			while (initialAssetSet.next()) {
				String assetClassName = initialAssetSet.getString("assetClassName");
				double rateOfReturn = initialAssetSet.getDouble("rateOfReturn");
				double allocationRate = initialAssetSet.getDouble("allocation");

				AssetClass assetClass = new AssetClass(assetClassName, rateOfReturn);
				Allocation allocation = new Allocation(allocationRate, AllocationTypeTypeEnum.EXPECTED_ASSET, scenarioNumber, assetClassName);
				
				AssetClassAllocationWrapper thisRow = new AssetClassAllocationWrapper(assetClass, allocation);
				allocationList.add(thisRow);
			}
		} catch (SQLException e) {
			System.out.println("An exception occured while Selecting records from Table. Exception is :: "	+ e);
		}
		return allocationList;

	}
	
	/********************
	 * Function			updateScenarioDoubleData
	 * Description		Method to update a column in a Scenario row with a double type value.
	 * Parameters 		String column, Double value, int scenarioNumber
	 * Returns			
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 ********************/
	public void updateScenarioDoubleData(String column, Double value, int scenarioNumber) {
		connect();
		try (PreparedStatement stmt = con.prepareStatement("UPDATE Scenario SET "+column+" = ? WHERE scenarioNumber = ? ");)
		{
			stmt.setDouble(1, value);
			stmt.setInt(2, scenarioNumber);
			stmt.execute();
			closeConnection();
		} catch (SQLException ex) {
			System.err.println("Error");
			// if anything goes wrong, you will need the stack trace:
			ex.printStackTrace(System.err);
		}
	}
	public void updateScenarioStringData(String column, String newValue, int scenarioNumber) {
		connect();
		try (
				PreparedStatement stmt = con.prepareStatement("UPDATE Scenario SET "+column+" = ? WHERE scenarioNumber = ? ");
				) {
			stmt.setString(1, newValue);
			stmt.setInt(2, scenarioNumber);
			stmt.execute();
			closeConnection();
		} catch (SQLException ex) {
			System.err.println("Error");
			// if anything goes wrong, you will need the stack trace:
			ex.printStackTrace(System.err);
		}
	}
	
	/********************
	 * Function			updateScenarioDoubleData
	 * Description		Method to update a column in a Scenario row with a double type value.
	 * Parameters 		String column, Double value, int scenarioNumber
	 * Returns			
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 *
	 * UPDATE Allocation
	 * SET allocation = 54
	 * WHERE assetClassName = 'Gold'
	 * AND scenarioNumber = 3
	 * AND allocationType = 'Expected Asset';
	 * 
	 *******************/
	public void editAllocationRate(int scenarioNumber, String assetClassName, String allocationType, double allocation) {
		String sql = "UPDATE Allocation SET allocation = ? " +
				"WHERE assetClassName = ? " + 
				"AND scenarioNumber = ? " + 
				"AND allocationType = ? ";
		connect();
		try (
				PreparedStatement stmt = con.prepareStatement(sql);
				) {
			stmt.setDouble(1, allocation);
			stmt.setString(2, assetClassName);
			stmt.setInt(3, scenarioNumber);
			stmt.setString(4, allocationType);
			stmt.executeUpdate();
			
			closeConnection();
		} catch (SQLException ex) {
			System.err.println("Error");
			// if anything goes wrong, you will need the stack trace:
			ex.printStackTrace(System.err);
		}
	}
	/**
	 * @throws SQLException ******
	 * 
	 */
	public void unlinkAssetFromScenario(String assetClassName, int scenarioNumber, String allocationType) throws SQLException {
		String deleteSQL = "DELETE FROM Allocation " + 
				"WHERE assetClassName = ? " + 
				"AND scenarioNumber = ? " + 
				"AND allocationType = ? ";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(deleteSQL)) {
 
			preparedStatement.setString(1, assetClassName);
			preparedStatement.setInt(2, scenarioNumber);
			preparedStatement.setString(3, allocationType);
 
			int rowCount = preparedStatement.executeUpdate();
 
			System.out
					.println("Record Deleted successfully from database. Row Count returned is :: "
							+ rowCount);
 
		} catch (SQLException e) {
			System.out
					.println("An exception occured while deleting data from database. Exception is :: "
							+ e);
		}
		
	}
	public void clearInitialAllocationsFromScenario(int scenarioNumber) {
		clearAllocationsFromScenario(scenarioNumber, "Initial Asset");
	}
	public void clearExpectedAllocationsFromScenario(int scenarioNumber) {
		clearAllocationsFromScenario(scenarioNumber, "Expected Asset");
	}

	public void clearAllocationsFromScenario(int scenarioNumber, String allocationType) {
		String deleteSQL = "DELETE FROM Allocation " + 
				"WHERE scenarioNumber = ? " + 
				"AND allocationType = ? ";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(deleteSQL)) {
 
			preparedStatement.setInt(1, scenarioNumber);
			preparedStatement.setString(2, allocationType);
 
			int rowCount = preparedStatement.executeUpdate();
 
			System.out
					.println("Record Deleted successfully from database. Row Count returned is :: "
							+ rowCount);
 
		} catch (SQLException e) {
			System.out
					.println("An exception occured while deleting data from database. Exception is :: "
							+ e);
		}
	}
	public double findRateOfReturnForAsset(String assetClassName) {
			 
			String selectSql = "SELECT rateOfReturn FROM AssetClass WHERE assetClassName = ? ";
			double rateOfReturn = 0;
	 
			try (Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
				preparedStatement.setString(1, assetClassName);
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					rateOfReturn = rs.getDouble("rateOfReturn");
				}
			} catch (SQLException e) {
				System.out.println("An exception occured while Selecting records from Table. Exception is :: "	+ e);
			}
			return rateOfReturn;
	}
	public AssetClass getAssetClass(String assetClassName) {
		String selectSql = "SELECT * FROM AssetClass WHERE assetClassName = ? ";
		AssetClass assetClass = null;
 
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
			preparedStatement.setString(1, assetClassName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				double rateOfReturn = rs.getDouble("rateOfReturn");
				
				assetClass = new AssetClass(assetClassName, rateOfReturn);
			}
		} catch (SQLException e) {
			System.out.println("An exception occured while Selecting records from Table. Exception is :: "	+ e);
		}
		return assetClass;
	}
	
	

	
}
