package it.polito.tdp.metrodeparis.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;
import it.polito.tdp.metrodeparis.exception.MetroDeParisException;

public class Model {

	private static MetroDAO dao = new MetroDAO();
	private WeightedMultigraph<Fermata, Link> graph;

	private MetroDAO getDAO() {
		return dao;
	}

	public List<Fermata> retrieveAllFermate() throws MetroDeParisException {
		return getDAO().getAllFermate();
	}

	private WeightedMultigraph<Fermata, Link> getGrafo() throws MetroDeParisException {
		if (this.graph == null) {
			this.creaGrafo();
		}
		return this.graph;
	}

	private void creaGrafo() throws MetroDeParisException {

		this.graph = new WeightedMultigraph<Fermata, Link>(Link.class);
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, retrieveAllFermate());

		// crea gli archi del grafo
		for (FermataPair fp : getDAO().listCoppieFermateAdiacenti()) {
			// Link l = graph.addEdge(fp.getF1(), fp.getF2());

			Link link = new Link(fp.getLinea());
			graph.addEdge(fp.getF1(), fp.getF2(), link);
			graph.setEdgeWeight(link, getTempo(fp));

		}

	}

	private double getTempo(FermataPair fp) {

		double velocita = fp.getLinea().getVelocita();
		double distanza = LatLngTool.distance(fp.getF1().getCoords(), fp.getF2().getCoords(), LengthUnit.KILOMETER);
		double tempo = (distanza / velocita) * 60 * 60;
		return tempo;

	}

	public void printGraphStats() throws MetroDeParisException {
		System.out.format("Grafo: Vertici %d, Archi %d\n", getGrafo().vertexSet().size(), getGrafo().edgeSet().size());
	}

	
	public Percorso getCamminoMinino(Fermata partenza, Fermata arrivo) throws MetroDeParisException {
		DijkstraShortestPath<Fermata, Link> dp = new DijkstraShortestPath<Fermata, Link>(getGrafo(), partenza, arrivo);
		
		List<Link> ll = dp.getPathEdgeList();
		double tempoPercorrenza = dp.getPathLength();
		
		if (ll == null)
			throw new MetroDeParisException("Non è stato possiible crare un percorso.");

		// Nel calcolo del tempo non tengo conto della prima e dell'ultima fermata
		if (ll.size() - 1 > 0) {
			tempoPercorrenza += (ll.size() - 1) * 30;
		}

		Percorso camminoMinimo = new Percorso(ll,tempoPercorrenza);
		
		return camminoMinimo;
		
		
		
		
		
	}
	
	
	
}
