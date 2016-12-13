package modele;
import java.io.FileNotFoundException;
import java.util.*;

import serialisation.Impression;

/**
 * La classe Interaction permet de gérer toutes les interactions avec l'utilisateur. 
 * Elle est caractérisée par une matrice qu'elle va créer sur base des informations fournies par l'utilisateur.
 *
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 */
public class Interaction {
	
	/**
	 * La matrice qu'elle crée et qu'elle remplit.
	 */
	private Matrice matrice;

	/**
	 * La sortie permettant de sauvegarder les résultats d'un problème
	 */
	private Impression sortie;

	
	/**
	 * Le scanner permettant de lire les données entrées par l'utilisateur
	 */
	private Scanner userInput;
	
	
	/**
	 * Constructeur Interaction.
	 * Il instancie la matrice.
	 */
	public Interaction() {
		userInput = new Scanner(System.in);
		boolean erreurs=true;
		this.matrice = new Matrice();
		while(erreurs) {
			try {
				sortie = new Impression(fichierSortie());
				erreurs=false;
			} catch (FileNotFoundException e) {
				System.out.println("Fichier introuvable, veuillez réessayer.");
			}
		}
	}

	/**
	 * Demande des données à l'utilisateur.
	 */
	public void demanderInfos() {
		//On s'assure que la matrice est vide en cas de relancement du programme
		matrice.getMat().clear();
		int varNb = 0, contrNb = 0;
		String tmpCoeff;

		//test sur le nombre de variables
		while(varNb<1) {
			System.out.print("Entrez le nombre de variables: ");
			try {
				varNb = userInput.nextInt();
			}
			catch(InputMismatchException e) {
				userInput.next();
			}
			if(varNb<1)
				System.out.println("Veuillez entrer une valeur numérique supérieure à 0.");

		}

		//test sur le nombre de contraintes
		while(contrNb<1) {
			System.out.print("Entrez le nombre de contraintes: ");
			try {
				contrNb = userInput.nextInt();
			}
			catch(InputMismatchException e) {
				contrNb = -1;
				userInput.next();
			}
			if(contrNb<1)
				System.out.println("Veuillez entrer une valeur numérique supérieure à 0.");
		}
		//liste temporaire à laquelle on ajoute les contraintes ainsi que la fonction objectif un à un
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
				tmpCoeff = userInput.next();
			}
			catch(InputMismatchException e) {
				System.out.println("Fonction objectif entrée incorrectement.");
			}
			if(tmpCoeff.split(",").length!=varNb)
				System.out.println("Le nombre de termes insérés ("+tmpCoeff.split(",").length+") n'est pas égal à celui attendu ("+varNb+").");
		}

		for(int i=0;i<varNb;i++) {
			try {
				listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[i]));
			}
			catch(NumberFormatException e) {
				System.out.print("Le terme à la position "+(i+1)+" n'es pas valide, veuillez le remplacer : ");
				boolean erreur=true;
				while(erreur) {
					try {
						listeCoeff.add(userInput.nextDouble());
						erreur=false;
					}
					catch(InputMismatchException z) {
						System.out.print("Nouvelle valeur incorrecte, veuillez réessayer : ");
						userInput.next();
					}
				}
			}
		}
		//sauvegarde des coefficients de la fonction objectif pour l'ajouter plus tard dans la matrice
		List<Double> objectif = new ArrayList<Double>(listeCoeff);

		System.out.println("Entrez les " + varNb + " coefficients des variables suivis du terme indépendant des contraintes, séparés par des virgules.");
		System.out.println("Exemple: 2,7,-3,18");

		//ajout des coefficients pour chaque contrainte
		for(int i=0;i<contrNb;i++) {
			tmpCoeff="";
			listeCoeff.clear();
			while(tmpCoeff.split(",").length!=varNb+1) {
				System.out.print("Inéquation n°"+(i+1)+": ");
				tmpCoeff = userInput.next();
				if(tmpCoeff.split(",").length!=varNb+1) {
					System.out.println("Le nombre de termes insérés ("+tmpCoeff.split(",").length+") n'est pas égal à celui attendu ("+(varNb+1)+").");
				}
			}
			for(int j=0;j<varNb+1;j++){
				try {
					listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[j]));
				}
				catch(NumberFormatException e) {
					System.out.print("Le terme à la position "+(j+1)+" n'es pas valide, veuillez le remplacer : ");
					boolean erreur=true;
					while(erreur) {
						try {
							listeCoeff.add(userInput.nextDouble());
							erreur=false;
						}
						catch(InputMismatchException z) {
							System.out.print("Nouvelle valeur incorrecte, veuillez réessayer : ");
							userInput.next();
						}
					}
				}
			}
			this.matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		}

		//ajout de la fonction objectif dans la matrice à l'aide de la liste créée précédemment
		this.matrice.ajouterLigne(new ArrayList<Double>(objectif));
	}
	
	/**
	 * Donne un nom au fichier de sortie afin de pouvoir y écrire
	 * @return Le nom du fichier où écrire
	 */
	public String fichierSortie() {
		System.out.print("Nom du fichier de sortie: ");
		return userInput.next();
	}

	/**
	 * Appelle la méthode calculerSolution de la classe Simplexe et affiche la chaîne qu'elle retourne.
	 */
	public void executerSimplexe() {
		sortie.ecrireDonnees(Simplexe.calculerSolution(this.matrice));
	}
	
	/**
	 * Demande à l'utilisateur s'il désire recommencer l'algorithme avec un nouveau problème
	 * @return Le choix de l'utilisateur
	 */
	public boolean isRecommencer() {
		String reponse="";
		while(!reponse.equalsIgnoreCase("o") || !reponse.equalsIgnoreCase("n")) {
			System.out.println("Voulez-vous recommencer avec un autre problème ? (O/N)");
			reponse=userInput.next();
			if(reponse.equalsIgnoreCase("o")) {
				return true;
			}
			else if(reponse.equalsIgnoreCase("n")) {
				return false;
			}
			else {
				System.out.println("Erreur - Veuillez répondre par oui (O) ou non (N).");
			}
		}
		return false;
	}
}
