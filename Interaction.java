package modele;

import java.util.*;
/**
 *
 * La classe Interaction permet de g�rer toutes les interactions avec l'utilisateur. 
 * Elle est caract�ris�e par une matrice qu'elle va cr�er sur base des informations fournies par l'utilisateur.
 * @author Nicolas Verhaeghe
 * @author Th�o Constant
 * @author Florian Vangaeveren
 * 
 */
public class Interaction {
	/**
	 * La matrice qu'elle cr�e et qu'elle va remplir.
	 * 
	 */
	private Matrice matrice;
	/**
	 * Constructeur Interaction, il instancie la matrice.
	 * 
	 */
	public Interaction() {
		this.matrice = new Matrice();
	}
	/**
	 * Demande des donn�es � l'utilisateur.
	 */
	public void demanderInfos() {
		int incNb = 0, ineqNb = 0;
		Scanner scan = new Scanner(System.in);
		String tmpCoeff;

		//test sur le nombre d'inconnues
		while(incNb<1) {
			System.out.print("Entrez le nombre d'inconnues: ");
			try {
				incNb = scan.nextInt();
			}
			catch(Exception e) {
				incNb = -1;
				scan.next();
			}
			if(incNb<1)
				System.out.println("Nombre d'inconnues incorrect.");
		}

		//test sur le nombre d'in�quations
		while(ineqNb<1) {
			System.out.print("Entrez le nombre d'in�quations: ");
			try {
				ineqNb = scan.nextInt();
			}
			catch(Exception e) {
				ineqNb = -1;
				scan.next();
			}
			if(ineqNb<1)
				System.out.println("Nombre d'in�quations incorrect.");
		}
		//liste temporaire � laquelle on ajoute les in�quations ainsi que la fonction objectif un � un
		List<Double> listeCoeff = new ArrayList<Double>();

		//ajout de la fonction objectif
		listeCoeff.clear();
		System.out.println("Entrez les " + incNb + " coefficients des inconnues de la fonction objectif");
		System.out.println("Exemple: 1,4,3.5");
		if(incNb==1){
			tmpCoeff = "1,4";
		}else{
			tmpCoeff = "";
		}
		while(tmpCoeff.split(",").length!=incNb) {
			try {
				System.out.print("Coefficients de la fonction objectif: ");
				tmpCoeff = scan.next();
			}
			catch(Exception e) {
				System.out.println("Fonction objectif entr�e incorrectement.");
			}
			if(tmpCoeff.split(",").length!=incNb)
				System.out.println("Le nombre de termes ins�r�s ("+tmpCoeff.split(",").length+") n'est pas �gal � celui attendu ("+incNb+").");
		}

		for(int i=0;i<incNb;i++) {
			listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[i]));
		}
		//Sauvegarde des coefficients de la fonction objectif pour l'ajouter plus tard dans la matrice
		List<Double> objectif = new ArrayList<Double>(listeCoeff);

		System.out.println("Entrez les " + incNb + " coefficients des variables suivis du terme ind�pendant des contraintes, s�par�s par des virgules.");
		System.out.println("Exemple: 2,7,-3,18");

		//ajout des coefficients pour chaque in�quation
		for(int i=0;i<ineqNb;i++) {
			tmpCoeff="";
			listeCoeff.clear();
			while(tmpCoeff.split(",").length!=incNb+1) {
				System.out.print("In�quation n�"+(i+1)+": ");
				tmpCoeff = scan.next();
				if(tmpCoeff.split(",").length!=incNb+1) {
					System.out.println("Le nombre de termes ins�r�s ("+tmpCoeff.split(",").length+") n'est pas �gal � celui attendu ("+(incNb+1)+").");
				}
			}
			for(int j=0;j<incNb+1;j++){
				listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[j]));
			}
			this.matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		}

		//Ajout de la fonction objectif dans la matrice � l'aide de la liste cr��e pr�c�demment.
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
