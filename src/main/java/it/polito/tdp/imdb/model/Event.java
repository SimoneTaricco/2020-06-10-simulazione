package it.polito.tdp.imdb.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
		/*public enum EventType{ 
			NUOVA_INTERVISTA, 
			ATTORE_CASUALE, 			// 60% di possibilità, l'attore è scelto tra quelli del grafo OPPURE giorno dopo pausa
			ATTORE_GIORNO_PRECEDENTE, 	// 40% di possibilità, l'attore è quello intervistato nel giorno precedente
			PAUSA						// 90% di possibilità, se 2 attori intervistati di fila hanno lo stesso genere
		}*/
		
		private int time;			// "tempo" per la coda
		private Actor actor;
		
		
		public Event(int time, Actor actor) {
			super();
			this.time = time;
			this.actor = actor;
		}


		public int getTime() {
			return time;
		}


		public void setTime(int time) {
			this.time = time;
		}


		public Actor getActor() {
			return actor;
		}


		public void setActor(Actor actor) {
			this.actor = actor;
		}


		@Override
		public int compareTo(Event o) {
			return this.time - o.time;
		}


		@Override
		public String toString() {

			return "Event [time=" + time + ", actor=" + actor + ", actor gender = " + this.actor.getGender() + "] ";
		} 
		
		
		
		


}
