import jssc.SerialPort; 
import jssc.SerialPortException;
import jssc.SerialPortEvent; 
import jssc.SerialPortEventListener;




public class WavefunctionSender {

	private static SerialPort serialPort;
	/**
	 * Launch the application.
	 */
	public static void Initial(){

		String portName = Store.getWGP();
		serialPort = new SerialPort(portName);
		try {
			serialPort.openPort();//Open serial port
			serialPort.setParams(SerialPort.BAUDRATE_9600, 
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		}catch(SerialPortException ex) {
			System.out.println(ex);
		}




		try{
			String wavefrequency = Double.toString(Store.getWaveFrequency());

			double conversion = Store.getVoltageConversion();
			String function = Store.getFunction();
			Double maxValue = Store.getAmplitudeMax()/conversion ;
			Double minValue = Store.getAmplitudeMin()/conversion;
			String sentMessageMax = Double.toString(maxValue);
			String sentMessageMin = Double.toString(minValue);

			String message = "FREQ " + wavefrequency + "; WAVE " + function;
			serialPort.writeString(message + (char)10);//Write data to port
			message =  "HILVL " + sentMessageMax + "; LOLVL " + sentMessageMin;
			serialPort.writeString(message + (char)10);
			message = "AMPL " + Double.toString(maxValue - minValue);
			serialPort.writeString(message + (char)10);
			message = "OUTPUT ON";
			serialPort.writeString(message + (char)10);

			



		}catch(SerialPortException ex) {
			System.out.println(ex);
		}





		try{
			serialPort.closePort();
		}catch(SerialPortException ex) {
			System.out.println(ex);
		}


	}

	/*
	 * This part of the code is still incomplete and requires me to get information regarding what amplitude is with regards to the other values
	 * and possibly add more values to the GUI. Again, not complete yet! 
	 * 
	 *

	public static void Regular(){
		switch(Store.getType()){
		case "Increasing k":
			
		case "Constant K":
			String portName = Store.getWGP();
			serialPort = new SerialPort(portName);
			try {
				serialPort.openPort();//Open serial port
				serialPort.setParams(SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
			}catch(SerialPortException ex) {
				System.out.println(ex);
			}



			try{
				String amplitude = Double.toString(Store.getAmplitudeMax() - Store.getAmplitudeMin()); 
				String message = "AMPL " + amplitude;
				serialPort.writeString(message + (char)10);//Write data to port
			}catch(SerialPortException ex) {
				System.out.println(ex);
			}





			try{
				serialPort.closePort();
			}catch(SerialPortException ex) {
				System.out.println(ex);
			}
			break;
		}

	} */
}



