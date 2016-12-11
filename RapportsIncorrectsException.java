package exception;
/**
 * L'exception RapportsIncorrectesException g�re les erreurs rencontr�e lors d'une division par 0 mais aussi lorsque qu'un des deux termes est n�gatif
 * @author Nicolas Verhaeghe
 * @author Th�o Constant
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
		return "Tous les rapports permettant de trouver la ligne du pivot sont n�gatifs. Il n'y a donc pas de solution possible pour ce probl�me.";
	}
}
