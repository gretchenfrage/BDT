package bdt.variabletypes.directories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import bdt.BinOps;
import bdt.TypeRegistry;
import bdt.Variable;

public class ListFolder implements Variable {
	
	private List<Variable> contents = new ArrayList<Variable>();
	
	public ListFolder() {}
	
	/*
	 * stored as [(int) body length, [(byte) type ID, content] ... ]
	 */
	public ListFolder(InputStream stream) {
		try {
			//header
			byte[] bodyLengthBytes = new byte[4];
			stream.read(bodyLengthBytes);
			int bodyLength = BinOps.bytesToInt(bodyLengthBytes);
			//body
			byte[] body = new byte[bodyLength];
			stream.read(body);
			InputStream bodyStream = new ByteArrayInputStream(body);
			while (bodyStream.available() > 0) {
				contents.add(TypeRegistry.constructWithID(bodyStream));
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public void add(Variable value) {
		contents.add(value);
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
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			for (Variable variable : contents) {
				TypeRegistry.writeWithID(variable, buffer);
			}
			byte[] body = buffer.toByteArray();
			stream.write(BinOps.intToBytes(body.length));
			stream.write(body);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public String toString() {
		String out = "[";
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
