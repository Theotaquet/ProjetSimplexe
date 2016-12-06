package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interaction {
	
	private Matrice matrice;

	//Constructeur pour �viter le NullPointerException en initialisant la matrice
	
	public Interaction() {
		matrice = new Matrice();
	}
	
	public void demanderInfos() {
	int nbInc=0, nbIneq=0;
		Scanner scan = new Scanner(System.in);
		String tmpCoeff;
		//Compter inconnues
		while(nbInc < 1) {
			System.out.print("Entrez le nombre d'inconnues: ");
			try {
				nbInc = scan.nextInt();
			}
			catch(Exception e) {
				nbInc=-1;
				scan.next();
			}
			if(nbInc < 1) {
				System.out.println("Nombre d'inconnues incorrect.");
			}
		}
		//Compter inequations
		while(nbIneq < 1) {
			System.out.print("Entrez le nombre d'in�quations: ");
			try {
				nbIneq = scan.nextInt();
			}
			catch(Exception e) {
				nbIneq=-1;
				scan.next();
			}
			if(nbIneq < 1) {
				System.out.println("Nombre d'in�quations incorrect.");
			}
		}
		System.out.println("Entrez les "+nbInc+" coefficients des inconnues suivis du terme ind�pendant des in�quations, s�par�s par des virgules.");
		System.out.println("Exemple: 2,7,-3,18");
		//Liste temporaire � laquelle on ajoute les in�quations une � une
		List<Double> listeCoeff = new ArrayList<Double>();
		
		//On ajoute les coefficients pour chaque in�quation
		for(int i=0;i<nbIneq;i++) {
			tmpCoeff="";
			listeCoeff.clear();
			while(tmpCoeff.split(",").length!=nbInc+1) {
				System.out.print("In�quation n�"+(i+1)+": ");
				tmpCoeff = scan.next();
				if(tmpCoeff.split(",").length!=nbInc+1) {
					System.out.println("Le nombre de termes ins�r�s ("+tmpCoeff.split(",").length+") n'est pas �gal � celui attendu ("+(nbInc+1)+").");
				}
			}
			for(int j=0;j<nbInc+1;j++) {
				listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[j]));
				if(j==nbInc-1) {
					for(int k=0;k<nbIneq;k++) {
						if(k==i) {
							listeCoeff.add(1.);
						}
						else {
							listeCoeff.add(0.);
						}
					}
				}
			}
			matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		}
		//Ajout de la fonction objectif
		listeCoeff.clear();
		System.out.println("Entrez les "+nbInc+" coefficients des inconnues de la fonction objectif");
		System.out.println("Exemple: 1,4,3.5");
		tmpCoeff="";
		while(tmpCoeff.split(",").length!=nbInc) {
			try {
				System.out.print("Coefficients de la fonction objectif: ");
				tmpCoeff = scan.next();
			}
			catch(Exception e) {
				System.out.println("Fonction objectif entr�e incorrectement.");
			}
			if(tmpCoeff.split(",").length!=nbInc) {
				System.out.println("Le nombre de termes ins�r�s ("+tmpCoeff.split(",").length+") n'est pas �gal � celui attendu ("+nbInc+").");
			}
		}
		for(int i=0;i<nbInc;i++) {
			listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[i]));
		}
		for(int i=0;i<nbIneq+1;i++) {
			listeCoeff.add(0.);
		}
		matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		System.out.println(matrice.toString());
		scan.close();
	}
	
	public Matrice getMatrice() {
		return matrice;
	}
	public String toString() {
		return matrice.toString();
	}
}
