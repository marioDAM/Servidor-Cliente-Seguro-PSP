
import java.io.IOException;
import java.sql.SQLException;

public class Principal {

    public static void main(String[] args) throws SQLException, IOException {
        Client rcd = new Client();
        rcd.runUsers();
        /*JpaClientDemo j = new JpaClientDemo();
        j.saveUser();*/

    }
}
