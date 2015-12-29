package bdt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BinOps {
	
	/*
	 * ints always use 4 bytes
	 */
	public static byte[] intToBytes(int n) {
		return ByteBuffer.allocate(4).putInt(n).array();
	}
	
	public static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	/*
	 * longs always use 8 bytes
	 */
	public static byte[] longToBytes(long n) {
		return ByteBuffer.allocate(8).putLong(n).array();
	}
	
	public static long bytesToLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}
	
	/*
	 * shorts always use 2 bytes
	 */
	public static byte[] shortToBytes(short n) {
		return ByteBuffer.allocate(2).putShort(n).array();
	}
	
	public static short bytesToShort(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getShort();
	}
	
	/*
	 * floats always use 4 bytes
	 */
	public static byte[] floatToBytes(float n) {
		return ByteBuffer.allocate(4).putFloat(n).array();
	}
	
	public static float bytesToFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getFloat();
	}
	
	/*
	 * doubles always use 8 bytes
	 */
	public static byte[] doubleToBytes(double n) {
		return ByteBuffer.allocate(8).putDouble(n).array();
	}
	
	public static double bytesToDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	/*
	 * chars always use 2 bytes
	 */
	public static byte[] charToBytes(char n) {
		return ByteBuffer.allocate(2).putChar(n).array();
	}
	
	public static char bytesToChar(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getChar();
	}
	
	/*
	 * strings use a variable number of bytes
	 */
	public static byte[] stringToBytes(String string) {
    	return string.getBytes(StandardCharsets.UTF_8);
    }
    
    public static String bytesToString(byte[] bytes) {
    	return new String(bytes, StandardCharsets.UTF_8);
    }
	
    /*
     * serializables use a variable number of bytes
     */
    public static byte[] serializableToBytes(Serializable serializable) throws IOException {
    	try (
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ObjectOutput out = new ObjectOutputStream(baos);
    			) {
    		out.writeObject(serializable);
    		return baos.toByteArray();
    	}
    }
    
    public static Serializable bytesToSerializable(byte[] bytes) throws IOException, ClassNotFoundException {
    	try (
    			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    			ObjectInput in = new ObjectInputStream(bais);
    			) {
    		return (Serializable) in.readObject();
    	}
    }
    
    public static List<Byte> toList(byte[] bytes) {
    	List<Byte> out = new ArrayList<Byte>();
    	for (byte b : bytes) {
    		out.add(b);
    	}
    	return out;
    }
    
    public static byte[] toArray(List<Byte> bytes) {
    	byte[] out = new byte[bytes.size()];
    	for (int i = 0; i < out.length; i++) {
    		out[i] = bytes.get(i);
    	}
    	return out;
    }
    
    public static byte[] splice(byte[] a, byte[] b) {
    	byte[] out = new byte[a.length + b.length];
    	for (int i = 0; i < a.length; i++) {
    		out[i] = a[i];
    	}
    	for (int i = 0; i < b.length; i++) {
    		out[i + a.length] = b[i];
    	}
    	return out;
    }
	
}
