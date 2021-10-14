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

public class UserPrizeGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelPrizes = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Prizes"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanePremios = new JScrollPane();

	private JTable tablePremios = new JTable();

	private DefaultTableModel tableModelPremios;

	private Integer p;

	private String[] columnNamesPremios = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Num"),
			ResourceBundle.getBundle("Etiquetas").getString("Prize"),
			ResourceBundle.getBundle("Etiquetas").getString("Value"),
			ResourceBundle.getBundle("Etiquetas").getString("Status")

	};

	protected Component frame;

	public UserPrizeGUI() {
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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MyPrizes"));
		jLabelPrizes.setBounds(47, 29, 85, 16);
		this.getContentPane().add(jLabelPrizes);
		jButtonClose.setBounds(27, 411, 130, 25);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		JButton btnClaim = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Claim"));
		btnClaim.setVisible(true);
		btnClaim.setEnabled(false);

		tablePremios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int i = tablePremios.getSelectedRow();
				p = (Integer) tableModelPremios.getValueAt(i, 0);
				if (p != null) {
					if (!(tableModelPremios.getValueAt(i, 3)).equals("Recibido"))
						btnClaim.setEnabled(true);
				}
			}
		});

		scrollPanePremios.setBounds(47, 55, 338, 335);
		scrollPanePremios.setViewportView(tablePremios);
		tableModelPremios = new DefaultTableModel(null, columnNamesPremios);

		tablePremios.setModel(tableModelPremios);
		tablePremios.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablePremios.getColumnModel().getColumn(1).setPreferredWidth(268);
		tablePremios.getColumnModel().getColumn(2).setPreferredWidth(80);
		tablePremios.getColumnModel().getColumn(3).setPreferredWidth(100);

		this.getContentPane().add(scrollPanePremios, null);

		btnClaim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = EntryGUI.getBusinessLogic();
				String u = LoginGUI.getUsuarioLogeado();
				boolean result = facade.claimPrize(u, p);
				
				if (result) {
					
					JOptionPane.showMessageDialog(frame,
						    "Claimed!");
					System.out.println("Claimed");
					
				} else {
					
					JOptionPane.showMessageDialog(frame,
						    "The prize has been already claimed!", "Error",
						    JOptionPane.ERROR_MESSAGE);
					
					System.out.println("Coudn't claim");
				}
				
				dispose();
				JFrame a = new UserPrizeGUI();
				a.setVisible(true);
			}
		});
		btnClaim.setBounds(47, 418, 112, 21);
		getContentPane().add(btnClaim);

	}

	private void searchPrizes() {

		BLFacade facade = EntryGUI.getBusinessLogic();
		String u = LoginGUI.getUsuarioLogeado();
		ArrayList<Premio> queries = facade.viewMyPrizes(u);
		System.out.println(queries);
		
		if (!queries.isEmpty()) {

			for (Premio q : queries) {
				Vector<Object> row = new Vector<Object>();
				row.add(q.getPrizeID());
				row.add(q.getName());
				row.add(q.getValor());
				row.add(q.getEstado());
				tableModelPremios.addRow(row);
			}
			tablePremios.getColumnModel().getColumn(0).setPreferredWidth(70);
			tablePremios.getColumnModel().getColumn(1).setPreferredWidth(268);
			tablePremios.getColumnModel().getColumn(2).setPreferredWidth(120);
			tablePremios.getColumnModel().getColumn(3).setPreferredWidth(120);
		} 
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);

	}
	
}