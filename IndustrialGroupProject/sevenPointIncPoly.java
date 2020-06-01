//This is basically an attempt at a 'translation' of the main loop from the
//example code in the american test standards, I apologise for the lack of
//notes but that's largely because I'm not confident in the maths myself

//I tested this with some of the data from the american stardards and it works
//I just don't quite know why

public class sevenPointIncPoly{
	private double ar = 0;
	private double dadn = 0;
	private double x = 0;
	private double deltaK;
	public sevenPointIncPoly(){}
	public sevenPointIncPoly(double[] a, double[] N, double deltaP)	{
		//Declaring variables for terms used in the seven point approximation.
		double c1	 = .5*(N[6]+N[0]);
		double c2	 = .5*(N[6]-N[0]);
		double sx	 = 0;
		double sx2	 = 0;
		double sx3	 = 0;
		double sx4	 = 0;
		double sy	 = 0;
		double syx	 = 0;
		double syx2	 = 0;
		//This loop generates the values of the variables 
		for(int i=0; i<7; i++){
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
		double Denom = 7*Term1-sx*Term2+sx2*Term3;
		double Numer2= sy*Term1-syx*Term2+syx2*Term3;
		double bb1 = Numer2/Denom;
		
		//generates second regression parameter bb2
		double Term4 = (syx*sx4 - syx2*sx3);
		double Term5 = (sy*sx4-syx2*sx2);
		double Term6 = (sy*sx3 - syx*sx2);
		double Numer3= 7*Term4 - sx*Term5 +sx2 * Term6;
		double bb2 = Numer3/Denom;
		
		//generates third regression parameter bb3
		double Term7 = (sx2*syx2 - sx3*syx);
		double Term8 = (sx*syx2-sx3*sy);
		double Term9 = (sx*syx - sx2*sy);
		double Numer4= 7*Term7 - sx*Term8 +sx2 * Term9;
		double bb3 = Numer4/Denom;
		
		//more variables declared
		double yb = sy/7;
		double rss= 0;
		double tss=0;
		
		//another loop to determine values
		for(int i=0; i<7; i++){
			double x = (N[i]-c1)/c2;
			double yhat = bb1 + bb2*x + bb3*x*x;
			rss = rss + (a[i]-yhat)*(a[i]-yhat);
			tss = tss + (a[i]-yb)*(a[i]-yb);
		}

		System.out.println(bb1);
		System.out.println(bb2);
		System.out.println(bb3);
		//calculating final results for central point a[3]
		//ar is fitted crack size
		//dadn is da/dN
		//double r2 = 1- rss/tss;
		dadn = bb2/c2 + 2*bb3*(N[3]-c1)/(c2*c2);
		x = (N[3]-c1)/c2;
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