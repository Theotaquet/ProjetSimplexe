package modele;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Interaction {
	
	private Matrice matrice;
	
	public Interaction() {
		matrice = new Matrice();
	}
	
	//demande des données à l'utilisateur
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
		
		//test sur le nombre d'inéquations
		while(ineqNb<1) {
			System.out.print("Entrez le nombre d'inéquations: ");
			try {
				ineqNb = scan.nextInt();
			}
			catch(Exception e) {
				ineqNb = -1;
				scan.next();
			}
			if(ineqNb<1)
				System.out.println("Nombre d'inéquations incorrect.");
		}
		
		System.out.println("Entrez les " + incNb + " coefficients des variables suivis du terme indépendant des contraintes, séparés par des virgules.");
		System.out.println("Exemple: 2,7,-3,18");
		//liste temporaire à laquelle on ajoute les inéquations une à une
		List<Double> listeCoeff = new ArrayList<Double>();
		
		//ajout des coefficients pour chaque inéquation
		for(int i=0;i<ineqNb;i++) {
			tmpCoeff="";
			listeCoeff.clear();
			while(tmpCoeff.split(",").length!=incNb+1) {
				System.out.print("Inéquation n°"+(i+1)+": ");
				tmpCoeff = scan.next();
				if(tmpCoeff.split(",").length!=incNb+1) {
					System.out.println("Le nombre de termes insérés ("+tmpCoeff.split(",").length+") n'est pas égal à celui attendu ("+(incNb+1)+").");
				}
			}
			for(int j=0;j<incNb+1;j++) {
				listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[j]));
			}
			matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		}

		//ajout de la fonction objectif
		listeCoeff.clear();
		System.out.println("Entrez les " + incNb + " coefficients des inconnues de la fonction objectif");
		System.out.println("Exemple: 1,4,3.5");
		tmpCoeff = "";
		
		while(tmpCoeff.split(",").length!=incNb) {
			try {
				System.out.print("Coefficients de la fonction objectif: ");
				tmpCoeff = scan.next();
			}
			catch(Exception e) {
				System.out.println("Fonction objectif entrée incorrectement.");
			}
			if(tmpCoeff.split(",").length!=incNb)
				System.out.println("Le nombre de termes insérés ("+tmpCoeff.split(",").length+") n'est pas égal à celui attendu ("+incNb+").");
		}
		
		for(int i=0;i<incNb;i++) {
			listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[i]));
		}
		
		matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		scan.close();
	}
	
	public Matrice getMatrice() {
		return matrice;
	}
}
