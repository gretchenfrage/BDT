package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class CharVar implements Variable {

	private char value;
	
	public CharVar(char valueIn) {
		value = valueIn;
	}
	
	public CharVar(InputStream stream) {
		byte[] bytes = new byte[2];
		try {
			stream.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		value = BinOps.bytesToChar(bytes);
	}
	
	public char getValue() {
		return value;
	}
	
	public void setValue(char valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeBytes(OutputStream stream) {
		try {
			stream.write(BinOps.charToBytes(value));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return "'" + value + "'";
	}
	
}
