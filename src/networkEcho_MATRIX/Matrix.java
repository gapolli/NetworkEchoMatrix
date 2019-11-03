package networkEcho_MATRIX;

import java.io.Serializable;
import java.util.Random;

/**
 * The class Matrix implements all the necessary methods to create and transpose
 * a matrix.
 * 
 * @author Mateus Pim Santos
 * @author Gustavo Adrien Polli
 * @author Victor Gomes Sampaio
 * @author Max Lúcio Martins de Assis
 * @version 1.0
 * @since version 1.0
 */
public class Matrix implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int matrix[][];
	private int linecount;
	private int rowcount;

	private boolean isTransposed = false;

	/**
	 * Matrix class Constructor.
	 * Generate random numbers for the linecount and rowcount
	 * and fill the matrix with random numbers from (0,255)
	 */
	public Matrix() {
		Random random = new Random();

		linecount = random.nextInt(10) + 1;
		rowcount = random.nextInt(10) + 1;

		matrix = new int[linecount][rowcount];

		// System.out.println(" Line: " + linecount + " Row: "+ rowcount);

		for (int l = 0; l < linecount; l++) {
			for (int c = 0; c < rowcount; c++) {
				matrix[l][c] = random.nextInt(255);
			}
		}
	}
	
	/**
	 * Matrix class overrided Constructor.
	 * Creates a matrix from its given parameters.
	 * @param matrix[][] the matrix passed to the Class
	 */
	public Matrix(int matrix[][]) {

		this.linecount = matrix.length;
		this.rowcount = matrix[0].length;

		this.matrix = matrix;
	}
	
	//public Matrix(int linecount, int rowcount , int matrix[][]) {
	/**
	 * This method do a transposition of the elements of the received matrix.
	 */
	public void transposeMatrix() {
		int transp_matrix[][] = new int[rowcount][linecount];

		for (int l = 0; l < linecount; l++) {
			for (int c = 0; c < rowcount; c++) {
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
	 * Prints information of the transposition and the handled matrix.
	 */
	void printMatrix() {
		
		Info.getDecoration(rowcount);
		
		System.out.printf("The following Matrix [%d][%d] %s Transposed: \n", linecount , rowcount , (isTransposed)? "is": "is not");
				
		//Info.getDecoration(rowcount);
		
		for (int l = 0; l < linecount; l++) {
			for (int c = 0; c < rowcount; c++) {
				System.out.print(matrix[l][c] + "\t");
			}
			System.out.println();
		}
		
		Info.getDecoration(rowcount);
	}
	
}
