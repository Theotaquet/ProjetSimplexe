package modele;

import java.util.List;
import java.util.ArrayList;

public class Matrice {

	private List<ArrayList<Double>> donnees;
	
	public Matrice() {
		donnees = new ArrayList<ArrayList<Double>>();
	}
	
	public List<ArrayList<Double>> getDonnees() {
		return donnees;
	}
	
	public void remplirTableau(List<ArrayList<Double>> newdonnees) {
		this.donnees = newdonnees;
	}
	
	public void ajouterLigne(ArrayList<Double> ligne) {
		this.donnees.add(ligne);
	}

}
