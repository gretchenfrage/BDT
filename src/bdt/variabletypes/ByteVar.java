package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.Variable;

public class ByteVar implements Variable {

	private byte value;
	
	public ByteVar(byte valueIn) {
		value = valueIn;
	}
	
	public ByteVar(InputStream stream) {
		try {
			value = (byte) stream.read();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public byte getValue() {
		return value;
	}
	
	public void setValue(byte valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeBytes(OutputStream stream) {
		try {
			stream.write(value);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return value + "b";
	}
	
}
