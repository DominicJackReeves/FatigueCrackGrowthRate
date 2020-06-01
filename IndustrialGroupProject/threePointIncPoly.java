//This is a piece of three point increasing polynomial code that should find the fitted crack size over any number of values.

public class threePointIncPoly	{
	private double ar;
	private double dadn;
	private double x;
	private double deltaK;
	
	
	public threePointIncPoly(){
		ar = 0;
		dadn = 0;
		x = 0;
		deltaK = 0;
	}
	
	
	
	public threePointIncPoly(double[] a, double[] N, double deltaP)	{
		//Declaring variables for terms used in the seven point approximation.
		double c1	 = .5*(N[2]+N[0]);
		double c2	 = .5*(N[2]-N[0]);
		double sx	 = 0;
		double sx2	 = 0;
		double sx3	 = 0;
		double sx4	 = 0;
		double sy	 = 0;
		double syx	 = 0;
		double syx2	 = 0;
		//This loop generates the values of the variables 
		for(int i=0; i<3; i++){
			double x = (N[i]-c1)/c2;
			double y = a[i];
			sx  = sx + x;
			sx2 = sx2 + x*x;
			sx3 = sx3 + x*x*x;
			sx4 = sx4 + x*x*x*x;
			sy  = sy + y;
			syx = syx + x*y;
			syx2= syx2 + x*x*y;
		}
		
		
		//generates first regression parameter bb1
		double Term1 = (sx2*sx4 - sx3*sx3);
		double Term2 = (sx*sx4 - sx2*sx3);
		double Term3 = (sx*sx3 - sx2*sx2);
		double Denom = 3*Term1-sx*Term2+sx2*Term3;
		double Numer2= sy*Term1-syx*Term2+syx2*Term3;
		double bb1 = Numer2/Denom;
		
		//generates second regression parameter bb2
		double Term4 = (syx*sx4 - syx2*sx3);
		double Term5 = (sy*sx4-syx2*sx2);
		double Term6 = (sy*sx3 - syx*sx2);
		double Numer3= 3*Term4 - sx*Term5 +sx2 * Term6;
		double bb2 = Numer3/Denom;
		
		//generates third regression parameter bb3
		double Term7 = (sx2*syx2 - sx3*syx);
		double Term8 = (sx*syx2-sx3*sy);
		double Term9 = (sx*syx - sx2*sy);
		double Numer4= 3*Term7 - sx*Term8 +sx2 * Term9;
		double bb3 = Numer4/Denom;
		
		//more variables declared
		double yb = sy/3;
		double rss= 0;
		double tss=0;
		
		//another loop to determine values
		for(int i=0; i<3; i++){
			double x = (N[i]-c1)/c2;
			double yhat = bb1 + bb2*x + bb3*x*x;
			rss = rss + (a[i]-yhat)*(a[i]-yhat);
			tss = tss + (a[i]-yb)*(a[i]-yb);
		}
		
		//calculating final results for central point a[3]
		//ar is fitted crack size
		//dadn is da/dN
		//double r2 = 1- rss/tss;
		dadn = bb2/c2 + 2*bb3*(N[2]-c1)/(c2*c2);
		x = (N[1]-c1)/c2;
		ar = bb1 + bb2*x + bb3*x*x;
		DeltaK K = new DeltaK(ar, deltaP);
		deltaK = K.value();
	}
	public double getdadn(){
		return this.dadn;
	}
	public double getar(){
		return this.ar;
	}
	public double getdeltaK(){
		return this.deltaK;
	}

}