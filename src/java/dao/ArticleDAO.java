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
    
    public Article getArticleById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,identifier,owner_id,displayIndex,create_time,modified_time FROM articles where id=?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            userdao = new UserDAO();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setIdentifier(resultSet.getString("identifier"));
                article.setOwner(userdao.getUserById(resultSet.getInt("owner_id")));
                article.setDisplayIndex(resultSet.getInt("displayIndex"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(null);
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
    
    public Article getArticleByTutorialAndTitle(Integer tutorialId, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,identifier,owner_id,displayIndex,create_time,modified_time FROM articles where tutorial_id=? and title=?");
            statement.setInt(1, tutorialId);
            statement.setString(2, title);
            resultSet = statement.executeQuery();
            userdao = new UserDAO();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setIdentifier(resultSet.getString("identifier"));
                article.setOwner(userdao.getUserById(resultSet.getInt("owner_id")));
                article.setDisplayIndex(resultSet.getInt("displayIndex"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(null);
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
    
    public Article getArticleByTutorialAndIdentifier(Integer tutorialId, String identifier) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,identifier,owner_id,displayIndex,create_time,modified_time FROM articles where tutorial_id=? and identifier=?");
            statement.setInt(1, tutorialId);
            statement.setString(2, identifier);
            resultSet = statement.executeQuery();
            userdao = new UserDAO();
            if(resultSet.first()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setIdentifier(resultSet.getString("identifier"));
                article.setOwner(userdao.getUserById(resultSet.getInt("owner_id")));
                article.setDisplayIndex(resultSet.getInt("displayIndex"));
                article.setCreateTime(resultSet.getTimestamp("create_time"));
                article.setModifiedTime(resultSet.getTimestamp("modified_time"));
                article.setData(null);
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
    
    public List<Article> getAllArticles() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,identifier,owner_id,displayIndex,create_time,modified_time FROM articles");
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            userdao = new UserDAO();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setIdentifier(resultSet.getString("identifier"));
                article.setOwner(userdao.getUserById(resultSet.getInt("owner_id")));
                article.setDisplayIndex(resultSet.getInt("displayIndex"));
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
    
    public List<Article> getArticlesByOwnerId(Integer ownerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,identifier,owner_id,displayIndex,create_time,modified_time FROM articles where owner_id=?");
            statement.setInt(1, ownerId);
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            userdao = new UserDAO();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setIdentifier(resultSet.getString("identifier"));
                article.setOwner(userdao.getUserById(resultSet.getInt("owner_id")));
                article.setDisplayIndex(resultSet.getInt("displayIndex"));
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
    
    public List<Article> getArticlesByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Article> articles = null;
        Article article;
        UserDAO userdao;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,title,identifier,owner_id,displayIndex,create_time,modified_time FROM articles where tutorial_id=?");
            statement.setInt(1, tutorialId);
            resultSet = statement.executeQuery();
            articles = new ArrayList<>();
            userdao = new UserDAO();
            while(resultSet.next()) {
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setIdentifier(resultSet.getString("identifier"));
                article.setOwner(userdao.getUserById(resultSet.getInt("owner_id")));
                article.setDisplayIndex(resultSet.getInt("displayIndex"));
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
    
    public Integer getLastDisplayIndexByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT max(displayIndex) FROM articles where tutorial_id=?");
            statement.setInt(1, tutorialId);
            resultSet = statement.executeQuery();
            if(resultSet.first()) {
                return resultSet.getInt("max(displayIndex)");
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
    
    public Integer getTutorialIdByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT tutorial_id FROM articles where id=?");
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
    
    public void createNewArticleInTutorialWithData(String title, Integer tutorialId, Integer ownerId, Integer displayIndex, String data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String ident = title.toLowerCase().replace(" ", "-");
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO articles (title,identifier,tutorial_id,owner_id,displayIndex,data) VALUES (?,?,?,?,?,?)");
            statement.setString(1, title);
            statement.setString(2, ident);
            statement.setInt(3,tutorialId);
            statement.setInt(4, ownerId);
            statement.setInt(5, displayIndex);
            statement.setString(6, data);
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
    
    public void createNewArticleInTutorial(String title, Integer tutorialId, Integer ownerId, Integer displayIndex) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String ident = title.toLowerCase().replace(" ", "-");
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO articles (title,identifier,tutorial_id,owner_id,displayIndex) VALUES (?,?,?,?,?)");
            statement.setString(1, title);
            statement.setString(2, ident);
            statement.setInt(3,tutorialId);
            statement.setInt(4, ownerId);
            statement.setInt(5, displayIndex);
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
    
    public void createNewArticle(String title, Integer ownerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String ident = title.toLowerCase().replace(" ", "-");
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO articles (title,identifier,owner_id) VALUES (?,?,?)");
            statement.setString(1, title);
            statement.setString(2, ident);
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
    
    public void createNewArticleWithData(String title, Integer ownerId, String data) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String ident = title.toLowerCase().replace(" ", "-");
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO articles (title,identifier,owner_id,data) VALUES (?,?,?,?)");
            statement.setString(1, title);
            statement.setString(2, ident);
            statement.setInt(3, ownerId);
            statement.setString(4, data);
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
    
    public void incrementDisplayIndexForArticleById(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET displayIndex=displayIndex+1 WHERE id=?");
            statement.setInt(1, articleId);
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
    
    public void incrementDisplayIndexForArticlesByTutorialId(Integer tutorialId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET displayIndex=displayIndex+1 WHERE tutorial_id=?");
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
    
    public void incrementDisplayIndexForArticlesByTutorialId(Integer tutorialId, Integer minDisplayIndex) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET displayIndex=displayIndex+1 WHERE tutorial_id=? and displayIndex>=?");
            statement.setInt(1, tutorialId);
            statement.setInt(2, minDisplayIndex);
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
    
    public void updateDisplayIndexByArticleId(Integer articleId, Integer displayIndex) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE articles SET displayIndex=? WHERE id=?");
            statement.setInt(1, displayIndex);
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
    
    public Boolean verifyArticleTitleAndTutorialId(Integer tutorialId, String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id FROM articles WHERE tutorial_id=? and title=?");
            statement.setInt(1,tutorialId);
            statement.setString(2, title);
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
    
    public void deleteByArticleId(Integer articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM articles WHERE id=?");
            statement.setInt(1,articleId);
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
