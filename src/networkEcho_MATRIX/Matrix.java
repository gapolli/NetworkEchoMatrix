package networkEcho_MATRIX;

import java.io.Serializable;
import java.util.Random;

/**
 * The Class Matrix implements all the necessary methods to create and transpose a matrix
 * @author
 * @version
 * @since
 */
public class Matrix implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int matrix[][];
	private int linecount;
	private int rowcount;
	
	boolean isTransposed = false;
	
	/**
	 * Class Constructor
	 */
	public Matrix() {
		Random random = new Random();
		
		linecount = random.nextInt(10) + 1;
		rowcount = random.nextInt(10) + 1;
		
		matrix = new int[linecount][rowcount];
		
		//System.out.println(" Line: " + linecount + " Row: "+ rowcount);
		
		for(int l = 0; l < linecount; l++) {
			for(int c = 0; c < rowcount; c++) {
				matrix[l][c] = random.nextInt(255);
			}
		}
	}
	
	/**
	 * Inserir descrição aqui
	 */
	public void transposeMatrix() {
		int transp_matrix[][] = new int[rowcount][linecount];
		
		for(int l = 0; l < linecount; l++) {
			for(int c = 0; c < rowcount; c++) {
				transp_matrix[c][l] = matrix[l][c];
			}
		}
		
		matrix = transp_matrix;
		
		int aux = linecount;
		linecount = rowcount;
		rowcount = aux;
		
		isTransposed = !isTransposed;
	}
	
	/**
	 * Inserir descrição aqui
	 */
	void printMatrix() {
		if (isTransposed)
			System.out.println("The given Matrix is Transposed: ");
		else
			System.out.println("The given Matrix is not Transposed: ");
		
		for(int l = 0; l < linecount; l++) {
			for(int c = 0; c < rowcount; c++) {
				System.out.print(matrix[l][c] + "\t");
			}
			System.out.println();
		}
	}
}
