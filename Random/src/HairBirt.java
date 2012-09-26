import java.io.Serializable;


public class HairBirt implements Serializable {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String Name;
 public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
float Value;
public float getValue() {
	return Value;
}
public void setValue(float value) {
	Value = value;
}
}
