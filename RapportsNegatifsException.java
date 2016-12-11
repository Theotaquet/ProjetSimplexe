package Exception;

public class RapportsNegatifsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String getMessage() {
		return "Tous les rapports permettant de trouver la ligne du pivot sont négatifs. Il n'y a donc pas de solution possible pour ce problème.";
	}
}
