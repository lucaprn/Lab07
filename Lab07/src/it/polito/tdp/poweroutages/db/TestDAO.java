package it.polito.tdp.poweroutages.db;

import java.util.*;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Outage;

public class TestDAO {

	public static void main(String[] args) {
		
		PowerOutageDAO dao = new PowerOutageDAO();
		
		List<Nerc> listaNerc = dao.getNercList();
		
		for(Nerc nerc : listaNerc) {
			System.out.println(nerc);
		}
		
		List<Outage> listaOutages = dao.getOutageList(2);
		
		for(Outage o : listaOutages) {
			System.out.println(o);
		}
		

	}

}