
import dao.UsuarioDao;
import dao.UsuarioDaoImpl;
import rest.Config;
import rest.AccesoDatosRest;

public class JpaClientDemo {

    AccesoDatosRest restService = Config.getService();
    UsuarioDao ud = new UsuarioDaoImpl();

   /* public Users insertRepositorio(Users repositorio) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().persist(repositorio);
            hc.getTransaction().commit();
            return repositorio;
        } catch (Exception e) {
            throw new SQLException("Error RepositorioRepository al insertar repositorio en BD:" + e.getMessage());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }*/

/*    public void guardarUser() throws SQLException, IOException {
        List<Users> usuarios;

        Response<List<Users>> response = restService.usersGetAll().execute();
        // La hemos obtenido correctamente
        if (response.isSuccessful()) {
            System.out.println("Request Done! - Código: " + response.code() +
                    " - Datos obtenidos: " + response.body().size());
            System.out.println("Resultado de GET ALL");
            usuarios = (response.body());
            for (int i = 0; i < usuarios.size(); i++) {
                Users usuario = usuarios.get(i);
                insertRepositorio(usuario);
            }
          *//*  Client cliente = new Client();
            List<Users> usuarios;
            usuarios = cliente.usersGetAll();
            for (int i = 0; i < usuarios.size(); i++) {
                Users usuario = usuarios.get(i);
                insertRepositorio(usuario);
            }*//*
        }
    }*/

/*    public void saveUser() throws SQLException {
        Client cliente = new Client();
        List<Users> usuarios;
        usuarios = cliente.usersGetAll();
        System.out.println("GUARDANDO USUARIO EN LA BASE DE DATOS mongo controller");
        for (int i = 0; i < usuarios.size(); i++) {
            Users usuario = usuarios.get(i);
            ud.saveUserBd(usuario);
        }
    }*/

}
