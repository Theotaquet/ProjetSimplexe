package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.RapportsIncorrectsException;
import modele.Matrice;
import modele.Simplexe;

public class TestSimplexe {

	private Matrice matId;
	private ArrayList<List<Double>> matIdAl;
	private Matrice matNoId;
	private ArrayList<List<Double>> matNoIdAl;
	private Matrice matVide;
	private ArrayList<List<Double>> matVideAl;
	private Simplexe smplx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		smplx = new Simplexe();
		matId = new Matrice();
		matIdAl = (ArrayList<List<Double>>)Explorateur.getField(matId, "mat");
		matNoId = new Matrice();
		matNoIdAl = (ArrayList<List<Double>>)Explorateur.getField(matNoId, "mat");
		matVide = new Matrice();
		matVideAl = (ArrayList<List<Double>>)Explorateur.getField(matVide, "mat");

		//Ajout de 3 in�quations avec fonction objectif et matrice identit�
		matIdAl.add(new ArrayList<Double>(){{
			add(3.);add(2.);add(4.);add(1.);add(0.);add(0.);add(24.);
		}});
		matIdAl.add(new ArrayList<Double>(){{
			add(4.);add(1.);add(2.);add(0.);add(1.);add(0.);add(24.);
		}});
		matIdAl.add(new ArrayList<Double>(){{
			add(1.);add(1.);add(5.);add(0.);add(0.);add(1.);add(15.);
		}});
		matIdAl.add(new ArrayList<Double>(){{
			add(5.);add(6.);add(7.);add(0.);add(0.);add(0.);add(0.);
		}});

		//Ajout de 3 in�quations avec fonction objectif sans matrice identit� ni 0 suppl�mentaires pour la fonction objectif
		matNoIdAl.add(new ArrayList<Double>(){{
			add(3.);add(2.);add(4.);add(24.);
		}});
		matNoIdAl.add(new ArrayList<Double>(){{
			add(4.);add(1.);add(2.);add(24.);
		}});
		matNoIdAl.add(new ArrayList<Double>(){{
			add(1.);add(1.);add(5.);add(15.);
		}});
		matNoIdAl.add(new ArrayList<Double>(){{
			add(5.);add(6.);add(7.);
		}});
	}

	@Test
	public void testCalculerSolution() {
		//Coefficients fonctionnels
		assertTrue("La valeur optimale a �t� trouv�e.",smplx.calculerSolution(matNoId).contains("La valeur optimale de Z est de 72.0"));
		//Coefficients du pivot tous � 0
		matVideAl.add(new ArrayList<Double>(){{add(3.);add(2.);add(0.);add(24.);}});
		matVideAl.add(new ArrayList<Double>(){{add(4.);add(1.);add(0.);add(24.);}});
		matVideAl.add(new ArrayList<Double>(){{add(1.);add(1.);add(0.);add(15.);}});
		matVideAl.add(new ArrayList<Double>(){{add(5.);add(6.);add(7.);}});
		assertTrue("La solution n'admet pas de solution",smplx.calculerSolution(matVide).contains("pas de solution"));
		matVide.getMat().clear();
		//Rapports n�gatifs
		matVideAl.add(new ArrayList<Double>(){{add(3.);add(2.);add(4.);add(-24.);}});
		matVideAl.add(new ArrayList<Double>(){{add(4.);add(1.);add(-2.);add(24.);}});
		matVideAl.add(new ArrayList<Double>(){{add(1.);add(1.);add(-5.);add(15.);}});
		matVideAl.add(new ArrayList<Double>(){{add(5.);add(6.);add(7.);}});
		assertTrue("La solution n'admet pas de solution",smplx.calculerSolution(matVide).contains("pas de solution"));
	}

	@Test
	public void testAjouterMatriceIdentite() {
		smplx.ajouterMatriceIdentite(matNoId);
		int nbContraintes = matNoIdAl.size()-1;
		int nbVariables = (matNoIdAl.get(0).size()-nbContraintes-1);

		for(int i=0;i<nbContraintes;i++) {
			for(int j=nbVariables;j<nbVariables+nbContraintes;j++) {
				if(j==(i+nbVariables) && matNoIdAl.get(i).get(j)!=1) {
					fail("Il manque au moins un 1 dans la matrice identit�");
				}
				else if(j!=(i+nbVariables) && matNoIdAl.get(i).get(j)!=0) {
					fail("Il manque au moins un 0 dans la matrice identit�");
				}
			}
		}
		for(int i=nbVariables;i<=(nbVariables+nbContraintes);i++) {
			if(matNoIdAl.get(matNoIdAl.size()-1).get(i)!=0) {
				fail("Les 0 n�cessaires dans la fonction objectif n'ont pas tous �t� ajout�s");
			}
		}
	}

	@Test
	public void testRechercherPivotCol() {
		assertTrue("La colonne du pivot a �t� trouv�e correctement",smplx.rechercherPivotCol(matId)==2);
		//Toujours prendre l'�lem. le plus � gauche en cas de doublon
		matIdAl.get(3).set(0, 7.);
		matIdAl.get(3).set(1, 7.);
		assertTrue("La colonne choisie est celle la plus � gauche en cas de doublons",smplx.rechercherPivotCol(matId)==0);
	}

	@Test
	public void testRechercherPivotLigne() {
		try {
			assertTrue("La ligne du pivot a �t� trouv�e correctement.",smplx.rechercherPivotLigne(matId, 2)==2);
		}
		catch(RapportsIncorrectsException e) {
			fail("La ligne du pivot aurait du �tre trouv�e");
		}

		matIdAl.get(0).set(2, 0.);
		matIdAl.get(1).set(6, -5.);
		matIdAl.get(2).set(2, 0.);
		try {
			smplx.rechercherPivotLigne(matId, 2);
			fail("Une erreur aurait du se produire");
		}
		catch(RapportsIncorrectsException e) {
		}
	}

	@Test
	public void testRendrePivotUnitaire() {
		List<Double> checkList = new ArrayList<Double>(matIdAl.get(2));
		smplx.rendrePivotUnitaire(matId, 2, 2);
		for(int i=0;i<checkList.size();i++) {
			if(checkList.get(i)/checkList.get(2)!=matIdAl.get(2).get(i)) {
				fail("Le pivot n'a pas �t� rendu unitaite");
			}
		}
	}

	@Test
	public void testActualiserMatrice() {
		List<Double> checkList = new ArrayList<Double>(matIdAl.get(0));
		smplx.rendrePivotUnitaire(matId, 2, 2);
		smplx.actualiserMatrice(matId, 2, 2);
		for(int i=0;i<checkList.size();i++) {
			if(checkList.get(i)-checkList.get(2)*matIdAl.get(2).get(i) != matIdAl.get(0).get(i)) {
				fail("La matrice n'a pas �t� actualis�e correctement");
			}
		}
	}

	@Test
	public void testGenererSolBase() {
		List<Double> solBase = new ArrayList<Double>();
		
		for(int i=0;i<matId.getMat().get(0).size();i++) {
			solBase.add(0.);
		}
		
		assertTrue("La matrice des solutions de base est effectivement g�n�r�e.",smplx.genererSolBase(matId, solBase).contains("{    0.000 ;    0.000 ;    0.000 ;   24.000 ;   24.000 ;   15.000 }"));
	}

	@Test
	public void testIsSolution() {
		matVideAl.add(new ArrayList<Double>(){{add(1.5);add(1.);add(2.);add(0.5);add(0.);add(0.);add(12.);}});
		matVideAl.add(new ArrayList<Double>(){{add(2.5);add(0.);add(0.);add(-0.5);add(1.);add(0.);add(12.);}});
		matVideAl.add(new ArrayList<Double>(){{add(-0.5);add(0.);add(3.);add(-0.5);add(0.);add(1.);add(3.);}});
		matVideAl.add(new ArrayList<Double>(){{add(-4.);add(0.);add(-5.);add(-3.);add(0.);add(0.);add(-72.);}});
		assertTrue("La matrice est solution du probl�me",smplx.isSolution(matVide));
		
		assertFalse("La matrice n'est pas solution du probl�me, smplx",smplx.isSolution(matId));
	}

}
