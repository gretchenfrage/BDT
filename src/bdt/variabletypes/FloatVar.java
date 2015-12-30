package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class FloatVar implements Variable {

	private float value;
	
	public FloatVar(float valueIn) {
		value = valueIn;
	}
	
	public FloatVar(InputStream stream) {
		byte[] bytes = new byte[4];
		try {
			stream.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		value = BinOps.bytesToFloat(bytes);
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			stream.write(BinOps.floatToBytes(value));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return value + "f";
	}
	
}
