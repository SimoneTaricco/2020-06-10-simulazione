package it.polito.tdp.imdb.model;

public class Adiacenza {
	
	private Integer id1;
	private Integer id2;
	private Integer peso;
			
	public Adiacenza(Integer id1, Integer id2, Integer peso) {
				this.id1 = id1;
				this.id2 = id2;
				this.peso = peso;
			}
	
	
	
	public Integer getId1() {
		return id1;
	}



	public void setId1(Integer id1) {
		this.id1 = id1;
	}



	public Integer getId2() {
		return id2;
	}



	public void setId2(Integer id2) {
		this.id2 = id2;
	}



	public Integer getPeso() {
		return peso;
	}



	public void setPeso(Integer peso) {
		this.peso = peso;
	}



	@Override
	public String toString() {
		return "Partenza (ID): " + id1 + ", arrivo (ID): " + id2 + ", distanza = " + peso;
			}


}
