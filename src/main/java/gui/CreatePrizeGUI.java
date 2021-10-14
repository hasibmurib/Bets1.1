package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Premio;

public class CreatePrizeGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField name;
	private JTextField valor;
	private JTextArea resultado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatePrizeGUI frame = new CreatePrizeGUI();
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
	public CreatePrizeGUI() {
		setTitle("Create Prize");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Description");
		lblNombre.setBounds(42, 36, 94, 25);
		contentPane.add(lblNombre);
		
		JLabel lblValor = new JLabel("Value");
		lblValor.setBounds(42, 73, 70, 26);
		contentPane.add(lblValor);
		
		name = new JTextField();
		name.setBounds(154, 36, 152, 26);
		contentPane.add(name);
		name.setColumns(10);
		
		valor = new JTextField();
		valor.setBounds(154, 74, 152, 26);
		contentPane.add(valor);
		valor.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		
		BLFacade facade = EntryGUI.getBusinessLogic();
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) throws InputMismatchException{
				
				try {
					if (!name.getText().isEmpty() && !valor.getText().isEmpty()) {
						Premio premioCreado = facade.createPrize(name.getText(),Double.parseDouble(valor.getText()));
						if (premioCreado!=null) 
							resultado.setText("Premio creado!");
						else
							resultado.setText("No se ha creado!");
					}
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setBounds(164, 112, 117, 25);
		contentPane.add(btnAdd);
		
		resultado = new JTextArea();
		resultado.setBounds(52, 162, 331, 77);
		contentPane.add(resultado);
	}
}
