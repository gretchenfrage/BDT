package bdt.variabletypes.directories;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bdt.BinOps;
import bdt.TypeRegistry;
import bdt.Variable;

/*
 * a directory that organizes its contents by assigning them unique string names
 */
public class NameFolder implements Variable {
	
	private Map<String, Variable> contents = new HashMap<String, Variable>();
	
	public NameFolder() {}
	
	/*
	 * stored as [(int) body length, [(short) name length, (string) name, (byte) type ID, content] ... ]
	 */
	public NameFolder(InputStream stream) {
		try {
			//determine body length
			byte[] bodyLengthBytes = new byte[4];
			stream.read(bodyLengthBytes);
			int bodyLength = BinOps.bytesToInt(bodyLengthBytes);
			
			//read body
			byte[] body = new byte[bodyLength];
			stream.read(body);
			ByteArrayInputStream bodyStream = new ByteArrayInputStream(body);
			while (bodyStream.available() > 0) {
				//determine name length
				byte[] nameLengthBytes = new byte[2];
				bodyStream.read(nameLengthBytes);
				short nameLength = BinOps.bytesToShort(nameLengthBytes);
				//determine name
				byte[] nameBytes = new byte[nameLength];
				bodyStream.read(nameBytes);
				String name = BinOps.bytesToString(nameBytes);
				//extract variable
				contents.put(name, TypeRegistry.constructWithID(bodyStream));
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public void put(String name, Variable value) {
		contents.put(name, value);
	}
	
	public Variable get(String name) {
		return contents.get(name);
	}
	
	public void remove(String name) {
		contents.remove(name);
	}
	
	public void remove(Variable value) {
		for (String name : contents.keySet()) {
			if (contents.get(name) == value) {
				contents.remove(name);
			}
		}
	}
	
	public List<String> getNames() {
		return new ArrayList<String>(contents.keySet());
	}
	
	public List<Variable> getVariables() {
		return new ArrayList<Variable>(contents.values());
	}
	
	@Override
	public void writeTo(OutputStream stream) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			for (Entry<String, Variable> entry : contents.entrySet()) {
				byte[] name = BinOps.stringToBytes(entry.getKey());
				byte[] nameLength = BinOps.shortToBytes((short) name.length);
				byte typeID = TypeRegistry.getTypeID(entry.getValue());
				buffer.write(nameLength);
				buffer.write(name);
				TypeRegistry.writeWithID(entry.getValue(), buffer);
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
		List<Entry<String, Variable>> list = new ArrayList<Entry<String, Variable>>(contents.entrySet());
		for (int i = 0; i < list.size(); i++) {
			out += list.get(i).getKey() + " = " + list.get(i).getValue().toString();
			if (i != list.size() - 1) {
				out += ", ";
			}
		}
		out += "]";
		return out;
	}
	
}
