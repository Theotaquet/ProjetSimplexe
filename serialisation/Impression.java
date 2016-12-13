package serialisation;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Impression {
	
	PrintWriter pw;
	
	public Impression(String fichier) throws FileNotFoundException {
		pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fichier)));
	}
	
	public void ecrireDonnees(String donnees) {
		System.out.println(donnees);
		pw.write(donnees);
		pw.close();
	}
}
