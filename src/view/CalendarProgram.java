package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import view.CalendarWindow.btnNext_Action;
import view.CalendarWindow.btnPrev_Action;
import view.CalendarWindow.cmbYear_Action;
import view.CalendarWindow.tblCalendarRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class CalendarProgram extends JFrame {
	private final String FRAME_TITLE = "Calendar Program";
	static int realYear, realMonth, realDay, currentYear, currentMonth;

	private static JTable table;
	private static DefaultTableModel mtblCalendar;
	private JScrollPane stblCalendar;
	private JTextField txtMonth;
	private JButton logoutButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		CalendarProgram window = new CalendarProgram();
	}

	/**
	 * Create the application.
	 */
	public CalendarProgram() {
		this.setTitle(FRAME_TITLE);
		this.setResizable(false);
		this.setBounds(100, 100, 600, 375);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		//create controls
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
			
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 296, 341);
		this.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(6, 6, 284, 37);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		txtMonth = new JTextField();
		txtMonth.setText("Month");
		txtMonth.setBounds(6, 6, 94, 28);
		panel_4.add(txtMonth);
		txtMonth.setColumns(10);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(226, 6, 37, 28);
		panel_4.add(spinner);
		
		JButton button = new JButton("<<");
		button.setBounds(112, 7, 45, 29);
		panel_4.add(button);
		
		JButton button_1 = new JButton(">>");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(150, 7, 45, 29);
		panel_4.add(button_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(6, 55, 284, 280);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		table = new JTable(mtblCalendar);
//		table.setBounds(6, 6, 272, 193);
		stblCalendar = new JScrollPane(table);
		panel_5.add(stblCalendar);
		stblCalendar.setBounds(6,6,272,268);
		
		//No resize/reorder
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		
		//set row/column count
		table.setRowHeight(38);
		mtblCalendar.setColumnCount(0);
		mtblCalendar.setRowCount(6);
		
		//Single cell selection
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Add headers to mtblCalendar
		String[] headers = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}; 
		for (int i=0; i<7; i++)
			mtblCalendar.addColumn(headers[i]);
		
		//Set background
		table.getParent().setBackground(table.getBackground()); 
		
		//No resize/reorder
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		
		//Get real month and year
		GregorianCalendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		realDay = cal.get(Calendar.DAY_OF_MONTH); //Get day of month
		realMonth = cal.get(Calendar.MONTH); //Get month of year
		realYear = cal.get(Calendar.YEAR); // Get year
		currentMonth = realMonth;
		currentYear = realYear;		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(314, 6, 280, 341);
		this.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 6, 268, 37);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnAddNewEvent = new JButton("Add New Event");
		btnAddNewEvent.setBounds(6, 6, 124, 29);
		panel_2.add(btnAddNewEvent);
		
		logoutButton = new JButton("Log out");
		logoutButton.setBounds(138, 6, 124, 29);
		panel_2.add(logoutButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(6, 55, 268, 280);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 10, 248, 260);
		panel_3.add(textArea);
		
		//Refresh calendar
		refreshCalendar(realMonth, realYear); //Refresh calendar
		
		this.setVisible(true);
	}
	
	public static void refreshCalendar(int month, int year) {
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month
	
		//Clear table
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++)
				mtblCalendar.setValueAt(null, i, j);	
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month,1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //number of days of current month
		som = (cal.get(GregorianCalendar.DAY_OF_WEEK) + 5) % 7 + 1; 
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}		
		//Apply renderers: If so: create a tblCalendarRenderer-class which extends DefaultTableCellRenderer
				table.setDefaultRenderer(table.getColumnClass(0), new tblCalendarRenderer());
	}
	
	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 5 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}
		
	public void addLogoutButtonListener(ActionListener listenerForLogoutButton) {
		logoutButton.addActionListener(listenerForLogoutButton);
	}
	
}
