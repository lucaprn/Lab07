package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		List<Nerc> nercList = model.getNercList();
		System.out.println("Nerc List size: " + nercList.size());
		
		Nerc selectedNerc = nercList.get(3);
		List<PowerOutageEvent> worstCase = model.getWorstCase(3, 250, selectedNerc);
		
		System.out.println("Tot people affected: " + model.sumAffectedPeople(worstCase));
		System.out.println("Tot hours of outage: " + model.sumOutageHours(worstCase));
		
		for (PowerOutageEvent event : worstCase) {
			System.out.println(String.format("%d %s %s %d %d", event.getYear(), event.getOutageStart(),
					event.getOutageEnd(), event.getOutageDuration(), event.getAffectedPeople()));
		}
	}
}
