package bdt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import bdt.variabletypes.directories.ListFolder;
import bdt.variabletypes.directories.ArrayFolder;
import bdt.variabletypes.directories.NameFolder;

/*
 * responsible for storing correlation between type IDs and variable classes, and constructing
 * a variable given its type ID and binary contents
 */
public class TypeRegistry {
	
	public static final byte INTEGER_ID = 0;
	public static final byte LONG_ID = 1;
	public static final byte SHORT_ID = 2;
	public static final byte FLOAT_ID = 3;
	public static final byte BYTE_ID = 4;
	public static final byte BOOLEAN_ID = 5;
	public static final byte DOUBLE_ID = 6;
	public static final byte CHAR_ID = 7;
	public static final byte STRING_ID = 8;
	public static final byte SERIALIZABLE_ID = 9;
	public static final byte NAME_FOLDER_ID = 10;
	public static final byte LIST_FOLDER_ID = 11;
	public static final byte ARRAY_FOLDER_ID = 12;
	
	private static interface Type {
	
		boolean isAnInstance(Variable variable);
		
		Variable constructFrom(InputStream stream);
		
	}
	
	private static Map<Byte, Type> types = new HashMap<Byte, Type>();
	static {
		types.put((byte) INTEGER_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof IntegerVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new IntegerVar(stream);
			}
			
		});
		
		types.put((byte) LONG_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof LongVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new LongVar(stream);
			}
			
		});
		
		types.put((byte) SHORT_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof ShortVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new ShortVar(stream);
			}
			
		});
		
		types.put((byte) FLOAT_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof FloatVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new FloatVar(stream);
			}
			
		});
		
		types.put((byte) BYTE_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof ByteVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new ByteVar(stream);
			}
			
		});
		
		types.put((byte) BOOLEAN_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof BooleanVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new BooleanVar(stream);
			}
			
		});
		
		types.put((byte) DOUBLE_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof DoubleVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new DoubleVar(stream);
			}
			
		});
		
		types.put((byte) CHAR_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof CharVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new CharVar(stream);
			}
			
		});
		
		types.put((byte) STRING_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof StringVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new StringVar(stream);
			}
			
		});

		types.put((byte) SERIALIZABLE_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof SerializableVar;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new SerializableVar(stream);
			}
			
		});
		
		types.put((byte) NAME_FOLDER_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof NameFolder;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new NameFolder(stream);
			}
			
		});
		
		types.put((byte) LIST_FOLDER_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof ListFolder;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new ListFolder(stream);
			}
			
		});
		
		types.put((byte) ARRAY_FOLDER_ID, new Type() {
			
			@Override
			public boolean isAnInstance(Variable variable) {
				return variable instanceof ArrayFolder;
			}
			
			@Override
			public Variable constructFrom(InputStream stream) {
				return new ArrayFolder(stream);
			}
			
		});
	}
	
	public static byte getTypeID(Variable variable) {
		for (Map.Entry<Byte, ? extends Type> entry : types.entrySet()) {
			if (entry.getValue().isAnInstance(variable)) {
				return entry.getKey();
			}
		}
		throw new RuntimeException("Type ID not found by object");
	}
	
	public static Variable construct(byte typeID, InputStream stream) {
		Type type = types.get(typeID);
		if (type != null) {
			return types.get(typeID).constructFrom(stream);
		} else {
			throw new RuntimeException("Type object not found by ID");
		}
	}
	
	public static Variable constructWithID(InputStream stream) {
		try {
			byte typeID = (byte) stream.read();
			return construct(typeID, stream);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public static void writeWithID(Variable variable, OutputStream stream) {
		try {
			stream.write(getTypeID(variable));
			variable.writeTo(stream);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
}
