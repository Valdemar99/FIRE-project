package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AssetClass {
	private StringProperty assetClassName;
	private DoubleProperty rateOfReturn;
	private DoubleProperty allocation;
	private StringProperty assetClassType;
	private IntegerProperty scenarioNumber;
	public enum AssetClassTypeEnum {
		INITIAL_ASSET, EXPECTED_ASSET
	}
	
	
	public AssetClass(String assetClassName, double rateOfReturn, double allocation,
			AssetClassTypeEnum assetClassType, int scenarioNumber) {
		this.assetClassName = new SimpleStringProperty(assetClassName);
		this.rateOfReturn = new SimpleDoubleProperty(rateOfReturn);
		this.allocation = new SimpleDoubleProperty(allocation);
		if (assetClassType.equals(AssetClassTypeEnum.INITIAL_ASSET)) {
			this.assetClassType = new SimpleStringProperty("Initial Asset");
		}
		else {
			this.assetClassType = new SimpleStringProperty("Expected Asset");
		}
		this.scenarioNumber = new SimpleIntegerProperty(scenarioNumber);
	}

	public final StringProperty assetClassNameProperty() {
		return this.assetClassName;
	}
	


	public final String getAssetClassName() {
		return this.assetClassNameProperty().get();
	}
	


	public final void setAssetClassName(final String assetClassName) {
		this.assetClassNameProperty().set(assetClassName);
	}
	


	public final DoubleProperty rateOfReturnProperty() {
		return this.rateOfReturn;
	}
	


	public final double getRateOfReturn() {
		return this.rateOfReturnProperty().get();
	}
	


	public final void setRateOfReturn(final double rateOfReturn) {
		this.rateOfReturnProperty().set(rateOfReturn);
	}
	


	public final DoubleProperty allocationProperty() {
		return this.allocation;
	}
	


	public final double getAllocation() {
		return this.allocationProperty().get();
	}
	


	public final void setAllocation(final double allocation) {
		this.allocationProperty().set(allocation);
	}
	


	public final StringProperty assetClassTypeProperty() {
		return this.assetClassType;
	}
	


	public final String getAssetClassType() {
		return this.assetClassTypeProperty().get();
	}
	


	public final void setAssetClassType(final String assetClassType) {
		this.assetClassTypeProperty().set(assetClassType);
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
	
}
