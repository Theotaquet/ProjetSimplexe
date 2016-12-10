package modele;

public class StartSimplexe {

	public static void main(String[] args) {
		
		Interaction inter = new Interaction();
		inter.demanderInfos();
		
		Simplexe.calculerSolution(inter.getMatrice());
	}
}
