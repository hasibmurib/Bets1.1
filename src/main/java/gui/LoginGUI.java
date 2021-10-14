package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import exceptions.NoSuchUserExist;

public class LoginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField passwordField;
	private User acreditado;
	private static String usuarioLogeado;
	private final JLabel lblUser = new JLabel((ResourceBundle.getBundle("Etiquetas").getString("User")));
	private final JLabel lblPassword = new JLabel((ResourceBundle.getBundle("Etiquetas").getString("Password")));
	private JButton btnSignIn = new JButton((ResourceBundle.getBundle("Etiquetas").getString("Login")));

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() throws Exception {
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("SignInTitle"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		lblUser.setBounds(67, 21, 98, 15);
		contentPane.add(lblUser);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setColumns(10);
		textFieldUsuario.setBounds(150, 11, 185, 35);
		contentPane.add(textFieldUsuario);

		lblPassword.setBounds(67, 65, 98, 18);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(150, 58, 185, 35);
		contentPane.add(passwordField);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(67, 169, 306, 69);
		contentPane.add(textArea);
		
		btnSignIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
					
					if (!textFieldUsuario.getText().isEmpty() && passwordField.getPassword().length!=0) {
					//BLFacade facade = MainGUI.getBusinessLogic();

					try {
						BLFacade facade = EntryGUI.getBusinessLogic();
						acreditado = facade.authenticateUser(textFieldUsuario.getText(), new String(passwordField.getPassword()));
					} catch (NoSuchUserExist e1) {
						
						textArea.setText(e1.getMessage());
					}
					
					if(acreditado != null) {
						usuarioLogeado=acreditado.getUser();
						if (acreditado.getAdmin()) {
							JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("AdminLogin"));
							MainGUIAdmin b = new MainGUIAdmin();
							b.setVisible(true);
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, ResourceBundle.getBundle("Etiquetas").getString("UserLogin"));
							MainGUI c = new MainGUI();
							c.setVisible(true);
							dispose();
							
						}
					}
						

					else 
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("LoggedInFailed"));
					
			}
				}
		});
		
		
		btnSignIn.setBounds(225, 116, 110, 25);
		contentPane.add(btnSignIn);
		
		JButton btnReturn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReturnBack")); 
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EntryGUI c = new EntryGUI();
				c.setVisible(true);
				dispose();
			}
		});
		btnReturn.setBounds(67, 116, 116, 25);
		contentPane.add(btnReturn);

	}
	public static String getUsuarioLogeado() {
		return usuarioLogeado;
	}
}
