package modele;
import modele.*;

public class StartSimplexe {

	public static void main(String[] args) {
		
		Interaction inter1 = new Interaction();
		inter1.demanderInfos();
		
		Simplexe.calculerSolution(inter1.getMatrice());
	}
}
