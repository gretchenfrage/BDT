package bdt.variabletypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bdt.BinOps;
import bdt.Variable;

public class StringVar implements Variable {

	private String value;
	
	public StringVar(String valueIn) {
		value = valueIn;
	}
	
	/*
	 * are in [(int) size, string] format
	 */
	public StringVar(InputStream stream) {
		try {
			byte[] sizeBytes = new byte[4];
			stream.read(sizeBytes);
			int size = BinOps.bytesToInt(sizeBytes);
			
			byte[] body = new byte[size];
			stream.read(body);
			value = BinOps.bytesToString(body);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeBytes(OutputStream stream) {
		try {
			byte[] body = BinOps.stringToBytes(value);
			stream.write(BinOps.intToBytes(body.length));
			stream.write(body);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return '"' + value + '"';
	}
	
}
