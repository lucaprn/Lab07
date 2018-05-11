package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println("\n-------NERC-------\n");
		System.out.println(model.getNercList());
		System.out.println("\n-------NERC FROM VALUE-------\n");
		System.out.println(model.getIdNercFromValue("MAAC"));
		System.out.println("\n-------OUTAGES-------\n");
		List<Outage> outages = new ArrayList<>(model.getOutagesList(12));
		for(Outage o : outages) {
			System.out.println(o);
		}
		
		System.out.println("\n-------WORST CASE-------\n");
		
		model.reset();
		
		model.worstCase("MAAC", 0, 3, 250);
		
		List<Outage> tmp = new ArrayList<>(model.getBest());
	
		for(Outage o : tmp ) {
			System.out.println(o);
		}
	
		
	}
}