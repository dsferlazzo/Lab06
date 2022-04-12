package it.polito.tdp.meteo.model;

public class LocalitaEAVGUmidita {
	
	String localita;
	float avgUmidita;
	
	public LocalitaEAVGUmidita(String localita, float avgUmidita) {
		super();
		this.localita = localita;
		this.avgUmidita = avgUmidita;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public float getAvgUmidita() {
		return avgUmidita;
	}
	public void setAvgUmidita(int avgUmidita) {
		this.avgUmidita = avgUmidita;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(avgUmidita);
		result = prime * result + ((localita == null) ? 0 : localita.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalitaEAVGUmidita other = (LocalitaEAVGUmidita) obj;
		if (Float.floatToIntBits(avgUmidita) != Float.floatToIntBits(other.avgUmidita))
			return false;
		if (localita == null) {
			if (other.localita != null)
				return false;
		} else if (!localita.equals(other.localita))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "citta: " + localita + "          umidità media: " + avgUmidita ;
	}
	
	
}
