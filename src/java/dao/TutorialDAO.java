package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.Tutorial;

public class TutorialDAO {
    
    DataSource dataSource;
    
    public TutorialDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Tutorial getTutorialById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM tutorials where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setIdentifier(resultSet.getString("identifier"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return tutorial;
    }
    
    public Tutorial getTutorialByTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM tutorials where title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setIdentifier(resultSet.getString("identifier"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return tutorial;
    }
    
    public Tutorial getTutorialByIdentifier(String ident) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM tutorials where identifier=?");
            statement.setString(1, ident);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setIdentifier(resultSet.getString("identifier"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return tutorial;
    }
    
    public List<Tutorial> getAllTutorials() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tutorial> tutorials = null;
        Tutorial tutorial;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM tutorials");
            resultSet = statement.executeQuery();
            tutorials = new ArrayList<>();
            while(resultSet.next()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setIdentifier(resultSet.getString("identifier"));
                tutorials.add(tutorial);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return tutorials;
    }
    
    public void createNewTutorial(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String identifier = title.toLowerCase().replace(" ","-");
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO tutorials (title,identifier) VALUES (?,?)");
            statement.setString(1, title);
            statement.setString(2, identifier);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
            } catch (SQLException e) {
            }
        }
    }
    
    public Boolean verifyTutorialTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id FROM tutorials WHERE title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return false;
    }
    
    public Boolean verifyTutorialId(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id FROM tutorials WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TutorialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection!=null) 
                    connection.close();
                if(statement!=null) 
                    statement.close();
                if(resultSet!=null) 
                    resultSet.close();
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
