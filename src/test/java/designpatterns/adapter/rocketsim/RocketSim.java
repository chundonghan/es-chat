package designpatterns.adapter.rocketsim;

public interface RocketSim {
	double getMass();
	double getThrust();
	void setSimTime(double time);
}
