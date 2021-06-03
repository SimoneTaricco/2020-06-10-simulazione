package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graphs;



public class Simulator {
	
	private PriorityQueue<Event> queue;
	private Model model;
	
	// Valori del modello
	private int numeroGiorni;
	private ArrayList<Actor> daIntervistare;
	private int T; 								// per gestire ordinamento degli eventi messi in coda
	int pausaPossibile; 					// contatore per vedere se viene messo il giorno di pausa
	
	// Valori per il return
	private int numeroPause;
	private List<Actor> listaAttori;
	
	public Simulator(Model model) {
		this.model = model;
		this.listaAttori = new ArrayList<Actor>();
	}
	 	
	public void init(List<Actor> list, int numeroGiorni) {
		
		this.queue = new PriorityQueue<Event>();
		this.daIntervistare = new ArrayList<Actor>(list);
		this.numeroGiorni = numeroGiorni;
		this.T = 1; 
		this.numeroPause = 0;
		
		Random rand = new Random();
		Actor primo = list.get(rand.nextInt(list.size()));
		this.daIntervistare.remove(primo);
		this.pausaPossibile++;
		
		this.queue.add(new Event(T,primo)); 
	}


	public void run() {
				
		Event e;
		
		while((e = this.queue.poll()).getTime()<this.numeroGiorni+1) {  
			
		this.T = e.getTime();				// aggiornando il contatore
		Actor corrente = e.getActor();		// vedere se è o meno una pausa
			
		if(corrente==null) {				// processamento di un giorno di pausa
			this.numeroPause++;
			this.pausaPossibile = 0;
			
			Random rand = new Random();
			Actor prossimo = daIntervistare.get(rand.nextInt(daIntervistare.size()));	// attore successivo scelto a caso	
			this.queue.add(new Event(T+1,prossimo)); 
			
			continue; // si salta il resto
		}
			
		this.listaAttori.add(corrente); 		// intervisto l'attore
		this.daIntervistare.remove(corrente); 	// che non intervisterò più
					
		Random r = new Random(); // numero random da 1 a 100
		int random = r.nextInt(100) + 1;	
		int indiceCasuale = (new Random()).nextInt(this.daIntervistare.size()) + 1; // estrazione persona da intervistare	
		Actor prossimo;
		
		if (e.getTime()>1 && controlloGenere(corrente, this.listaAttori.get(listaAttori.size()-2)) && this.pausaPossibile>=2) {
			pausa();
			continue;
		}
			
		if (random<=60) { // 60% di estrarlo tra quelli non ancora intervistati
			prossimo = this.daIntervistare.get(indiceCasuale);
			this.queue.add(new Event(T+1,prossimo));
			this.pausaPossibile++;

		} else { // 40% di chiedere all'ultimo intervistato e aggiungere il suggerito
			prossimo = model.vicinoGradoMassimo(corrente);
			if (prossimo==null) {
				Random rand = new Random();
				prossimo = daIntervistare.get(rand.nextInt(daIntervistare.size()));		
			}
			this.queue.add(new Event(T+1,prossimo));
			this.pausaPossibile++;
		}		
		}

	}
	
	private void pausa() {
		
		Random r2 = new Random(); // numero random da 1 a 100
		int random2 = r2.nextInt(100) + 1;
			
		if (random2<=90)  // 90% di prendersi una pausa
			this.queue.add(new Event(T+1,null));
		
	}
	
	private boolean controlloGenere(Actor ultimo, Actor penultimo) {
		
		if (ultimo.getGender().equals(penultimo.getGender()))
			return true;
		else
			return false;
	}
	
	public List<Actor> getListaAttori() {
		return listaAttori;
	}

	public int getNumeroPause() {
		return numeroPause;
	}

}
