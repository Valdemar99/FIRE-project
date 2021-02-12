package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class AssetClassAllocationWrapper {
	private AssetClass assetClass;
	private Allocation allocation;
	private StringProperty assetClassName;
	private DoubleProperty rateOfReturn;
	private DoubleProperty allocationRate;
	
	
	public AssetClassAllocationWrapper(
			AssetClass assetClass, Allocation allocation) {
		this.assetClassName = assetClass.assetClassNameProperty();
		this.rateOfReturn = assetClass.rateOfReturnProperty();
		this.allocationRate = allocation.allocationProperty();
		this.setAllocation(allocation);
		this.setAssetClass(assetClass);
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

	public final DoubleProperty allocationRateProperty() {
		return this.allocationRate;
	}
	

	public final double getAllocationRate() {
		return this.allocationRateProperty().get();
	}
	

	public final void setAllocationRate(final double allocationRate) {
		this.allocationRateProperty().set(allocationRate);
	}

	public AssetClass getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(AssetClass assetClass) {
		this.assetClass = assetClass;
	}

	public Allocation getAllocation() {
		return allocation;
	}

	public void setAllocation(Allocation allocation) {
		this.allocation = allocation;
	}
	
	

}

