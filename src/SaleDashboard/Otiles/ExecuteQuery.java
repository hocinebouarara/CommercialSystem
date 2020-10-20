package SaleDashboard.Otiles;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import helpers.DbConnect;

import java.sql.SQLException;

public class ExecuteQuery {
    public static int GetLastIdCommend(){
        int i=-1;
        try{
            String Sql="SELECT IDCA FROM `commande_cl` ORDER BY IDCA DESC LIMIT 1";
            Connection connection=(Connection) DbConnect.getConnect();
            PreparedStatement preparedStatement =(PreparedStatement) connection.prepareStatement(Sql);
            ResultSetImpl resultSet=(ResultSetImpl) preparedStatement.executeQuery();
            resultSet.next();
            i= resultSet.getInt(1);
        }catch (SQLException e){

        }
        return i;
    }
    public static int GetLastIdReglement(){
        int i=-1;
        try{
            String Sql="SELECT IDRG FROM `reglement_cl` ORDER BY IDRG DESC LIMIT 1";
            Connection connection=(Connection) DbConnect.getConnect();
            PreparedStatement preparedStatement =(PreparedStatement) connection.prepareStatement(Sql);
            ResultSetImpl resultSet=(ResultSetImpl) preparedStatement.executeQuery();
            resultSet.next();
            i= resultSet.getInt(1);
        }catch (SQLException e){

        }
        return i;
    }
    public static int GetLastIdFactureVent(){
        int i=-1;
        try{
            String Sql="SELECT IDFV FROM `facture_vente` ORDER BY IDFV DESC LIMIT 1";
            Connection connection=(Connection) DbConnect.getConnect();
            PreparedStatement preparedStatement =(PreparedStatement) connection.prepareStatement(Sql);
            ResultSetImpl resultSet=(ResultSetImpl) preparedStatement.executeQuery();
            resultSet.next();
            i= resultSet.getInt(1);
        }catch (SQLException e){

        }
        return i;
    }
    public static int GetLastIdLivraision(){
        int i=-1;
        try{
            String Sql="SELECT IDBA FROM `livraision_cl` ORDER BY IDBA DESC LIMIT 1";
            Connection connection=(Connection) DbConnect.getConnect();
            PreparedStatement preparedStatement =(PreparedStatement) connection.prepareStatement(Sql);
            ResultSetImpl resultSet=(ResultSetImpl) preparedStatement.executeQuery();
            resultSet.next();
            i= resultSet.getInt(1);
        }catch (SQLException e){

        }
        return i;
    }
}

