package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//question: doit gérer la création des objets matrice, mais contient un attribut matrice? OUI
//question: stocker en attributs toutes les infos reçues de l'utilisateur pour pouvoir les réutiliser dans Simplexe?
public class Interaction {
	
	private Matrice matrice;
	
	//question: constructeur par défaut?
	
	public void demanderInfos() {
	//Scanner
	//mettre les valeurs passées par l'utilisateur en argument	
	//remplirTableauInitial();
	int nbInc=0, nbIneq=0;
		Scanner scan = new Scanner(System.in);
		String tmpCoeff;
		//Compter inconnues
		while(nbInc < 1) {
			System.out.print("Entrez le nombre d'inconnues: ");
			nbInc = scan.nextInt();
			if(nbInc < 1) {
				System.out.println("Nombre d'inconnues ("+nbInc+") incorrect.");
			}
		}
		//Compter inequations
		while(nbIneq < 1) {
			System.out.print("Entrez le nombre d'inéquations: ");
			nbIneq = scan.nextInt();
			if(nbIneq < 1) {
				System.out.println("Nombre d'inéquations ("+nbIneq+") incorrect.");
			}
		}
		System.out.println("<Info>\tEntrez les "+nbInc+" coefficients des inconnues suivis du terme indépendant des inéquations, séparés par des virgules.");
		System.out.println("\tExemple: 2,7,3,18");
		//Liste temporaire qui ajoute les inéquations une à une
		List<Double> listeCoeff = new ArrayList<Double>();
		
		for(int i=0;i<nbIneq;i++) {
			listeCoeff.clear();
			System.out.print("Fonction contrainte n°"+(i+1)+": ");
			tmpCoeff = scan.next();
			
			for(int j=0;j<nbInc;j++) {
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
			for(Double d : listeCoeff) {
				System.out.println(d);
			}
			matrice.ajouterLigne(listeCoeff);
		}
		
		/*---------OLD---------CODE---------------------*/
		/*List<List<Double>> tmpCoeffList = new ArrayList<>();
		for(int i=0;i<nbIneq;i++) {
			List<>
			//coeffContraintes.add(tmpCoeffList);
			System.out.print("Fonction contrainte n°"+(i+1)+": ");
			tmpCoeff = scan.next();
			
			//tmp = tmpCoeff.split(",");
			for(int j=0;j<nbInc;j++) {
				tmpCoeffList.get(i).add(Double.parseDouble(tmpCoeff.split(",")[j]));
				if(j==nbInc-1) {
					for(int k=0;k<nbIneq;k++) {
						if(i==0)
						tmpCoeffList.get(i).add(e)
					}
				}
			}
		}*/
	}
	
	public void remplirTableauInitial() {
		
	}
	
	public Matrice getMatrice() {
		return matrice;
	}
	
	public void afficherSolOptimale() {
		
	}
}
