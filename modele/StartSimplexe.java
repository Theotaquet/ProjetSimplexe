package modele;

/**
 * Classe StartSimplexe.
 * Elle gère le démarrage de l'application.
 *
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 */
public class StartSimplexe {
	
	/**
	 * Crée l'objet Interaction et appelle ses méthodes.
	 * @param args
	 */
	public static void main(String[] args) {

		Interaction inter = new Interaction();
		boolean firstLaunch=true;
		while(firstLaunch || inter.isRecommencer()) {
			firstLaunch=false;
			inter.demanderInfos();
			inter.executerSimplexe();
		}
	}
}
