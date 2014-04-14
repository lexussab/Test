package ru.smirnov.anton;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Record {
	private String name;
	private String uuid;
	private	String state;
	private int cpu;
	private int timeWork;
	private int quantity;
	private Date dateReg;
	
	public Record(String name, String uuid, String state, int cpu,
			int timeWork, int quantity) {
		super();
		this.name = name;
		this.uuid = uuid;
		this.state = state;
		this.cpu = cpu;
		this.timeWork = timeWork;
		this.quantity = quantity;
	}
	
	public Record(String name, String uuid, String state, int cpu,
			int timeWork, int quantity, Date dateReg) {
		super();
		this.name = name;
		this.uuid = uuid;
		this.state = state;
		this.cpu = cpu;
		this.timeWork = timeWork;
		this.quantity = quantity;
		this.dateReg = dateReg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getTimeWork() {
		return timeWork;
	}

	public void setTimeWork(int timeWork) {
		this.timeWork = timeWork;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDateReg() {	
		return dateReg;
	}

	public void setDateReg(Date dateReg) {
		this.dateReg = dateReg;
	}
	
	@Override
	public String toString() {
		//Формат вывода даты с милисекундами
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		return this.getName() + ", " + this.getUuid() + ", " + this.getState() + ", " + 
			   this.getCpu() + "%, " + this.getTimeWork() + ", " + this.getQuantity() + ", " + 
			   dateFormat.format(this.getDateReg());
	}

}
