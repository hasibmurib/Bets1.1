package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Premio;
import domain.Ticket;

public class ViewTicketsGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelPrizes = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Tickets"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanePremios = new JScrollPane();

	private JTable tablePremios = new JTable();

	private DefaultTableModel tableModelPremios;

	private Integer p;

	private String[] columnNamesPremios = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Num"),
			ResourceBundle.getBundle("Etiquetas").getString("Date"),
			ResourceBundle.getBundle("Etiquetas").getString("Description"),
			ResourceBundle.getBundle("Etiquetas").getString("Asiento")

	};

	protected Component frame;

	public ViewTicketsGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			jbInit();
			searchPrizes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ViewTickets"));
		jLabelPrizes.setBounds(47, 29, 85, 16);
		this.getContentPane().add(jLabelPrizes);
		jButtonClose.setBounds(27, 411, 130, 25);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		this.getContentPane().add(jButtonClose, null);

		scrollPanePremios.setBounds(47, 55, 338, 335);
		scrollPanePremios.setViewportView(tablePremios);
		tableModelPremios = new DefaultTableModel(null, columnNamesPremios);

		tablePremios.setModel(tableModelPremios);
		tablePremios.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablePremios.getColumnModel().getColumn(1).setPreferredWidth(268);
		tablePremios.getColumnModel().getColumn(2).setPreferredWidth(200);
		tablePremios.getColumnModel().getColumn(3).setPreferredWidth(100);

		this.getContentPane().add(scrollPanePremios, null);

	}

	private void searchPrizes() {

		BLFacade facade = EntryGUI.getBusinessLogic();
		String u = LoginGUI.getUsuarioLogeado();
		ArrayList<Ticket> queries = facade.viewMyTickets(u);
		System.out.println(queries.size());
		
		if (!queries.isEmpty()) {

			for (Ticket q : queries) {
				Vector<Object> row = new Vector<Object>();
				row.add(q.getTicketID());
				row.add(q.getFecha().toString());
				row.add(q.getDescription());
				row.add(q.getAsiento());
				tableModelPremios.addRow(row);
			}
			tablePremios.getColumnModel().getColumn(0).setPreferredWidth(70);
			tablePremios.getColumnModel().getColumn(1).setPreferredWidth(268);
			tablePremios.getColumnModel().getColumn(2).setPreferredWidth(200);
			tablePremios.getColumnModel().getColumn(3).setPreferredWidth(120);
		} 
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		MainGUI a = new MainGUI();
		a.setVisible(true);

	}
	
}