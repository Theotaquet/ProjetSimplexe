package modele;

import modele.*;

public class StartSimplexe {

	public static void main(String[] args) {
		
		Interaction inter1 = new Interaction();
		inter1.demanderInfos();
		//inter1.remplirTableauInitial();
		
		Simplexe simp1 = new Simplexe(inter1.getMatrice());
		while(!simp1.isSolution()) {
			simp1.rechercherPivot();
			simp1.rendrePivotUnitaire();
			simp1.actualiserMatrice();
		}
		
		//comment lui envoyer la solution?
		//inter1.afficherSolution();
		//System.out.println(simp1.afficherSolution());
	}
}
