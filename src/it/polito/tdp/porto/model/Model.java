package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private SimpleGraph<Author,DefaultEdge> grafo; 
	private Map<Integer,Author> idMap;
	private PortoDAO dao;
	
	public Model() {
		dao=new PortoDAO();
		idMap=new HashMap<Integer,Author>();
	}
	
	public void creaGrafo() {
		
		grafo=new SimpleGraph<>(DefaultEdge.class);
		
		
		for(Author a: idMap.values() ) {
		grafo.addVertex(a);
		}
		
		for(Author a1: grafo.vertexSet()) {
		for(Author a: dao.getAutoriVicini(idMap, a1) ) {
			grafo.addEdge(a1, a);
			}
		}
	}
	
	public List<Paper> getCamminoMinimo(Author a,Author b) {
		List<Author>cammino= new ArrayList<Author>();
		List<Paper>c= new ArrayList<Paper>();
		
		DijkstraShortestPath<Author,DefaultEdge> p= new DijkstraShortestPath<Author,DefaultEdge>(grafo);
		cammino.addAll(p.getPath(a, b).getVertexList());
		for(int i=0;i<cammino.size()-1;i++) {
			for(Paper p: dao.getArticoliAutore(cammino.get(i))) {
				if(dao.getArticoliAutore(cammino.get(i+1)).contains(p)) {
					break;
				}
			}
		}
		return c;
	}
	
	public Map<Integer,Author> getIdMap(){
		return idMap;
	}

	public SimpleGraph<Author, DefaultEdge> getGrafo() {
		return grafo;
	}

}
