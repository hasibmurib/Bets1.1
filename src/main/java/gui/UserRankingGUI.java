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
import domain.User;


public class UserRankingGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final JLabel jLabelBets = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bets")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	

	
	private JScrollPane scrollPaneBets = new JScrollPane();
	
	private JTable tableBets= new JTable();
	
	private DefaultTableModel tableModelBets;
	

	private String[] columnNamesBets = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Rank"), 
			ResourceBundle.getBundle("Etiquetas").getString("User"), 
			ResourceBundle.getBundle("Etiquetas").getString("Gains"), 

	};
	
	public UserRankingGUI()
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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ViewRank"));
	
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
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableBets.getColumnModel().getColumn(2).setPreferredWidth(80);

		this.getContentPane().add(scrollPaneBets, null);
		
		JButton btnMyPrizes = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyPrizes")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMyPrizes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new UserPrizeGUI();
				a.setVisible(true);
				
			}
		});
		btnMyPrizes.setBounds(336, 27, 120, 21);
		getContentPane().add(btnMyPrizes);
		
	}
	
	private void searchBits() {
		
		BLFacade facade = EntryGUI.getBusinessLogic();
		
		//String loggedUser = LoginGUI.getUsuarioLogeado();
		ArrayList<User> queries= facade.viewRanking();
		int rank = 1;
		if (!queries.isEmpty()) {
			
			for (domain.User q:queries){
				
				Vector<Object> row = new Vector<Object>();

				row.add(rank++);
				row.add(q.getUser());
				row.add(q.getGanancias());
				tableModelBets.addRow(row);	
			}
			tableBets.getColumnModel().getColumn(0).setPreferredWidth(70);
			tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableBets.getColumnModel().getColumn(2).setPreferredWidth(80);
		}
		
		else
			return;	
	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		JFrame a = new MainGUI();
		a.setVisible(true);

	}
}