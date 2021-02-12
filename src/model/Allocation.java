package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Allocation {
	private DoubleProperty allocation;
	private StringProperty allocationType;
	private IntegerProperty scenarioNumber;
	private StringProperty assetClassName;
	public enum AllocationTypeTypeEnum {
		INITIAL_ASSET, EXPECTED_ASSET
	}
	public Allocation(double allocation, AllocationTypeTypeEnum allocationType,
			int scenarioNumber, String assetClassName) {
		this.assetClassName = new SimpleStringProperty(assetClassName);
		this.allocation = new SimpleDoubleProperty(allocation);
		this.scenarioNumber = new SimpleIntegerProperty(scenarioNumber);
		if (allocationType.equals(AllocationTypeTypeEnum.INITIAL_ASSET)){
			this.allocationType = new SimpleStringProperty("Initial Asset");
		} else {
			this.allocationType = new SimpleStringProperty("Expected Asset");
		}
		

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
	
	public final StringProperty allocationTypeProperty() {
		return this.allocationType;
	}
	
	public final String getAllocationType() {
		return this.allocationTypeProperty().get();
	}
	
	public final void setAllocationType(final String allocationType) {
		this.allocationTypeProperty().set(allocationType);
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

	public final StringProperty assetClassNameProperty() {
		return this.assetClassName;
	}
	

	public final String getAssetClassName() {
		return this.assetClassNameProperty().get();
	}
	

	public final void setAssetClassName(final String assetClassName) {
		this.assetClassNameProperty().set(assetClassName);
	}
	
	
}
