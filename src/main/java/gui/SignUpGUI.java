package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import exceptions.UserAlreadyExist;

public class SignUpGUI extends JFrame {

	/**
	 * 
	 */
	
	private final JLabel lblUser = new JLabel((ResourceBundle.getBundle("Etiquetas").getString("User")));
	private final JLabel lblPassword = new JLabel((ResourceBundle.getBundle("Etiquetas").getString("Password")));
	private JButton btnSignUp = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignUp")); 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField passwordField;
	private boolean insertado;
	private JTextArea textArea;
	private JTextField textFieldCreditCard;
	private JButton btnReturnBack = new JButton((ResourceBundle.getBundle("Etiquetas").getString("ReturnBack")));
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpGUI frame = new SignUpGUI();
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
	public SignUpGUI() {
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("SignUpTitle"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblUser.setBounds(47, 36, 128, 15);
		contentPane.add(lblUser);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(188, 29, 180, 30);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		
		lblPassword.setBounds(47, 71, 115, 18);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(188, 65, 180, 30);
		contentPane.add(passwordField);
		
		textArea = new JTextArea();
		textArea.setBounds(48, 185, 361, 68);
		contentPane.add(textArea);
		BLFacade facade = EntryGUI.getBusinessLogic();
		
		btnSignUp.addActionListener(new ActionListener() {
			
			

			public void actionPerformed(ActionEvent e) {
				
				if (!textFieldUsuario.getText().isEmpty() && passwordField.getPassword().length!=0 && !textFieldCreditCard.getText().isEmpty()) {
				
					try {
						insertado = facade.createUser(textFieldUsuario.getText(), new String(passwordField.getPassword()), Long.parseLong(textFieldCreditCard.getText()));
					} catch (UserAlreadyExist e1) {

						e1.printStackTrace();
					}
				
					if (insertado) 
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("UserAdded"));

					else textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("UserQueryAlreadyExist"));
				
				}
				else textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("Empty"));
			}
		});
		
		btnSignUp.setBounds(213, 145, 128, 30);
		contentPane.add(btnSignUp);
		
		JLabel lblCreditCard = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreditCard")); 
		lblCreditCard.setBounds(48, 109, 128, 20);
		contentPane.add(lblCreditCard);
		
		textFieldCreditCard = new JTextField();
		//textFieldCreditCard.setText(ResourceBundle.getBundle("Etiquetas").getString("CreditCard")); 
		textFieldCreditCard.setBounds(188, 99, 180, 30);
		contentPane.add(textFieldCreditCard);
		textFieldCreditCard.setColumns(10);
		
		btnReturnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReturnBack")); 
		btnReturnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EntryGUI c = new EntryGUI();
				c.setVisible(true);
				dispose();
			}
		});
		btnReturnBack.setBounds(58, 145, 118, 29);
		contentPane.add(btnReturnBack);
		
		
	}
}
