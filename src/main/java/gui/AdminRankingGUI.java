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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Premio;
import domain.User;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdminRankingGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final JLabel jLabelRank = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bets"));
	
	private final JLabel jLabelPrizes = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Prizes")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	
	private JButton btnPremiar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Reward"));
	
	private JButton btnReset = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Reset")); 
	
	private String u;
	
	private Integer p;
	
	private JScrollPane scrollPaneRanking = new JScrollPane();
	
	private JTable tableRank= new JTable();
	
	private DefaultTableModel tableModelRank;
	
	private JScrollPane scrollPanePremios = new JScrollPane();
	
	private JTable tablePremios= new JTable();
	
	private DefaultTableModel tableModelPremios;
	
	

	private String[] columnNamesBets = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Rank"), 
			ResourceBundle.getBundle("Etiquetas").getString("User"), 
			ResourceBundle.getBundle("Etiquetas").getString("Gains"), 

	};
	private String[] columnNamesPremios = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Num"), 
			ResourceBundle.getBundle("Etiquetas").getString("Prize"), 
			ResourceBundle.getBundle("Etiquetas").getString("Value"), 

	};
	
	public AdminRankingGUI()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try
		{
			jbInit();
			searchBits();
			searchPrizes();
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
	
		jLabelRank.setBounds(27, 29, 85, 16);
		this.getContentPane().add(jLabelRank);
		jLabelPrizes.setBounds(254, 29, 85, 16);
		this.getContentPane().add(jLabelPrizes);
		jButtonClose.setBounds(27, 411, 130, 25);

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		 
		btnPremiar.setEnabled(false);
		btnPremiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = EntryGUI.getBusinessLogic();
				try {
					if(p!=null && u!=null) {
						facade.reward(u, p);
						System.out.println("Premiado");
						btnReset.setEnabled(true);
						JOptionPane.showMessageDialog(null ,ResourceBundle.getBundle("Etiquetas").getString("Rewarded"));
						
					}
				} catch(Exception e1) {
					System.out.println("Choose a user and a reward");
				}
				
			}
		});
		btnPremiar.setBounds(254, 411, 110, 25);
		getContentPane().add(btnPremiar);
		
		this.getContentPane().add(jButtonClose, null);
		tableRank.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableRank.getSelectedRow();
				u = (String) tableModelRank.getValueAt(i, 1);
			}
		});
		
		
		scrollPaneRanking.setBounds(27, 59, 222, 342);
		scrollPaneRanking.setViewportView(tableRank);
		tableModelRank = new DefaultTableModel(null, columnNamesBets);
		
		tableRank.setModel(tableModelRank);
		tableRank.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableRank.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableRank.getColumnModel().getColumn(2).setPreferredWidth(80);

		this.getContentPane().add(scrollPaneRanking, null);
		tablePremios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tablePremios.getSelectedRow();
				p = (Integer) tableModelPremios.getValueAt(i, 0);
				if(u!=null)
					btnPremiar.setEnabled(true);
			}
		});
		
		scrollPanePremios.setBounds(254, 59, 222, 342);
		scrollPanePremios.setViewportView(tablePremios);
		tableModelPremios = new DefaultTableModel(null, columnNamesPremios);
		
		tablePremios.setModel(tableModelPremios);
		tablePremios.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablePremios.getColumnModel().getColumn(1).setPreferredWidth(268);
		tablePremios.getColumnModel().getColumn(2).setPreferredWidth(80);

		this.getContentPane().add(scrollPanePremios, null);
		
		btnReset.setEnabled(false);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BLFacade facade = EntryGUI.getBusinessLogic();
					facade.resetRanking();
					btnPremiar.setEnabled(true);
					btnReset.setEnabled(false);
					JOptionPane.showMessageDialog(null ,ResourceBundle.getBundle("Etiquetas").getString("Reseted"));
				} catch (Exception e2) {
					System.out.println("Couldn't reset");
				}
			}
		});
		btnReset.setBounds(391, 10, 85, 21);
		getContentPane().add(btnReset);
		
		
		
	}
	
	private void searchBits() {
		
		BLFacade facade = EntryGUI.getBusinessLogic();
		
		ArrayList<User> queries= facade.viewRanking();
		int rank = 1;
		if (!queries.isEmpty()) {
			
			for (User q:queries){
				
				Vector<Object> row = new Vector<Object>();

				row.add(rank++);
				row.add(q.getUser());
				row.add(q.getGanancias());
				tableModelRank.addRow(row);	
			}
			tableRank.getColumnModel().getColumn(0).setPreferredWidth(70);
			tableRank.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableRank.getColumnModel().getColumn(2).setPreferredWidth(80);
		}
		
		else
			return;	
	}
	
private void searchPrizes() {
		
		BLFacade facade = EntryGUI.getBusinessLogic();
		
		ArrayList<Premio> queries= facade.viewPrizes();
		//int rank = 1;
		if (!queries.isEmpty()) {
			
			for (Premio q:queries){
				
				Vector<Object> row = new Vector<Object>();

				row.add(q.getPrizeID());
				row.add(q.getName());
				row.add(q.getValor());
				tableModelPremios.addRow(row);	
			}
			tablePremios.getColumnModel().getColumn(0).setPreferredWidth(70);
			tablePremios.getColumnModel().getColumn(1).setPreferredWidth(268);
			tablePremios.getColumnModel().getColumn(2).setPreferredWidth(80);
		}
		
		else
			return;	
	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);

	}
}