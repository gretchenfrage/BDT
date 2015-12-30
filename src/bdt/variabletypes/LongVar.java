package bdt.variabletypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class LongVar implements Variable {

	private long value;
	
	public LongVar(long valueIn) {
		value = valueIn;
	}
	
	public LongVar(InputStream stream) {
		byte[] bytes = new byte[8];
		try {
			stream.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		value = BinOps.bytesToLong(bytes);
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			stream.write(BinOps.longToBytes(value));
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return value + "l";
	}
	
}
