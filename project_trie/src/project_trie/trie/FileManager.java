package project_trie.trie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

public class FileManager {
	public static Trie dataBase;
	private static final String FILE_NAME = "dictionar.ser";

	public FileManager() {
		File f = new File(FILE_NAME);
		if (!f.exists()) {
			JOptionPane.showMessageDialog(null, "Dictionary is Empty");
			dataBase = new Trie();
			return;
		} else {
			try {
				dataBase = deserialize();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void saveChanges() {
		try {
			serialize(dataBase, FILE_NAME);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private static  void serialize(Trie words, String fileName)
			throws IOException {
		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(words);
		}
	}

	@SuppressWarnings({ "resource" })
	private Trie deserialize() throws IOException,
			ClassNotFoundException {
		try (FileInputStream fileIn = new FileInputStream(FILE_NAME)) {
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			return(Trie) objIn.readObject();
		}
	}

	public Trie getDictionary() {
		return dataBase;
	}
}
