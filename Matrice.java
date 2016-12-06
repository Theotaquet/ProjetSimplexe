package modele;

import java.util.List;
import java.util.ArrayList;
public class Matrice {

	private List<List<Double>> donnees;
	
	public Matrice() {
		donnees = new ArrayList<List<Double>>();
	}
	
	public List<List<Double>> getDonnees() {
		return donnees;
	}
	
	public void ajouterLigne(List<Double> ligne) {
		donnees.add(ligne);
	}
	
	public String toString() {
		String tab="";
		for(List<Double> listeCoeff : donnees) {
			for(Double termeDonnee : listeCoeff) {
				tab+=termeDonnee+"\t";
			}
			tab+="\n";
		}
		return tab;
	}

}
