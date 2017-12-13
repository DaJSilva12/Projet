package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.swing.JOptionPane;



public class DAO {

	private final DataSource myDataSource;

	/**
	 * Construit le AO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
	public DAO(DataSource dataSource) {
		this.myDataSource = dataSource;
	}

	/**
	 * Contenu de la table DISCOUNT_CODE
	 * @return Liste des discount codes
	 * @throws SQLException renvoyées par JDBC
	 */
	

	/**
	 * Ajout d'un enregistrement dans la table DISCOUNT_CODE
	 * @param code le code (non null)
	 * @param rate le taux (positive or 0)
	 * @return 1 si succès, 0 sinon
	 * @throws SQLException renvoyées par JDBC
	 */
	public int addDiscountCode(String code, float rate) throws SQLException {
		int result = 0;
		String sql = "INSERT INTO DISCOUNT_CODE VALUES (?, ?)";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, code);
			stmt.setFloat(2, rate);
                        
			result = stmt.executeUpdate();
		}
		return result;
	}

		
	/**
	 * Supprime un enregistrement dans la table DISCOUNT_CODE
	 * @param code la clé de l'enregistrement à supprimer
	 * @return le nombre d'enregistrements supprimés (1 ou 0)
	 * @throws java.sql.SQLException renvoyées par JDBC
	 **/
	public int deleteDiscountCode(String code) throws SQLException {
		int result = 0;
		String sql = "DELETE FROM DISCOUNT_CODE WHERE DISCOUNT_CODE = ?";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, code);
			result = stmt.executeUpdate();
		}
		return result;
	}
        
        public boolean idCustomer(int id,String Email)throws SQLException {
            
            String sql = "SELECT CUSTOMER_ID FROM CUSTOMER WHERE EMAIL=?";
            
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                    
                        stmt.setString(1,Email);
                        
			ResultSet rs = stmt.executeQuery();
                        
                        if(rs.next()){
                        int mdp = rs.getInt(1);
                        
                        if(id==mdp){
                                        return true;
                            }else {
                                        return false;
                                   }
                        }else {
                                return false;
			}
		}catch(Exception e){
                    System.out.println(e.getMessage());
                }
            return false;
        }
        
        public List<Product> Product() throws SQLException {

		List<Product> resultprod = new LinkedList<>();

		String sql = "SELECT PRODUCT_ID,PURCHASE_COST,DESCRIPTION FROM PRODUCT";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                            
				String Descr = rs.getString("DESCRIPTION");
                                int id = rs.getInt("PRODUCT_ID");
                                int prix = rs.getInt("PURCHASE_COST");

				Product c = new Product(Descr,prix,id);
				resultprod.add(c);
			}
		}
		return resultprod;
	}
        
       public List<Product> ListproductClient(int id) throws SQLException {
           
           List<Product> Lp = new LinkedList<>();
           String sql = "SELECT DESCRIPTION,QUANTITY,PURCHASE_COST,PRODUCT.PRODUCT_ID FROM PRODUCT,MANUFACTURER,PURCHASE_ORDER WHERE MANUFACTURER.MANUFACTURER_ID=PRODUCT.MANUFACTURER_ID AND PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID AND PURCHASE_ORDER.CUSTOMER_ID=?";
           try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                            String Descr = rs.getString("DESCRIPTION");
                            float prix = rs.getFloat("PURCHASE_COST");
                            int idproduit = rs.getInt("PRODUCT_ID");
                            Product c = new Product(Descr,prix,idproduit);
                            Lp.add(c);
                        }
            }
           
           return Lp;
       }
       
       
      public List<Commande> prixCommande(int id) throws SQLException {
           List<Commande> Lp = new LinkedList<>();
           
           String sql = "SELECT ORDER_NUM,SALES_DATE,DESCRIPTION,QUANTITY,PURCHASE_COST,SHIPPING_COST FROM PRODUCT,MANUFACTURER,PURCHASE_ORDER WHERE MANUFACTURER.MANUFACTURER_ID=PRODUCT.MANUFACTURER_ID AND PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID AND PURCHASE_ORDER.CUSTOMER_ID=?";
           try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                            String Descr = rs.getString("DESCRIPTION");
                            float prix = rs.getFloat("PURCHASE_COST");
                            float prixCom = rs.getFloat("SHIPPING_COST");
                            String dateAchat = rs.getString("SALES_DATE");
                            int orderNum= rs.getInt("ORDER_NUM");

                            int qtt = rs.getInt("QUANTITY");
                            Commande c = new Commande(Descr,qtt,prix,prixCom,dateAchat,orderNum);
                            Lp.add(c);
                        }
            }
           
           return Lp;
       }
      
      public int addCommande(long max,int id,int productid,String date, int qte,String desc) throws SQLException {
		int result = 0;
                
		String sql = "INSERT INTO PURCHASE_ORDER VALUES (?,?,?,?,10,?,?,?)";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, max);
			stmt.setInt(2, id);
                        stmt.setInt(3, productid);
                        stmt.setInt(4, qte);
                        stmt.setString(5, date);
                        stmt.setString(6, date);
                        stmt.setString(7, desc);

			result = stmt.executeUpdate();
		}
		return result;
	}
      
      public int deleteCommande(int OrderNum) throws SQLException {
		int result = 0;
		String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, OrderNum);
			result = stmt.executeUpdate();
		}
		return result;
	}
      
      public long MaxOrdernum ()throws SQLException {
          
                long result=0;
		String sql = "SELECT MAX(ORDER_NUM) FROM PURCHASE_ORDER";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                        ResultSet rs=stmt.executeQuery();
                        if (rs.next()){
                           result = rs.getLong(1);
                        }
                        
                }
            return result;
      }
      
      public int modifierCommande(int OrderCom,int quantité) throws SQLException {
		int result = 0;
		String sql = "UPDATE PURCHASE_ORDER SET QUANTITY =? WHERE ORDER_NUM = ?";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, quantité);
                        stmt.setInt(2,OrderCom);
			result = stmt.executeUpdate();
		}
		return result;
	}
      
      
      public Map<String, Float> CAcatégorieZoneGeo(String dateDeb, String dateFin)throws SQLException, ParseException{
               Map<String, Float> CA = new HashMap<>();
               
                       Date date_debut_format = new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb);
                        Date date_fin_format = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
          String sql = "SELECT SUM(QUANTITY*PURCHASE_COST),STATE FROM PURCHASE_ORDER INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID = PURCHASE_ORDER.PRODUCT_ID INNER JOIN CUSTOMER ON PURCHASE_ORDER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID WHERE SALES_DATE <= ? AND SALES_DATE > ? GROUP BY STATE";
          try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
              
                        java.sql.Date date_debut_sql = new java.sql.Date(date_debut_format.getTime());
                        java.sql.Date date_fin_sql = new java.sql.Date(date_fin_format.getTime());
            
                        stmt.setString(1,dateFin);
                        stmt.setString(2,dateDeb);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                               String etat = rs.getString("STATE");
                               float ca = rs.getFloat(1);
                            CA.put(etat, ca);
                        }
            }catch(SQLException e){
                e.printStackTrace();
            }
          return CA;
      }
      public Map<String, Float> CAcatégorieArticle(String dateDeb, String dateFin)throws SQLException{
               Map<String, Float> CA = new HashMap<>();
               
          String sql ="SELECT SUM(PURCHASE_COST*QUANTITY),PRODUCT_CODE\n" +
                    "FROM PURCHASE_ORDER\n" +
                    "INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID = PURCHASE_ORDER.PRODUCT_ID\n" +
                    "WHERE SALES_DATE <= ? AND SALES_DATE > ? \n" +
                    "GROUP BY PRODUCT_CODE";
          try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setString(1,dateFin );
                        stmt.setString(2,dateDeb );

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                               String categorie = rs.getString("PRODUCT_CODE");
                               float ca = rs.getFloat(1);
                            CA.put(categorie, ca);
                        }
            }catch(SQLException e){
                e.printStackTrace();
            }
          return CA;
      }
      
     
      
      
      
      public Map<String, Float> CAClient(String dateDeb, String dateFin)throws SQLException, ParseException{
               Map<String, Float> CA = new HashMap<>();
                Date date_debut_format = new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb);
                        Date date_fin_format = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
          String sql ="SELECT SUM(QUANTITY*PURCHASE_COST),NAME\n" +
                        "FROM PURCHASE_ORDER\n" +
                        "INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID = PURCHASE_ORDER.PRODUCT_ID\n" +
                        "INNER JOIN CUSTOMER ON PURCHASE_ORDER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID\n" +
                         "WHERE SALES_DATE <= ? AND SALES_DATE > ? \n" +
                        "GROUP BY NAME";
          try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                       java.sql.Date date_debut_sql = new java.sql.Date(date_debut_format.getTime());
                        java.sql.Date date_fin_sql = new java.sql.Date(date_fin_format.getTime());
                        stmt.setString(1,dateFin );
                        stmt.setString(2,dateDeb );

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                               String categorie = rs.getString("NAME");
                               float ca = rs.getFloat(1);
                            CA.put(categorie, ca);
                        }
            }catch(SQLException e){
                e.printStackTrace();
            }
          return CA;
      }
}
