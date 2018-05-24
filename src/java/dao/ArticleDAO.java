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
import model.Article;

public class ArticleDAO {
    
    DataSource dataSource;
    
    public ArticleDAO() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            this.dataSource = (DataSource) context.lookup("appConnPool");
        }
        catch(NamingException e) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Article getArticleById(String id, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope"
                    + (includeData?",data":"")
                    + ",create_time,modified_time FROM articles where id=?;");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(includeData?resultSet.getString("data"):null);
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
        return article;
    }
    
    public Article getArticleByTitle(String title, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope"
                    + (includeData?",data":"")
                    + ",create_time,modified_time FROM articles where title=?;");
            
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(includeData?resultSet.getString("data"):null);
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
        return article;
    }
    
    public List<Article> getAllArticles(boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope"
                    + (includeData?",data":"")
                    + ",create_time,modified_time FROM articles;");
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(includeData?resultSet.getString("data"):null);
                articles.add(article);
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
        return articles;
    }
    
    public List<Article> getArticlesByTutorialIdAndStatus(String tutorialId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT a.id,a.title,a.owner_id,a.status,a.scope"
                + (includeData?",a.data":"")
                + ",a.create_time,a.modified_time "
                    + "FROM tutorial_article_map m JOIN articles a "
                    + "ON m.article_id = a.id "
                    + "WHERE tutorial_id=?"
                    + (status==null?"":" AND status=?")
                    + "ORDER BY m.priority DESC");
            statement.setString(1, tutorialId);
            if(status!=null)
                statement.setString(2,status);
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(includeData?resultSet.getString("data"):null);
                articles.add(article);
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
        return articles;
    }
    
    public List<Article> getUnlinkedArticles() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope,create_time,modified_time "
                    + "FROM articles "
                    + "WHERE id NOT IN (SELECT DISTINCT article_id FROM tutorial_article_map UNION SELECT DISTINCT article_id FROM topic_article_map)");
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(null);
                articles.add(article);
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
        return articles;
    }
    
    public List<Article> getArticlesByTopicIdAndStatus(String topicId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            // all topics
            if(topicId==null) {
                statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope"
                    + (includeData?",data":"")
                    + ",create_time,modified_time "
                        + "FROM articles "
                        + "WHERE id NOT IN (SELECT article_id FROM topic_article_map)"
                    + (status==null?"":" AND status=?;"));
                if(status!=null)
                    statement.setString(1,status);
            }
            else {
                statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope"
                    + (includeData?",data":"")
                    + ",create_time,modified_time "
                        + "FROM articles "
                        + "WHERE id IN (SELECT article_id FROM topic_article_map WHERE topic_id=?)"
                    + (status==null?"":" AND status=?;"));
                statement.setString(1, topicId);
                if(status!=null)
                    statement.setString(2,status);
            }
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(includeData?resultSet.getString("data"):null);
                articles.add(article);
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
        return articles;
    }
    
    public Integer getMaximumPriorityValueByTutorialId(String tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT max(priority) FROM tutorial_article_map where tutorial_id=?");
            statement.setString(1, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("max(priority)");
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
        return null;
    }
    
    public Integer getMinimumPriorityValueByTutorialId(String tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT min(priority) FROM tutorial_article_map where tutorial_id=?");
            statement.setString(1, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("min(priority)");
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
        return null;
    }
    
    public Integer getPriorityByTutorialIdAndArticleId(String tutorialId, String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT priority FROM tutorial_article_map where tutorial_id=? and article_id=?");
            statement.setString(1, tutorialId);
            statement.setString(2, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("priority");
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
        return null;
    }
    
    public String getDataByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT data FROM articles where id=?");
            statement.setString(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getString("data");
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
        return null;
    }
    
    public String getTutorialIdByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT tutorial_id FROM tutorial_article_map where article_id=?");
            statement.setString(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getString("tutorial_id");
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
        return null;
    }
    
    public Article getMaximumPriorityArticleByTutorialIdAndStatus(String tutorialId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT a.id,a.title,a.owner_id,a.status,a.scope"
                + (includeData?",a.data":"")
                + ",a.create_time,a.modified_time "
                + "FROM tutorial_article_map m JOIN articles a "
                + "ON m.article_id = a.id "
                + "WHERE m.tutorial_id=? AND m.priority=(SELECT max(priority) FROM tutorial_article_map WHERE tutorial_id=?) "
                + (status==null?"":" AND a.status=?;"));
            statement.setString(1, tutorialId);
            statement.setString(2, tutorialId);
            if(status!=null)
                statement.setString(3, status);
            System.out.println("Statement : "+statement);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getString("a.id"));
                article.setTitle(resultSet.getString("a.title"));
                article.setOwnerId(resultSet.getInt("a.owner_id"));
                article.setStatus(resultSet.getString("a.status"));
                article.setScope(resultSet.getString("a.scope"));
                article.setData(includeData?resultSet.getString("a.data"):null);
                article.setCreateTime(resultSet.getTimestamp("a.create_time"));
                article.setModifiedTime(resultSet.getTimestamp("a.modified_time"));
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
        return article;
    }
    
    public Article getMinimumPriorityArticleByTutorialIdAndStatus(String tutorialId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT a.id,a.title,a.owner_id,a.status,a.scope"
                + (includeData?",a.data":"")
                + ",a.create_time,a.modified_time "
                + "FROM tutorial_article_map m JOIN articles a "
                + "ON m.article_id = a.id "
                + "WHERE m.tutorial_id=? AND m.priority=(SELECT min(priority) FROM tutorial_article_map WHERE tutorial_id=?) "
                + (status==null?"":" AND a.status=?;"));
            statement.setString(1, tutorialId);
            statement.setString(2, tutorialId);
            if(status!=null)
                statement.setString(3, status);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setData(includeData?resultSet.getString("data"):null);
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
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
        return article;
    }
    
    public Article getArticleByTutorialIdByPriorityGTE(String tutorialId, Integer minimumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope,create_time,modified_time "
                    + "FROM articles WHERE id in "
                        + "(SELECT article_id from tutorial_article_map "
                        + "WHERE tutorial_id=? and priority>=? ORDER BY priority ASC LIMIT 1);");
            statement.setString(1, tutorialId);
            statement.setInt(2, minimumPriority);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setData(null);
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
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
        return article;
    }
    
    public Article getArticleByTutorialIdByPriorityLTE(String tutorialId, Integer maximumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,owner_id,status,scope,create_time,modified_time "
                    + "from articles where id in "
                        + "(SELECT article_id from tutorial_article_map "
                        + "WHERE tutorial_id=? and priority<=? ORDER BY priority DESC LIMIT 1);");
            statement.setString(1, tutorialId);
            statement.setInt(2, maximumPriority);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getString("id"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setScope(resultSet.getString("scope"));
                article.setData(null);
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
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
        return article;
    }
    
    public void createNewArticle(String id, String title, Integer ownerId, String scope, String data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            if(data==null) {
                statement = connection.prepareStatement("INSERT INTO articles (id,title,owner_id,data,scope,status) VALUES (?,?,?,?,?,'new');");
                statement.setString(1, id);
                statement.setString(2, title);
                statement.setInt(3, ownerId);
                statement.setString(4,data);
                statement.setString(5,scope);
            }
            else {
                statement = connection.prepareStatement("INSERT INTO articles (id,title,owner_id,scope,status) VALUES (?,?,?,?,'new');");
                statement.setString(1, id);
                statement.setString(2, title);
                statement.setInt(3, ownerId);
                statement.setString(4,scope);
            }
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void createTutorialArticleLink(String articleId, String tutorialId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO tutorial_article_map (tutorial_id,article_id,priority) VALUES (?,?,?)");
            statement.setString(1,articleId);
            statement.setString(2,tutorialId);
            statement.setInt(3, priority);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void createTopicArticleLink(String topicId, String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO topic_article_map (topic_id,article_id) VALUES (?,?)");
            statement.setString(1,topicId);
            statement.setString(2,articleId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void incrementPriorityForArticleIdInTutorialId(String articleId, String tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=priority+1 WHERE article_id=? and tutorial_id=?;");
            statement.setString(1, articleId);
            statement.setString(2, tutorialId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void incrementPriorityForAllArticlesByTutorialId(String tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=priority+1 WHERE tutorial_id=?;");
            statement.setString(1, tutorialId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void incrementPriorityForAllArticlesByTutorialIdByPriorityGTE(String tutorialId, Integer minimumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=priority+1 WHERE tutorial_id=? and priority>=?");
            statement.setString(1, tutorialId);
            statement.setInt(2, minimumPriority);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updateDataByArticleId(String articleId, String data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET data=? WHERE id=?");
            statement.setString(1, data);
            statement.setString(2, articleId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updateStatusByArticleId(String articleId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET status=? WHERE id=?");
            statement.setString(1, status);
            statement.setString(2, articleId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updateScopeByArticleId(String articleId, String scope) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET scope=? WHERE id=?");
            statement.setString(1, scope);
            statement.setString(2, articleId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updatePriorityByTutorialIdAndArticleId(String tutorialId, String articleId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=? WHERE tutorial_id=? AND article_id=?;");
            statement.setInt(1, priority);
            statement.setString(2, tutorialId);
            statement.setString(3, articleId);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Boolean verifyArticleIdAndTutorialIdLink(String tutorialId, String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM tutorial_article_map WHERE tutorial_id=? and article_id=?;");
            statement.setString(1,tutorialId);
            statement.setString(2,articleId);
            resultSet = statement.executeQuery();
            return (resultSet.first());
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
        return false;
    }
    
    public Boolean verifyArticleTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM articles WHERE title=?");
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            return (resultSet.first());
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
        return false;
    }
    
    public void deleteByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM articles WHERE id=?");
            statement.setString(1,articleId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void removeAllLinksByArticleId(String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM tutorial_article_map WHERE article_id=?;");
            statement.setString(1,articleId);
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM topic_article_map WHERE article_id=?;");
            statement.setString(1,articleId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void removeTopicArticleLink(String topicId, String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM topic_article_map WHERE topic_id=? AND article_id=?;");
            statement.setString(1,topicId);
            statement.setString(2,articleId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void removeTutorialArticleLink(String tutorialId, String articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM tutorial_article_map where tutorial_id=? AND article_id=?;");
            statement.setString(1,tutorialId);
            statement.setString(2,articleId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
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
