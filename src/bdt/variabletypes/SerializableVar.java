package bdt.variabletypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import bdt.BinOps;
import bdt.Variable;

public class SerializableVar implements Variable {

	private Serializable value;
	
	public SerializableVar(Serializable valueIn) {
		value = valueIn;
	}
	
	/*
	 * are in [(int) size, serializable] format
	 */
	public SerializableVar(InputStream stream) {
		try {
			byte[] sizeBytes = new byte[4];
			stream.read(sizeBytes);
			int size = BinOps.bytesToInt(sizeBytes);
			
			byte[] body = new byte[size];
			stream.read(body);
			value = BinOps.bytesToSerializable(body);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException();
		}
	}
	
	public Serializable getValue() {
		return value;
	}
	
	public void setValue(Serializable valueIn) {
		value = valueIn;
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			byte[] body = BinOps.serializableToBytes(value);
			stream.write(BinOps.intToBytes(body.length));
			stream.write(body);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return "serializable(" + value.toString() + ")";
	}
	
}
