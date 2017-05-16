package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.db.MetroDAO;

public class Model {

	private Map<Integer, Linea> linee;
	private Map<Integer, Fermata> fermate;
	private List<Connessione> connessioni;
	private List<FermataSuLinea> fermateSuLinea;

	private List<Link> pathEdgeList = null;
	private double pathTempoTotale = 0;

	private MetroDAO metroDAO;

	// Directed Weighted Graph
	private DefaultDirectedWeightedGraph<FermataSuLinea, Link> grafo = null;

	public Model() {
		metroDAO = new MetroDAO();
	}

	public List<Fermata> getStazioni() {
		if (fermate == null)
			fermate = metroDAO.getAllFermate();
		return new ArrayList(fermate.values());
	}

	public void creaGrafo() {
		System.out.println("Building the graph...");

		fermate = metroDAO.getAllFermate();
		linee = metroDAO.getAllLinee();
		connessioni = metroDAO.getAllConnessioni(fermate, linee);
		fermateSuLinea = metroDAO.getAllFermateSuLinea(fermate, linee);

		// Directed Weighted
		grafo = new DefaultDirectedWeightedGraph<FermataSuLinea, Link>(Link.class);

		// FASE1: Aggiungo un vertice per ogni fermata
		Graphs.addAllVertices(grafo, fermateSuLinea);

		// FASE2: Aggiungo tutte le connessioni tra tutte le fermate
		for (Connessione c : connessioni) {

			double velocita = c.getLinea().getVelocita();
			double distanza = LatLngTool.distance(c.getStazP().getCoords(), c.getStazA().getCoords(), LengthUnit.KILOMETER);
			double tempo = (distanza / velocita) * 60 * 60;

			// Cerco la fermataSuLinea corretta all'interno della lista delle fermate
			Map<Key, FermataSuLinea> fermateSuLineaDataFermata = c.getStazP().getFermateSuLinea();
			FermataSuLinea fslPartenza = fermateSuLineaDataFermata.get(new Key(c.getStazP().getIdFermata(), c.getLinea().getIdLinea()));

			// Cerco la fermataSuLinea corretta all'interno della lista delle fermate
			fermateSuLineaDataFermata = c.getStazA().getFermateSuLinea();
			FermataSuLinea fslArrivo = fermateSuLineaDataFermata.get(new Key(c.getStazA().getIdFermata(), c.getLinea().getIdLinea()));

			if (fslPartenza != null && fslArrivo != null) {
				// Aggiungoun un arco pesato tra le due fermate
				Link link = grafo.addEdge(fslPartenza, fslArrivo);
				if (link != null) {
					grafo.setEdgeWeight(link, tempo);
					link.setLinea(c.getLinea());
				}
			}
		}

		// FASE3: Aggiungo tutte le connessioni tra tutte le fermateSuLinee
		// della stessa Fermata
		for (Fermata fermata : fermate.values()) {
			for (FermataSuLinea fslP : fermata.getFermateSuLinea().values()) {
				for (FermataSuLinea fslA : fermata.getFermateSuLinea().values()) {
					if (!fslP.equals(fslA)) {
						Link link = grafo.addEdge(fslP, fslA);
						if (link != null) {
							Linea linea = fslA.getLinea();
							grafo.setEdgeWeight(link, linea.getIntervallo() * 60);
							link.setLinea(linea);
						}
					}
				}
			}
		}

		System.out.println("Grafo creato: " + grafo.vertexSet().size() + " nodi, " + grafo.edgeSet().size() + " archi");
	}

	public void calcolaPercorso(Fermata partenza, Fermata arrivo) {

		DijkstraShortestPath<FermataSuLinea, Link> dijkstra;

		// Usati per salvare i valori temporanei
		double pathTempoTotaleTemp;

		// Usati per salvare i valori migliori
		List<Link> bestPathEdgeList = null;
		double bestPathTempoTotale = Double.MAX_VALUE;

		for (FermataSuLinea fslP : partenza.getFermateSuLinea().values()) {
			for (FermataSuLinea fslA : arrivo.getFermateSuLinea().values()) {
				dijkstra = new DijkstraShortestPath<FermataSuLinea, Link>(grafo, fslP, fslA);

				pathTempoTotaleTemp = dijkstra.getPathLength();

				if (pathTempoTotaleTemp < bestPathTempoTotale) {
					bestPathTempoTotale = pathTempoTotaleTemp;
					bestPathEdgeList = dijkstra.getPathEdgeList();
				}
			}
		}

		pathEdgeList = bestPathEdgeList;
		pathTempoTotale = bestPathTempoTotale;

		if (pathEdgeList == null)
			throw new RuntimeException("Non è stato creato un percorso.");

		// Nel calcolo del tempo non tengo conto della prima e dell'ultima
		if (pathEdgeList.size() - 1 > 0) {
			pathTempoTotale += (pathEdgeList.size() - 1) * 30;
		}
	}

	public String getPercorsoEdgeList() {

		if (pathEdgeList == null)
			throw new RuntimeException("Non è stato creato un percorso.");

		StringBuilder risultato = new StringBuilder();
		risultato.append("Percorso:\n\n");

		Linea lineaTemp = pathEdgeList.get(0).getLinea();
		risultato.append("Prendo Linea: " + lineaTemp.getNome() + "\n[");

		for (Link edge : pathEdgeList) {
			risultato.append(grafo.getEdgeTarget(edge).getNome());

			if (!edge.getLinea().equals(lineaTemp)) {
				risultato.append("]\n\nCambio su Linea: " + edge.getLinea().getNome() + "\n[");
				lineaTemp = edge.getLinea();

			} else {
				risultato.append(", ");
			}
		}
		risultato.setLength(risultato.length() - 2);
		risultato.append("]");

		return risultato.toString();
	}

	public double getPercorsoTempoTotale() {

		if (pathEdgeList == null)
			throw new RuntimeException("Non è stato creato un percorso.");

		return pathTempoTotale;
	}
}
