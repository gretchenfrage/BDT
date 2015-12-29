package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.Variable;

public class BooleanVar implements Variable {

	private boolean value;
	
	public BooleanVar(boolean valueIn) {
		value = valueIn;
	}
	
	public BooleanVar(InputStream stream) {
		try {
			value = stream.read() != 0;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeBytes(OutputStream stream) {
		try {
			stream.write((byte) (value ? 1 : 0));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return "true";
	}

}
