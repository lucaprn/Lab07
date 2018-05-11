package it.polito.tdp.poweroutages.model;

import java.util.*;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	List<Nerc> nercList;
	List<Outage> best;
	
	
	public Model() {
		podao = new PowerOutageDAO();
		nercList = new ArrayList<>(this.getNercList());
		best= null ;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public int getIdNercFromValue(String value) {
		for(Nerc nerc : nercList) {
			if(nerc.getValue().equals(value)) {
				return nerc.getId();
			}
		}
		return -1;
	}
	
	public List<Outage> getOutagesList(int id){
		return podao.getOutageList(id);
	}
	
	public void worstCase(String value, int livello, int maxAnni, int maxOre) {
		// determino gli outages su cui ciclare
		this.reset();
		List<Outage> outagesFromNerc = new ArrayList<>(this.getOutagesList(this.getIdNercFromValue(value)));
		List<Outage> parziale = new ArrayList<>();
		this.calcolaSequenza(outagesFromNerc, parziale, livello, maxAnni, maxOre);
		
		
	}

	private void calcolaSequenza(List<Outage> outagesFromNerc, List<Outage> parziale, int livello, int maxAnni, int maxOre) {
	
		int customersAffected = this.getAllCustomers(parziale);
		//condizione di terminazione
		
		if(best==null|| customersAffected > this.getAllCustomers(best) ) {
			best = new ArrayList<>(parziale);
		}
		
		for(Outage o : outagesFromNerc) {
			if(!parziale.contains(o)) {
				parziale.add(o);
				if(this.isCorretta(parziale,maxAnni,maxOre)) {
					this.calcolaSequenza(outagesFromNerc, parziale, livello+1, maxAnni, maxOre);
				}
				parziale.remove(o);
			}
		}
	
	}

	private boolean isCorretta(List<Outage> parziale, int maxAnni, int maxOre) {
		if(this.numeroAnni(parziale)<=maxAnni && this.numeroOre(parziale)<=maxOre) {
			return true;
		}
		return false;
	}

	private long numeroOre(List<Outage> parziale) {
		long durataTot = 0; 
		for(Outage o : parziale) {
			durataTot+=o.getDurata();
		}
		return durataTot;
	}

	private int numeroAnni(List<Outage> parziale) {
		
		List<Outage> tmp = new ArrayList<>(parziale);
		Collections.sort(tmp);
		return tmp.get(tmp.size()-1).getStart().getYear() - tmp.get(0).getStart().getYear();
	}

	private int getAllCustomers(List<Outage> parziale) {
		int n =0 ; 
		for(Outage o : parziale) {
			n+=o.getCustomers();
		}
		return n;
	}

	public List<Outage> getBest() {
		return best;
	}

	public void reset() {
		best=null;
		
	}
	
	public String toStringBest() {
		StringBuilder sb = new StringBuilder();
		for(Outage po : best) {
			sb.append(po.toString()+"\n");
		}
		return sb.toString();
		
	}
	
	public int numeroClientiBest() {
		return this.getAllCustomers(best);
	}
	public long numeroOreBest() {
		return this.numeroOre(best);
	}
	
	public int intervalloValidoAnni(String nerc) {
		List<Outage> tmp = new ArrayList<>(this.getOutagesList(this.getIdNercFromValue(nerc)));
		return this.numeroAnni(tmp);
	}
	
	public long intervalloValidoOre(String nerc) {
		List<Outage> tmp = new ArrayList<>(this.getOutagesList(this.getIdNercFromValue(nerc)));
		return this.numeroOre(tmp);
	}
	

}
