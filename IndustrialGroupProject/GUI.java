import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import jssc.SerialPort; 
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class GUI {
	static SerialPort chosenPort;
	private JFrame frmExovaTestFrame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_9; //I apologise, this is never used, it was instigated for a previous text field and then discontinued after I had already coded the other text fields.
	static int x = 0;
	private static String[] portNames;

	private JTextField textField_8;
	private JTextField textField_10;//Similarly textField_11 isn't used for a similar reason, again, apologies.
	private JTextField textField_11;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		portNames = SerialPortList.getPortNames();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmExovaTestFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frmExovaTestFrame = new JFrame();
		frmExovaTestFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmExovaTestFrame.setBackground(Color.LIGHT_GRAY);
		frmExovaTestFrame.setResizable(false);
		frmExovaTestFrame.setTitle("Exova Test Frame Software");
		frmExovaTestFrame.setBounds(100, 100, 865, 512);
		frmExovaTestFrame.getContentPane().setLayout(null);
		frmExovaTestFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmExovaTestFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{ 
				String ObjButtons[] = {"Yes","No"};
				int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to end test?","Fatigue Crack Growth Rate Test",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
				if(PromptResult==JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});

		JLabel lblTitleLabel = new JLabel("Input Methods for FCGR Testing");
		lblTitleLabel.setBounds(201, 7, 438, 16);
		lblTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblTitleLabel.setBackground(Color.LIGHT_GRAY);
		frmExovaTestFrame.getContentPane().add(lblTitleLabel);

		JLabel label = new JLabel("Type of FCGR Testing:");
		label.setBounds(30, 43, 247, 23);
		frmExovaTestFrame.getContentPane().add(label);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"(None Selected)", "Increasing K", "Decreasing K", "Constant K"}));
		comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox.setBounds(289, 42, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox);

		JLabel label_0 = new JLabel("Sample Type:");
		label_0.setBounds(444, 42, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_0);

		JComboBox comboBox_0 = new JComboBox();
		comboBox_0.setModel(new DefaultComboBoxModel(new String[] {"(None Selected)", "C(T)", "M(T)", "ESE(T)","CC(T)","SEN B3","SEN B4","SEN B8","SEN(T) Pinned-end","SEN(T) Clamped-end"}));
		comboBox_0.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_0.setBounds(703, 42, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_0);

		JLabel label_1 = new JLabel("Wavefunction Applied to Sample:");
		label_1.setBounds(30, 77, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_1);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"(None Selected)", "SINE", "SQUARE", "RAMP", "TRIANG", "PULSE"}));
		comboBox_1.setBounds(289, 76, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_1);

		JLabel label_2 = new JLabel("Frequency of Wavefunction (Hz):");
		label_2.setBounds(30, 113, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_2);


		JLabel lblMaxForcekn = new JLabel("Max Force (kN):");
		lblMaxForcekn.setBounds(30, 147, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblMaxForcekn);

		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField.setColumns(10);
		textField.setBounds(289, 111, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_1.setColumns(10);
		textField_1.setBounds(289, 145, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_1);

		JLabel label_4 = new JLabel("Preffered FCGR Calculation Type");
		label_4.setBounds(444, 392, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_4);

		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setModel(new DefaultComboBoxModel(new String[] {"(None Selected)", "da/dn Three Point Polynomial", "da/dn Five Point Polynomial", "da/dn Seven Point Polynomial", "da/dn Nine Point Polynomial", "da/dn Secant"}));
		comboBox_7.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_7.setBounds(703, 392, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_7);


		JLabel label_5 = new JLabel("Termination Condition:");
		label_5.setBounds(30, 322, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_5);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"(None Selected)", "Cycles", "Crack Length", "Relative Voltage Change"}));
		comboBox_2.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_2.setBounds(289, 321, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_2);

		JLabel label_6 = new JLabel("Termination Specific:");
		label_6.setBounds(30, 358, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_6);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_3.setColumns(10);
		textField_3.setBounds(289, 356, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_3);

		JLabel lblNotchCurrent = new JLabel("Current Generator Voltage (V): ");
		lblNotchCurrent.setBounds(30, 253, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblNotchCurrent);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_4.setColumns(10);
		textField_4.setBounds(289, 253, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_4);

		JLabel lblNotchWidthmm = new JLabel("Y0 Value:");
		lblNotchWidthmm.setBounds(444, 184, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblNotchWidthmm);

		textField_5 = new JTextField();
		textField_5.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_5.setColumns(10);
		textField_5.setBounds(703, 184, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_5);
		textField_5.setEnabled(false);
		textField_5.setBackground(Color.LIGHT_GRAY);
		textField_5.setText("No Y0 Value Needed");
		comboBox_0.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_0.getSelectedItem() == "M(T)" || comboBox_0.getSelectedItem() == "ESE(T)"){
					textField_5.setEnabled(true);
					textField_5.setBackground(Color.WHITE);
					textField_5.setText("");
				}else{
					textField_5.setEnabled(false);
					textField_5.setBackground(Color.LIGHT_GRAY);
					textField_5.setText("No Y0 Value Needed");
				}
			}
		});

		JLabel lblSampleWidth = new JLabel("Sample Width:");
		lblSampleWidth.setBounds(444, 77, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblSampleWidth);

		textField_6 = new JTextField();
		textField_6.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_6.setColumns(10);
		textField_6.setBounds(703, 77, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_6);

		JLabel lblSampleThickness = new JLabel("Sample Thickness (mm): ");
		lblSampleThickness.setBounds(444, 112, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblSampleThickness);

		textField_7 = new JTextField();
		textField_7.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_7.setColumns(10);
		textField_7.setBounds(703, 112, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_7);


		JLabel lblPolynomialOrder = new JLabel("Polynomial Order:");
		lblPolynomialOrder.setBounds(30, 290, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblPolynomialOrder);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"(None Selected)", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBox_3.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_3.setBounds(289, 286, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_3);


		JLabel lblFrameVoltagePort = new JLabel("Sample Voltage Port:");
		lblFrameVoltagePort.setBounds(444, 254, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblFrameVoltagePort);

		JLabel lblFrameVoltagePort_1 = new JLabel("Frame Voltage Port:");
		lblFrameVoltagePort_1.setBounds(444, 219, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblFrameVoltagePort_1);

		JLabel lblReferenceVoltagePort = new JLabel("Reference Voltage Port:");
		lblReferenceVoltagePort.setBounds(444, 289, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblReferenceVoltagePort);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(portNames));
		comboBox_4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_4.setBounds(703, 219, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_4);


		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(portNames));
		comboBox_5.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_5.setBounds(703, 253, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_5);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(portNames));
		comboBox_6.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_6.setBounds(703, 289, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_6);

		textField_8 = new JTextField();
		textField_8.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_8.setColumns(10);
		textField_8.setBounds(289, 217, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_8);


		JLabel label_7 = new JLabel("Force Calibration (1V = kN):");
		label_7.setBounds(30, 220, 247, 23);
		frmExovaTestFrame.getContentPane().add(label_7);

		JLabel lblWavefunctionGenerator = new JLabel("Wavefunction Generator:");
		lblWavefunctionGenerator.setBounds(444, 323, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblWavefunctionGenerator);

		JLabel lblCurrentGenerator = new JLabel("Current Generator:");
		lblCurrentGenerator.setBounds(444, 358, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblCurrentGenerator);

		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setModel(new DefaultComboBoxModel(portNames));
		comboBox_8.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_8.setBounds(703, 322, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_8);

		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setModel(new DefaultComboBoxModel(portNames));
		comboBox_9.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		comboBox_9.setBounds(703, 358, 143, 27);
		frmExovaTestFrame.getContentPane().add(comboBox_9);
		

		JLabel lblMinForcekn = new JLabel("Min Force (kN):");
		lblMinForcekn.setBounds(30, 184, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblMinForcekn);
		
		textField_10 = new JTextField();
		textField_10.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_10.setColumns(10);
		textField_10.setBounds(289, 182, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_10);
		
		
		JLabel lblReferenceCrackSize = new JLabel("Reference Crack Size (mm):");
		lblReferenceCrackSize.setBounds(444, 144, 247, 23);
		frmExovaTestFrame.getContentPane().add(lblReferenceCrackSize);
		
		textField_11 = new JTextField();
		textField_11.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textField_11.setColumns(10);
		textField_11.setBounds(703, 144, 143, 26);
		frmExovaTestFrame.getContentPane().add(textField_11);


		Button button = new Button("Execute");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Calibrate.calibrate();

				String[] Data = new String[21];
				Data[0] = (String)comboBox.getSelectedItem();
				Data[1] = (String)comboBox_1.getSelectedItem();
				Data[2] = textField.getText();
				if(Data[1] == "Spectrum"){
					Data[2] = "Not Applicable";
				}
				Data[3] = textField_1.getText();
				Data[4] = (String)comboBox_7.getSelectedItem();
				Data[5] = (String)comboBox_2.getSelectedItem();
				Data[6] = textField_3.getText();
				Data[7] = textField_4.getText();
				if(textField_5.isEnabled()){
					Data[8] = textField_5.getText();
				}else{
					String message = "0";
					Data[8] = message;
				}
				Data[9] = (String)comboBox_0.getSelectedItem();
				Data[10] = textField_6.getText();
				Data[11] = textField_7.getText();
				Data[12] = (String)comboBox_3.getSelectedItem();
				Data[13] = (String)comboBox_4.getSelectedItem();
				Data[14] = (String)comboBox_5.getSelectedItem();
				Data[15] = (String)comboBox_6.getSelectedItem();
				Data[16] = textField_8.getText();	
				Data[17] = (String)comboBox_8.getSelectedItem();
				Data[18] = (String)comboBox_9.getSelectedItem();
				Data[19] = textField_10.getText();
				Data[20] = textField_11.getText();		



				if(Data[0] == "(No Selected)" || Data[1] == "(None Selected)" || Data[9] == "(None Selected)" || Data[5] == "(None Selected)" || Data[12] == "(None Selected)" || Data[2].isEmpty() ||
						Data[3].isEmpty() || Data[4] == "(None Selected)" || Data[6].isEmpty() || Data[7].isEmpty() || Data[8].isEmpty()|| Data[10].isEmpty()|| Data[11].isEmpty() ||
						Data[13] == Data[14] || Data[13] == Data[14] || Data[13] == Data[17] || Data[13] == Data[18] || Data[14] == Data[15] || Data[14] == Data[17] || Data[14] == Data[18] || Data[15] == Data[17] || Data[15] == Data[18] || Data[17] == Data[18]){
					JOptionPane.showMessageDialog(null, "Please Ensure All Options are Filled"); 
				}else{
					try{
						Store.set(Data);
						Analysis.Graph(null);
						textField.setEnabled(false);
						textField_1.setEnabled(false);
						comboBox_7.setEnabled(false);
						textField_3.setEnabled(false);
						textField_4.setEnabled(false);
						textField_5.setEnabled(false);
						textField_6.setEnabled(false);
						textField_7.setEnabled(false);
						comboBox.setEnabled(false);
						comboBox_0.setEnabled(false);
						comboBox_1.setEnabled(false);
						comboBox_2.setEnabled(false);
						comboBox_3.setEnabled(false);
						comboBox_4.setEnabled(false);
						comboBox_5.setEnabled(false);
						comboBox_6.setEnabled(false);
						comboBox_7.setEnabled(false);
						comboBox_8.setEnabled(false);
						comboBox_9.setEnabled(false);
						textField_8.setEnabled(false);
						textField_10.setEnabled(false);
						textField_11.setEnabled(false);

						WavefunctionSender.Initial();

						String portName = Store.getCGP();
						SerialPort serialPort = new SerialPort(portName);
						try {
							serialPort.openPort();//Open serial port
							serialPort.setParams(SerialPort.BAUDRATE_9600, 
									SerialPort.DATABITS_8,
									SerialPort.STOPBITS_1,
									SerialPort.PARITY_NONE);
							try{
								String current = Double.toString(Store.getCurrentGenVoltage());
								String message = "V1 " + current + "; OP1 1";
								serialPort.writeString(message + (char)10);//Write data to port
							}catch(SerialPortException ex) {
								System.out.println(ex);
							}





							try{
								serialPort.closePort();
							}catch(SerialPortException ex) {
								System.out.println(ex);
							}










						} catch (Exception error) {
							JOptionPane.showMessageDialog(null, "Error in creating graphs, please check all your inputs are of the correct data type.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
							error.printStackTrace();
						}
					}catch(IOException e1){} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}

				}
			});
		button.setBounds(433, 451, 117, 29);
		frmExovaTestFrame.getContentPane().add(button);

		Button button_1 = new Button("Open Voltage Graph");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					VoltageGraph.Graph(null); //This cannot be used currently due to the lack of DataQ compatibility. It can open but will not create a graph.
				} catch (Exception error) {
					System.out.println("Error in creating graphs");
					error.printStackTrace();
				}
			}
		});
		button_1.setBounds(289, 451, 150, 29);
		frmExovaTestFrame.getContentPane().add(button_1);

		


		}
	}