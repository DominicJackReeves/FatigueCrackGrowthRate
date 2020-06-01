public class Secant{
	private double dadn;
	private double ar;
	private double deltaK;
	public Secant(double a, double b, double N, double M, double deltaP){
		//read in two crack sizes 'a' and 'b'
		//and two cycle numbers such that (a,N) and (b,M)
		//calculate midpoint of 'a' and 'b' to give fitted crack length aFIT
		ar = (a+b)/2;
		dadn = (b-a)/(M-N);
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