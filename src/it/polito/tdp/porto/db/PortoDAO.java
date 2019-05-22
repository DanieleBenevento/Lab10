package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getListaAutori(Map<Integer,Author> m) {

		
		final String sql = "SELECT * FROM author";
		
	 List<Author> listaAutori= new ArrayList<Author>();

		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();

			while(rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				listaAutori.add(autore);
				m.put(rs.getInt("id"), autore);
				
			}

			conn.close();
			return listaAutori;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
public List<Author> getAutoriVicini(Map<Integer,Author> m,Author a) {

		
		final String sql = "SELECT creator.authorid FROM author,creator,paper WHERE creator.eprintid=paper.eprintid AND author.id=? AND creator.authorid!=author.id";
		
	 List<Author> listaAutoriVicini= new ArrayList<Author>();

		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		    st.setInt(1,a.getId() );
			ResultSet rs = st.executeQuery();

			while(rs.next()) {

				listaAutoriVicini.add(m.get(rs.getInt("authorid")));
				
				
			}

			conn.close();
			return listaAutoriVicini;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

      public List<Author> getCamminoPaper(Author a,Author b) {

	
	  final String sql = "SELECT paper.eprintid,paper.title,paper.date,paper.type,paper.types,paper.publication,paper.issn  FROM author,creator,paper WHERE creator.eprintid=paper.eprintid AND creator.authorid=? AND creator.authorid=?";
	
      List<Author> listaAutoriVicini= new ArrayList<Author>();

	  try {
		
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
	    st.setInt(1,a.getId() );
		ResultSet rs = st.executeQuery();

		while(rs.next()) {

			listaAutoriVicini.add(m.get(rs.getInt("authorid")));
			
			
		}

		conn.close();
		return listaAutoriVicini;
		

	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db");
	}
}
      
      public List<Paper> getArticoliAutore(Author a) {

  		final String sql = "SELECT * FROM paper,creator WHERE creator.authorid=? AND creator.eprintid=paper.eprintid ";

  		List<Paper>lpaper=new ArrayList<Paper>();
  		try {
  			Connection conn = DBConnect.getConnection();
  			PreparedStatement st = conn.prepareStatement(sql);
  			st.setInt(1,a.getId());

  			ResultSet rs = st.executeQuery();

  			if (rs.next()) {
  				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
  						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
  				lpaper.add(paper);
  			}

  			conn.close();
  			return lpaper;

  		} catch (SQLException e) {
  			 e.printStackTrace();
  			throw new RuntimeException("Errore Db");
  		}
  	}
  	
}