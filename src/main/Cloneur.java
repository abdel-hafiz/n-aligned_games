/**
 * 
 */
package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author abdel-hafiz
 *
 */
public class Cloneur {
	
	 public static Object deepClone(Object obj) {
	        try {
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ObjectOutputStream oos = new ObjectOutputStream(baos);
	            oos.writeObject(obj);

	            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	            ObjectInputStream ois = new ObjectInputStream(bais);

	            return ois.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            return null;
	        }
	    }

}
