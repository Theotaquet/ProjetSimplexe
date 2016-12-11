package exception;
/**
 * L'exception RapportsIncorrectesException gère les erreurs rencontrée lors d'une division par 0 mais aussi lorsque qu'un des deux termes est négatif
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 *
 */
public class RapportsIncorrectsException extends Exception {

		private static final long serialVersionUID = 1L;
	/**
	 * Renvoie le message de l'exception.
	 */
	@Override
	public String getMessage() {
		return "Tous les rapports permettant de trouver la ligne du pivot sont négatifs. Il n'y a donc pas de solution possible pour ce problème.";
	}
}
