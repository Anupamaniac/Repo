import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LookForPattern {
	
	public static void main(String[] args) throws IOException {
	
			Map<String, Integer> words = new HashMap<String, Integer>();
		
		    File file = new File("C:\\Users\\anupam_g.LISTER\\Desktop\\succor logs\\14_9_12catalina.out.old\\catalina.out.old");
		    String s = null;
	        DataInputStream dis = new DataInputStream(new FileInputStream(file));
	            while (dis.available() != 0) {
	            	if((s=dis.readLine()).contains("FTPException"))
	                    System.out.println(s);
	            }
	            dis.close();
		}

}
