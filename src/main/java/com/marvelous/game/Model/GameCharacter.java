package com.marvelous.game.model;

public class GameCharacter {

	private String name;
	private long power;
	private int frequency;
	
	
	
	public GameCharacter(String name, long power, int frequency) {
		super();
		this.name = name;
		this.power = power;
		this.frequency = frequency;
	}
	
	
	public GameCharacter(String name, long power) {
		super();
		this.name = name;
		this.power = power;
	}


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getPower() {
		return power;
	}
	
	public void setPower(long power) {
		this.power = power;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name+","+this.power;
	}
	
	
}
