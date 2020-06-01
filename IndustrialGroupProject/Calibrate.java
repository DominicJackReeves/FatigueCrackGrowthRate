import org.apache.commons.math3.fitting.WeightedObservedPoints;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;

/**
Use for CCT and SENB specimens
With known crack size vs voltage data, this will fit a polynomial curve to the said data.
This polynomial will be used for all further cracksize calculations.
**/
public class Calibrate{
	
	private static double[] array;

	public static void calibrate(){
		final WeightedObservedPoints obs = new WeightedObservedPoints();		
		int polynomial = Store.getPoly();                                         //polynomial order
		try{
			File measuredData = new File("Calibration.csv");					//get crack size vs voltage data from csv file
			Scanner scanner = new Scanner(measuredData);
			while(scanner.hasNextLine()){
				try{
					String[] line1 = scanner.nextLine().split(",");
					double x = Double.parseDouble(line1[0]);
					double y = Double.parseDouble(line1[1]);
					obs.add(x, y);
				}catch (Exception e){}
			}
			scanner.close();
		} catch (FileNotFoundException e) {} catch (Exception e1) {}
		// Instantiate a third-degree polynomial fitter.
		final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(polynomial);

		// Retrieve fitted parameters (coefficients of the polynomial function).
		final double[] coeff = fitter.fit(obs.toList());
		array = coeff.clone();
	}

//Form polynomial
	public static Double curve(Double x){
		double sum = 0;
		for(int i = 0; i < array.length; i++){
			sum = sum + array[i]*Math.pow(x, i);
		}
		return sum;
	}


}