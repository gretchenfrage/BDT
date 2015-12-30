package bdt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BDTFile {

	private File file;
	private Variable variable;
	
	/*
	 * constructs the variable from the file
	 */
	public BDTFile(File fileIn) {
		file = fileIn;
		try (InputStream stream = new FileInputStream(file)) {
			variable = TypeRegistry.constructWithID(stream);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public BDTFile(Variable variableIn, File fileIn) {
		variable = variableIn;
		file = fileIn;
	}
	
	public void setFile(File fileIn) {
		file = fileIn;
	}
	
	public void setVariable(Variable variableIn) {
		variable = variableIn;
	}
	
	public File getFile() {
		return file;
	}
	
	public Variable getVariable() {
		return variable;
	}
	
	public void save() {
		try (OutputStream stream = new FileOutputStream(file)) {
			TypeRegistry.writeWithID(variable, stream);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
}
