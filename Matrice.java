package modele;

import java.util.List;
import java.util.ArrayList;

public class Matrice {

	private List<List<Double>> mat;
	
	public Matrice() {
		this.mat = new ArrayList<List<Double>>();
	}
	
	public List<List<Double>> getMat() {
		return this.mat;
	}
	
	public void ajouterLigne(List<Double> ligne) {
		this.mat.add(ligne);
	}
	
	public String toString() {
		String tab = "";
		
		for(List<Double> ligne : this.mat) {
			for(Double element : ligne) {
				tab += element + "\t";
			}
			tab += "\n";
		}
		return tab;
  }
}
