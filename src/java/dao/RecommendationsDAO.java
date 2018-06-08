package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecommendationsDAO {
    
    DataSource dataSource;
    
    public RecommendationsDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(RecommendationsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void newRecommendation(Integer articleId, String title, String link, String type) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO recommendations (article_id,title,link,type,priority) VALUES (?,?,?,?,5)");
            statement.setInt(1, articleId);
            statement.setString(2, title);
            statement.setString(3, link);
            statement.setString(4, type);
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
    
    public void updateRecommendationPriority(Integer recommendationId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE recommendations SET priority=? WHERE id=?");
            statement.setInt(1,priority);
            statement.setInt(2,recommendationId);
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
    
    public void updateRecommendationType(Integer recommendationId, String type) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE recommendations SET type=? WHERE id=?");
            statement.setString(1,type);
            statement.setInt(2,recommendationId);
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
    
    public void updateRecommendationTitle(Integer recommendationId, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE recommendations SET title=? WHERE id=?");
            statement.setString(1,title);
            statement.setInt(2,recommendationId);
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
    
    public void updateRecommendationLink(Integer recommendationId, String link) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE recommendations SET link=? WHERE id=?");
            statement.setString(1,link);
            statement.setInt(2,recommendationId);
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
    
    public void deleteRecommendation(Integer recommendationId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE from recommendations WHERE id=?");
            statement.setInt(1,recommendationId);
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
    
    public List<HashMap<String,String>> getRecommendationsByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<HashMap<String,String>> recommendations = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM `recommendations` WHERE `article_id`=? ORDER BY `priority` DESC");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            recommendations = new ArrayList<>();
            HashMap<String,String> tempRecommendation = null;
            while(resultSet.next()) {
                tempRecommendation = new HashMap<>();
                tempRecommendation.put("title",resultSet.getString("title"));
                tempRecommendation.put("link",resultSet.getString("link"));
                tempRecommendation.put("type",resultSet.getString("type"));
                tempRecommendation.put("id",Integer.toString(resultSet.getInt("id")));
                tempRecommendation.put("priority",Integer.toString(resultSet.getInt("priority")));
                
                recommendations.add(tempRecommendation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return recommendations;
    }
}