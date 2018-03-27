package designpatterns.adapter.rocketsim;

public class PhysicalRocket {
	private double burnArea, burnRate, fuelMass, totalMass;
	public PhysicalRocket(double burnArea,double burnRate,double fuelMass,double totalMass) {
		this.burnArea = burnArea;
		this.burnRate = burnRate;
		this.fuelMass = fuelMass;
		this.totalMass = totalMass;
	}
	double getBurnTime(){
		return 0d;
	}
	double getMass(double time){
		return time;
	}
	double getThrust(double time){
		return time;
	}
}
