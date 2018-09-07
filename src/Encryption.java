
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encryption {
	
	public static void main(String[] args) {
		String inputFile = System.getProperty("user.dir")+"\\plain.gif";
		String outputFile = System.getProperty("user.dir")+"\\cipher.gif";;
		File file = new File(inputFile);
		FileInputStream fin = null;
		byte cipherText[] = null;
		try {
			fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int)file.length()];
			fin.read(fileContent);
			EncryptionMode em = new EncryptionMode((byte) 1997, 123);
			cipherText = em.encryptCBC(fileContent);
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(cipherText);
			fos.close();
			System.out.println(outputFile + "\nFile Sucessfully Encrypted");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				if (fin != null) {
					fin.close();
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}