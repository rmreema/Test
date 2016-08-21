import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

 
//import com.fasterxml.jackson.databind.ObjectMapper;
 
public class ExcelToDb {
 
                private static final String DB_DRIVER = "org.h2.Driver";
                private static final String DB_CONNECTION = "jdbc:h2:D:/Test";
                private static final String DB_USER = "aa";
                private static final String DB_PASSWORD = "aa";
 
                public static void main(String[] args) throws SQLException {
                                Connection connection = getDBConnection();
                               
                                //Statement stmt = null;
                                
//                                try {
//                                                connection.setAutoCommit(false);
//                                                stmt = connection.createStatement();
// 
//                                                // stmt.execute("CREATE TABLE Reema(id int primary key, name VARCHAR(255))");
//                                               
//                                               // String jsonStructure = JsonTestClass.convertJsonStructure();
//                                               
//                                               // stmt.execute("INSERT INTO Test VALUES(3, 'nreema')");
//                                               
//                                               // stmt.execute("SELECT * FROM Test");
//                                               
//                                               
////                                                ResultSet rs = stmt.executeQuery("select * from Test");
//// 
////                                                while (rs.next()) {
////                                                                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
////                                                               
////                                                                //ObjectMapper mapper = new ObjectMapper();
////                                                                //Json user = mapper.readValue(rs, Json.class);
//////                                                            System.out.println(user.getData());
////                                                               
////                                                }
////// 
//                                                stmt.close();
//                                                connection.commit();
//                                } catch (SQLException e) {
//                                                System.out.println("Exception Message " + e.getLocalizedMessage());
//                                } catch (Exception e) {
//                                                e.printStackTrace();
//                                } finally {
//                                                connection.close();
//                                }
//                }
                                
                                try{
                                    List list=new ArrayList();	
                                    String fileName="d:\\menu.xls";
                                    File file=new File(fileName);
                                    InputStream input = new BufferedInputStream(new FileInputStream(file));
                                    POIFSFileSystem fs = new POIFSFileSystem( input );
                                    HSSFWorkbook wb = new HSSFWorkbook(fs);
                                    HSSFSheet sheet = wb.getSheetAt(0);
                                  //int i=0;
                                 Iterator rows=sheet.rowIterator();
                                 while(rows.hasNext()){
                                     HSSFRow row=(HSSFRow)rows.next();
                                     System.out.println("\n");
                                     Iterator cells=row.cellIterator();
                                     while( cells.hasNext() ) {
                                         HSSFCell cell = (HSSFCell) cells.next();
                                         if(HSSFCell.CELL_TYPE_NUMERIC==cell.getCellType()){
                                         System.out.print(cell.getNumericCellValue()+" " );
                                        // list.add(cell.getNumericCellValue());

                                         }
                                         else if (HSSFCell.CELL_TYPE_STRING==cell.getCellType()){
                                             System.out.print(cell.getStringCellValue()+" " );
                                             //list.add(cell.getStringCellValue());

                                         }
                                         else
                                             if (HSSFCell.CELL_TYPE_BOOLEAN==cell.getCellType()){
                                             System.out.print(cell.getBooleanCellValue()+" " );
                                             //list.add(cell.getBooleanCellValue());

                                             }
                                             else
                                                 if(HSSFCell.CELL_TYPE_BLANK==cell.getCellType()){
                                                     System.out.print( "BLANK     " );}
                                                     else
                                                 System.out.print("Unknown cell type");

                                     }
	                                     //insertRowInDB(list);

                                 }
                                System.out.println(list);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                }
                public static void insertRowInDB(List cellValues){
                    Connection con = null;
                	PreparedStatement preparedStatement = null;
                	
                    try{            
                   	Class.forName(DB_DRIVER);
                        con = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                        
                    	String sql = "INSERT INTO Test (id, name) VALUES (?,?)";
                         preparedStatement = con.prepareStatement(sql);
                         int paramIndex = 1;
                         for(Object cell : cellValues){
                             preparedStatement.setObject(paramIndex, cell);
                             paramIndex++;
                         }
                         int status = preparedStatement.executeUpdate();
                         //DO something with the status if needed
                    } catch (ClassNotFoundException e) {
                        System.out.println(e.getMessage());
        }
                    
                    catch(SQLException ex) { 
                          /* log the exception */
                    } finally {
                      try{
                            preparedStatement.close();
                            con.close();
                       } catch(SQLException ignored) {}
                    }
                
                }

 
                private static Connection getDBConnection() {
                	 Connection dbConnection = null;     
                                try {
                                                Class.forName(DB_DRIVER);
                                } catch (ClassNotFoundException e) {
                                                System.out.println(e.getMessage());
                                }
                                try {
                                                dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                                                return dbConnection;
                                } catch (SQLException e) {
                                                System.out.println(e.getMessage());
                                }
                                return dbConnection;
                }
}