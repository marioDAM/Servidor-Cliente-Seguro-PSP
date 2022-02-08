import javax.crypto.*;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayDeque;
import java.util.Base64;

public class GestionCliente extends Thread {
    String UUID;
    private SSLSocket cliente = null;
    DataInputStream controlEntrada = null;
    DataOutputStream controlSalida = null;
    ObjectInputStream bufferObjetosEntrada;
    ObjectOutputStream bufferObjetosSalida;
    private int contador = 1;
    private boolean salir = false;
    private static final int PAQUETES = 20;
    private Key sessionKey = null;
    private PrivateKey privateKey = null;
    private byte[] sesionCifrada = null;
    private PublicKey publicKey;




    public GestionCliente(SSLSocket cliente) {
        this.cliente = cliente;
        this.contador = 1;
        this.salir = false;
        this.UUID = cliente.getInetAddress() + ":" + cliente.getPort();
    }

    @Override
    public void run() {
        if (salir == false) {
            try {
                controlEntrada = new DataInputStream(cliente.getInputStream());
                controlSalida = new DataOutputStream(cliente.getOutputStream());
                bufferObjetosSalida = new ObjectOutputStream(cliente.getOutputStream());
                bufferObjetosEntrada = new ObjectInputStream(cliente.getInputStream());

                cargarClaves();
                recibirClave();
                descifrarClave();
                tratarConexion();
                try {
                    controlEntrada.close();
                    controlSalida.close();
                } catch (IOException ex) {
                    System.err.println("ServidorGC->ERROR: cerrar flujos de entrada y salida " + ex.getMessage());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.interrupt(); // Me interrumpo y no trabajo
        }
    }

    private void tratarConexion() {
        while (!salir) {
            System.out.println("Recepción de mensajes");
            try {
                MovimientoCliente mov = (MovimientoCliente) bufferObjetosEntrada.readObject();
                bufferObjetosSalida.writeObject(mov);

                System.out.println("ServidorGC->Mensaje recibido de [" + UUID + "]: " + mov);

            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("ServidorGC->ERROR: al recibir mensaje " + ex.getMessage());
            }
            System.out.println("ServidorGC->Enviado mensaje");
            try {
                String dato = "Mensaje de respuesta num: " + this.contador;

                controlSalida.writeUTF(this.cifrar(dato));
                System.out.println("ServidorGC->Mensaje enviado a [" + this.UUID + "]: " + dato);
                System.out.println("Leyendo objeto de client");

                System.out.println("Enviando objeto al servidor");
            } catch (IOException ex) {
                System.err.println("ServidorGC->ERROR: al enviar mensaje " + ex.getMessage());

            }
            this.contador++;
            if (!salir) {
                salir();

            }
        }
    }

    private void salir() {

        if (this.contador >= PAQUETES) {
            this.salir = true;
        } else {
            this.salir = false;
        }
        try {
            System.out.println("ServidorGC->Enviar si salir");
            //String salida = String.valueOf(this.salir);
            String mensaje = String.valueOf(this.salir);
            controlSalida.writeUTF(this.cifrar(mensaje));

            System.out.println("Cliente se ha ido");
        } catch (IOException ex) {
            System.err.println("ServidorGC->ERROR: al enviar ID de Cliente " + ex.getMessage());
        }
    }

    private void cargarClaves() {
        String fichero = "C:\\Users\\mario\\IdeaProjects\\Examen-Prog-Serv-04\\src\\servidor\\cert\\AlmacenSSL.jks";
        try {
            FileInputStream fis = new FileInputStream(fichero);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(fis, "quismondo".toCharArray());
            fis.close();
            String alias = "marioValverde";
            Key key = keystore.getKey(alias, "quismondo".toCharArray());
            if (key instanceof PrivateKey) {
                // Obtenemos el certificado
                Certificate cert = keystore.getCertificate(alias);
                // Obtenemos la clave pública
                this.publicKey = cert.getPublicKey();
                // Casteamos y almacenamos la clave
                this.privateKey = (PrivateKey) key;
            }

        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException ex) {
            System.err.println("ServidorGC->ERROR: al cargar la clave privada " + ex.getMessage());
        }
    }

    private void recibirClave() {
        System.out.println("Recibiendo clave de la sesión");
        try {
            int l = this.controlEntrada.readInt();
            byte[] clave = new byte[l];
            this.controlEntrada.read(clave);
            System.out.println("Clave de sesión recibida de " + this.UUID + "]: " + clave.toString());
            this.sesionCifrada = clave;
        } catch (IOException ex) {
            System.err.println(" no se pudo recibir mensaje " + ex.getMessage());
        }
    }

    private void descifrarClave() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            this.sessionKey = kg.generateKey();
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.UNWRAP_MODE, privateKey);
            this.sessionKey = c.unwrap(this.sesionCifrada, "AES", Cipher.SECRET_KEY);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println("ServidorGC->ERROR: al descodificar clave de sesion " + ex.getMessage());
        }
    }

    private String descifrar(String mensaje) {
        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, this.sessionKey);
            byte[] encriptado = Base64.getDecoder().decode(mensaje);
            byte[] desencriptado = c.doFinal(encriptado);
            return new String(desencriptado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
            System.err.println("ServidorGC->ERROR: descifrar mensaje " + ex.getMessage());
        }
        return null;
    }

    private String cifrar(String mensaje) {
        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, this.sessionKey);
            byte[] encriptado = c.doFinal(mensaje.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encriptado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println("ServidorGC->ERROR: cifrar mensaje " + ex.getMessage());
        }
        return null;
    }
}
