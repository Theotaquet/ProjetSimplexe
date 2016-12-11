package modele;

import java.util.*;
/**
 * La classe Matrice définit l'objet qu'on va utiliser dans la Classe Interaction.
 * Elle est caractérisée par une liste de listes de nombres. 
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 *
 */
public class Matrice {
	/**
	 * le tableau en deux dimensions.
	 */
	private List<List<Double>> mat;
	/**
	 * Constructeur Matrice, il instancie la liste des listes.
	 */
	public Matrice() {
		this.mat = new ArrayList<List<Double>>();
	}
	/**
	 * Getter de l'attribut mat
	 * @return Le tableau en deux dimensions
	 */
	public List<List<Double>> getMat() {
		return this.mat;
	}
	/**
	 * Ajoute une ligne au tableau.
	 * @param ligne La ligne à rajouter.
	 */
	public void ajouterLigne(List<Double> ligne) {
		this.mat.add(ligne);
	}
	/**
	 * Renvoie le tableau.
	 * @return Le tableau sous forme de chaîne de caractères.
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
