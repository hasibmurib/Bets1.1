package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Apuesta;


public class BetHistory extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final JLabel jLabelBets = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bets")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	

	
	private JScrollPane scrollPaneBets = new JScrollPane();
	
	private JTable tableBets= new JTable();
	
	private DefaultTableModel tableModelBets;
	

	private String[] columnNamesBets = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Num"), 
			ResourceBundle.getBundle("Etiquetas").getString("Bets"), 
			ResourceBundle.getBundle("Etiquetas").getString("Condition"), 

	};
	
	public BetHistory()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try
		{
			jbInit();
			searchBits();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BetHist"));
	
		jLabelBets.setBounds(57, 29, 85, 16);
		this.getContentPane().add(jLabelBets);
		jButtonClose.setBounds(155, 411, 130, 25);

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		
		
		scrollPaneBets.setBounds(57, 57, 291, 342);
		scrollPaneBets.setViewportView(tableBets);
		tableModelBets = new DefaultTableModel(null, columnNamesBets);
		
		tableBets.setModel(tableModelBets);
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableBets.getColumnModel().getColumn(2).setPreferredWidth(80);

		this.getContentPane().add(scrollPaneBets, null);
		
	}
	
	private void searchBits() {
		
		BLFacade facade = EntryGUI.getBusinessLogic();
		
		String loggedUser = LoginGUI.getUsuarioLogeado();
		
		ArrayList<Apuesta> queries= facade.getBets(loggedUser);
		
		if (!queries.isEmpty()) {
			
			for (domain.Apuesta q:queries){
				
				Vector<Object> row = new Vector<Object>();

				row.add(q.getBetNumber());
				row.add(q.getBet());
				row.add(q.getStatus());
				tableModelBets.addRow(row);	
			}
			tableBets.getColumnModel().getColumn(0).setPreferredWidth(25);
			tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableBets.getColumnModel().getColumn(2).setPreferredWidth(80);
		}
		
		else
			return;	
	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);

	}
}
