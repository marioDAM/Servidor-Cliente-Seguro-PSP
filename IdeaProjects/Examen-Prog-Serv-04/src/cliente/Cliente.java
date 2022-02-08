import javax.crypto.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Base64;

public class Cliente {

    private DataInputStream entrada;
    private DataOutputStream salida;
    private Key sessionKey;
    private PublicKey publicKey;
    private byte[] sesionCifrada;
    private boolean salir = false;
    private ObjectOutputStream bufferObjetosSalida;
    private ObjectInputStream bufferObjetosEntrada;
    private MovimientoCliente mov;

    public void conectarCliente() {
        try {
            String fichero = "C:\\Users\\mario\\IdeaProjects\\Examen-Prog-Serv-04\\src\\cliente\\cert\\UsuarioAlmacenSSL.jks";
            System.setProperty("javax.net.ssl.trustStore", fichero);
            System.setProperty("javax.net.ssl.trustStorePassword", "quismondo");
            String direccion = InetAddress.getLocalHost().getHostAddress();
            SSLSocketFactory clientFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket servidor = (SSLSocket) clientFactory.createSocket(direccion, 6969);
            entrada = new DataInputStream(servidor.getInputStream());
            salida = new DataOutputStream(servidor.getOutputStream());
            System.out.println("Cliente conectando al servidor");
            bufferObjetosSalida = new ObjectOutputStream(servidor.getOutputStream());
            bufferObjetosEntrada = new ObjectInputStream(servidor.getInputStream());
            claveAES();
            procesar();
        } catch (UnknownHostException ex) {
            System.exit(-1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void claveAES() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            sessionKey = kg.generateKey();
            cargarClave();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Cliente->ERROR: al generar la clave de sesion " + ex.getMessage());
        }
    }

    public void cargarClave() {
        String fichero = "C:\\Users\\mario\\IdeaProjects\\Examen-Prog-Serv-04\\src\\cliente\\cert\\UsuarioAlmacenSSL.jks";
        try {
            FileInputStream in = new FileInputStream(fichero);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(in, "quismondo".toCharArray());
            in.close();
            String alias = "marioValverde";
            Certificate cert = keystore.getCertificate(alias);
            publicKey = cert.getPublicKey();
            cifrarClave();
        } catch (FileNotFoundException | KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void procesar() throws IOException, InterruptedException {
        while (!salir) {
            try {
                System.out.println("Enviando mensaje al servidor");
                mov = new MovimientoCliente(1);
                System.out.println("Mandando objeto a servidor");
                bufferObjetosSalida.writeObject(mov);
                System.out.println("Objeto ha sido enviado");
                //Recibimos el mensaje
                System.out.println("Recibimos el mensaje");
                String mensaje = this.descifrar(this.entrada.readUTF());
                System.out.println("Mensaje recibido: " + mensaje);

                System.out.println("La cuenta es de :");
                System.out.println(mov);
                System.out.println("¿Salir?");
                String message = "FIN";
                salida.writeUTF(cifrar(message));

                System.out.println("Cliente->Salir: " + this.salir);
                Thread.sleep(1000);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Cliente->ERROR: al enviar mensaje " + ex.getMessage());
            }
        }
    }


    public void cifrarClave() {
        try {
            //se encripta la clave secreta con la clave pública
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.WRAP_MODE, this.publicKey);
            this.sesionCifrada = c.wrap(this.sessionKey);
            enviarClave();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException ex) {
            System.err.println("Cliente->ERROR: al cifrar la clave de sesion " + ex.getMessage());
        }
    }


    private void enviarClave() {
        byte[] clave = this.sesionCifrada;
        System.out.println("Cliente->Enviado clave de sesion");
        try {

            salida.writeInt(clave.length);
            salida.write(clave);
            System.out.println("Cliente->Clave de sesion enviada " + clave.toString());
        } catch (IOException ex) {
            System.err.println("Cliente->ERROR: al enviar clave " + ex.getMessage());
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

    private String cifrarObject(Object mensaje) {
        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, this.sessionKey);
            byte[] encriptado = c.doFinal((byte[]) mensaje);
            return Base64.getEncoder().encodeToString(encriptado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println("ServidorGC->ERROR: cifrar mensaje " + ex.getMessage());
        }
        return null;
    }
}
