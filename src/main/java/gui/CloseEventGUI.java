package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Prediction;
import domain.Question;


public class CloseEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel jLabelPredictions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Predictions")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	
	private JButton btnCloseQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CloseEventGUI.btnCloseQuestion.text")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private JButton btnCloseEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CloseEventGUI.btnCloseEvent.text")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private int pre;
	private int ques;
	private Event ev;
	private Integer evNum;
	
	private boolean closed;
	

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePredictions = new JScrollPane();

	private ArrayList<Date> datesWithEventsCurrentMonth = new ArrayList<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePredictions = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPredictions;


	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Num"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Num"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private String[] columnNamesPredictions = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Num"), 
			ResourceBundle.getBundle("Etiquetas").getString("Prediction"),
			ResourceBundle.getBundle("Etiquetas").getString("Gains")

	};
	

	public CloseEventGUI()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	private void jbInit() throws Exception
	{
		btnCloseQuestion.setEnabled(false);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));
		getContentPane().setLayout(null);
		jLabelEventDate.setBounds(40, 15, 140, 25);

		this.getContentPane().add(jLabelEventDate);
		jLabelQueries.setBounds(43, 241, 334, 14);
		this.getContentPane().add(jLabelQueries);
		jLabelEvents.setBounds(295, 19, 259, 16);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setBounds(40, 411, 130, 30);

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose);

		BLFacade facade = EntryGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
		jCalendar1.setBounds(40, 50, 225, 150);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			
			
			{
				
				btnCloseQuestion.setEnabled(false);
				btnCloseEvent.setEnabled(false);
				
				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));



					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = EntryGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);



					try {
						
						int rowCount = tableModelEvents.getRowCount();
						for (int i=0; i<rowCount; i--) {
							tableModelEvents.removeRow(i);
						}
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=EntryGUI.getBusinessLogic();

						ArrayList<Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1);
		
		/*
		 * int rowCountEvents = tableModelEvents.getRowCount(); for (int i=0;
		 * i<rowCountEvents; i--) { tableModelEvents.removeRow(i); }
		 */

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				int i=tableEvents.getSelectedRow();
				ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				evNum=(Integer)tableModelEvents.getValueAt(i,0);
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty()){
					btnCloseEvent.setEnabled(true);
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				}
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});


		
		/*
		 * int rowCountQueries = tableModelQueries.getRowCount(); for (int i=0;
		 * i<rowCountQueries; i--) { tableModelEvents.removeRow(i); }
		 */
		
		tableQueries.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				int i=tableQueries.getSelectedRow();
				
				ques=(int) tableModelQueries.getValueAt(i,0);
				
				ArrayList<Prediction> queries= facade.getPrediction(ques);

				tableModelPredictions.setDataVector(null, columnNamesPredictions);

				if (queries.isEmpty())
					jLabelPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("NoPredictions"));
				else
					jLabelPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedQuestion"));
				
				for (domain.Prediction p: queries) {
					
					Vector<Object> row = new Vector<Object>();
					row.add(p.getPredictionNumber());
					row.add(p.getResult());
					row.add(p.getGains());
					tableModelPredictions.addRow(row);
				}
				

				tablePredictions.getColumnModel().getColumn(0).setPreferredWidth(20);
				tablePredictions.getColumnModel().getColumn(1).setPreferredWidth(50);
				tablePredictions.getColumnModel().getColumn(2).setPreferredWidth(50);

			}
		});
		scrollPaneEvents.setBounds(292, 50, 346, 141);
		scrollPaneEvents.setViewportView(tableEvents);

		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		scrollPaneQueries.setBounds(40, 270, 324, 116);
		scrollPaneQueries.setViewportView(tableQueries);

		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);


		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		scrollPanePredictions.setBounds(389, 267, 276, 119);
		scrollPanePredictions.setViewportView(tablePredictions);

		tableModelPredictions = new DefaultTableModel(null, columnNamesPredictions);

		tablePredictions.setModel(tableModelPredictions);
		tablePredictions.getColumnModel().getColumn(0).setPreferredWidth(20);
		tablePredictions.getColumnModel().getColumn(1).setPreferredWidth(150);

		this.getContentPane().add(scrollPaneEvents);
		this.getContentPane().add(scrollPaneQueries);
		this.getContentPane().add(scrollPanePredictions);
		jLabelPredictions.setBounds(389, 240, 281, 16);
		
		getContentPane().add(jLabelPredictions);
		
		getContentPane().add(jLabelPredictions);
		
		/*
		 * int rowCountPredictions = tableModelQueries.getRowCount(); for (int i=0;
		 * i<rowCountPredictions; i--) { tableModelEvents.removeRow(i); }
		 */
		
		
		tablePredictions.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int i=tablePredictions.getSelectedRow();
				
				pre =(int) tableModelPredictions.getValueAt(i,0);
				
				btnCloseQuestion.setEnabled(true);
				
			}});
		
				
		btnCloseQuestion.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
					try {
						BLFacade facade = EntryGUI.getBusinessLogic();
						closed= facade.closeQuestion(ques, pre);
						
						
					} catch (Exception e2) {
						e2.getStackTrace();
					}
					if(!closed) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("NotClosed"));
					}
					else {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("Closed"));
						if(facade.emptyEvent(ques)==true) {
							btnCloseEvent.setEnabled(true);
							btnCloseQuestion.setEnabled(false);
							JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("QuestClosed"));
						}
						else
							JOptionPane.showMessageDialog(null ,ResourceBundle.getBundle("Etiquetas").getString("EventClose"));
					}
				}
				
			});
		
		btnCloseQuestion.setBounds(466, 413, 138, 26);
		getContentPane().add(btnCloseQuestion);
		btnCloseEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BLFacade facade = EntryGUI.getBusinessLogic();
					facade.closeEvent(evNum);
					JOptionPane.showMessageDialog(null ,ResourceBundle.getBundle("Etiquetas").getString("EventClosed"));
				} catch (Exception e2) {
					e2.getStackTrace();
					JOptionPane.showMessageDialog(null ,ResourceBundle.getBundle("Etiquetas").getString("EventNotClosed"));
				}
			}
		});
		
		
		
		btnCloseEvent.setBounds(226, 414, 138, 26);
		getContentPane().add(btnCloseEvent);



	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);

	}
}