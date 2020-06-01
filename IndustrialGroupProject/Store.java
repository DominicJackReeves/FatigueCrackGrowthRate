public class Store	{
	private static String type;
	private static String function;
	private static double waveFrequency = 1;
	private static double amplitudeMax;
	private static String testFrequency;
	private static String endType;
	private static double endSpecific;
	private static double currentGenVoltage;
	private static double notchVoltage;
	private static double dummyNotchVoltage;
	private static double Y0;
	private static double voltageConversion;
	private static String sampleType;
	private static double sampleWidth;
	private static double sampleThickness;
	private static int polynomialOrder;
	private static double time;
	public static double[] cycleNumber = {0,0,0,0,0,0,0,0,0};
	public static double[] crackSize = {0,0,0,0,0,0,0,0,0};
	public static double wfInputVoltage;
	public static double wfOutputVoltage;
	public static String frameVoltagePort;
	public static String sampleVoltagePort;
	public static String referenceVoltagePort;
	public static String WavefunctionGenPort;
	public static String CurrentGenPort;
	private static double amplitudeMin;
	

	
	public static void set(String[] data){
		type = data[0];
		function = data[1];
		waveFrequency = Double.parseDouble(data[2]);
		amplitudeMax = Double.parseDouble(data[3]);
		testFrequency = data[4];
		endType = data[5];
		endSpecific = Double.parseDouble(data[6]);
		currentGenVoltage = Double.parseDouble(data[7]);
		Y0 = Integer.parseInt((data[8]));
		sampleType = data[9];
		sampleWidth = Double.parseDouble(data[10]);
		sampleThickness = Double.parseDouble(data[11]);
		polynomialOrder = Integer.parseInt(data[12]);
		frameVoltagePort = data[13];
		sampleVoltagePort = data[14];
		referenceVoltagePort = data[15];
		voltageConversion = Double.parseDouble(data[16]);
		WavefunctionGenPort = data[17];
		CurrentGenPort = data[18];
		amplitudeMin = Double.parseDouble(data[19]);
		
	}
	public static void setTime(double newTime){
		time = newTime;
	}
	public static void setCycleNumber(double[] cyclenumber){
		cycleNumber = cyclenumber;
	}
	public static void SetDummyNotchVoltage(double newVoltage){
		dummyNotchVoltage = newVoltage;
	}
	public static void setNotchVoltage(double newVoltage1){
		notchVoltage = newVoltage1;
	}
	public static void outputVoltgae(double newVoltage){
		wfOutputVoltage = newVoltage;
	}
	public static void setCrackSize(double[] newCrackSize){
		crackSize = newCrackSize;
	}
	public static void setVoltage(double newVoltage){
		wfInputVoltage = newVoltage;
	}
	public static String getType(){
		return type;
	}
	public static String getFunction(){
		return function;
	}
	public static double getWaveFrequency(){
		return waveFrequency;
	}
	public static double getAmplitudeMin(){
		return amplitudeMin;
	}
	public static double getAmplitudeMax(){
		return amplitudeMax;
	}
	public static String getPrefferedCalcType(){
		return testFrequency;
	}
	public static String getEndType(){
		return endType;
	}
	public static double getEndSpecific(){
		return endSpecific;
	}
	public static double getNotchVoltage(){
		return notchVoltage;
	}
	public static double getCurrentGenVoltage(){
		return currentGenVoltage;
	}
	public static double getY0(){
		return Y0;
	}
	public static String getSampleType(){
		return sampleType;
	}
	public static double getSampleWidth(){
		return sampleWidth;
	}
	public static double getSampleThickness(){
		return sampleThickness;
	}
	public static double getVoltageConversion(){
		return voltageConversion;
	}
	public static int getPoly(){
		return polynomialOrder;
	}
	public static String getFVP(){
		return frameVoltagePort;
	}
	public static String getSVP(){
		return sampleVoltagePort;
	}
	public static String getRVP(){
		return referenceVoltagePort;
	}
	public static String getWGP(){
		return WavefunctionGenPort;
	}
	public static String getCGP(){
		return CurrentGenPort;
	}
	public static double getTime(){
		return time;
	}
	public static double[] getCycleNumber(){
		return cycleNumber;
	}
	public static double[] getCrackSize(){
		return crackSize;
	}
	public static double getVoltage(){
		return wfInputVoltage;
	}
	public static double getOutputVoltage(){
		return wfOutputVoltage;
	}
	public static double getDummyNotchVoltage(){
		return dummyNotchVoltage;
	}

}