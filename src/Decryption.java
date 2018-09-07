import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decryption {
	public static void main(String[] args) {
		String inputFile = System.getProperty("user.dir")+"\\cipher.gif";
		String outputFile = System.getProperty("user.dir")+"\\_plain.gif";;
		File file = new File(inputFile);
		FileInputStream fin = null;
		byte plainText[] = null;
		try {
			fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int)file.length()];
			fin.read(fileContent);
			plainText = EncryptionMode.fileDecryption(fileContent, 123);			
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(plainText);
			fos.close();
			System.out.println(outputFile + "\nFile Sucessfully Decrypted");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			// close the streams using close method
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
