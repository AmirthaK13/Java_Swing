import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.sql.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;

import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Users {

	private JFrame frmCrudswingmysql;
	private JTextField txtid;
	private JTextField txtname;
	private JTextField txtage;
	private JTextField txtcity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Users window = new Users();
					window.frmCrudswingmysql.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Users() {
		initialize();
		connect();
		loadData();
	}

	//DB connection
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	 
	public void connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaDB","root","ammu");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	//clear
	
	public void clear()
	{
		txtname.setText("");
	    txtid.setText("");
		txtage.setText("");
		txtcity.setText("");
		txtname.requestFocus();
	}
	//load table
	
	public void loadData()
	{
		try {
			pst=con.prepareStatement("select * from userDB");
			rs=pst.executeQuery();
			table.setModel(Dbutils.resultSetToTableModel(rs));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCrudswingmysql = new JFrame();
		frmCrudswingmysql.setTitle("CRUD_swingMysql");
		frmCrudswingmysql.setFont(new Font("Dialog", Font.ITALIC, 14));
		frmCrudswingmysql.setBounds(100, 100, 869, 414);
		frmCrudswingmysql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCrudswingmysql.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Management");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 201, 37);
		frmCrudswingmysql.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 43, 321, 210);
		frmCrudswingmysql.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 21, 74, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 47, 74, 17);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Age");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(10, 82, 74, 17);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("City");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_2.setBounds(10, 109, 74, 17);
		panel.add(lblNewLabel_1_1_2);
		
		txtid = new JTextField();
		txtid.setEditable(false);
		txtid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtid.setBounds(64, 22, 203, 16);
		panel.add(txtid);
		txtid.setColumns(10);
		
		txtname = new JTextField();
		txtname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtname.setColumns(10);
		txtname.setBounds(64, 48, 203, 16);
		panel.add(txtname);
		
		txtage = new JTextField();
		txtage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtage.setColumns(10);
		txtage.setBounds(64, 83, 203, 16);
		panel.add(txtage);
		
		txtcity = new JTextField();
		txtcity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtcity.setColumns(10);
		txtcity.setBounds(64, 109, 203, 17);
		panel.add(txtcity);
		
		JButton btnsave = new JButton("save");
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=txtid.getText();
				String name=txtname.getText();
				String age=txtage.getText();
				String city=txtcity.getText();
				
				if(name==null||name.isEmpty()||name.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"please enter name");
					txtname.requestFocus();
					return;	
				}
				if(age==null||age.isEmpty()||age.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"please enter age");
					txtage.requestFocus();
					return;	
				}
				if(city==null||city.isEmpty()||city.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"please enter city");
					txtcity.requestFocus();
					return;	
				}
				
				if(txtid.getText().isEmpty())
				{
					String qry="insert into userDB (name,age,city) values (?,?,?)";
					try {
						pst=con.prepareStatement(qry);
						pst.setString(1, name);
						pst.setString(2, age);
						pst.setString(3, city);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"data entered");
						clear();
						loadData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		btnsave.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnsave.setBounds(52, 136, 68, 21);
		panel.add(btnsave);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			String id=txtid.getText();
			String name=txtname.getText();
			String age=txtage.getText();
			String city=txtcity.getText();
			
			if(name==null||name.isEmpty()||name.trim().isEmpty())
			{
				JOptionPane.showMessageDialog(null,"please enter name");
				txtname.requestFocus();
				return;	
			}
			if(age==null||age.isEmpty()||age.trim().isEmpty())
			{
				JOptionPane.showMessageDialog(null,"please enter age");
				txtage.requestFocus();
				return;	
			}
			if(city==null||city.isEmpty()||city.trim().isEmpty())
			{
				JOptionPane.showMessageDialog(null,"please enter city");
				txtcity.requestFocus();
				return;	
			}
			
			if(!txtid.getText().isEmpty())
			{
				try {
				String qry="update userDB set name=?,age=?,city=? where id=?";
				
					pst=con.prepareStatement(qry);
					pst.setString(1, name);
					pst.setString(2, age);
					pst.setString(3, city);
					pst.setString(4, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"data updated");
					clear();
					loadData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpdate.setBounds(130, 137, 80, 21);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=txtid.getText();
				if(!txtid.getText().isEmpty())
				{
					int result=JOptionPane.showConfirmDialog(null, "sure u want to delete","Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result==JOptionPane.YES_OPTION) {
					try {
					String qry="delete from userDB where id=?";
					
						pst=con.prepareStatement(qry);
						pst.setString(1, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"data deleted");
						clear();
						loadData();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(220, 136, 78, 21);
		panel.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(363, 43, 304, 210);
		frmCrudswingmysql.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel model = table.getModel();
				// ID NAME AGE CITY
				txtid.setText(model.getValueAt(index, 0).toString());
				txtname.setText(model.getValueAt(index, 1).toString());
				txtage.setText(model.getValueAt(index, 2).toString());
				txtcity.setText(model.getValueAt(index, 3).toString());
			}
		});
		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		

	}
}
