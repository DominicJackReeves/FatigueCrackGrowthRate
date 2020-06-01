import jssc.SerialPort; 
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import jssc.SerialPortEvent; 
import jssc.SerialPortEventListener;
import jssc.SerialPort; import jssc.SerialPortEvent; import jssc.SerialPortEventListener; import jssc.SerialPortException;



public class NOPE {

	private JFrame frmExovaTestFrame;
	private JTextField textField_5;
	private static String[] portNames;
	private static SerialPort serialPort;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		String message = "";
		serialPort = new SerialPort("COM5");
		try {
			serialPort.openPort();//Open serial port
			serialPort.setParams(SerialPort.BAUDRATE_9600, 
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			message = sc.next();
			do{
			serialPort.writeString(message + (char)10);//Write data to port
			byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
			try{
				
			System.out.write(buffer);
			}catch(IOException e){};
			}while(message != "End");
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
	 * In this class must implement the method serialEvent, through it we learn about 
	 * events that happened to our port. But we will not report on all events but only 
	 * those that we put in the mask. In this case the arrival of the data and change the 
	 * status lines CTS and DSR
	 */
	static class SerialPortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if(event.isRXCHAR()){//If data is available
				if(event.getEventValue() == 10){//Check bytes count in the input buffer
					//Read data, if 10 bytes available 
					try {
						byte buffer[] = serialPort.readBytes(10);
						String out =  buffer.toString();
						System.out.println(out);
						
					}
					catch (SerialPortException ex) {
						System.out.println(ex);
					}
				}
			}
			else if(event.isCTS()){//If CTS line has changed state
				if(event.getEventValue() == 1){//If line is ON
					System.out.println("CTS - ON");
				}
				else {
					System.out.println("CTS - OFF");
				}
			}
			else if(event.isDSR()){///If DSR line has changed state
				if(event.getEventValue() == 1){//If line is ON
					System.out.println("DSR - ON");
				}
				else {
					System.out.println("DSR - OFF");
				}
			}
		}
	}
}