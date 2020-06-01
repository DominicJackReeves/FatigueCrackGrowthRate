import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fazecast.jSerialComm.SerialPort;
/**This allows one to create a graph out of the following variables:
	Crack size,
	Cycle #,
	deltaK,
	da/dn Three Point Polynomial,
	da/dn Five Point Polynomial,
	da/dn Seven Point Polynomial,
	da/dn Nine Point Polynomial,
	da/dn Secant.
 **/


public class Analysis{

	private static double[] cracksize = new double[9];
	private static double[] cycleNumber = new double[9];
	static int i = 0;
	static SerialPort dummyVoltagePort;
	static SerialPort notchVoltagePort;

	public static void Graph(String[] args) throws IOException, InterruptedException {
		//Create window
		JFrame window = new JFrame();
		window.setTitle("Analysis Graph");
		window.setSize(600, 400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// create a drop-down box at top of window
		JButton startButton = new JButton("Start");
		JComboBox<String> xcoord = new JComboBox<String>();
		JComboBox<String> ycoord = new JComboBox<String>();
		JPanel topPanel = new JPanel();
		topPanel.add(xcoord);
		topPanel.add(ycoord);
		topPanel.add(startButton);
		window.add(topPanel, BorderLayout.NORTH);


		xcoord.addItem("(Select x axis)");
		xcoord.addItem("Crack size");
		xcoord.addItem("Cycle #");
		xcoord.addItem("deltaK");
		xcoord.addItem("da/dn Three Point Polynomial");
		xcoord.addItem("da/dn Five Point Polynomial");
		xcoord.addItem("da/dn Seven Point Polynomial");
		xcoord.addItem("da/dn Nine Point Polynomial");
		xcoord.addItem("da/dn Secant");

		ycoord.addItem("(Select y axis)");
		ycoord.addItem("Crack size");
		ycoord.addItem("Cycle #");
		ycoord.addItem("deltaK");
		ycoord.addItem("da/dn Three Point Polynomial");
		ycoord.addItem("da/dn Five Point Polynomial");
		ycoord.addItem("da/dn Seven Point Polynomial");
		ycoord.addItem("da/dn Nine Point Polynomial");
		ycoord.addItem("da/dn Secant");
		XYSeries expected = new XYSeries("Data Set");



		double startTime = System.currentTimeMillis()/1000;
		// get some data
		startButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0){


				if(startButton.getText().equals("Start") && !xcoord.getSelectedItem().equals("(Select x axis)") && !ycoord.getSelectedItem().equals("(Select y axis)")){
					startButton.setText("Stop");
					xcoord.setEnabled(false);
					ycoord.setEnabled(false);
					// create line graph
					expected.clear();
					XYSeries expected = new XYSeries("Data Set");
					expected.setMaximumItemCount(10);
					XYSeriesCollection dataset = new XYSeriesCollection(expected);
					JFreeChart chart = ChartFactory.createXYLineChart("Analysis Graph", xcoord.getSelectedItem().toString(), ycoord.getSelectedItem().toString(),dataset);
					chart.getXYPlot().setRenderer(new XYSplineRenderer());
					window.add(new ChartPanel(chart), BorderLayout.CENTER);



					//Get start time of analysis


					try {
						BufferedWriter out = new BufferedWriter(new FileWriter("data.csv", true));
						out.write("Cycle Number, Cracksize, Delta K,");
						out.newLine();


						// create a new thread that listens for incoming data and adds to graph
						Thread thread = new Thread(){
							@Override public void run(){

								dummyVoltagePort = SerialPort.getCommPort(Store.getRVP());
								dummyVoltagePort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

								notchVoltagePort = SerialPort.getCommPort(Store.getSVP());
								notchVoltagePort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

								double runTime = 0;
								while(window.isVisible() && !TerminationConditions.Check()){
									try {
										BufferedWriter out = new BufferedWriter(new FileWriter("Data.csv", true));

										TimeUnit.SECONDS.sleep(1);

										//calculate how long analysis has been running
										double currentTime = System.currentTimeMillis()/1000;

										runTime = currentTime - startTime;
										Store.setTime(runTime);
										//find frequency of cycles
										if(dummyVoltagePort.openPort() && notchVoltagePort.openPort()){
											Scanner scDummyVoltage = new Scanner(dummyVoltagePort.getInputStream());
											Scanner scNotchVoltage = new Scanner(notchVoltagePort.getInputStream());
											String dummyVoltage = scDummyVoltage.nextLine();
											dummyVoltage = dummyVoltage.replaceAll("[AVDC ]", "");
											String notchVoltage = scNotchVoltage.nextLine();
											notchVoltage = notchVoltage.replaceAll("[AVDC ]", "");
											try{
												Store.SetDummyNotchVoltage(Double.parseDouble(dummyVoltage));
											}catch(NumberFormatException f ){System.out.println(f);}
											try{
												Store.setNotchVoltage(Double.parseDouble(notchVoltage));
											}catch(NumberFormatException e ){System.out.println(e);}
											scDummyVoltage.close(); 
											scNotchVoltage.close();
										}
										else{System.out.println("COMM PORT FAILURE");}
										double frequency = Store.getWaveFrequency();
										String preferredCalcMethod = Store.getPrefferedCalcType();

										
										coord newCracksize = new coord();
										coord deltaK = new coord();
										coord dadN = new coord();
										
										cycleNumber = Store.getCycleNumber();
										cracksize = Store.getCrackSize();
										for (int j = 7; j >= 0; j--){
											cycleNumber[j+1] = cycleNumber[j];
											cracksize[j+1] = cracksize[j];
										}
										cycleNumber[0] = runTime*frequency;
										cracksize[0] = newCracksize.value(xcoord.getItemAt(1), preferredCalcMethod);
										double[] data = new double[6];

										data[0] = Store.getCycleNumber()[4];
										data[1] = Store.getNotchVoltage();
										data[2] = Store.getDummyNotchVoltage();
										data[3] = cracksize[4];
										data[4] = deltaK.value("deltaK", preferredCalcMethod);
										data[5] = dadN.value(preferredCalcMethod, "Cycle #");

										Store.setCrackSize(cracksize);
										Store.setCycleNumber(cycleNumber);

										out.write(Store.getCycleNumber()[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + ",");
										out.newLine();
										out.close(); 

										coord generate = new coord();
										double x = 0;
										double y = 0;
										x = generate.value(xcoord.getSelectedItem().toString(),ycoord.getSelectedItem().toString());
										y = generate.value(ycoord.getSelectedItem().toString(),xcoord.getSelectedItem().toString());

										switch(xcoord.getSelectedIndex()){
										case 1: x = data[0];
										case 2: x = data[3];
										case 3: x = data[4];
										case 4: x = generate.value(xcoord.getSelectedItem().toString(),ycoord.getSelectedItem().toString());
										case 5: x = generate.value(xcoord.getSelectedItem().toString(),ycoord.getSelectedItem().toString());
										case 6: x = generate.value(xcoord.getSelectedItem().toString(),ycoord.getSelectedItem().toString());
										case 7: x = generate.value(xcoord.getSelectedItem().toString(),ycoord.getSelectedItem().toString());
										case 8: x = generate.value(xcoord.getSelectedItem().toString(),ycoord.getSelectedItem().toString());
										}


										expected.add(x, y);



										window.repaint();
										i++;
									} catch (IOException | InterruptedException e) {System.out.println(runTime);}	


								}
								dummyVoltagePort.closePort();
								notchVoltagePort.closePort();

							}

						};
						thread.start();
					} catch (IOException e) {}
				} 
				else{
					xcoord.setEnabled(true);
					ycoord.setEnabled(true);
					startButton.setText("Start");
				}
			}
		});
		window.setVisible(true);
	}



}
