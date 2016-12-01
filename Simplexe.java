package modele;

public class Simplexe {
		
	public static void calculerSolution(Matrice matrice) {
		int pivotLigne, pivotCol;
		while(!isSolution(matrice)) {
				pivotCol = rechercherPivotCol(matrice);
				pivotLigne = rechercherPivotLigne(matrice, pivotCol);
				rendrePivotUnitaire(matrice, pivotLigne, pivotCol);
				actualiserMatrice(matrice);
				afficherMatrice(matrice);
		}
	}	
	
	public static int rechercherPivotCol(Matrice matrice) {
		double max = 0.;
		int maxCol = 0;
		for(int i=0;i<matrice.getDonnees().get(0).size()-2;i++) {
			if(matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i)>max) {
				max = matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i);
				maxCol = matrice.getDonnees().indexOf(matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i)); //simplifier et peut etre moyen de ne le faire qu'une seule fois en dehors de la boucle
			}
		}
		return maxCol;
	}
	
	public static int rechercherPivotLigne(Matrice matrice, int pivotCol) {
		double min = Double.MAX_VALUE;
		int minLigne = 0;
		for(int i=0;i<matrice.getDonnees().size()-2;i++) {
			if(matrice.getDonnees().get(i).get(matrice.getDonnees().size()-2)/matrice.getDonnees().get(i).get(pivotCol)<min) {
				min = matrice.getDonnees().get(i).get(matrice.getDonnees().size()-2)/matrice.getDonnees().get(i).get(pivotCol);
				minLigne = matrice.getDonnees().indexOf(matrice.getDonnees().get(i)); //idem que rechercherPivotCol
			}
		}
		return minLigne;
	}
	
	public static void rendrePivotUnitaire(Matrice matrice, int pivotLigne, int pivotCol) {
		double pivotVal = matrice.getDonnees().get(pivotLigne).get(pivotCol);
		for(int i=0;i<matrice.getDonnees().get(0).size()-1;i++) {
			//setter de la liste correspondant à la ligne du pivot, avec en argument l'indice de l'élément visé, et l'élément divisé par le pivot
			matrice.getDonnees().get(pivotLigne).set(i, matrice.getDonnees().get(pivotLigne).get(i) / pivotVal);
		}
	}
	
	public static void actualiserMatrice(Matrice matrice) {
		
	}
	
	public static boolean isSolution(Matrice matrice) {
		for(int i=0;i<matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i);i++){
			if(matrice.getDonnees().get(matrice.getDonnees().size()-1).get(i)>0)
				return false;
		}
		return true;
	}
	
	/*public String afficherSolution() {
		String str = "";
		boucle pour traiter chaque inconnue
		for(int i=0;i<matrice.getDonnees().get(0).size()-(matrice.getDonnees().size()-1)-1;i++)
		str += "La valeur de l'inconnue " + i + " doit valoir " + matrice.getDonnees().get
	}*/
	
	public static String afficherMatrice(Matrice matrice) {
		return null;
	}
}
