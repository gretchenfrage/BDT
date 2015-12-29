package bdt;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import bdt.variabletypes.BooleanVar;
import bdt.variabletypes.ByteVar;
import bdt.variabletypes.CharVar;
import bdt.variabletypes.DoubleVar;
import bdt.variabletypes.FloatVar;
import bdt.variabletypes.IntegerVar;
import bdt.variabletypes.LongVar;
import bdt.variabletypes.SerializableVar;
import bdt.variabletypes.ShortVar;
import bdt.variabletypes.StringVar;
import bdt.variabletypes.directories.NameFolder;

/*
 * responsible for storing correlation between type IDs and variable classes, and constructing
 * a variable given its type ID and binary contents
 */
public class TypeRegistry {
	

	private static interface Type {
	
		boolean isAnInstance(Variable variable);
		
		Variable constructFrom(InputStream stream);
		
	}
	
	private static Map<Byte, Type> types = new HashMap<Byte, Type>();
	static {
		types.put((byte) 0, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof IntegerVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new IntegerVar(stream);
			}
			
		});
		
		types.put((byte) 1, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof LongVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new LongVar(stream);
			}
			
		});
		
		types.put((byte) 2, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof ShortVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new ShortVar(stream);
			}
			
		});
		
		types.put((byte) 3, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof FloatVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new FloatVar(stream);
			}
			
		});
		
		types.put((byte) 4, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof ByteVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new ByteVar(stream);
			}
			
		});
		
		types.put((byte) 5, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof BooleanVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new BooleanVar(stream);
			}
			
		});
		
		types.put((byte) 6, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof DoubleVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new DoubleVar(stream);
			}
			
		});
		
		types.put((byte) 7, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof CharVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new CharVar(stream);
			}
			
		});
		
		types.put((byte) 8, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof StringVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new StringVar(stream);
			}
			
		});

		types.put((byte) 9, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof SerializableVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new SerializableVar(stream);
			}
			
		});
		
		types.put((byte) 10, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof NameFolder;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new NameFolder(stream);
			}
			
		});
	}
	
	public static byte getTypeID(Variable variable) throws RuntimeException {
		for (Map.Entry<Byte, ? extends Type> entry : types.entrySet()) {
			if (entry.getValue().isAnInstance(variable)) {
				return entry.getKey();
			}
		}
		throw new RuntimeException("Type ID not found by object");
	}
	
	public static Variable construct(byte typeID, InputStream stream) throws RuntimeException {
		Type type = types.get(typeID);
		if (type != null) {
			return types.get(typeID).constructFrom(stream);
		} else {
			throw new RuntimeException("Type object not found by ID");
		}
	}
	
	public static byte[] toBytes(Variable variable) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		variable.writeBytes(stream);
		return stream.toByteArray();
	}
	
}
