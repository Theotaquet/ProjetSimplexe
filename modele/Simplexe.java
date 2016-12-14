package modele;
import java.util.*;
import exception.RapportsIncorrectsException;

/**
 * La classe Simplexe s'occupe des différents traitements propres à la méthode du Simplexe.
 * Elle ne comporte que des méthodes statiques.
 * Elle n'a aucun attribut et aucun objet n'est instancié durant le programme.
 *
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 */
public class Simplexe {
	
	/**
	 * Appelle toutes les autres méthodes et gère la boucle correspondant aux itérations du Simplexe.
	 * Elle peut être considérée comme la méthode principale car c'est la seule appelée en dehors de la classe.
	 * Elle repose sur une boucle correspondant aux itérations du Simplexe et c’est elle qui appelle toutes les autres méthodes. 
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @return Les différentes itérations avec leur solution de base, ainsi que la solution optimale, sous forme de chaîne de caractères.
	 */
	public static String calculerSolution(Matrice matrice) {
		//solBase contiendra la solution de base pour chaque itération
		List<Double> solBase = new ArrayList<Double>();
		List<Double> derniereLigneListe = matrice.getMat().get(matrice.getMat().size() - 1);
		
		//la chaîne res sera renvoyée à la fin de la méthode
		String res = "---------------------------------------------------------------------------------\n"
			+ "RESOLUTION DU SIMPLEXE\n"
			+ "---------------------------------------------------------------------------------\n"
			+ "Max Z = ";
		
		for(int i=0;i<derniereLigneListe.size();i++) {
			res += derniereLigneListe.get(i)+ " x" + (i+1) + " ";
		}
		res += "\n";
		for(int i=0;i<matrice.getMat().size()-1;i++) {
			for(int j=0;j<matrice.getMat().get(i).size();j++) {
				if(j==matrice.getMat().get(i).size()-1) {
					res += "<= " + matrice.getMat().get(i).get(j) + "\n";
				}
				else {
					res += matrice.getMat().get(i).get(j) + " x" + (i+1) + " ";
				}
			}
		}
		res += "\n---------------------------------------------------------------------------------\n"
			+ "Tableau Initial\n---------------------------------------------------------------------------------\n";
		
		ajouterMatriceIdentite(matrice);
		
		//solBase est initialisée
		for(int i=0;i<matrice.getMat().get(0).size();i++) {
			solBase.add(0.);
		}
		
		//boucle correspondant aux itérations du Simplexe
		//elle prend fin lorsqu’une erreur est détectée ou que la solution optimale est trouvée
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
				erreurSol = true;
			}
		}
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
		//boucle sur les lignes correspondant aux contraintes et rajoute un 1 ou un 0
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
	 * Recherche la colonne du pivot.
	 * Elle consiste en une boucle qui recherche le maximum dans les éléments de la dernière ligne de la matrice.
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
		
		//indexOf permet de renvoyer d’office le premier élément rencontré, donc ici le plus à gauche (important en cas d’égalité).
		return derniereLigneListe.indexOf(max);
	} 
	
	/**
	 * Recherche la ligne du pivot.
	 * Elle boucle sur les lignes des contraintes et recherche le minimum des rapports entre l’élément de la dernière colonne et celle du pivot.
	 * Elle évite de traiter les lignes qui conduisent à un rapport négatif ou une division par zéro. 
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
		//Si aucune ligne ne convient (si minLigne n’a pas changé de valeur), une exception RapportIncorrectException est lancée
		if(minLigne==-1) {
			throw new RapportsIncorrectsException();
		}
		return minLigne;
	}
	
	/**
	 * Modifie la ligne du pivot en rendant le pivot unitaire.
	 * Elle boucle sur chaque élément de la ligne et le divise par la valeur du pivot.
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
	 * Modifie les autres lignes en fonction de la ligne du pivot.
	 * Elle boucle sur chaque ligne en évitant celle du pivot (l’indice est comparé) et elle modifie les valeurs.
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
	 * Crée et concatène une chaîne de caractères qui contiendra la solution de base de l'itération traitée.
	 * Elle vérifie si chaque variable est une variable de base ou hors base.
	 * VB: remplit la chaîne avec le terme indépendant correspondant.
	 * VHB: remplit avec un zéro.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param solBase Liste instanciée dans calculerSolution qui contiendra la solution de base de l'itération traitée.
	 * @return La solution de base de l'itération traitée.
	 */
	public static String genererSolBase(Matrice matrice, List<Double> solBase) {
		String sol = "";
		int ligne1 = 0;
		int derniereCol = matrice.getMat().get(0).size() - 1;
		
		//boucle sur les colonnes de la matrice, qui correspondent aux variables du problème
		for(int i=0;i<derniereCol;i++) {
			boolean varBase = true;
			boolean presence1 = false;
			//boucle sur les éléments de la colonne actuelle tant que c'est une variable de base potentielle
			int j = 0;
			while(varBase && j<matrice.getMat().size()-1) {
				//conditions pour être une variable de base: colonne composée de 0 et d'un seul 1
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
				//attribution du terme indépendant de la ligne du 1 si variable de base
				solBase.set(i, matrice.getMat().get(ligne1).get(derniereCol));
			else
				//attribution d'un 0 si hors base
				solBase.set(i, 0.);
		}
		//attribution de la valeur du Z (fin de la dernière ligne de la matrice)
		solBase.set(derniereCol, 0 - (matrice.getMat().get(matrice.getMat().size()-1).get(derniereCol)));
		
		//génération de la chaîne en bouclant sur solBase et grâce à String.format()
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
	 * Vérifie si la solution de base de l'itération traitée est admissible comme solution optimale.
	 * Elle boucle sur la ligne de la fonction objectif et vérifie si tous éléments sont négatifs ou nuls.
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
	 * Crée et concatène une chaîne de caractères qui contiendra la solution optimale.
	 * @param matrice La matrice sur laquelle on applique les traitements.
	 * @param solBase Liste qui contient la solution de base de l'itération traitée.
	 * @return La solution optimale.
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
