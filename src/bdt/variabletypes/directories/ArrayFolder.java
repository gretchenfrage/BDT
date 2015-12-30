package bdt.variabletypes.directories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import bdt.BinOps;
import bdt.TypeRegistry;
import bdt.Variable;

public class ArrayFolder implements Variable {
	
	private final byte typeID;
	private List<Variable> contents = new ArrayList<Variable>();
	
	public ArrayFolder(byte typeIDIn) {
		typeID = typeIDIn;
	}
	
	/*
	 * stored as [(byte) typeID, (int) body length, [content] ... ]
	 */
	public ArrayFolder(InputStream stream) {
		try {
			//header
			typeID = (byte) stream.read();
			byte[] bodyLengthBytes = new byte[4];
			stream.read(bodyLengthBytes);
			int bodyLength = BinOps.bytesToInt(bodyLengthBytes);
			//body
			byte[] body = new byte[bodyLength];
			stream.read(body);
			InputStream bodyStream = new ByteArrayInputStream(body);
			while (bodyStream.available() > 0) {
				contents.add(TypeRegistry.construct(typeID, bodyStream));
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public void add(Variable value) {
		if (TypeRegistry.getTypeID(value) == typeID) {
			contents.add(value);
		} else {
			throw new RuntimeException();
		}
	}
	
	public void remove(Variable value) {
		contents.remove(value);
	}
	
	public Variable get(int index) throws ArrayIndexOutOfBoundsException {
		return contents.get(index);
	}
	
	public List<Variable> getAll() {
		return contents;
	}
	
	public void remove(int index) throws ArrayIndexOutOfBoundsException {
		contents.remove(index);
	}
	
	public int size() {
		return contents.size();
	}
	
	public byte getType() {
		return typeID;
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			for (Variable variable : contents) {
				variable.writeTo(buffer);
			}
			byte[] body = buffer.toByteArray();
			stream.write(typeID);
			stream.write(BinOps.intToBytes(body.length));
			stream.write(body);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public String toString() {
		String out = "FixedType[";
		for (int i = 0; i < contents.size(); i++) {
			out += contents.get(i).toString();
			if (i < contents.size() - 1) {
				out += ", ";
			}
		}
		out += "]";
		return out;
	}

}
