package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class IntegerVar implements Variable {

	private int value;
	
	public IntegerVar(int valueIn) {
		value = valueIn;
	}
	
	public IntegerVar(InputStream stream) {
		byte[] bytes = new byte[4];
		try {
			stream.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		value = BinOps.bytesToInt(bytes);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			stream.write(BinOps.intToBytes(value));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return Integer.toString(value);
	}
	
}
