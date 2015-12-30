package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class ShortVar implements Variable {

	private short value;
	
	public ShortVar(short valueIn) {
		value = valueIn;
	}
	
	public ShortVar(InputStream stream) {
		byte[] bytes = new byte[2];
		try {
			stream.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		value = BinOps.bytesToShort(bytes);
	}
	
	public short getValue() {
		return value;
	}
	
	public void setValue(short valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			stream.write(BinOps.shortToBytes(value));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return value + "s";
	}
	
}
