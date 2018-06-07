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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Topic;

public class TopicDAO {
    
    DataSource dataSource;
    
    public TopicDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Topic getTopicById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Topic topic = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM topics where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                topic = new Topic();
                topic.setId(resultSet.getInt("id"));
                topic.setKey(resultSet.getString("key"));
                topic.setTitle(resultSet.getString("title"));
                topic.setPriority(resultSet.getInt("priority"));
                topic.setStatus(resultSet.getString("status"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topic;
    }
    
    public Topic getTopicByKey(String key) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Topic topic = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM topics where key=?");
            statement.setString(1, key);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                topic = new Topic();
                topic.setId(resultSet.getInt("id"));
                topic.setKey(resultSet.getString("key"));
                topic.setTitle(resultSet.getString("title"));
                topic.setPriority(resultSet.getInt("priority"));
                topic.setStatus(resultSet.getString("status"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topic;
    }
    
    public Topic getTopicByTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Topic topic = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM topics where title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                topic = new Topic();
                topic.setId(resultSet.getInt("id"));
                topic.setKey(resultSet.getString("key"));
                topic.setTitle(resultSet.getString("title"));
                topic.setPriority(resultSet.getInt("priority"));
                topic.setStatus(resultSet.getString("status"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topic;
    }
    
    public Integer getTopicIdByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT topic_id FROM topic_tutorial_map where tutorial_id=?");
            statement.setInt(1, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("topic_id");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Integer getTopicIdByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT topic_id FROM topic_article_map where article_id=?");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("topic_id");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<Topic> getAllTopics() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Topic> topics = null;
        Topic topic;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM topics ORDER BY priority DESC;");
            resultSet = statement.executeQuery();
            topics = new ArrayList<>();
            while(resultSet.next()) {
                topic = new Topic();
                topic.setId(resultSet.getInt("id"));
                topic.setKey(resultSet.getString("key"));
                topic.setTitle(resultSet.getString("title"));
                topic.setPriority(resultSet.getInt("priority"));
                topic.setStatus(resultSet.getString("status"));
                topics.add(topic);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return topics;
    }
    
    public void createNewTopic(String key, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO topics (key,title,status,priority) VALUES (?,?,'new',10)");
            statement.setString(1, key);
            statement.setString(2, title);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyTopicTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM topics WHERE title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyTopicId(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM topics WHERE id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return (resultSet.first());
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void deleteByTopicId(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topics WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void removeAllLinksByTopicId(Integer topicId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topic_tutorial_map WHERE topic_id=?");
            statement.setInt(1, topicId);
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM topic_article_map WHERE topic_id=?");
            statement.setInt(1, topicId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TopicDAO.class.getName()).log(Level.SEVERE, null, ex);
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