package serialisation;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * La classe Impression collecte les données générées par les autres classes sous forme de chaînes de caractères dans un fichier
 * spécifié par l'utilisateur. Elle est caractérisée par une classe permettant l'écriture.
 *
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 */
public class Impression {
	
	/**
	 * La variable permettant l'écriture des données dans un fichier.
	 */
	PrintWriter pw;
	
	/**
	 * Constructeur Impression.
	 * Il instancie le PrintWriter avec le nom du fichier qu'il utilisera passé en argument.
	 * @param fichier Le nom du fichier où seront stockées les données.
	 * @throws FileNotFoundException Lorsque le fichier spécifié est introuvable.
	 */
	public Impression(String fichier) throws FileNotFoundException {
		pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fichier)));
	}
	
	/**
	 * Écrit les données passées en argument.
	 * @param donnees Chaîne de caractères écrite dans le fichier.
	 */
	public void ecrireDonnees(String donnees) {
		System.out.println(donnees);
		pw.write(donnees);
		pw.close();
	}
}
