package model;

import dataAccessLayer.DataAccessLayer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AssetClass {
	private StringProperty assetClassName;
	private DoubleProperty rateOfReturn;
	private static DataAccessLayer data = new DataAccessLayer();
	public DataAccessLayer getData() {
		return data;
	}
	public void setData(DataAccessLayer data) {
		AssetClass.data = data;
	}
	public final StringProperty assetClassNameProperty() {
		return this.assetClassName;
	}
	public AssetClass(String assetClassName, double rateOfReturn) {
		this.assetClassName = new SimpleStringProperty(assetClassName);
		this.rateOfReturn = new SimpleDoubleProperty(rateOfReturn);
	}
	public AssetClass(String assetClassName) {
		this.assetClassName = new SimpleStringProperty(assetClassName);
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
	public String toString() {
		return this.getAssetClassName();
	}
	public AssetClass fromString(String string) {
		
		return data.getAssetClass(string);
	}

	
}
