package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.CityBO;
import exception.CityDAOException;

public class CityDAO implements InterfaceDAO<CityBO> {

	private Connection jdbcConnection;

	public CityDAO(Connection jdbcConnection) {
		if (jdbcConnection == null)
			System.out.println("Connexion inexistante !");
		this.jdbcConnection = jdbcConnection;
	}

	// Insérer une ville dans la BDD
	@Override
	public boolean add(CityBO cityBO) throws CityDAOException {
		try {
			String sql = "INSERT INTO city (name, mayor, inhabitants, postalcode) " + "VALUES (?, ?, ?, ?)";

			PreparedStatement statement = this.jdbcConnection.prepareStatement(sql);
			statement.setString(1, cityBO.getName());
			statement.setString(2, cityBO.getMayor());
			statement.setInt(3, cityBO.getInhabitants());
			statement.setString(4, cityBO.getPostalcode());

			boolean rowInserted = statement.executeUpdate() > 0;
			statement.close();
			return rowInserted;
		} catch (SQLException e) {
			throw new CityDAOException(e);
		}
	}

	// Mettre à jour une ville dans la BDD
	@Override
	public boolean update(CityBO cityBO) throws CityDAOException {
		try {
			String sql = "UPDATE city " + "SET name = ?, mayor = ?, inhabitants = ?, postalcode = ? "
					+ "WHERE id_city = ?";

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, cityBO.getName());
			statement.setString(2, cityBO.getMayor());
			statement.setInt(3, cityBO.getInhabitants());
			statement.setString(4, cityBO.getPostalcode());
			statement.setInt(5, cityBO.getId_city());

			boolean rowUpdated = statement.executeUpdate() > 0;
			statement.close();
			return rowUpdated;
		} catch (SQLException e) {
			throw new CityDAOException(e);
		}
	}

	// Supprimer une ville dans la BDD
	@Override
	public boolean delete(CityBO cityBO) throws CityDAOException {
		try {
			String sql = "DELETE FROM city " 
					+ "WHERE id_city = ?";

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setInt(1, cityBO.getId_city());

			boolean rowDeleted = statement.executeUpdate() > 0;
			statement.close();
			return rowDeleted;
		} catch (SQLException e) {
			throw new CityDAOException(e);
		}
	}

	// Obtenir toutes les villes
	@Override
	public List<CityBO> getAll() throws CityDAOException {
		try {
			List<CityBO> listCityBO = new ArrayList<CityBO>();

			String sql = "SELECT id_city, name, mayor, inhabitants, postalcode " + "FROM city";

			Statement statement = jdbcConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id_city = resultSet.getInt("id_city");
				String name = resultSet.getString("name");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");

				CityBO cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
				listCityBO.add(cityBO);
			}

			resultSet.close();
			statement.close();
			return listCityBO;
		} catch (SQLException e) {
			throw new CityDAOException(e);
		}
	}

	// Obtenir une ville par son ID
	@Override
	public CityBO getById(int id_city) throws CityDAOException {
		try {
			CityBO cityBO = null;
			String sql = "SELECT name, mayor, inhabitants, postalcode " + "FROM city " + "WHERE id_city = ?";

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setInt(1, id_city);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String name = resultSet.getString("name");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");

				cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
			}

			resultSet.close();
			statement.close();
			return cityBO;
		} catch (SQLException e) {
			throw new CityDAOException(e);
		}
	}

	// Obtenir une ville par son nom
	public CityBO getByName(String name) throws CityDAOException {
		try {
			CityBO cityBO = null;
			String sql = "SELECT id_city, mayor, inhabitants, postalcode " + "FROM city " + "WHERE name = ?";

			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int id_city = resultSet.getInt("id_city");
				String mayor = resultSet.getString("mayor");
				int inhabitants = resultSet.getInt("inhabitants");
				String postalcode = resultSet.getString("postalcode");

				cityBO = new CityBO(name, mayor, inhabitants, postalcode);
				cityBO.setId_city(id_city);
			}

			resultSet.close();
			statement.close();
			return cityBO;
		} catch (SQLException e) {
			throw new CityDAOException(e);
		}
	}

	// Vérifier l'existence d'une ville dans la BDD
	@Override
	public boolean exist(CityBO cityBO) throws CityDAOException {
		boolean result = false;
		if (this.getById(cityBO.getId_city()) != null)
			result = true;
		return result;
	}

}
