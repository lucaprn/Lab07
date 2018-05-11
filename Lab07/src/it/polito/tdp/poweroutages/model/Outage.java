package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Outage implements Comparable<Outage> {
	
	private int id;
	private int idNerc;
	private int customers;
	private LocalDateTime start; 
	private LocalDateTime end;
	private long durata;
	public Outage(int id, int idNerc, int customers, LocalDateTime start, LocalDateTime end) {
		super();
		this.id = id;
		this.idNerc = idNerc;
		this.customers = customers;
		this.start = start;
		this.end = end;
		LocalDateTime tempDateTime = LocalDateTime.from(start);
		this.durata = tempDateTime.until(end, ChronoUnit.HOURS);
	}
	public long getDurata() {
		return durata;
	}
	public void setDurata(long durata) {
		this.durata = durata;
	}
	public Outage() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdNerc() {
		return idNerc;
	}
	public void setIdNerc(int idNerc) {
		this.idNerc = idNerc;
	}
	public int getCustomers() {
		return customers;
	}
	public void setCustomers(int customers) {
		this.customers = customers;
	}

	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Outage other = (Outage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Outage [id=" + id + ", idNerc=" + idNerc + ", customers=" + customers + ", start=" + start + ", end="
				+ end + "]";
	}
	@Override
	public int compareTo(Outage o) {
		return this.getStart().compareTo(o.getStart());
	}
	
	
	

}