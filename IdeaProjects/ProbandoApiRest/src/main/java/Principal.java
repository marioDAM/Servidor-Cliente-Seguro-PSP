import java.io.IOException;
import java.sql.SQLException;

public class Principal {

    public static void main(String[] args) throws SQLException {
        Client rcd = new Client();
        rcd.runUsers();
        JpaClientDemo j = new JpaClientDemo();
        j.saveUser();
        j.login("@hotmail.com","1234");
    }
}
