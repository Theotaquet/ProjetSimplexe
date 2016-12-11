package modele;

import java.util.*;
/**
 * La classe Matrice d�finit l'objet qu'on va utiliser dans la Classe Interaction.
 * Elle est caract�ris�e par une liste de listes de nombres. 
 * @author Nicolas Verhaeghe
 * @author Th�o Constant
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
	 * @param ligne La ligne � rajouter.
	 */
	public void ajouterLigne(List<Double> ligne) {
		this.mat.add(ligne);
	}
	/**
	 * Renvoie le tableau.
	 * @return Le tableau sous forme de cha�ne de caract�res.
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
