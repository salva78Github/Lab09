package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {

	private Map<Integer, Linea> linee;
	private Map<Integer, Fermata> fermate;
	private List<Connessione> connessioni;

	private MetroDAO metroDAO;
	private WeightedMultigraph<Fermata, Link> grafo = null;

	private List<Link> shortestPathEdgeList = null;
	private double shortestPathTempoTotale = -1;

	public Model() {
		metroDAO = new MetroDAO();
	}

	public List<Fermata> getStazioni() {
		if (fermate == null)
			fermate = metroDAO.getAllFermate();
		return new ArrayList(fermate.values());
	}

	public void creaGrafo() {
		System.out.println("Building the graph.");

		// Collect info
		fermate = metroDAO.getAllFermate();
		linee = metroDAO.getAllLinee();
		connessioni = metroDAO.getAllConnessioni(fermate, linee);

		// Inizializzazione
		grafo = new WeightedMultigraph<Fermata, Link>(Link.class);
		shortestPathEdgeList = null;
		shortestPathTempoTotale = -1;

		Graphs.addAllVertices(grafo, fermate.values());

		for (Connessione c : connessioni) {
			double velocita = c.getLinea().getVelocita();
			double distanza = LatLngTool.distance(c.getStazP().getCoords(), c.getStazA().getCoords(), LengthUnit.KILOMETER);
			double tempo = (distanza / velocita) * 60 * 60;

			// Alternativa 1: viene creato un link (è richesto il costruttore senza parametri!!) ed in seguito impostata
			// la linea
			Link link = grafo.addEdge(c.getStazP(), c.getStazA());
			if (link != null) {
				grafo.setEdgeWeight(link, tempo);
				link.setLinea(c.getLinea());
			}

			// Alternativa 2: creo un link in cui imposto la linea ed in seguito aggiunto al grafo
			// Link link = new Link(c.getLinea());
			// grafo.addEdge(c.getStazP(), c.getStazA(), link);
			// grafo.setEdgeWeight(link, tempo);

		}

		System.out.println("Grafo creato: " + grafo.vertexSet().size() + " nodi, " + grafo.edgeSet().size() + " archi");
	}

	public void calcolaPercorso(Fermata partenza, Fermata arrivo) {
		DijkstraShortestPath<Fermata, Link> dijkstra = new DijkstraShortestPath<Fermata, Link>(grafo, partenza, arrivo);

		shortestPathEdgeList = dijkstra.getPathEdgeList();
		shortestPathTempoTotale = dijkstra.getPathLength();

		if (shortestPathEdgeList == null)
			throw new RuntimeException("Non è stato possiible crare un percorso.");

		// Nel calcolo del tempo non tengo conto della prima e dell'ultima fermata
		if (shortestPathEdgeList.size() - 1 > 0) {
			shortestPathTempoTotale += (shortestPathEdgeList.size() - 1) * 30;
		}
	}

	public String getPercorsoEdgeList() {
		if (shortestPathEdgeList == null)
			throw new RuntimeException("Non è stato creato alcun percorso.");

		StringBuilder risultato = new StringBuilder();
		risultato.append("Percorso: [ ");

		for (Link link : shortestPathEdgeList) {
			risultato.append(grafo.getEdgeTarget(link).getNome());
			risultato.append(", ");
		}
		risultato.setLength(risultato.length() - 2);
		risultato.append("]");

		return risultato.toString();
	}

	public double getPercorsoTempoTotale() {
		if (shortestPathEdgeList == null)
			throw new RuntimeException("Non è stato creato alcun percorso.");

		return shortestPathTempoTotale;
	}
}
