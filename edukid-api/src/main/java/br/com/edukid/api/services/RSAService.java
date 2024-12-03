	package br.com.edukid.api.services;
	
	
	import org.springframework.stereotype.Service;
	
	import javax.crypto.Cipher;
	import java.io.File;
	import java.nio.file.Files;
	import java.security.KeyFactory;
	import java.security.PrivateKey;
	import java.security.PublicKey;
	import java.security.spec.PKCS8EncodedKeySpec;
	import java.security.spec.X509EncodedKeySpec;
	import java.util.Base64;
	
	//@Service
	public class RSAService {
		
		 public static void main(String[] args) {
			 System.out.println("Teste criptografia RSA");
			 String dadoTeste = "Rafão Agroboy, está sempre enterrando a cenoura";
			 
			 RSAService rsa  = new RSAService();
			 
			 try {
				String criptografado = rsa.encrypt(dadoTeste);
				System.out.println("criptografado: "+criptografado);
				String decriptografado = rsa.decrypt(criptografado);
				System.out.println("decriptografado: "+decriptografado);
				
			} catch (Exception e) {
				e.printStackTrace();
			}		 
		 }
		 
	
	    private PublicKey publicKey;
	    private PrivateKey privateKey;
	
	    public RSAService() {
	        try {
	            this.publicKey = loadPublicKeyFromFile();
	            this.privateKey = loadPrivateKeyFromFile();
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao carregar as chaves RSA", e);
	        }
	    }
	    
	    /**
	     * Método para carregar a chave pública de um arquivo
	     * @return
	     * @throws Exception
	     */
	    private PublicKey loadPublicKeyFromFile() throws Exception {
	        // Obtém o ClassLoader para carregar o recurso
	        ClassLoader classLoader = getClass().getClassLoader();
	        // Localiza o arquivo dentro de resources
	        File file = new File(classLoader.getResource("static/rsa/publicKey.pem").getFile());
	        String keyContent = new String(Files.readAllBytes(file.toPath()));

	        keyContent = keyContent
	                .replace("-----BEGIN PUBLIC KEY-----", "")
	                .replace("-----END PUBLIC KEY-----", "")
	                .replaceAll("\\s+", "");
	        
	        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePublic(keySpec);
	    }
	
	    /**
	     * Método para carregar a chave privada de um arquivo
	     * @return
	     * @throws Exception
	     */
	    private PrivateKey loadPrivateKeyFromFile() throws Exception {
	        // Obtém o ClassLoader para carregar o recurso
	        ClassLoader classLoader = getClass().getClassLoader();
	        // Localiza o arquivo dentro de resources
	        File file = new File(classLoader.getResource("static/rsa/privateKey.pem").getFile());
	        String keyContent = new String(Files.readAllBytes(file.toPath()));

	        keyContent = keyContent
	                .replace("-----BEGIN PRIVATE KEY-----", "")
	                .replace("-----END PRIVATE KEY-----", "")
	                .replaceAll("\\s+", "");
	        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePrivate(keySpec);
	    }
	
	    /**
	     * Método para criptografar usando a chave pública
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    public String encrypt(String data) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	
	        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedBytes);
	    }
	
	    /**
	     * Método para descriptografar usando a chave privada
	     * @param encryptedData
	     * @return
	     * @throws Exception
	     */
	    public String decrypt(String encryptedData) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	
	        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(decryptedBytes);
	    }
	}
