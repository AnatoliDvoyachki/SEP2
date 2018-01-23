package client.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Order;
import model.OrderList;
import model.Shoe;
import model.ShoeList;
import client.controller.ClientController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

/** Grapical user interface for the client side of the application **/
public class ClientGUI extends JFrame implements ClientView {

	private ClientController controller;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel labelStatus;
	private JButton buttonListAllShoes;
	private JButton buttonSearchByCriteria;
	private JButton buttonPlaceAnOrder;
	private JButton buttonLogin;
	private JButton buttonRegister;
	private JButton buttonShowAllOrders;

	public static final int WINDOW_WIDTH = 960;
	public static final int WINDOW_HEIGHT = 600;
	public static final int MENU_BUTTONS = 6;
	public static final int BUTTON_WIDTH = WINDOW_WIDTH / MENU_BUTTONS;

	/** Used to intantiate thte client graphical user interface **/
	public ClientGUI() {
		initializeWindow();
		initializeContentPane();
		initializeLabels();
		initializeButtons();
		initializeTable();
	}

	private void initializeWindow() {
		setTitle("VIA Shoes");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int userChoice = JOptionPane.showOptionDialog(null, "Exit?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (userChoice == JOptionPane.YES_OPTION) {
					setVisible(false);
					dispose();
					controller.execute(ClientController.EXECUTE_QUIT);
				}
			}
		});
	}

	private void initializeContentPane() {
		// content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	private void initializeLabels() {
		// status label
		labelStatus = new JLabel("Welcome");
		labelStatus.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
		labelStatus.setBounds(10, 50, 934, 20);
		contentPane.add(labelStatus);
	}

	private ActionListener buttonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(buttonListAllShoes)) {
				controller.execute(ClientController.EXECUTE_LIST_ALL);
			} else if (e.getSource().equals(buttonSearchByCriteria)) {
				controller.execute(ClientController.EXECUTE_LIST_SOME);
			} else if (e.getSource().equals(buttonPlaceAnOrder)) {
				controller.execute(ClientController.EXECUTE_ORDER);
			} else if (e.getSource().equals(buttonLogin)) {
				controller.execute(ClientController.EXECUTE_LOGIN);
			} else if (e.getSource().equals(buttonRegister)) {
				controller.execute(ClientController.EXECUTE_REGISTER);
			} else if (e.getSource().equals(buttonShowAllOrders)) {
				controller.execute(ClientController.EXECUTE_LIST_ORDERS);
			}
		}
	};

	private void initializeButtons() {
		int buttonsAdded = 0;

		// 'List all shoes'
		buttonListAllShoes = new JButton("List all shoes");

		buttonListAllShoes.addActionListener(buttonListener);
		buttonListAllShoes.setBounds(BUTTON_WIDTH * buttonsAdded - 1, 0,
				BUTTON_WIDTH, 23);
		buttonListAllShoes.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(buttonListAllShoes);
		buttonsAdded++;

		// 'Search by criteria'
		buttonSearchByCriteria = new JButton("Search by criteria");
		buttonSearchByCriteria.addActionListener(buttonListener);
		buttonSearchByCriteria.setBounds(BUTTON_WIDTH * buttonsAdded - 1, 0,
				BUTTON_WIDTH, 23);
		buttonSearchByCriteria.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(buttonSearchByCriteria);
		buttonsAdded++;

		// 'Place an order'
		buttonPlaceAnOrder = new JButton("Place an order");
		buttonPlaceAnOrder.addActionListener(buttonListener);
		buttonPlaceAnOrder.setFont(new Font("Tahoma", Font.BOLD, 13));
		buttonPlaceAnOrder.setBounds(BUTTON_WIDTH * buttonsAdded - 1, 0,
				BUTTON_WIDTH, 23);
		contentPane.add(buttonPlaceAnOrder);
		buttonsAdded++;

		// 'Login'
		buttonLogin = new JButton("Login");
		buttonLogin.addActionListener(buttonListener);
		buttonLogin.setBounds(BUTTON_WIDTH * buttonsAdded - 1, 0, BUTTON_WIDTH,
				23);
		buttonLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(buttonLogin);
		buttonsAdded++;

		// 'Register'
		buttonRegister = new JButton("Register");
		buttonRegister.addActionListener(buttonListener);
		buttonRegister.setBounds(BUTTON_WIDTH * buttonsAdded - 1, 0,
				BUTTON_WIDTH, 23);
		buttonRegister.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(buttonRegister);
		buttonsAdded++;

		// 'List your orders'
		buttonShowAllOrders = new JButton("List your orders");
		buttonShowAllOrders.addActionListener(buttonListener);
		buttonShowAllOrders.setBounds(BUTTON_WIDTH * buttonsAdded - 1, 0,
				BUTTON_WIDTH, 23);
		buttonShowAllOrders.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(buttonShowAllOrders);
		buttonsAdded++;
	}

	private void initializeTable() {
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setBounds(10, 90, WINDOW_WIDTH - 25, WINDOW_HEIGHT - 130);
		table.setDragEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setDragEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 90, WINDOW_WIDTH - 25, WINDOW_HEIGHT - 130);
		contentPane.add(scrollPane);
	}

	@Override
	public void start(ClientController controller) {
		this.controller = controller;
		setVisible(true);
	}

	@Override
	public void show(String value, int messageType) {
		Color textColor = null;

		switch (messageType) {
		case ClientController.ERROR_MESSAGE:
			// red
			textColor = new Color(255, 0, 0);
			break;
		case ClientController.SUCCESS_MESSAGE:
			// green
			textColor = new Color(50, 205, 50);
			break;
		case ClientController.INFO_MESSAGE:
			// blue
			textColor = new Color(30, 144, 255);
			break;
		default:
			// red
			textColor = new Color(255, 0, 0);
			break;
		}
		labelStatus.setOpaque(true);
		labelStatus.setText(value);
		labelStatus.setForeground(textColor);
		labelStatus.setVisible(true);
	}

	@Override
	public String[] get(String... what) {
		// creates a input dialog box for all the inputs entered
		JTextField[] textFields = new JTextField[what.length];
		JPanel inputPanel = new JPanel(new GridLayout(what.length, 1));
		for (int i = 0; i < what.length; i++) {
			// text fields
			textFields[i] = new JTextField(20);
			// labels
			JLabel nextLabel = new JLabel(what[i]);
			nextLabel.setSize(50, 30);
			nextLabel.setHorizontalTextPosition(SwingConstants.TRAILING);
			// rows
			JPanel nextRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			nextRow.add(nextLabel);
			nextRow.add(textFields[i]);
			// panel
			inputPanel.add(nextRow);
		}

		int result = JOptionPane.showConfirmDialog(this, inputPanel,
				"Multiple Inputs", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		String[] values = new String[what.length];
		if (result == JOptionPane.OK_OPTION) {
			for (int i = 0; i < what.length; i++) {
				values[i] = textFields[i].getText();
			}
		}
		return values;
	}

	@Override
	public void showShoeList(ShoeList list) {
		// sets column names and cell data
		String[] columnNames = { "Product ID", "Brand", "Model", "Color",
				"Size", "Quantity", "Price", "Description" };
		Object[][] tableData = new Object[list.size()][columnNames.length];
		for (int i = 0; i < list.size(); i++) {
			Shoe currentShoe = list.get(i);
			tableData[i][0] = currentShoe.getProductId();
			tableData[i][1] = currentShoe.getBrand();
			tableData[i][2] = currentShoe.getModel();
			tableData[i][3] = currentShoe.getColor();
			tableData[i][4] = currentShoe.getSize();
			tableData[i][5] = currentShoe.getQuantity();
			tableData[i][6] = currentShoe.getPrice();
			tableData[i][7] = currentShoe.getDescription();
		}

		// changes the classes of certain columns
		DefaultTableModel tableModel = new DefaultTableModel(tableData,
				columnNames) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0 || columnIndex == 5) {
					return Integer.class;
				} else if (columnIndex == 4 || columnIndex == 6) {
					return Double.class;
				} else {
					return String.class;
				}
			}
		};

		// initiates and arranges the table
		table.setModel(tableModel);
		leftAlignTable(table);
		adjustColumnWidths(table, 67, 134, 134, 100, 70, 70, 70, 272);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public void showOrderList(OrderList list) {
		// sets column names and cell data
		String[] columnNames = { "Order ID", "Product Id", "Username",
				"Billing Name", "Billing Address", "Order Date", "Quantity",
				"Status" };
		Object[][] tableData = new Object[list.size()][columnNames.length];

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (int i = 0; i < list.size(); i++) {
			Order currentOrder = list.get(i);
			tableData[i][0] = currentOrder.getOrderId();
			tableData[i][1] = currentOrder.getProductId();
			tableData[i][2] = currentOrder.getUsername();
			tableData[i][3] = currentOrder.getBillingName();
			tableData[i][4] = currentOrder.getBillingAddress();
			tableData[i][5] = dateFormat.format(currentOrder.getOrderDate());
			tableData[i][6] = currentOrder.getQuantity();
			tableData[i][7] = currentOrder.getStatus();
		}

		// changes the class of certain columns
		DefaultTableModel tableModel = new DefaultTableModel(tableData,
				columnNames) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 6) {
					return Integer.class;
				} else {
					return String.class;
				}
			}
		};

		// initiates and arranges the table
		table.setModel(tableModel);
		leftAlignTable(table);
		adjustColumnWidths(table, 73, 91, 182, 182, 182, 91, 53, 91);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
	}

	private void leftAlignTable(JTable table) {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
		}
	}

	private void adjustColumnWidths(JTable table, int... widths) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
		}
	}
}
