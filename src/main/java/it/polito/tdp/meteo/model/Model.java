package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO meteoDAO = new MeteoDAO();
	private PercorsoRicorsione pr = new PercorsoRicorsione();
	private CittaRicorsione cr = new CittaRicorsione();

	public Model() {

	}

	// of course you can change the String output with what you think works best
	public List<LocalitaEAVGUmidita> getUmiditaMedia(int mese) {
		return this.meteoDAO.getCittaUmidita(mese);
	}
	
	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		
		//INIZIALIZZA LE CITTA
		System.out.println("Sto inizializzando");
		
		List<Rilevamento> rilevamenti = this.meteoDAO.getAllRilevamenti();
		List<String> localita = new ArrayList<String>();
		for(int i = 0;i<rilevamenti.size();i++)
		{
			if(!localita.contains(rilevamenti.get(i).getLocalita()))
			{
				localita.add(rilevamenti.get(i).getLocalita());
				System.out.println(rilevamenti.get(i).getLocalita());		//DEBUG
			}
		}		//CREO UNA LISTA DI TUTTE LE LOCALITA DISPONIBILI
		List<Citta> citta = new ArrayList<Citta>();
		for(int i = 0;i<localita.size();i++)
		{
			List<Rilevamento> r = this.meteoDAO.getAllRilevamentiLocalitaMese(mese, localita.get(i));
			Citta c = new Citta(localita.get(i), r, r.size() );
			citta.add(c);
		}
		System.out.println("Ho inzializzato, con dimensioni della lista citta: " + citta.size());		//DEBUG
		//HO INIZIALIZZATO LE MIE 3 CITTA, ALL'INTERNO DELLA LISTA CITTA
		//COSI, OGNI CITTA HA AL SUO INTERNO UNA COLLECTION DI RILEVAMENTI, UNO PER OGNI GIORNO DEL MESE
		
		//List<Citta> soluzione = pr.risolviPercorso(citta);
		List<Citta> soluzione = cr.risolviPercorso(citta);
		
		return soluzione;
	}
	

}
