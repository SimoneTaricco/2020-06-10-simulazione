package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	private Map<Integer,Actor> idMap; 
	private Simulator sim;

	public Model() {
		this.dao = new ImdbDAO();
	}
	
	public List<String> genresList() {
		return dao.listAllGenres();
	}
	
	public void creaGrafo(String genere) {
		
		this.idMap = new HashMap<Integer,Actor>();
		
		for (Actor a:dao.listSelectedActors(genere)) { 
			idMap.put(a.getId(), a); 
		}
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
			
		Graphs.addAllVertices(grafo, idMap.values()); 
			
		for(Adiacenza a:dao.getAdiacenze(genere)) { 
			Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());	
		}
		
	    System.out.println("Numero di vertici: " + grafo.vertexSet().size());
	    System.out.println("Numero di archi: " + grafo.edgeSet().size());
	
	}
	
	public void runSim(int numGiorni) {	
		this.sim = new Simulator(this);
		sim.init(this.vertici(), numGiorni);
		sim.run();	
	}
	
	public int giorniPausa(){
		return sim.getNumeroPause();	
	}
	
	public List<Actor> intervistati() {
		return sim.getListaAttori();
	}

	
	public List<Actor> vertici() {
		
		TreeMap<String,Actor> map = new TreeMap<String,Actor>();
		
		for (Actor a:this.grafo.vertexSet()) 
			map.put(a.lastName + a.firstName, a);
		
		return new ArrayList<Actor>(map.values());
	}
	
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Actor> collegati (Actor partenza) { 
		
		TreeMap<String,Actor> map = new TreeMap<String,Actor>();

		ConnectivityInspector<Actor,DefaultWeightedEdge> insp = new ConnectivityInspector<>(this.grafo);
		
		for (Actor a:insp.connectedSetOf(partenza)) {
			if (!a.equals(partenza))
				map.put(a.lastName + a.firstName, a);
		}
		
		return new ArrayList<Actor>(map.values());
	}
	
	public Actor vicinoGradoMassimo(Actor a) {
		
		int gradoMassimo = 0;
		
		Actor res = null;
		
		List <Actor> list = Graphs.neighborListOf(this.grafo, a);
		
		for (Actor ac:list) {
			if (this.grafo.degreeOf(ac)>gradoMassimo) {
				res = ac;
				gradoMassimo = this.grafo.degreeOf(ac);
			}
		}
		
		return res;
		
	}

}
