/** Copyright or License
 *
 */


/**
 * Package: 
 *
 * Class: PairValues PairValues.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Clase simple para almacenar par de Valores
 * 
 * Implementation: [Notes on implementation]
 *
 * Created: Feb 29, 2016 4:11:30 AM
 * 
 */
public class PairValues<T,K> implements IPair<T, K> {

	private T x_Value;
	private K y_Value;
		
	public PairValues(T x_Value, K y_Value) {
		super();
		this.x_Value = x_Value;
		this.y_Value = y_Value;
	}

	public T getX() {
		return x_Value;
	}

	public void setX(T xValue) {
		this.x_Value = xValue;
	}

	public K getY() {
		return y_Value;
	}

	public void setY(K y_Value) {
		this.y_Value = y_Value;
	}
	

}
