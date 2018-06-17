package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
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
    
    public Article getArticleById(Integer id, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `id`,`key`,`title`,`owner_id`,`status`"
                    + (includeData?",`data`":"")
                    + ",`description`,`create_time`,`modified_time` FROM `articles` where `id`=?;");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setDescription(resultSet.getString("description"));
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
    
    public Article getArticleByKey(String key, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `id`,`key`,`title`,`owner_id`,`status`"
                    + (includeData?",`data`":"")
                    + ",`description`,`create_time`,`modified_time` FROM `articles` WHERE `key`=?;");
            
            statement.setString(1, key);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setData(includeData?resultSet.getString("data"):null);
                article.setDescription(resultSet.getString("description"));
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
    
    public Article getArticleByTitle(String title, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `id`,`key`,`title`,`owner_id`,`status`"
                    + (includeData?",`data`":"")
                    + ",`create_time`,`modified_time` FROM `articles` where `title`=?;");
            
            statement.setString(1, title);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setData(includeData?resultSet.getString("data"):null);
                article.setDescription(resultSet.getString("description"));
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
    
    public List<Article> getAllArticles(boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `id`,`key`,`title`,`owner_id`,`status`"
                    + (includeData?",`data`":"")
                    + ",`description`,`create_time`,`modified_time` FROM `articles`;");
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setDescription(resultSet.getString("description"));
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
    
    public List<Article> getArticlesByTutorialIdAndStatus(Integer tutorialId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT a.`id`,a.`key`,a.`title`,a.`owner_id`,a.`status`"
                + (includeData?",a.`data`":"")
                + ",a.`description`,a.`create_time`,a.`modified_time` "
                    + "FROM `tutorial_article_map` m JOIN `articles` a "
                    + "ON m.`article_id` = a.`id` "
                    + "WHERE `tutorial_id`=? "
                    + (status==null?"":" AND `status`=? ")
                    + "ORDER BY m.`priority` DESC");
            statement.setInt(1, tutorialId);
            if(status!=null)
                statement.setString(2,status);
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(includeData?resultSet.getString("data"):null);
                article.setDescription(resultSet.getString("description"));
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
            statement = connection.prepareStatement("SELECT `id`,`key`,`title`,`owner_id`,`status`,`description`,`create_time`,`modified_time` "
                    + "FROM `articles` "
                    + "WHERE `id` NOT IN (SELECT DISTINCT `article_id` FROM `tutorial_article_map`)");
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setDescription(resultSet.getString("description"));
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
    
    public List<Article> getArticlesBySearchQuery(String query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT `id`,`key`,`title`,`owner_id`,`status`,`description`,`create_time`,`modified_time` "
                    + "FROM `articles` "
                    + "WHERE title LIKE '%"+query+"%' or description LIKE '%"+query+"%';");
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setDescription(resultSet.getString("description"));
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
    
    public List<Article> getTopArticles(Integer limit) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT a.`id`,a.`key`,a.`title`,a.`owner_id`,a.`status`,a.`description`,"
                    + "a.`create_time`,a.`modified_time` "
                    + "FROM `articles` AS a JOIN `votes` AS v ON a.`id` = v.`article_id` WHERE v.`type`='up'"
                    + " ORDER BY v.`count` DESC LIMIT "+limit+";");
            articles = new ArrayList<>();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setDescription(resultSet.getString("description"));
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
    
    public Integer getMaximumPriorityValueByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT max(priority) FROM tutorial_article_map where tutorial_id=?");
            statement.setInt(1, tutorialId);
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
    
    public Integer getMinimumPriorityValueByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT min(priority) FROM tutorial_article_map where tutorial_id=?");
            statement.setInt(1, tutorialId);
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
    
    public Integer getPriorityByTutorialIdAndArticleId(Integer tutorialId, Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT priority FROM tutorial_article_map where tutorial_id=? and article_id=?");
            statement.setInt(1, tutorialId);
            statement.setInt(2, articleId);
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
    
    public String getDataByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT data FROM articles where id=?");
            statement.setInt(1, articleId);
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
    
    public String getKeyByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT key FROM articles where id=?");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getString("key");
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
    
    public Integer getIdByArticleKey(String articleKey) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT `id` FROM `articles` where `key`=?");
            statement.setString(1, articleKey);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("id");
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
    
    public Integer getTutorialIdByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT tutorial_id FROM tutorial_article_map where article_id=?");
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("tutorial_id");
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
    
    public Article getMaximumPriorityArticleByTutorialIdAndStatus(Integer tutorialId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT a.id,a.key,a.title,a.owner_id,a.status"
                + (includeData?",a.data":"")
                + ",a.description,a.create_time,a.modified_time "
                + "FROM tutorial_article_map m JOIN articles a "
                + "ON m.article_id = a.id "
                + "WHERE m.tutorial_id=? AND m.priority=(SELECT max(priority) FROM tutorial_article_map WHERE tutorial_id=?) "
                + (status==null?"":" AND a.status=?;"));
            statement.setInt(1, tutorialId);
            statement.setInt(2, tutorialId);
            if(status!=null)
                statement.setString(3, status);
            System.out.println("Statement : "+statement);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("a.id"));
                article.setKey(resultSet.getString("a.key"));
                article.setTitle(resultSet.getString("a.title"));
                article.setOwnerId(resultSet.getInt("a.owner_id"));
                article.setStatus(resultSet.getString("a.status"));
                article.setData(includeData?resultSet.getString("a.data"):null);
                article.setDescription(resultSet.getString("a.description"));
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
    
    public Article getMinimumPriorityArticleByTutorialIdAndStatus(Integer tutorialId, String status, boolean includeData) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT a.id,a.key,a.title,a.owner_id,a.status"
                + (includeData?",a.data":"")
                + ",a.description,a.create_time,a.modified_time "
                + "FROM tutorial_article_map m JOIN articles a "
                + "ON m.article_id = a.id "
                + "WHERE m.tutorial_id=? AND m.priority=(SELECT min(priority) FROM tutorial_article_map WHERE tutorial_id=?) "
                + (status==null?"":" AND a.status=?;"));
            statement.setInt(1, tutorialId);
            statement.setInt(2, tutorialId);
            if(status!=null)
                statement.setString(3, status);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("a.id"));
                article.setKey(resultSet.getString("a.key"));
                article.setTitle(resultSet.getString("a.title"));
                article.setOwnerId(resultSet.getInt("a.owner_id"));
                article.setStatus(resultSet.getString("a.status"));
                article.setData(includeData?resultSet.getString("a.data"):null);
                article.setDescription(resultSet.getString("a.description"));
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
    
    public Article getArticleByTutorialIdByPriorityGTE(Integer tutorialId, Integer minimumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,key,title,owner_id,status,description,create_time,modified_time "
                    + "FROM articles WHERE id in "
                        + "(SELECT article_id from tutorial_article_map "
                        + "WHERE tutorial_id=? and priority>=? ORDER BY priority ASC LIMIT 1);");
            statement.setInt(1, tutorialId);
            statement.setInt(2, minimumPriority);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setData(null);
                article.setDescription(resultSet.getString("description"));
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
    
    public Article getArticleByTutorialIdByPriorityLTE(Integer tutorialId, Integer maximumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,key,title,owner_id,status,description,create_time,modified_time "
                    + "from articles where id in "
                        + "(SELECT article_id from tutorial_article_map "
                        + "WHERE tutorial_id=? and priority<=? ORDER BY priority DESC LIMIT 1);");
            statement.setInt(1, tutorialId);
            statement.setInt(2, maximumPriority);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setKey(resultSet.getString("key"));
                article.setTitle(resultSet.getString("title"));
                article.setOwnerId(resultSet.getInt("owner_id"));
                article.setStatus(resultSet.getString("status"));
                article.setData(null);
                article.setDescription(resultSet.getString("description"));
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
    
    public void createNewArticle(String key, String title, Integer ownerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO `articles` (`key`,`title`,`owner_id`,`data`,`status`,`description`) VALUES (?,?,?,'new data','new','new desc');");
            statement.setString(1, key);
            statement.setString(2, title);
            statement.setInt(3, ownerId);
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
    
    public void createTutorialArticleLink(Integer articleId, Integer tutorialId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO tutorial_article_map (tutorial_id,article_id,priority) VALUES (?,?,?)");
            statement.setInt(1,articleId);
            statement.setInt(2,tutorialId);
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
    
    public void incrementPriorityForArticleIdInTutorialId(Integer articleId, Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=priority+1 WHERE article_id=? and tutorial_id=?;");
            statement.setInt(1, articleId);
            statement.setInt(2, tutorialId);
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
    
    public void incrementPriorityForAllArticlesByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=priority+1 WHERE tutorial_id=?;");
            statement.setInt(1, tutorialId);
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
    
    public void incrementPriorityForAllArticlesByTutorialIdByPriorityGTE(Integer tutorialId, Integer minimumPriority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=priority+1 WHERE tutorial_id=? and priority>=?");
            statement.setInt(1, tutorialId);
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
    
    public void updateDataByArticleId(Integer articleId, String data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET data=? WHERE id=?");
            statement.setString(1, data);
            statement.setInt(2, articleId);
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
    
    public void updateStatusByArticleId(Integer articleId, String status) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET status=? WHERE id=?");
            statement.setString(1, status);
            statement.setInt(2, articleId);
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
    
    public void updateDescriptionByArticleId(Integer articleId, String description) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET description=? WHERE id=?");
            statement.setString(1, description);
            statement.setInt(2, articleId);
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
    
    public void updateTitleByArticleId(Integer articleId, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET title=? WHERE id=?");
            statement.setString(1, title);
            statement.setInt(2, articleId);
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
    
    public void updateKeyByArticleId(Integer articleId, String key) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE `articles` SET `key`=? WHERE `id`=?");
            statement.setString(1, key);
            statement.setInt(2, articleId);
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
    
    public void updatePriorityByTutorialIdAndArticleId(Integer tutorialId, Integer articleId, Integer priority) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE tutorial_article_map SET priority=? WHERE tutorial_id=? AND article_id=?;");
            statement.setInt(1, priority);
            statement.setInt(2, tutorialId);
            statement.setInt(3, articleId);
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
    
    public Boolean verifyArticleIdAndTutorialIdLink(Integer tutorialId, Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT 1 FROM tutorial_article_map WHERE tutorial_id=? and article_id=?;");
            statement.setInt(1,tutorialId);
            statement.setInt(2,articleId);
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
    
    public void deleteByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM articles WHERE id=?");
            statement.setInt(1,articleId);
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
    
    public void removeTutorialArticleLinksByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM tutorial_article_map WHERE article_id=?;");
            statement.setInt(1,articleId);
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
    
    public void removeTutorialArticleLink(Integer tutorialId, Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM tutorial_article_map where tutorial_id=? AND article_id=?;");
            statement.setInt(1,tutorialId);
            statement.setInt(2,articleId);
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
