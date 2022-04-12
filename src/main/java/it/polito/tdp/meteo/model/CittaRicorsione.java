package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

public class CittaRicorsione {
	
	List<Citta> solMinore;
	int ctMinore;
	
	public List<Citta> risolviPercorso(List<Citta> citta)
	{
		System.out.println(ctMinore);	//DEBUGGING
		solMinore = new ArrayList<Citta>();		//NEL CASO DI UTILIZZI DI CLASSE CONTINUI, INIZIALIZZO OGNI VOLTA LA SOLUZIONE
		ctMinore=1000000;
		int livello = 4;		//INSERISCO GIA ALL'INIZIO DELLA RICORSIONE I PRIMI 3 GIORNI, PERCIO, IL PRIMO GIORNO DA INSERIRE NELLA RICORSIONE SARA IL QUARTO
		int ctIniziale;
		List<Citta> parziale = new ArrayList<Citta>();
		
		for(int i = 0;i<citta.size();i++)
		{
			ctIniziale = 0;
			for(int j = 0;j<3;j++)
			{
			parziale.add(citta.get(i));		//AGGIUNGO, PER COMINCIARE, 3 VOLTE LA PRIMA CITTA, E POI COMINCIO AD ITERARE
			ctIniziale+=citta.get(i).getRilevamenti().get(j).getUmidita();
			}
			cittaRicorsione(citta, parziale, ctIniziale, livello);
			//DEVO ANCORA CALCOLARE IL COSTO DI PARTENZA!!!!!!!!!!!
			//COMINCIO ITERAZIONE
			for(int j = 0;j<3;j++)
			parziale.remove(parziale.size()-1);		//BACKTRACKING
			
			
		}
		
		return solMinore;
		
	}
	/**
	 * 
	 * @param citta lista delle citta disponibili, dotate di un riferimento ad i loro rilevamenti
	 * @param parziale soluzione parziale, collezione delle città già inserite
	 * @param ctParziale somma dei costi giornalieri della soluzione parziale
	 * @param ctMinore costo minore fino ad ora trovato, per verificare se una soluzione ammissibile è la migliore, => ctParziale<ctMinore
	 * @param livello livello della ricorsione
	 */
	private void cittaRicorsione(List<Citta> citta,List<Citta> parziale,int ctParziale, int livello) {
		
		if(livello==16)		//CASO TERMINALE, TERMINO QUANDO LIVELLO == 16, PERCIO HO GIA INSERITO I PRIMI 15 GIORNI DEL MESE, E STO PER INSERIRE IL SEDICESIMO
		{		//CHECK AFFICHE TUTTE E 3 LE CITTA DELLA LISTA CITTA SIANO PRESENTI NELLA SOLUZIONE PARZIALE
			int flTutteCitta = 1;
			int ct;
			
			for(int i = 0;i<citta.size();i++)
			{
				ct = 0;
				for(int j = 0;j<15;j++)		//COUNTER TROPPO ALTO
				{
					if(citta.get(i).equals(parziale.get(j)))
						ct++;
				}
				if(ct==0)
					flTutteCitta=0;
			}		//SE ALLA FINE DEL FOR FLTUTTECITTA=1, => TUTTE E 3 LE CITTA SONO PRESENTI NELLA SOLUZIONE PARZIALE
			
			if(flTutteCitta==1)		//SE SONO ARRIVATO FIN QUI, IL COSTO TROVATO E IL MINIMO (DA INSERIRE DEL COSTO NEL CASO NORMALE)
			{
				
				ctMinore=ctParziale;		//AGGIORNO IL CTMINORE, IN CASO DI NUOVA SOLUZIONE TROVATA
				/*System.out.println(ctMinore);	//DEBUGGING
				
				//DEBUGGING
				for(int i = 0;i<parziale.size();i++)
					System.out.println(parziale.get(i).getNome());
				*/
				solMinore = new ArrayList<Citta>(parziale);		//SALVO LA NUOVA SOLUZIONE CHE HO TROVATO IN SOLMINORE
				return;
			}
			return;
		}
		
		//CASO NORMALE, CHECK SUL NUMERO MASSIMO DI VOLTE CHE INSERISCO LA CITTA, SULLA CONTINUAZIONE DI LAVORO IN UNA CITTA, E SUL COSTO MINIMO
		
		
		
		if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) &&
				parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-3)))	//CHECK SULLA CONTINUAZIONE DI LAVORO IN UNA CITTA
		{
			
			//POSSO CAMBIARE LA CITTA
			for(int i = 0;i<citta.size();i++)
			{
				int ct = 0;
				//CONTROLLO SUL NUMERO MASSIMO DI VOLTE CHE POSSO INSERIRE UNA CITTA NELLA LISTA
				for(int j = 0;j<parziale.size();j++)
				{
					if(parziale.get(j).equals(citta.get(i)))
						ct++;
				}
				
				if(ct<6)
				{
					int nuovoCosto;
					parziale.add(citta.get(i));
					if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)))
					{
					nuovoCosto = ctParziale + citta.get(i).getRilevamenti().get(livello-1).getUmidita();
					}
					else nuovoCosto = ctParziale + 100 + citta.get(i).getRilevamenti().get(livello-1).getUmidita();		//SOMMO 100 NEL CASO IN CUI IL TECNICO DEVE CAMBIARE CITTA
					if(nuovoCosto<=ctMinore)
					{
					cittaRicorsione(citta, parziale, nuovoCosto, livello+1);		//FACCIO LA RICORSIONE, SOLO SE IL COSTO TOTALE E MINORE DEL COSTOMIN SALVATO
					}
					parziale.remove(parziale.size()-1);		//BACKTRACKING
				}
			}
			return;
		}
		
		//NON POSSO CAMBIARE CITTA, POICHE IL TECNICO NON E ANCORA STATO NELLA STESSA CITTA PER 3 GIORNI CONSECUTIVI
		
		int ct = 0;
		//CONTROLLO SUL NUMERO MASSIMO DI VOLTE CHE POSSO INSERIRE UNA CITTA NELLA LISTA
		for(int j = 0;j<parziale.size();j++)
		{
			if(parziale.get(j).equals(parziale.get(parziale.size()-1)))
				ct++;
		}
		
		if(ct<6)		//HO INSERITO NELLA MIA SOLUZIONE PARZIALE L'ULTIMA CITTA PER MENO DI 6 VOLTE
		{
			int nuovoCosto;
			parziale.add(parziale.get(parziale.size()-1));
			if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)))
			{
			nuovoCosto = ctParziale + parziale.get(parziale.size()-1).getRilevamenti().get(livello-1).getUmidita();
			}
			else nuovoCosto = ctParziale + 100 + parziale.get(parziale.size()-1).getRilevamenti().get(livello-1).getUmidita();		//SOMMO 100 NEL CASO IN CUI IL TECNICO DEVE CAMBIARE CITTA
			if(nuovoCosto<=ctMinore)
			{
			cittaRicorsione(citta, parziale, nuovoCosto, livello+1);		//FACCIO LA RICORSIONE, SOLO SE IL COSTO TOTALE E MINORE DEL COSTOMIN SALVATO
			}
			parziale.remove(parziale.size()-1);		//BACKTRACKING
		}
		
		
	}
	
	
	

}
