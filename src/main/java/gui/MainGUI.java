package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Event;


public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnNewButton;
	private JLabel lblCredit;
	private JButton jButtonBets;
	private JButton btnLogout;

	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();

	}



	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton());
			jContentPane.add(getPanel());
			jContentPane.add(getBtnNewButton());
			jContentPane.add(getLblMoney());
			jContentPane.add(getBtnHist());
			
			JButton btnRanking = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ViewRank")); //$NON-NLS-1$ //$NON-NLS-2$
			btnRanking.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					JFrame a = new UserRankingGUI();
					a.setVisible(true);
				}
			});
			btnRanking.setBounds(252, 55, 178, 33);
			jContentPane.add(btnRanking);
			jContentPane.add(getBtnLogout());
			
			JButton btnBuyTickets = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BuyTickets")); //$NON-NLS-1$ //$NON-NLS-2$
			btnBuyTickets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					JFrame a = new BuyTicketsGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
			btnBuyTickets.setBounds(12, 141, 194, 27);
			jContentPane.add(btnBuyTickets);
			
			JButton btnViewTickets = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ViewTickets")); //$NON-NLS-1$ //$NON-NLS-2$
			btnViewTickets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					JFrame a = new ViewTicketsGUI();
					a.setVisible(true);
				}
			});
			btnViewTickets.setBounds(252, 141, 178, 27);
			jContentPane.add(btnViewTickets);
			
		}
		return jContentPane;
	}


	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	
	private JButton getBtnHist() {
		if (jButtonBets == null) {
			jButtonBets = new JButton();
			jButtonBets.setBounds(12, 98, 194, 33);
			jButtonBets.setText(ResourceBundle.getBundle("Etiquetas").getString("BetHist"));
			jButtonBets.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new BetHistory();
					a.setVisible(true);
				}
			});
		}
		return jButtonBets;
	}
	private JButton getBoton() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(252, 98, 178, 33);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();

					a.setVisible(true);
					dispose();
				}
			});
		}
		return jButtonQueryQueries;
	}


	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(138, 28, 173, 17);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(83, 216, 322, 27);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddWithdrawFunds")); 
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					FundsGUI c = new FundsGUI();
					c.setVisible(true);
					dispose();
				}
			});
			btnNewButton.setBounds(12, 55, 194, 33);
		}
		return btnNewButton;
	}
	private JLabel getLblMoney() {
		if (lblCredit == null) {
			lblCredit = new JLabel("New label");
			lblCredit.setBounds(337, 12, 146, 15);
			BLFacade facade = EntryGUI.getBusinessLogic();
			String s=String.valueOf(" " +facade.usersFunds(LoginGUI.getUsuarioLogeado())+" "); 
			lblCredit.setText(ResourceBundle.getBundle("Etiquetas").getString("Funds")+s); 
		}
		return lblCredit;
	}
	private JButton getBtnLogout() {
		if (btnLogout == null) {
			btnLogout = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnLogout.text")); 
			btnLogout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					EntryGUI c = null;
					try {
						c = new EntryGUI();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					c.setVisible(true);
					dispose();
				}
			});
			btnLogout.setBounds(175, 176, 117, 27);
		}
		return btnLogout;
	}
} 

