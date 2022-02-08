import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class Servidor {

    private static Servidor servidor;
    private final int PUERTO = 6969;
    private SSLServerSocketFactory serverFactory;
    private SSLServerSocket servidorControl;
    private SSLSocket cliente = null;
    private boolean salir = false;

    public static Servidor iniciarServidor() throws IOException {
        if (servidor == null) {
            servidor = new Servidor();
            servidor.activarServidor();
        }
        return servidor;
    }

    private void activarServidor() throws IOException {
        String fichero = "C:\\Users\\mario\\IdeaProjects\\Examen-Prog-Serv-04\\src\\servidor\\cert\\AlmacenSSL.jks";
        System.setProperty("javax.net.ssl.keyStore", fichero);
        System.setProperty("javax.net.ssl.keyStorePassword", "quismondo");
        serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        servidorControl = (SSLServerSocket) serverFactory.createServerSocket(PUERTO);
        System.out.println("Esperando cliente");
        while (!salir) {
            cliente = (SSLSocket) servidorControl.accept();
            System.out.println("Se conectó el cliente " + cliente.getInetAddress());
            procesarCliente();
        }
        cliente.close();
        servidorControl.close();
        System.exit(-1);
    }

    private void procesarCliente() {
        GestionCliente cc = new GestionCliente(cliente);
        cc.start();
    }

}
