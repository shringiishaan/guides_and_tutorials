package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.Article;
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
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
    
    public Tutorial getTutorialByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                      "SELECT * "
                    + "FROM tutorial_article_map m JOIN tutorials t "
                    + "ON m.tutorial_id=t.id "
                    + "WHERE m.article_id=?");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("t.id"));
                tutorial.setKey(resultSet.getString("t.key"));
                tutorial.setTitle(resultSet.getString("t.title"));
                tutorial.setStatus(resultSet.getString("t.status"));
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
    
    public Tutorial getMaximumPriorityTutorialByTopicId(Integer topicId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM tutorials WHERE id="
                        + "(SELECT tutorial_id FROM topic_tutorial_map "
                            + "WHERE topic_id=? AND priority=(SELECT max(priority) FROM topic_tutorial_map WHERE topic_id=?))");
            statement.setInt(1, topicId);
            statement.setInt(2, topicId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
    
    public Tutorial getTutorialByTopicIdByPriorityGTE(Integer topicId, Integer minimumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * from tutorials where id in "
                    + "(SELECT tutorial_id from topic_tutorial_map WHERE topic_id=? and priority>=? ORDER BY priority ASC LIMIT 1);");
            statement.setInt(1, topicId);
            statement.setInt(2, minimumPriority);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
    
    public Tutorial getTutorialByTopicIdByPriorityLTE(Integer topicId, Integer maximumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Tutorial tutorial = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * from tutorials WHERE id in "
                    + "(SELECT tutorial_id from topic_tutorial_map WHERE topic_id=? and priority<=? ORDER BY priority DESC LIMIT 1);");
            statement.setInt(1, topicId);
            statement.setInt(2, maximumPriority);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
            statement = connection.prepareStatement("SELECT * FROM tutorials;");
            resultSet = statement.executeQuery();
            tutorials = new ArrayList<>();
            while(resultSet.next()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
    
    public List<Tutorial> getTutorialsByTopicIdAndStatus(Integer topicId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tutorial> tutorials = null;
        Tutorial tutorial;
        try {
            connection = dataSource.getConnection();
            if(status==null) {
                statement = connection.prepareStatement("SELECT * "
                        + "FROM topic_tutorial_map tmap JOIN tutorials t "
                        + "ON tmap.tutorial_id = t.id "
                        + "WHERE topic_id=? "
                        + "ORDER BY priority DESC;");
                statement.setInt(1, topicId);
            }
            else {
                statement = connection.prepareStatement("SELECT * "
                        + "FROM topic_tutorial_map tmap JOIN tutorials t "
                        + "ON tmap.tutorial_id = t.id "
                        + "WHERE topic_id=? AND status=? "
                        + "ORDER BY priority DESC;");
                statement.setInt(1, topicId);
                statement.setString(2, status);
            }
            resultSet = statement.executeQuery();
            tutorials = new ArrayList<>();
            while(resultSet.next()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("t.id"));
                tutorial.setKey(resultSet.getString("t.key"));
                tutorial.setTitle(resultSet.getString("t.title"));
                tutorial.setStatus(resultSet.getString("t.status"));
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
    
    public List<Tutorial> getUnlinkedTutorials() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tutorial> tutorials = null;
        Tutorial tutorial;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * "
                    + "FROM tutorials "
                    + "WHERE id NOT IN "
                        + "(SELECT DISTINCT tutorial_id FROM topic_tutorial_map)");
            resultSet = statement.executeQuery();
            tutorials = new ArrayList<>();
            while(resultSet.next()) {
                tutorial = new Tutorial();
                tutorial.setId(resultSet.getInt("id"));
                tutorial.setKey(resultSet.getString("key"));
                tutorial.setTitle(resultSet.getString("title"));
                tutorial.setStatus(resultSet.getString("status"));
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
    
    public List<Tutorial> getRelatedTutorialsByArticleId(Integer articleId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tutorial> relatedTutorials=null;
        Tutorial tempTutorial;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM topic_tutorial_map m JOIN tutorials t "
                    + "ON m.tutorial_id = t.id "
                    + "WHERE m.tutorial_id IN "
                        + "(SELECT tutorial_id FROM topic_tutorial_map WHERE topic_id="
                            + "(SELECT topic_id FROM topic_tutorial_map WHERE tutorial_id="
                                + "(SELECT tutorial_id FROM tutorial_article_map WHERE article_id=? LIMIT 1))) "
                    + "ORDER BY m.priority DESC;");
            statement.setInt(1, articleId);
                
            resultSet = statement.executeQuery();
            relatedTutorials = new ArrayList<>();
            while(resultSet.next()) {
                tempTutorial = new Tutorial();
                tempTutorial.setId(resultSet.getInt("t.id"));
                tempTutorial.setKey(resultSet.getString("t.key"));
                tempTutorial.setTitle(resultSet.getString("t.title"));
                tempTutorial.setStatus(resultSet.getString("t.status"));
                relatedTutorials.add(tempTutorial);
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
        return relatedTutorials;
    }
    
    public Map<Tutorial,List<Article>> getRelatedTutorialsAndArticlesByArticleId(Integer articleId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<Tutorial,List<Article>> relatedTutorials=null;
        Tutorial tempTutorial;
        List<Article> tempArticles;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM tutorials WHERE id IN "
                + "(SELECT tutorial_id FROM topic_tutorial_map WHERE topic_id="
                    + "(SELECT topic_id FROM topic_tutorial_map WHERE tutorial_id="
                            + "(SELECT tutorial_id FROM tutorial_article_map WHERE article_id=?) "
                        + "ORDER BY priority DESC)"
                    + "ORDER BY priority DESC);");
            statement.setInt(1, articleId);
            
            resultSet = statement.executeQuery();
            relatedTutorials = new HashMap<>();
            ArticleDAO articledao = new ArticleDAO();
            while(resultSet.next()) {
                tempTutorial = new Tutorial();
                tempTutorial.setId(resultSet.getInt("id"));
                tempTutorial.setKey(resultSet.getString("key"));
                tempTutorial.setTitle(resultSet.getString("title"));
                tempTutorial.setStatus(resultSet.getString("status"));
                tempArticles = articledao.getArticlesByTutorialIdAndStatus(tempTutorial.getId(), status, false);
                relatedTutorials.put(tempTutorial,tempArticles);
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
        return relatedTutorials;
    }
    
    public void createNewTutorial(String key, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO `tutorials` (`key`,`title`,`status`) VALUES (?,?,'new')");
            statement.setString(1, key);
            statement.setString(2, title);
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
    
    public Integer getMaximumTutorialPriorityValueByTopicId(Integer topicId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT max(priority) FROM topic_tutorial_map where topic_id=?");
            statement.setInt(1, topicId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("max(priority)");
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
        return null;
    }
    
    public Integer getIdByTutorialKey(String tutorialKey) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `id` FROM `tutorials` where `key`=?");
            statement.setString(1, tutorialKey);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("id");
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
        return null;
    }
    
    public String getKeyByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `key` FROM `tutorials` where `id`=?");
            statement.setInt(1, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getString("key");
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
        return null;
    }
    
    public void incrementPriorityForAllTutorialsByTopicId(Integer topicId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE topic_tutorial_map SET priority=priority+1 WHERE topic_id=?");
            statement.setInt(1,topicId);
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
    
    public Integer getMinimumTutorialPriorityValueByTopicId(Integer topicId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT min(priority) FROM topic_tutorial_map where topic_id=?");
            statement.setInt(1, topicId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("min(priority)");
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
        return null;
    }
    
    public Integer getPriorityByTopicIdAndTutorialId(Integer topicId, Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT priority FROM topic_tutorial_map where topic_id=? and tutorial_id=?");
            statement.setInt(1, topicId);
            statement.setInt(2, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("priority");
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
        return null;
    }
    
    public void updatePriorityByTopicIdAndTutorialId(Integer topicId, Integer tutorialId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE topic_tutorial_map SET priority=? WHERE topic_id=? and tutorial_id=?");
            statement.setInt(1, priority);
            statement.setInt(2,topicId);
            statement.setInt(3,tutorialId);
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
    
    public void updateStatusByTutorialId(Integer tutorialId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorials SET status=? WHERE id=?");
            statement.setInt(2, tutorialId);
            statement.setString(1, status);
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
    
    public void updateKeyByTutorialId(Integer tutorialId, String key) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE `tutorials` SET `key`=? WHERE `id`=?");
            statement.setInt(2, tutorialId);
            statement.setString(1, key);
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
    
    public void updateTitleByTutorialId(Integer tutorialId, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorials SET title=? WHERE id=?");
            statement.setInt(2, tutorialId);
            statement.setString(1, title);
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
            statement = connection.prepareStatement("SELECT 1 FROM tutorials WHERE title=?");
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
            statement = connection.prepareStatement("SELECT 1 FROM tutorials WHERE id=?");
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
    
    public void deleteByTutorialId(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM tutorials WHERE id=?");
            statement.setInt(1, id);
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
    
    public void createTopicTutorialLink(Integer topicId, Integer tutorialId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO topic_tutorial_map (topic_id,tutorial_id,priority) VALUES (?,?,?)");
            statement.setInt(1, topicId);
            statement.setInt(2, tutorialId);
            statement.setInt(3, priority);
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
    
    public void removeTopicTutorialLink(Integer topicId, Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topic_tutorial_map WHERE topic_id=? and tutorial_id=?");
            statement.setInt(1, topicId);
            statement.setInt(2, tutorialId);
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
    
    public void removeAllLinksByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topic_tutorial_map WHERE tutorial_id=?");
            statement.setInt(1, tutorialId);
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM tutorial_article_map WHERE tutorial_id=?");
            statement.setInt(1, tutorialId);
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
}
