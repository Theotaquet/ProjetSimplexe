package modele;

import java.util.*;

import exception.RapportsIncorrectsException;
/**
 *
 * La classe Simplexe s'occupe des différents traitements propre à la méthode du Simplexe.
 * La classe ne comporte que des méthodes statiques, dont calculerSolution qui appellent toutes les autres méthodes.
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 * 
 */

public class Simplexe {

	/**
	 * Appelle toutes les autres méthodes et gère la boucle correspondant aux itérations du Simplexe.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @return les différentes itérations avec leur solution de base, ainsi que la solution optimale.
	 */

	public static String calculerSolution(Matrice matrice) {
		String res = "---------------------------------------------------------------------------------\nRESOLUTION DU SIMPLEXE\n";
		res+="---------------------------------------------------------------------------------\nMax Z = ";
		for(int i=0;i<matrice.getMat().get(matrice.getMat().size()-1).size();i++) {
			res+=matrice.getMat().get(matrice.getMat().size()-1).get(i)+" x"+(i+1)+" ";
		}
		res+="\n";
		for(int i=0;i<matrice.getMat().size()-1;i++) {
			for(int j=0;j<matrice.getMat().get(i).size();j++) {
				if(j==matrice.getMat().get(i).size()-1) {
					res+="<= "+matrice.getMat().get(i).get(j)+"\n";
				}
				else {
					res+=matrice.getMat().get(i).get(j)+" x"+(i+1)+" ";
				}
			}
		}
		res+="\n---------------------------------------------------------------------------------\n";
		res+="Tableau Initial\n---------------------------------------------------------------------------------\n";
		List<Double> solBase = new ArrayList<Double>();

		ajouterMatriceIdentite(matrice);

		for(int i=0;i<matrice.getMat().get(0).size();i++) {
			solBase.add(0.);
		}

		//boucle correspondant aux itérations du Simplexe
		res += matrice.toString();
		res += genererSolBase(matrice, solBase);
		boolean erreurSol = false;
		int nbIterations = 0;
		while(!erreurSol && !isSolution(matrice)) {
			nbIterations++;
			int pivotCol = rechercherPivotCol(matrice);
			try {
				int pivotLigne = rechercherPivotLigne(matrice, pivotCol);
				rendrePivotUnitaire(matrice, pivotLigne, pivotCol);
				actualiserMatrice(matrice, pivotLigne, pivotCol);
				res += "Iteration n°"+nbIterations+"\n---------------------------------------------------------------------------------\n";
				res += matrice.toString();
				res += genererSolBase(matrice, solBase);
			}
			catch(RapportsIncorrectsException e) {
				res += e.getMessage();
				erreurSol=true;
			}
		}
		res+="\nRésultat trouvé en "+nbIterations+" itération(s).\n";
		if(!erreurSol) {
			res += genererSolOpti(matrice, solBase);
		}

		return res;
	}
	/**
	 * Ajoute la matrice identité dans la matrice du Simplexe.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 */
	public static void ajouterMatriceIdentite(Matrice matrice) {
		//boucle sur les lignes correspondant aux contraintes
		for(int i=0;i<matrice.getMat().size()-1;i++) {
			List<Double> ligne = matrice.getMat().get(i);
			for(int j=0;j<matrice.getMat().size()-1;j++) {
				if(j==i)
					ligne.add(ligne.size()-1, 1.);
				else
					ligne.add(ligne.size()-1, 0.);
			}
		}
		//adaptation de la ligne de la fonction objectif
		List<Double> ligne = matrice.getMat().get(matrice.getMat().size()-1);
		for(int i=0;i<matrice.getMat().size();i++) {
			ligne.add(0.);
		}
	}
	/**
	 * Recherche la colonne du pivot
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @return L'indice de la colonne du pivot.
	 */
	public static int rechercherPivotCol(Matrice matrice) {
		double max = 0.;
		List<Double> derniereLigneListe = matrice.getMat().get(matrice.getMat().size() - 1);

		//boucle sur les éléments de la dernière ligne de la matrice, sauf la colonne du terme indépendant	
		for(double element : derniereLigneListe.subList(0, derniereLigneListe.size() - 1)) {
			if(element>max)
				max = element;
		}

		return derniereLigneListe.indexOf(max);
	} 
	/**
	 * Recherche la ligne du pivot.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param pivotCol L'indice de la colonne du pivot.
	 * @return L'indice de la ligne du pivot.
	 * @throws RapportsIncorrectsException Lors d'une division par 0 mais aussi lorsque qu'un des deux termes est négatif.  
	 */
	public static int rechercherPivotLigne(Matrice matrice, int pivotCol) throws RapportsIncorrectsException {
		double min = Double.MAX_VALUE;
		int minLigne = -1;
		int derniereCol = matrice.getMat().get(0).size() - 1;

		//boucle sur les lignes de la matrice, sauf celle de la fonction objectif
		for(List<Double> ligne : matrice.getMat().subList(0, matrice.getMat().size() - 1)) {
			if(ligne.get(pivotCol)>0 && ligne.get(derniereCol)>=0) {
				if(ligne.get(derniereCol)/ligne.get(pivotCol)<min) {
					min = ligne.get(derniereCol) / ligne.get(pivotCol);
					minLigne = matrice.getMat().indexOf(ligne);
				}
			}
		}
		if(minLigne==-1) {
			throw new RapportsIncorrectsException();
		}
		return minLigne;
	}
	/**
	 * Modifie la ligne du pivot en rendant le pivot unitaire.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param pivotLigne L'indice de la ligne du pivot.
	 * @param pivotCol L'indice de la colonne du pivot.
	 */
	public static void rendrePivotUnitaire(Matrice matrice, int pivotLigne, int pivotCol) {
		double pivotVal = matrice.getMat().get(pivotLigne).get(pivotCol);
		List<Double> pivotLigneListe = matrice.getMat().get(pivotLigne);

		//boucle sur les éléments de la ligne du pivot
		for(int i=0;i<pivotLigneListe.size();i++) {
			pivotLigneListe.set(i, pivotLigneListe.get(i) / pivotVal);
		}
	}
	/**
	 * Modifie les autres lignes en fonction de la ligne du pivot
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param pivotLigne L'indice de la ligne du pivot.
	 * @param pivotCol L'indice de la colonne du pivot.
	 */
	public static void actualiserMatrice(Matrice matrice, int pivotLigne, int pivotCol) {
		//boucle sur les autres lignes de la matrice
		for(List<Double> ligne : matrice.getMat()) {
			if(matrice.getMat().indexOf(ligne)!=pivotLigne) {
				double facteur = ligne.get(pivotCol);
				//boucle sur les éléments de la ligne actuelle
				for(int i=0;i<ligne.size();i++) {
					//setter de l'élément actuel, avec en argument son indice et la valeur calculée en fonction du facteur
					ligne.set(i, ligne.get(i) - facteur * matrice.getMat().get(pivotLigne).get(i));
				}
			}
		}
	}
	/**
	 * Crée et concatène une chaîne de caractéres qui contiendra la solution de base.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param solBase Liste instanciée dans calculerSolution qui contiendra la solution de base.
	 * @return La solution de base.
	 */
	public static String genererSolBase(Matrice matrice, List<Double> solBase) {
		String sol = "";
		int ligne1 = 0;
		int derniereCol = matrice.getMat().get(0).size() - 1;

		//boucle sur les colonnes de la matrice, qui correspondent aux variables du problème
		for(int i=0;i<derniereCol;i++) {
			boolean varBase = true;
			boolean presence1 = false;
			//boucle sur les éléments de la colonne actuelle tant qu'il n'y a que des 0 et un seul 1
			int j = 0;
			while(varBase && j<matrice.getMat().size()-1) {
				if( (matrice.getMat().get(j).get(i)!=0&&matrice.getMat().get(j).get(i)!=1) || (matrice.getMat().get(j).get(i)==1&&presence1) )
					varBase = false;
				//test sur la présence de 1
				else if(matrice.getMat().get(j).get(i)==1) {
					presence1 = true;
					ligne1 = j;
				}
				j++;
			}

			if(varBase)
				//attribution du terme indépendant correspondant
				solBase.set(i, matrice.getMat().get(ligne1).get(derniereCol));
			else
				solBase.set(i, 0.);
		}
		//attribution du Z
		solBase.set(derniereCol, 0 - (matrice.getMat().get(matrice.getMat().size()-1).get(derniereCol)));

		//génération de la chaîne
		sol += "\nSB = { ";
		for(int i=0;i<solBase.size()-1;i++) {
			String newElement = String.format("%8.3f", solBase.get(i));
			newElement = newElement.replace(",", ".");
			if(i==solBase.size()-2)
				sol += newElement + " } \n";
			else
				sol += newElement + " ; ";
		}
		sol += "Z = " + solBase.get(derniereCol) + "\n---------------------------------------------------------------------------------\n";

		return sol;
	}  
	/**
	 * Vérifie si la solution de base est admissible comme solution optimale.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @return Booléen qui valide ou non la solution.
	 */
	public static boolean isSolution(Matrice matrice) {
		//boucle sur les éléments de la ligne de la fonction objectif
		for(double element : matrice.getMat().get(matrice.getMat().size() - 1)) {
			if(element>0)
				return false;
		}	
		return true;
	}
	/**
	 * Crée et concatène une chaîne de caractéres qui contiendra la solution optimale.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param solBase Liste qui contient la solution de base de la dernière itération.
	 * @return La solution optimale
	 */
	public static String genererSolOpti(Matrice matrice, List<Double> solBase) {
		String sol = "";

		sol += "Solution optimale:\n"
				+ "La valeur optimale de Z est de " + solBase.get(solBase.size()-1) + "\n";
		//boucle sur chaque variable de la fonction objectif
		for(int i=0;i<matrice.getMat().get(0).size()-(matrice.getMat().size()-1)-1;i++) {
			String newElement = String.format("%8.3f", solBase.get(i));
			newElement = newElement.replace(",", ".");
			sol += "x" + (i+1) + " doit valoir " + newElement + "\n";
		}

		return sol;
	}
}
