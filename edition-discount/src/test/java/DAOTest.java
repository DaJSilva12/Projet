/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import javax.sql.DataSource;
import model.DAO;
import model.DAOException;
import static model.DataSourceFactory.getDataSource;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;


public class DAOTest {

    private static DataSource myDataSource; 
    private static Connection myConnection;
    private DAO myDAO;

    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        myDataSource = getDataSource();
        myConnection = myDataSource.getConnection();
        String sqlFilePath = DAOTest.class.getResource("/servlet/export.sql").getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
        sqlFile.setConnection(myConnection);
        sqlFile.execute();
        sqlFile.closeReader();
        // On crée l'objet à tester
        myDAO = new DAO(myDataSource);
    }


    @Test
    public void idCustomerTest() throws DAOException, SQLException {
        String useradress = "jumboeagle@example.com";
        int customerid = 1;
        assertTrue(myDAO.idCustomer(customerid,useradress ));
    }
    
    
    @Test
    public void commandeClientyIdTest() throws SQLException {
        int customerid = 2;
        int nombredecommande = 2;
        int nombredecommandeDaoMethod = myDAO.ListproductClient(customerid).size();
        assertEquals(nombredecommande, nombredecommandeDaoMethod);
    }
    
   
     public static DataSource getDataSource() throws SQLException {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
    


}
