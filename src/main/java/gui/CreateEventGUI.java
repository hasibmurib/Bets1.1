package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Ticket;

public class CreateEventGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Description"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private Event creado;

	private JList<Event> eventList = null;
	private DefaultListModel<Event> eventInfo = new DefaultListModel<Event>();



	private JTextField jTextFieldQuery = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();

	private ArrayList<Date> datesWithEventsCurrentMonth = new ArrayList<Date>();
	private JTextField textFieldTickets;

	public CreateEventGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		//list.setSelectionModel(new ListSelectionModel();
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		jLabelEvent.setBounds(new Rectangle(25, 223, 92, 20));
		jTextFieldQuery.setBounds(new Rectangle(156, 224, 193, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(59, 293, 130, 30));
		jButtonCreate.setEnabled(true);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(411, 293, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(368, 229, 173, 15));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(368, 254, 173, 15));
		jLabelError.setForeground(Color.red);

		eventList = new JList<Event>();
		eventList.setModel(eventInfo);
		eventList.setBounds(325, 50, 221, 150);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 137, 15);
		jLabelEvents.setBounds(325, 16, 70, 15);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldQuery, null);
		this.getContentPane().add(jLabelEvent, null);
		this.getContentPane().add(eventList, null);
		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jCalendar, null);
		this.getContentPane().add(jLabelEvents, null);

		BLFacade facade = EntryGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);
		
		textFieldTickets = new JTextField();
		textFieldTickets.setBounds(156, 264, 193, 19);
		getContentPane().add(textFieldTickets);
		textFieldTickets.setColumns(10);
		
		JLabel lblNumTickets = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NumTickets")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNumTickets.setBounds(25, 267, 81, 13);
		getContentPane().add(lblNumTickets);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
				//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				eventInfo.clear();
				jLabelError.setText("");
				jLabelMsg.setText("");
				
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: " + calendarAnt.getTime());
					System.out.println("calendarAct: " + calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);

						BLFacade facade = EntryGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
					}

					paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);

					// Date firstDay = UtilDate.trim(new
					// Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = EntryGUI.getBusinessLogic();

						ArrayList<Event> events = facade.getEvents(firstDay);
						//eventCollection = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else {
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
							eventInfo.clear();

							for (Event ev : events)
								eventInfo.addElement(ev);
							/*
							 * if (events.size() == 0) jButtonCreate.setEnabled(false); else
							 * jButtonCreate.setEnabled(true);
							 * 
							 */
						}

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
	}

	public static void paintDaysWithEvents(JCalendar jCalendar, ArrayList<Date> datesWithEventsCurrentMonth2) {
		// For each day with events in current month, the background color for that day
		// is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d : datesWithEventsCurrentMonth2) {

			calendar.setTime(d);
			System.out.println(d);

			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);

	}

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		//domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String inputQuery = jTextFieldQuery.getText();

			if (inputQuery.length() > 0) {

				//jButtonCreate.setEnabled(true);
				BLFacade facade = EntryGUI.getBusinessLogic();
				creado = facade.createEvent(inputQuery, jCalendar.getDate());
				int tickets = Integer.parseInt(textFieldTickets.getText());
				for(int i = 0; i<tickets; i++) {
					facade.createTicket(inputQuery, jCalendar.getDate(), i, creado.getEventNumber());
				}
			}

			else jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventError2"));

			if (creado!=null) jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("EventCreated"));
			else jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("EventError"));
		}

		catch(
				java.lang.NumberFormatException e1)
		{

		}catch(
				Exception e1)
		{
			e1.printStackTrace();
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}