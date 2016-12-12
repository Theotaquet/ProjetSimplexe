package modele;
import java.util.*;

/**
 * La classe Interaction permet de g�rer toutes les interactions avec l'utilisateur. 
 * Elle est caract�ris�e par une matrice qu'elle va cr�er sur base des informations fournies par l'utilisateur.
 *
 * @author Nicolas Verhaeghe
 * @author Th�o Constant
 * @author Florian Vangaeveren
 */
public class Interaction {

	/**
	 * La matrice qu'elle cr�e et qu'elle remplit.
	 */
	private Matrice matrice;

	/**
	 * Constructeur Interaction.
	 * Il instancie la matrice.
	 */
	public Interaction() {
		this.matrice = new Matrice();
	}

	/**
	 * Demande des donn�es � l'utilisateur.
	 */
	public void demanderInfos() {
		int varNb = 0, contrNb = 0;
		Scanner scan = new Scanner(System.in);
		String tmpCoeff;

		//test sur le nombre de variables
		while(varNb<1) {
			System.out.print("Entrez le nombre de variables: ");
			try {
				varNb = scan.nextInt();
			}
			catch(InputMismatchException e) {
				scan.next();
			}
			if(varNb<1)
				System.out.println("Veuillez entrer une valeur num�rique sup�rieure � 0.");

		}

		//test sur le nombre de contraintes
		while(contrNb<1) {
			System.out.print("Entrez le nombre de contraintes: ");
			try {
				contrNb = scan.nextInt();
			}
			catch(InputMismatchException e) {
				contrNb = -1;
				scan.next();
			}
			if(contrNb<1)
				System.out.println("Veuillez entrer une valeur num�rique sup�rieure � 0.");
		}
		//liste temporaire � laquelle on ajoute les contraintes ainsi que la fonction objectif un � un
		List<Double> listeCoeff = new ArrayList<Double>();

		//ajout de la fonction objectif
		listeCoeff.clear();
		System.out.println("Entrez les " + varNb + " coefficients des variables de la fonction objectif");
		System.out.println("Exemple: 1,4,3.5");
		if(varNb==1)
			tmpCoeff = "1,4";
		else
			tmpCoeff = "";

		while(tmpCoeff.split(",").length!=varNb) {
			try {
				System.out.print("Coefficients de la fonction objectif: ");
				tmpCoeff = scan.next();
			}
			catch(InputMismatchException e) {
				System.out.println("Fonction objectif entr�e incorrectement.");
			}
			if(tmpCoeff.split(",").length!=varNb)
				System.out.println("Le nombre de termes ins�r�s ("+tmpCoeff.split(",").length+") n'est pas �gal � celui attendu ("+varNb+").");
		}

		for(int i=0;i<varNb;i++) {
			try {
				listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[i]));
			}
			catch(NumberFormatException e) {
				System.out.print("Le terme � la position "+(i+1)+" n'es pas valide, veuillez le remplacer : ");
				boolean erreur=true;
				while(erreur) {
					try {
						listeCoeff.add(scan.nextDouble());
						erreur=false;
					}
					catch(InputMismatchException z) {
						System.out.print("Nouvelle valeur incorrecte, veuillez r�essayer : ");
						scan.next();
					}
				}
			}
		}
		//sauvegarde des coefficients de la fonction objectif pour l'ajouter plus tard dans la matrice
		List<Double> objectif = new ArrayList<Double>(listeCoeff);

		System.out.println("Entrez les " + varNb + " coefficients des variables suivis du terme ind�pendant des contraintes, s�par�s par des virgules.");
		System.out.println("Exemple: 2,7,-3,18");

		//ajout des coefficients pour chaque contrainte
		for(int i=0;i<contrNb;i++) {
			tmpCoeff="";
			listeCoeff.clear();
			while(tmpCoeff.split(",").length!=varNb+1) {
				System.out.print("In�quation n�"+(i+1)+": ");
				tmpCoeff = scan.next();
				if(tmpCoeff.split(",").length!=varNb+1) {
					System.out.println("Le nombre de termes ins�r�s ("+tmpCoeff.split(",").length+") n'est pas �gal � celui attendu ("+(varNb+1)+").");
				}
			}
			for(int j=0;j<varNb+1;j++){
				try {
					listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[j]));
				}
				catch(NumberFormatException e) {
					System.out.print("Le terme � la position "+(j+1)+" n'es pas valide, veuillez le remplacer : ");
					boolean erreur=true;
					while(erreur) {
						try {
							listeCoeff.add(scan.nextDouble());
							erreur=false;
						}
						catch(InputMismatchException z) {
							System.out.print("Nouvelle valeur incorrecte, veuillez r�essayer : ");
							scan.next();
						}
					}
				}
			}
			this.matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		}

		//ajout de la fonction objectif dans la matrice � l'aide de la liste cr��e pr�c�demment
		this.matrice.ajouterLigne(new ArrayList<Double>(objectif));
		scan.close();
	}

	/**
	 * Appelle la m�thode calculerSolution de la classe Simplexe et affiche la cha�ne qu'elle retourne.
	 */
	public void executerSimplexe() {
		System.out.println(Simplexe.calculerSolution(this.matrice));
	}
}
