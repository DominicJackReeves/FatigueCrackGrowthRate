import java.util.Scanner; 
import java.lang.Math;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

//This is the methods for calculating the crack size from voltage values for 
//C(T), M(T) and ESE(T) specimens. 

public class crackSizeCalc{ 
	
		
		
		//Variable Y0 is the voltage measurement lead spacing from the crack plane.
	
		

		public static double value(){
			double Y0 = Store.getY0();
			double W = Store.getSampleWidth(); 
			double Vr = Store.getDummyNotchVoltage();
			double ar = Store.getCrackSize()[0];  
			double V = Store.getNotchVoltage();
			
			double cracksize = 0;
			
			switch(Store.getSampleType()){
				case "C(T)":
						cracksize = ct(W, Vr, V);
						break;
				
				case "M(T)":                 
					cracksize = johnsoneqn(ar,Vr, W, V, Y0);
						break;
					
				case "ESE(T)":
					cracksize = johnsoneqn2(ar, Vr, W, V, Y0);
						break;
			}
			return cracksize;
	}
	
	
	//method to calculate the crack size A for C(T) specimen.
	//this uses an initial crack size and voltage measurement to calculate a reference voltage.
	//then uses this reference voltage to calculate crack size for all voltages.
	private static double ct(double W, double Vr, double V){
		/*
		//declaring constants
		double A0 = 0.5766;                                                    
		double A1 = 1.9169;
		double A2 = -1.0712;
		double A3 = 1.6898;
		
		double aW = a/W;
		double Eqn1 = A0 + (A1*aW) + (A2*(aW*aW)) + (A3*(aW*aW*aW));
		//I don't know if they need to be able to see what the reference voltage is
		double Vr = initialV/Eqn1;
		*/
		//declaring constants
		double B0 = -0.5051;
		double B1 = 0.8857;                                                                 
		double B2 = -0.1398;
		double B3 = 0.0002398;
		
		double VVr = V/Vr;
		double Eqn2 = B0 + (B1*VVr) + (B2*(VVr*VVr)) + (B3*(VVr*VVr*VVr));
		//crack size, A calculated for any voltage
		double A = Eqn2 * W;
		return A;
		
	}
	
	//method to calculate the crack size using johnsons equation which can be used to 
	//calculate crack size for M(T).
	//for this method the reference crack size, a, is found from some other method. 
	//the corresponding reference voltage, initialV is also known.
	private static double johnsoneqn(double ar, double Vr, double W, double V, double Y0){
		double x = (Math.cosh((Math.PI/W)*Y0)/Math.cos((Math.PI/W)*ar));
		double inverse = acosh(x);
		double A = (W/Math.PI)*Math.acos((Math.cosh((Math.PI/W)*Y0))/(Math.cosh((V/Vr)*inverse)));
		return A;
	}
	
	//method to calculate the crack size  using johnsons equation but with W/2
	//which is required for the ESE(T) specimen.
	//for this method the reference crack size, a, is found from some other method. 
	//the corresponding reference voltage, initialV is also known.
	private static double johnsoneqn2(double ar, double Vr, double W, double V, double Y0){
		double x = (Math.cosh((Math.PI/(W/2))*Y0)/Math.cos((Math.PI/(W/2))*ar));
		double inverse = acosh(x);
		double A = ((W/2)/Math.PI)*Math.acos((Math.cosh((Math.PI/(W/2))*Y0))/(Math.cosh((V/Vr)*inverse)));
		return A;
	}
	
	//method to calculate inverse cosh.
	private static double acosh(double x){
		return Math.log(x + Math.sqrt(x*x-1.0));
	}
	
}

