package modele;

import java.util.List;
import java.util.ArrayList;

public class Matrice {

	private List<List<Double>> donnees;
	
	public Matrice() {
		List<List<Double>> donnees = new ArrayList<List<Double>>();
	}
	
	public List<List<Double>> getDonnees() {
		return donnees;
	}
	
	public void remplirTableau(List<List<Double>> newdonnees) {
		this.donnees = newdonnees;
	}
	
	public void ajouterLigne(List<Double> ligne) {
		this.donnees.add(ligne);
	}
}
