import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
This only calculates the value of coord based upon what the othercoord is. 
DeltaK depends upon the order of the polynomial fit.
Use twice, switching around coord and othercoord, to generate both coordinates. 
**/


//Lots of errors in here due to getAmplitude not being a function, I just want to clarify which should be getAmplitude (which we will initialise) and which should be getMaxAmplitude.
public class coord {
	public double value(Object coord, Object othercoord) throws IOException{
		double x = 0;
		if(coord == "Crack size"){
			if(Store.getSampleType() == "C(T)" || Store.getSampleType() == "M(T)" || Store.getSampleType() == "ESE(T)"){
				x = crackSizeCalc.value();
			}
			else{
				x = Calibrate.curve(Store.getNotchVoltage());
			}
		}else if(coord == "Cycle #"){
			x = Store.getTime()*Store.getWaveFrequency();
		}else if(coord == "deltaK"){
			if(othercoord == "da/dn Three Point Polynomial"){
				threePointIncPoly three = threePoint();
				x = three.getdeltaK();
			}else if(othercoord == "da/dn Five Point Polynomial"){
				fivePointIncPoly five = fivePoint();
				x = five.getdeltaK();
			}else if(othercoord == "da/dn Seven Point Polynomial"){
				sevenPointIncPoly seven = sevenPoint();
				x = seven.getdeltaK();
			}else if(othercoord == "da/dn Nine Point Polynomial"){
				ninePointIncPoly nine = ninePoint();
				x = nine.getdeltaK();
			}else if(othercoord == "da/dn Secant"){
					double a = Store.getCrackSize()[3];
					double N = Store.getCycleNumber()[3];
					//what should be used as deltaP
					double deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();
						
					double b = Store.getCrackSize()[5];
					double M = Store.getCycleNumber()[5];
					Secant secant = new Secant(a, b, N, M, deltaP);
					x = secant.getdeltaK();
			}else{
					double a = Store.getCrackSize()[4];
					double deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();
					DeltaK K = new DeltaK(a, deltaP);
					x = K.value();
			}
				
		}else if(coord == "da/dn Three Point Polynomial"){
					threePointIncPoly three = threePoint();
					x = three.getdadn();
		}else if(coord == "da/dn Five Point Polynomial"){
					fivePointIncPoly five = fivePoint();
					x = five.getdadn();
		}else if(coord == "da/dn Seven Point Polynomial"){
					sevenPointIncPoly seven = sevenPoint();
					x = seven.getdadn();
		}else if(coord == "da/dn Nine Point Polynomial"){
					ninePointIncPoly nine = ninePoint();
					x = nine.getdadn();
		}else if(coord == "da/dn Secant"){
				double a = Store.getCrackSize()[3];
				double N = Store.getCycleNumber()[3];
				//what should be used as deltaP
				double deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();	
				double b = Store.getCrackSize()[5];
				double M = Store.getCycleNumber()[5];
				Secant secant = new Secant(a, b, N, M, deltaP);
				x = secant.getdadn();
		}
		return x;
	}
	private threePointIncPoly threePoint() {
		threePointIncPoly three = new threePointIncPoly();
				double[] a = new double[3];
				double[] N = new double[3];
				double deltaP;
				a[0] = Store.getCrackSize()[3];
				N[0] = Store.getCycleNumber()[3];
				a[1] = Store.getCrackSize()[4];
				N[1] = Store.getCycleNumber()[4];
				deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();
				a[2] = Store.getCrackSize()[5];
				N[2] = Store.getCycleNumber()[5];
				three = new threePointIncPoly(a, N, deltaP);
		return three;
	}
	private fivePointIncPoly fivePoint(){
		fivePointIncPoly five = new fivePointIncPoly();
				double[] a = new double[5];
				double[] N = new double[5];
				double deltaP;
				a[0] = Store.getCrackSize()[2];
				N[0] = Store.getCycleNumber()[2];
				a[1] = Store.getCrackSize()[3];
				N[1] = Store.getCycleNumber()[3];
				a[2] = Store.getCrackSize()[4];
				N[2] = Store.getCycleNumber()[4];
				deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();
				a[3] = Store.getCrackSize()[5];
				N[3] = Store.getCycleNumber()[5];
				a[4] = Store.getCrackSize()[6];
				N[4] = Store.getCycleNumber()[6];
				five = new fivePointIncPoly(a, N, deltaP);
		return five;
	}
	private sevenPointIncPoly sevenPoint(){
		sevenPointIncPoly seven = new sevenPointIncPoly();
			double[] a = new double[7];
			double[] N = new double[7];
			double deltaP;
			a[0] = Store.getCrackSize()[1];
			N[0] = Store.getCycleNumber()[1];
			a[1] = Store.getCrackSize()[2];
			N[1] = Store.getCycleNumber()[2];
			a[2] = Store.getCrackSize()[3];
			N[2] = Store.getCycleNumber()[3];
			a[3] = Store.getCrackSize()[4];
			N[3] = Store.getCycleNumber()[4];
			deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();
			a[4] = Store.getCrackSize()[5];
			N[4] = Store.getCycleNumber()[5];
			a[5] = Store.getCrackSize()[6];
			N[5] = Store.getCycleNumber()[6];
			a[6] = Store.getCrackSize()[7];
			N[6] = Store.getCycleNumber()[7];
			seven = new sevenPointIncPoly(a, N, deltaP);
		return seven;
	}
	private ninePointIncPoly ninePoint(){
		ninePointIncPoly nine = new ninePointIncPoly();
			double[] a = new double[9];
			double[] N = new double[9];
			double deltaP;
			a[0] = Store.getCrackSize()[0];
			N[0] = Store.getCycleNumber()[0];
			a[1] = Store.getCrackSize()[1];
			N[1] = Store.getCycleNumber()[1];
			a[2] = Store.getCrackSize()[2];
			N[2] = Store.getCycleNumber()[2];
			a[3] = Store.getCrackSize()[3];
			N[3] = Store.getCycleNumber()[3];
			deltaP = Store.getAmplitudeMax() - Store.getAmplitudeMin();
			a[4] = Store.getCrackSize()[4];
			N[4] = Store.getCycleNumber()[4];
			a[5] = Store.getCrackSize()[5];
			N[5] = Store.getCycleNumber()[5];
			a[6] = Store.getCrackSize()[6];
			N[6] = Store.getCycleNumber()[6];
			a[7] = Store.getCrackSize()[7];
			N[7] = Store.getCycleNumber()[7];
			a[8] = Store.getCrackSize()[8];
			N[8] = Store.getCycleNumber()[8];
			nine = new ninePointIncPoly(a, N, deltaP);
		return nine;
	}
}