import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import jssc.SerialPort;
import jssc.SerialPortException;


public class VoltageGraph{
	
	static SerialPort chosenPort;
	static double x = 0;
	static double voltage = Store.getVoltage();
	static double outvoltage = Store.getOutputVoltage();
	
	public static void Graph(String[] args) throws IOException, InterruptedException {
		//Create window
		JFrame window = new JFrame();
		window.setTitle("Voltage Graph");
		window.setSize(600, 400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		// create a drop-down box at top of window
		JButton startButton = new JButton("Start");
		JPanel topPanel = new JPanel();
		topPanel.add(startButton);
		window.add(topPanel, BorderLayout.NORTH);
		
		// create line graph
		//should be able to add second line here
		XYSeries expected = new XYSeries("Voltage Expected");
		expected.setMaximumItemCount(100);
		XYSeries measured = new XYSeries("Voltage Measured");
		measured.setMaximumItemCount(100);
		XYSeriesCollection dataset = new XYSeriesCollection(expected);
		dataset.addSeries(measured);
		JFreeChart chart = ChartFactory.createXYLineChart("Voltage Reading", "Time", "Voltage",dataset);
		chart.getXYPlot().setRenderer(new XYSplineRenderer());
		window.add(new ChartPanel(chart), BorderLayout.CENTER);
		
		// get some data
		startButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0){
				if(startButton.getText().equals("Start")){
					startButton.setText("Stop");
					
					// create a new thread that listens for incoming data and adds to graph
					Thread thread = new Thread(){
						@Override public void run(){
														
							while( window.isVisible() && startButton.getText().equals("Stop")){
								try{
									TimeUnit.MILLISECONDS.sleep(50);
						
									
									SerialPort serialPort = new SerialPort(Store.getFVP());
								    try {
								        serialPort.openPort();//Open serial port
								        serialPort.setParams(9600, 8, 1, 0);//Set params.
								        serialPort.writeString("Start" + (char)10);
								        byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
								        serialPort.closePort();//Close serial port
								    }
								    catch (SerialPortException ex) {
								        System.out.println(ex);
								    }
									
									
									
									
									
									
									//will change to scanner.nextLine(); when connected
									double number1 = voltage;
									measured.add(x, number1);
								}	catch(Exception e) {}
								try{
								
									double number2 = Math.sin(2*Math.PI*Store.getWaveFrequency());
									expected.add(x++, number2);
								}	catch(Exception e) {}
								window.repaint();
							}
						}
					};
					thread.start();
				} else{

					startButton.setText("Start");
				}
			}
		});
		
		
		// shoe the window H:\My Documents\Exova_Group_Project
		window.setVisible(true);
	}



}



