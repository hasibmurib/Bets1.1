package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

public class FundsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFunds;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FundsGUI frame = new FundsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FundsGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Funds")); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFunds = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Funds"));
		lblFunds.setBounds(32, 56, 70, 23);
		contentPane.add(lblFunds);

		textFieldFunds = new JTextField();
		textFieldFunds.setBounds(133, 54, 183, 27);
		contentPane.add(textFieldFunds);
		textFieldFunds.setColumns(10);

		JLabel lblSelect = new JLabel("Select");
		lblSelect.setBounds(32, 104, 70, 15);
		contentPane.add(lblSelect);

		JRadioButton rdbtnAdd = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Add"));
		buttonGroup.add(rdbtnAdd);
		rdbtnAdd.setBounds(133, 100, 103, 23);
		contentPane.add(rdbtnAdd);

		JRadioButton rdbtnWithdraw = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Withdraw"));
		buttonGroup.add(rdbtnWithdraw);
		rdbtnWithdraw.setBounds(241, 100, 104, 23);
		contentPane.add(rdbtnWithdraw);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(32, 168, 337, 61);
		contentPane.add(textArea);

		JButton btnExecute = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Execute"));

		BLFacade facade = EntryGUI.getBusinessLogic();

		facade.usersFunds(LoginGUI.getUsuarioLogeado());

		btnExecute.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (rdbtnAdd.isSelected()) {
					facade.addFunds(LoginGUI.getUsuarioLogeado(), Double.parseDouble(textFieldFunds.getText()));
					textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("AddFundSuccess"));
				}

				else 
				{
					if (rdbtnWithdraw.isSelected()) {
						
						Double myFund = facade.usersFunds(LoginGUI.getUsuarioLogeado());
						
						if (myFund>=Double.parseDouble(textFieldFunds.getText())) 
						{
							facade.withdrawFunds(LoginGUI.getUsuarioLogeado(),Double.parseDouble(textFieldFunds.getText()));
							textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawFundSuccess"));
							
						}
						
						else 
							textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawFundFailure"));
					}
				}

			}
		});
		btnExecute.setBounds(137, 131, 146, 25);
		contentPane.add(btnExecute);
		
		String s=String.valueOf(" " +facade.usersFunds(LoginGUI.getUsuarioLogeado())); 	
		JLabel lblCredit = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Funds")+s); 
		lblCredit.setBounds(241, 12, 146, 15);
		contentPane.add(lblCredit);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); 
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
				
			}
		});
		btnClose.setBounds(151, 239, 85, 21);
		contentPane.add(btnClose);
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		MainGUI a = new MainGUI();
		a.setVisible(true);

	}
}
