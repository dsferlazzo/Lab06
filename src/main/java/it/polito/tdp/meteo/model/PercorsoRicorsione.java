package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

public class PercorsoRicorsione {
	List<Citta> solMinore;
	
	public List<Citta> risolviPercorso(List<Citta> citta) {
		solMinore = new ArrayList<Citta>();		//RESETTO LA SOLUZIONE, IN CASO DI USI CONTINUI
		int livello = 1;		//LIVELLO RAPPRESENTA IL GIORNO DEL MESE DA ANALIZZARE, PERCIO IL LIVELLO INIZIALE DEVE ESSERE UGUALE AL PRIMO GIORNO DEL MESE, OVVERO LIVELLO=1
		List<Citta> parziale = new ArrayList<Citta>();		//SOLUZIONE PARZIALE, CHE AD OGNI LIVELLO DELL'ITERAZIONE  VERRA ARRICCHITA
		this.percorsoRicorsione(citta, parziale,0,100000, livello);
		
		
		return solMinore;
	}
	
	private void percorsoRicorsione(List<Citta> citta,List<Citta> parziale,int ctParziale, int ctMinore, int livello) {		//SO DI AVERE SOLTANTO 3 CITTA
		//LIVELLO COMBACIA CON IL GIORNO DEL MESE
		if(livello==15)		//CASO TERMINALE
		{
			int ct=0;
			for(int i = 0;i<citta.size();i++)		//CONTROLLO PER LA CITTA
			{
				ct=0;
				for(int j = 0;j<parziale.size();j++)		//CONTROLO LE CITTA ALL'INTERNO DELLA PARZIALE
				{
					if(citta.get(i).equals(parziale.get(j)))
						ct++;
				}		//ERRORE SUL CONTROLLO DI CONDIZIONI
				if(ct>0 && ct<=6 && ctParziale<ctMinore) {		//PROBABILMENTE TOGLIERE IL CONTROLLO SUL COSTO
					solMinore = new ArrayList<Citta>(parziale);
					System.out.println("SOL SALVATA");
				}
					
			}
			return;
	
		}
		//CASO NORMALE
		System.out.println("Caso normale del livello " + livello+ "      " + citta.size());
		for(int i = 0;i<citta.size();i++)		//ITERO IL PROSSIMO LIVELLO PER OGNUNA DELLE 3 CITTA
		{
			parziale.add(citta.get(i));
			//AGGIORNO IL COSTO PARZIALE
			int ct1 = ctParziale;
			if( parziale.size()>=2 && parziale.get(livello-1).equals(parziale.get(livello-2)))
				ctParziale+=parziale.get(livello-1).getRilevamenti().get(i).getUmidita();		//FARE IN MODO CHE I RILEVAMENTI SIANO DISPOSTI IN ORDINE CRESCENTE
			else ctParziale+=100+ parziale.get(livello-1).getRilevamenti().get(i).getUmidita();		//AGGIUNGO 100 AL CT SE DEVO CAMBIARE CITTA
			
			if(ctParziale>=ctMinore) {
				ctParziale=ct1;
				parziale.remove(livello-1);		//FACCIO BACKTRACKING ANCHE QUI DENTRO
				return;		//INTERROMPO LA RICORSIONE, POICHE NON POTRO TROVARE UN COSTO MINORE DI QUELLO MINIMO GIA SALVATO
			}
			
			//FARE LA RICORSIONE
			System.out.println("faccio la ricorsione");
			this.percorsoRicorsione(citta, parziale, ctParziale, ctMinore, livello+1);
			
			//BACKTRACKING
			ctParziale=ct1;
			parziale.remove(livello-1);
			
			
		}
		
		
		
	}

}
