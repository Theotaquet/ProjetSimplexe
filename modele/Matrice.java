package modele;
import java.util.*;

/**
 * La classe Matrice définit l'objet qu'on va utiliser dans la classe Interaction.
 * Elle est caractérisée par une liste de listes de nombres, qui constitue réellement la matrice.
 *
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 */
public class Matrice {
	
	/**
	 * La matrice à deux dimensions, sous forme de listes.
	 */
	private List<List<Double>> mat;
	
	/**
	 * Constructeur Matrice.
	 * Il instancie la liste de listes.
	 */
	public Matrice() {
		this.mat = new ArrayList<List<Double>>();
	}
	
	/**
	 * Renvoie la matrice.
	 * @return La matrice en deux dimensions.
	 */
	public List<List<Double>> getMat() {
		return this.mat;
	}
	
	/**
	 * Ajoute une ligne à la matrice.
	 * @param ligne La ligne à rajouter.
	 */
	public void ajouterLigne(List<Double> ligne) {
		this.mat.add(ligne);
	}
	
	/**
	 * Renvoie l'affichage de la matrice.
	 * @return La matrice sous forme de chaîne de caractères.
	 */
	@Override
	public String toString() {
		String tab = "";
		
		for(List<Double> ligne : this.mat) {
			boolean debutLigne = true;
			
			for(Double element : ligne) {
				if(debutLigne) {
					tab+="       ";
					debutLigne=false;
				}
				String newElement = String.format("%8.3f  ", element);
				newElement = newElement.replace(",", ".");
				tab += newElement + " ";
			}
			tab += "\n";
		}
		
		return tab;
  	}
}
