import java.io.File;

import bdt.BDTFile;
import bdt.TypeRegistry;
import bdt.variabletypes.BooleanVar;
import bdt.variabletypes.ByteVar;
import bdt.variabletypes.LongVar;
import bdt.variabletypes.directories.ArrayFolder;
import bdt.variabletypes.directories.ListFolder;
import bdt.variabletypes.directories.NameFolder;

public class Main {

	public static void main(String[] args) {
		File save = new File("save.bdt");
		ListFolder folder = new ListFolder();
		folder.add(new ByteVar((byte) 5));
		folder.add(new BooleanVar(false));
		folder.add(new LongVar(2394723423432523l));
		
		ArrayFolder array = new ArrayFolder(TypeRegistry.BOOLEAN_ID);
		array.add(new BooleanVar(true));
		array.add(new BooleanVar(false));
		array.add(new BooleanVar(true));
		array.add(new BooleanVar(false));
		array.add(new BooleanVar(true));
		array.add(new BooleanVar(false));
		array.add(new BooleanVar(true));
		array.add(new BooleanVar(false));
		folder.add(array);
		
		System.out.println(folder);
		BDTFile file = new BDTFile(folder, save);
		file.save();
		BDTFile newFile = new BDTFile(save);
		System.out.println(newFile.getVariable());
		
		File save2 = new File("save2.bdt");
		NameFolder folder2 = new NameFolder();
		folder2.put("byte", new ByteVar((byte) 5));
		folder2.put("boolean", new BooleanVar(true));
		folder2.put("long", new LongVar(2342384932847298374l));
		System.out.println(folder2);
		BDTFile file2 = new BDTFile(folder2, save2);
		file2.save();
		BDTFile newFile2 = new BDTFile(save2);
		System.out.println(newFile2.getVariable());
	}
	
}
