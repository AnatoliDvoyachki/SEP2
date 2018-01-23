package server.persistence;

import java.sql.*;
import java.util.Map;

import model.Order;
import model.OrderList;
import model.OrderSearchCriteria;
import model.Shoe;
import model.ShoeList;
import model.ShoeSearchCriteria;
import model.User;
import model.UserList;

/**
 * This class is implemented as a Singleton, and is used to establish the
 * connection between the Java application and the SQL database
 **/
public class SQLDatabase implements Persistence {
	public static Persistence database = null;
	private Connection connection = null;
	private Statement statement = null;

	/** Private constrctor, because of Singleton implementation **/
	private SQLDatabase() {
		openConnection();
	}

	public static final int SHOE_BRAND = 1;
	public static final int SHOE_MODEL = 2;
	public static final int SHOE_COLOR = 3;
	public static final int SHOE_SIZE = 4;
	public static final int SHOE_QUANTITY = 5;
	public static final int SHOE_PRICE = 6;
	public static final int SHOE_DESCRIPTION = 7;

	@Override
	public void addShoe(Shoe shoe) {
		try {
			String insert = "INSERT INTO viashoes.shoe (brand, model, color, size, quantity, price, description) "
					+ "VALUES ('"
					+ shoe.getBrand()
					+ "', '"
					+ shoe.getModel()
					+ "', '"
					+ shoe.getColor()
					+ "', "
					+ shoe.getSize()
					+ ", "
					+ shoe.getQuantity()
					+ " , "
					+ shoe.getPrice()
					+ " ,'"
					+ shoe.getDescription() + "' );";
			statement.executeUpdate(insert);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addUser(User user) {
		try {
			String insert = "INSERT INTO viashoes.account (username, password, fullname, address, email) VALUES ('"
					+ user.getUsername()
					+ "', '"
					+ user.getPassword()
					+ "', '"
					+ user.getName()
					+ "', '"
					+ user.getAddress()
					+ "', '"
					+ user.getEmail() + "');";
			statement.executeUpdate(insert);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int addOrder(Order order) {
		try {
			String insert = "INSERT INTO viashoes.productOrder (productId, username,"
					+ " billingName, billingAddress, quantity) VALUES ('"
					+ order.getProductId()
					+ "', '"
					+ order.getUsername()
					+ "', '"
					+ order.getBillingName()
					+ "', '"
					+ order.getBillingAddress()
					+ "', '"
					+ order.getQuantity()
					+ "');";

			statement.executeUpdate(insert);

			String query = "SELECT MAX(orderid) as newid FROM viashoes.productorder;";

			ResultSet select = statement.executeQuery(query);
			select.next();
			int orderId = select.getInt("newid");
			select.close();
			return orderId;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Shoe removeShoe(int productId) {
		if (!shoeExists(productId)) {
			throw new IllegalArgumentException("Shoe does not exist");
		}

		Shoe objToRemove = null;
		try {
			objToRemove = getShoe(productId);
			String remove = "DELETE FROM viashoes.shoe WHERE productid='"
					+ productId + "';";
			statement.executeUpdate(remove);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objToRemove;
	}

	@Override
	public void editShoe(int change, Shoe shoe) {
		String update = "";
		try {
			switch (change) {
			case SHOE_BRAND:

				update = "UPDATE viashoes.shoe SET brand ='" + shoe.getBrand()
						+ "' WHERE productid =" + shoe.getProductId() + ";";
				break;
			case SHOE_MODEL:
				update = "UPDATE viashoes.shoe SET model ='" + shoe.getModel()
						+ "' WHERE productid =" + shoe.getProductId() + ";";
				break;
			case SHOE_COLOR:
				update = "UPDATE viashoes.shoe SET color ='" + shoe.getColor()
						+ "' WHERE productid =" + shoe.getProductId() + ";";
				break;
			case SHOE_SIZE:
				update = "UPDATE viashoes.shoe SET size ='" + shoe.getSize()
						+ "' WHERE productid =" + shoe.getProductId() + ";";
				break;
			case SHOE_QUANTITY:
				update = "UPDATE viashoes.shoe SET quantity ='"
						+ shoe.getQuantity() + "' WHERE productid ="
						+ shoe.getProductId() + ";";
				break;
			case SHOE_PRICE:
				update = "UPDATE viashoes.shoe SET price ='" + shoe.getPrice()
						+ "' WHERE productid =" + shoe.getProductId() + ";";
				break;
			case SHOE_DESCRIPTION:
				update = "UPDATE viashoes.shoe SET description='"
						+ shoe.getDescription() + "' WHERE productid ="
						+ shoe.getProductId() + ";";
				break;
			default:
				break;
			}
			statement.executeUpdate(update);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeDeliveryStatus(int orderId, String status) {
		if (!orderExists(orderId)) {
			throw new IllegalArgumentException("Order does not exist");
		}

		String query = null;
		switch (status) {
		case "1":
			query = "UPDATE viashoes.productorder SET status='"
					+ Order.STATUS_UNPROCESSED + "' WHERE orderid=" + orderId
					+ ";";
			break;
		case "2":
			query = "UPDATE viashoes.productorder SET status='"
					+ Order.STATUS_SHIPPED + "' WHERE orderid=" + orderId + ";";
			break;
		case "3":
			query = "UPDATE viashoes.productorder SET status='"
					+ Order.STATUS_DELIVERED + "' WHERE orderid=" + orderId
					+ ";";
			break;
		default:
			throw new IllegalArgumentException("Invalid status");
		}

		try {
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ShoeList getShoeList(ShoeSearchCriteria criteria) {
		try {
			String queryStart = "SELECT * FROM viashoes.shoe";

			// adding filters and sorts to the query
			StringBuilder queryWhere = new StringBuilder();
			StringBuilder queryOrderBy = new StringBuilder();
			queryOrderBy.append(" ORDER BY ");
			boolean oneOrMoreFilters = false;
			boolean oneOrMoreSorts = false;
			for (Map.Entry<String, String> entry : criteria.getCriteriaMap()
					.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				// constructing 'WHERE' clause
				if (key.startsWith("filter")) {
					if (!oneOrMoreFilters) {
						queryWhere.append(" WHERE ");
						queryWhere.append(key.replace("filter", "") + "='"
								+ value + "'");
						oneOrMoreFilters = true;
					} else {
						queryWhere.append(" and " + key.replace("filter", "")
								+ "='" + value + "'");
					}
				}

				// constructing the 'ORDER BY' clause
				if (key.startsWith("sort")) {
					if (value.equals("0")) {
						value = "asc";
					} else if (entry.getValue().equals("1")) {
						value = "desc";
					}
					if (!oneOrMoreSorts) {
						queryOrderBy.append(key.replace("sort", "") + " "
								+ value);
						oneOrMoreSorts = true;
					} else {
						queryOrderBy.append(", " + key.replace("sort", "")
								+ " " + value);
					}
				}
			}

			// this segment gets the label of the primary key of the table as
			// to use it as the final parameter in the ORDER BY clause
			ResultSet primaryKeyResult = connection.createStatement()
					.executeQuery("SELECT * FROM viashoes.shoe limit 1");

			primaryKeyResult.next();
			String primaryKeyLabel = primaryKeyResult.getMetaData()
					.getColumnLabel(1);

			if (!oneOrMoreSorts) {
				queryOrderBy.append(primaryKeyLabel + " asc");
			} else {
				queryOrderBy.append(", " + primaryKeyLabel + " asc");
			}

			// builds the full query then fetches the result set
			String fullQuery = queryStart + queryWhere.toString()
					+ queryOrderBy.toString();

			ResultSet resultSet = connection.createStatement().executeQuery(
					fullQuery);

			// builds the shoe list
			ShoeList shoeList = new ShoeList();
			while (resultSet.next()) {
				shoeList.add(getShoe(resultSet));
			}

			resultSet.close();
			return shoeList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public OrderList getOrderList(OrderSearchCriteria criteria) {
		try {
			String queryStart = "SELECT * FROM viashoes.productorder";

			// adding filters and sorts to the query
			StringBuilder queryWhere = new StringBuilder();
			StringBuilder queryOrderBy = new StringBuilder();
			queryOrderBy.append(" ORDER BY ");
			boolean oneOrMoreFilters = false;
			boolean oneOrMoreSorts = false;

			for (Map.Entry<String, String> entry : criteria.getCriteriaMap()
					.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				// constructing 'WHERE' clause
				if (key.startsWith("filter")) {
					if (!oneOrMoreFilters) {
						queryWhere.append(" WHERE ");
						queryWhere.append(key.replace("filter", "") + "='"
								+ value + "'");
						oneOrMoreFilters = true;
					} else {
						queryWhere.append(" and " + key.replace("filter", "")
								+ "='" + value + "'");
					}
				}

				// constructing the 'ORDER BY' clause
				if (key.startsWith("sort")) {
					if (value.equals("0")) {
						value = "asc";
					} else if (entry.getValue().equals("1")) {
						value = "desc";
					}
					if (!oneOrMoreSorts) {
						queryOrderBy.append(key.replace("sort", "") + " "
								+ value);
						oneOrMoreSorts = true;
					} else {
						queryOrderBy.append(", " + key.replace("sort", "")
								+ " " + value);
					}
				}
			}

			// this segment gets the label of the primary key of the table as
			// to use it as the final parameter in the ORDER BY clause
			ResultSet primaryKeyResult = connection
					.createStatement()
					.executeQuery("SELECT * FROM viashoes.productorder limit 1");

			primaryKeyResult.next();
			String primaryKeyLabel = primaryKeyResult.getMetaData()
					.getColumnLabel(1);

			if (!oneOrMoreSorts) {
				queryOrderBy.append(primaryKeyLabel + " asc");
			} else {
				queryOrderBy.append(", " + primaryKeyLabel + " asc");
			}

			// builds the full query then fetches the result set
			String fullQuery = queryStart + queryWhere.toString()
					+ queryOrderBy.toString();

			ResultSet resultSet = connection.createStatement().executeQuery(
					fullQuery);

			// builds the shoe list
			OrderList orderList = new OrderList();
			while (resultSet.next()) {
				orderList.add(getOrder(resultSet));
			}

			resultSet.close();
			return orderList;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UserList getUserList() {
		String query = "SELECT * FROM viashoes.account";
		UserList userList = new UserList();
		try {
			ResultSet resultSet = connection.createStatement().executeQuery(
					query);
			while (resultSet.next()) {
				userList.add(getUser(resultSet));
			}
			resultSet.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	@Override
	public String getUserBillingName(String username) {
		String billingName = "";
		try {
			ResultSet resultSet = connection.createStatement().executeQuery(
					"SELECT * FROM viashoes.account WHERE username ='"
							+ username + "';");
			resultSet.next();
			billingName = resultSet.getString("fullname");
			resultSet.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return billingName;
	}

	@Override
	public String getUserBillingAddress(String username) {
		String billingAddress = "";
		try {
			ResultSet resultSet = connection.createStatement().executeQuery(
					"SELECT * FROM viashoes.account WHERE username ='"
							+ username + "';");
			resultSet.next();
			billingAddress = resultSet.getString("address");
			resultSet.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return billingAddress;
	}

	@Override
	public void increaseQuantity(int productId, int quantity) {
		try {
			String update = "UPDATE viashoes.shoe SET quantity = quantity +"
					+ quantity + " WHERE productid='" + productId + "';";

			statement.executeUpdate(update);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decreaseQuantity(int productId, int quantity) {
		try {
			String update = "UPDATE viashoes.shoe SET quantity = quantity -"
					+ quantity + " WHERE productid='" + productId + "';";

			statement.executeUpdate(update);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getQuantity(int productId) {
		if (!shoeExists(productId))
			throw new IllegalArgumentException("Shoe does not exist");

		int quantityInStock = 0;
		try {
			String query = "SELECT quantity FROM viashoes.shoe WHERE productid = '"
					+ productId + "';";

			ResultSet select = statement.executeQuery(query);
			select.next();
			quantityInStock = select.getInt("quantity");
			select.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return quantityInStock;
	}

	@Override
	public boolean userExists(String username) {
		try {
			String query = "SELECT COUNT(*) as numOfUsers FROM viashoes.account where "
					+ "username = '" + username + "';";

			ResultSet select = statement.executeQuery(query);
			select.next();
			if (select.getInt("numOfUsers") == 1)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean orderExists(int orderId) {
		try {
			String query = "SELECT COUNT(*) FROM viashoes.productorder WHERE orderid ="
					+ orderId + ";";
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			if (resultSet.getInt(1) == 1)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean shoeExists(int productId) {
		try {
			String query = "SELECT COUNT(*) FROM viashoes.shoe WHERE productid ="
					+ productId + ";";
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			if (resultSet.getInt(1) == 1)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean credentialsMatch(String username, String userPassword) {
		try {
			String query = "SELECT COUNT(*) FROM viashoes.account where "
					+ "username = '" + username + "' AND password='"
					+ userPassword + "';";
			ResultSet select = statement.executeQuery(query);
			select.next();

			if (select.getInt(1) == 1)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void closeConnection() {
		try {
			connection.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Shoe getShoe(int productId) {
		if (!shoeExists(productId)) {
			throw new IllegalArgumentException("Shoe does not exist");
		}

		Shoe shoe = null;
		try {
			ResultSet resultSet = connection.createStatement().executeQuery(
					"SELECT * FROM viashoes.shoe WHERE productid =" + productId
							+ ";");
			resultSet.next();
			shoe = getShoe(resultSet);

			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shoe;
	}

	/**
	 * Singleton implementation
	 * 
	 * @return the database connectivity
	 **/
	public static Persistence getInstance() {
		if (database == null) {
			database = new SQLDatabase();
		}
		return database;
	}

	/** Used to open a connection with a database **/
	private void openConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres", "postgres",
					"Postgres");

			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Shoe getShoe(ResultSet resultSet) {
		Shoe shoe = null;
		try {
			shoe = new Shoe(resultSet.getInt("productid"),
					resultSet.getString("brand"), resultSet.getString("model"),
					resultSet.getString("color"), resultSet.getDouble("size"),
					resultSet.getInt("quantity"), resultSet.getDouble("price"),
					resultSet.getString("description"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shoe;
	}

	private Order getOrder(ResultSet resultSet) {
		Order order = null;
		try {
			order = new Order(resultSet.getInt("orderid"),
					resultSet.getInt("productid"),
					resultSet.getString("billingname"),
					resultSet.getString("billingaddress"),
					resultSet.getString("username"),
					resultSet.getDate("orderdate"),
					resultSet.getInt("quantity"), resultSet.getString("status"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	private User getUser(ResultSet resultSet) {
		User user = null;
		try {
			user = new User(resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("fullname"),
					resultSet.getString("address"),
					resultSet.getString("email"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
