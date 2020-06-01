import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
/**
 * Purpose: a class to determine the value of the stress-intensity factor range
 *(delta K) for:
 * C(T), compact tension specimens
 * M(T), middle tension specimens
 * ESE(T), Eccentrically-loaded single edge crack tension specimens
 * CC(T), centre cracked tension
 * SEN B3, Single edge notch three-point bend
 * SEN B4, Single edge notch four-point bend
 * SEN B8, Single edge notch eight-point bend
 * SEN(T), Single edge notch tension 
 * 
 *@author: Michael Sherratt, Jacob Latham
 *@version: 2.0
 **/

/**
@param deltaK the stress-intensity factor range
@param a fitted crack size
@param W width of specimen
@param B thickness of specimen
@param deltaP the difference in minimum and maximum value of the load force
@param d is a function of deltaP, B and W
 **/
public class DeltaK	{

	private double deltaK;
	private double W;
	private double B;
	private String type;

	public DeltaK(double a, double deltaP)	{

		type = Store.getSampleType();
		W = Store.getSampleWidth();
		B = Store.getSampleThickness();


		//fitted crack size, a	
		//width of the specimen, W
		//thickness of the specimen, B						
		//force range, delta p
		//calculate d

		double d= (deltaP/(B*Math.pow(W,.5)));
		//calulate and print deltaK for the right specimen
		if (type.contentEquals("C(T)")){	
			deltaK = (d*c(a,W));	// Calculation from	BS ISO 12108:2012
		}
		else if (type.contentEquals("M(T)")){
			deltaK = (m(a,W,deltaP,B));	// Calculation from	ASTM E647-15
		}
		else if (type.contentEquals("ESE(T)")){
			deltaK = (eSE(a,W,deltaP,B));	// Calculation from	ASTM E647-15
		}
		else if (type.contentEquals("CC(T)")){
			deltaK = (d*cC(a,W));	// Calculation from	BS ISO 12108:2012
		}
		else if (type.contentEquals("SEN B3")){;
		deltaK = (d*sENB3(a,W));	// Calculation from	BS ISO 12108:2012
		}
		else if (type.contentEquals("SEN B4")){
			deltaK = (d*sENB4(a,W));	// Calculation from	BS ISO 12108:2012
		}
		else if (type.contentEquals("SEN B8")){
			deltaK = (d*sENB8(a,W));	// Calculation from	BS ISO 12108:2012
		}
		else if (type.contentEquals("SEN(T) Pinned-End")){
			deltaK = (d*pinned(a,W));	// Calculation from	BS ISO 12108:2012
		}
		else if (type.contentEquals("SEN(T) Clamped-End")){
			deltaK = (d*clamped(a,W));
		}


	}


	private static double c(double a ,double w){

		double z= a/w;																	
		double g= ((2+z)/(Math.pow(1-z,1.5)))
				* (0.886 + 4.64*z -13.32 * Math.pow(z, 2) + 14.72 * Math.pow(z,3)
				-5.6 * Math.pow(z,4));
		return g;

	}
	/**middle tension specimen calculation of stress-intensity factor range
	 *@param z constant 'alpha' which differs in different specimens
	 *@param deltaK the stress-intensity factor range
	 *@param a fitted crack size
	 *@param w width of specimen
	 *@param b thickness of specimen
	 *@param deltaP the difference in minimum and maximum value of the load force
	 **/
	private static double m(double a ,double w,double p, double b){

		double z=2*a/w;																
		double deltaK= (p/b)*Math.sqrt(((Math.PI * z)/(2*w))* (1/Math.cos((Math.PI * z)/2)));
		return deltaK;
		//deltaP conditiions

	}
	/**Eccentrically-loaded single edge crack specimen calculation of stress-intensity factor range
	 *@param z constant 'alpha' which differs in different specimens
	 *@param deltaK the stress-intensity factor range
	 *@param a fitted crack size
	 *@param w width of specimen
	 *@param b thickness of specimen
	 *@param deltaP the difference in minimum and maximum value of the load force
	 *@param F function to be determined
	/@param G a funtion of alpha
	 **/
	private static double eSE(double a ,double w,double p, double b){

		double z= a/w;																
		double F, G;
		G= 3.97- 10.88*z +26.25*z*z -38.9*z*z*z + 30.15*z*z*z*z - 9.27*z*z*z*z*z;
		F= Math.pow(z,0.5)*(1.4+z)*Math.pow((1-z),-1.5)*G;
		double deltaK= (p/(b*Math.pow(w,.5)))*F;
		return deltaK;

		//z needs to be less than one
	}
	/**centre cracked tension specimen calculation of g
	 *@param theta constant which differs in each specimen
	 *@param g a function of a and w
	 *@param a fitted crack size
	 *@param w width of specimen
	 **/
	private static double cC(double a ,double w){

		double theta= (Math.PI * a)/(2*w) ;																	
		double g= Math.pow((theta/(Math.cos(theta))),.5)
				* (0.7071-0.007*2*theta*theta +0.0070*Math.pow(theta,4));
		return g;

	}

	/**single edge notch three-point bend specimen calculation of g
	 *@param z constant 'alpha' which differs in each specimen
	 *@param g a function of a and w
	 *@param a fitted crack size
	 *@param w width of specimen
	 **/
	private static double sENB3(double a ,double w){

		double z= a/w;																	
		double g= ((6*Math.pow(z,.5))/((1+2*z)*Math.pow((1-z),1.5)))
				*(1.99-z*(1-z)*(2.15-3.93*z+2.7*z*z));
		return g;

	}
	/**single edge notch four-point bend specimen calculation of g
	 *@param theta constant which differs in each specimen
	 *@param g a function of a and w
	 *@param a fitted crack size
	 *@param w width of specimen
	 **/
	private static double sENB4(double a ,double w){

		double theta= (Math.PI * a)/(2*w) ;																
		double g= (3*Math.pow((2*Math.tan(theta)),.5))
				* ((0.923+0.199*Math.pow((1-Math.sin(theta)),4))/Math.cos(theta));
		return g;

	}

	/**single edge notch eight-point bend specimen calculation of g
	 *@param theta constant which differs in each specimen
	 *@param g a function of a and w
	 *@param a fitted crack size
	 *@param w width of specimen
	 **/
	private static double sENB8(double a ,double w){

		double theta= (Math.PI * a)/(2*w) ;																	
		double g= (3*Math.pow((2*Math.tan(theta)),.5))
				* ((0.923+0.199*Math.pow((1-Math.sin(theta)),4))/Math.cos(theta));
		return g;

	}

	/**single edge notch specimen calculation of g
	 *@param theta constant which differs in each specimen
	 *@param g a function of a and w
	 *@param a fitted crack size
	 *@param w width of specimen
	 **/
	private static double pinned(double a ,double w){ 
		double theta= (Math.PI * a)/(2*w);
		double z=a/w;
		double g;															// single edge notch pinned end tension specimen	
		g= (Math.pow((2*Math.tan(theta)),.5))
				* ((0.752+2.02*z+0.37*Math.pow((1-Math.sin(theta)),3))/Math.cos(theta));
		return g;
	}
	private static double clamped(double a ,double w){
		double z=a/w;				
		double g;															// single edge clamped-end tension specimen
		g= (Math.pow((1-z),-1.5))*
				(1.9878*Math.pow(z,.5)-
						2.9726*Math.pow(z,1.5)+
						6.9503*Math.pow(z,2.5)-
						14.4476*Math.pow(z,3.5)+
						10.0548*Math.pow(z,4.5)+
						3.4047*Math.pow(z,5.5)-
						8.7143*Math.pow(z,6.5)+
						3.7417*Math.pow(z,7.5));
		return g;
	}
	public double value(){
		return this.deltaK;
	}

}