package modele;
/**
* Classe de d�marrage de l'application.
* @author Nicolas Verhaeghe
* @author Th�o Constant
* @author Florian Vangaeveren
* 
*/
public class StartSimplexe {
	/**
	 * Cr�e l'objet Interaction et appelle ses m�thodes.
	 * @param args
	 */
	public static void main(String[] args) {
			
			Interaction inter = new Interaction();
			inter.demanderInfos();	
			inter.executerSimplexe();
		}

	}


