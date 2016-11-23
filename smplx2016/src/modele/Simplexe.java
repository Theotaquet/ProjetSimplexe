package modele;
import java.util.*;

public class Simplexe {
	
	private Matrice matrice;
	private List<Integer> pivot;
	
	//soit on donne la matrice en argument du constructeur, soit on la retire des attributs de Simplexe et on la renseigne à l'appel de chaque méthode
	public Simplexe(Matrice matrice) {
		this.matrice = matrice;
		pivot = new ArrayList<>();
	}
	
	public void rechercherPivot() {
		double max = 0.;
		int maxCol = 0;
		for(int i=0;i<matrice.getDonnees().get(0).size()-2;i++) {
			if(matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i)>max) {
				max = matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i);
				maxCol = matrice.getDonnees().indexOf(matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i));
			}
		}
		pivot.add(1, maxCol);
		
		double min = Double.MAX_VALUE;
		int minLigne = 0;
		for(int i=0;i<matrice.getDonnees().size()-2;i++) {
			if(matrice.getDonnees().get(i).get(matrice.getDonnees().size()-2)/matrice.getDonnees().get(i).get(maxCol)<min) {
				min = matrice.getDonnees().get(i).get(matrice.getDonnees().size()-2)/matrice.getDonnees().get(i).get(maxCol);
				minLigne = matrice.getDonnees().indexOf(matrice.getDonnees().get(i));
			}
		}
		pivot.add(0, minLigne);
	}
	
	public void rendrePivotUnitaire() {
		double pivotVal = matrice.getDonnees().get(pivot.get(0)).get(pivot.get(1));
		for(int i=0;i<matrice.getDonnees().get(0).size()-1;i++) {
			//setter de la liste correspondant à la ligne du pivot, avec en argument l'indice de l'élément visé, et l'élément divisé par le pivot
			matrice.getDonnees().get(pivot.get(0)).set(i, matrice.getDonnees().get(pivot.get(0)).get(i) / pivotVal);
		}
	}
	
	public void actualiserMatrice() {
		
	}
	
	public boolean isSolution() {
		for(int i=0;i<matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i);i++){
			if(matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i)>0)
				return false;
		}
		return true;
	}
	
	public String toString() {
		return null;
	}
	
	/*public String afficherSolution() {
		String str = "";
		boucle pour traiter chaque inconnue
		for(int i=0;i<matrice.getDonnees().get(0).size()-(matrice.getDonnees().size()-1)-1;i++)
		str += "La valeur de l'inconnue " + i + " doit valoir " + matrice.getDonnees().get
	}*/
}
