package designpatterns.visitor;

import java.util.Random;

public class FlowerGenerator {
	private static Random rand = new Random(); 
	  
	  public static Element newFlower() { 
	    switch (rand.nextInt(2)) { 
	    default: 
	    case 0: 
	      return new Osmanthus(); 
	    case 1: 
	      return new Chrysanthemum(); 
	    } 
	  } 
}
