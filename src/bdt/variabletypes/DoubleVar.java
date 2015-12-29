package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class DoubleVar implements Variable {

	private double value;
	
	public DoubleVar(double valueIn) {
		value = valueIn;
	}
	
	public DoubleVar(InputStream stream) {
		byte[] bytes = new byte[8];
		try {
			stream.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		value = BinOps.bytesToDouble(bytes);
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeBytes(OutputStream stream) {
		try {
			stream.write(BinOps.doubleToBytes(value));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return Double.toString(value);
	}
	
}
