package suport.statisticalTest;

import org.junit.Test;

import suport.statistical.Matrix;
import junit.framework.Assert;

public class MatrixTest {

	private Matrix matrix;
	@Test
	public void testMatrix() {

		matrix = new Matrix(1, 1);
		Assert.assertEquals(1, matrix.getnCol());
		Assert.assertEquals(1, matrix.getnLin());

		Assert.assertEquals(Matrix.class, matrix.getClass());

	}
	@Test
	public void testGetContent() {

		matrix = new Matrix(1, 1);
		matrix.setContent(0, 0, 10);
		Assert.assertEquals(10, matrix.getContent(0, 0), 0.0);
	}
	@Test
	public void testSetContent() {
		matrix = new Matrix(1, 1);
		matrix.setContent(0, 0, 10);
		Assert.assertEquals(10, matrix.getContent(0, 0), 0.0);
	}
	@Test
	public void testProduct() {

		Matrix matrixA = new Matrix(1, 2);
		Matrix matrixb = new Matrix(2, 1);
		Matrix matrixC = new Matrix(3, 3);

		matrixA.setContent(0, 0, 1);
		matrixA.setContent(0, 1, 1);

		matrixb.setContent(0, 0, 2);
		matrixb.setContent(1, 0, 2);

		Matrix result = Matrix.product(matrixA, matrixb);

		Assert.assertEquals(4, result.getContent(0, 0), 1.1);
		Assert.assertNull(Matrix.product(matrixA, matrixC));

	}
	@Test
	public void testTransposed() {
		matrix = new Matrix(3, 1);

		Assert.assertEquals(1, Matrix.transposed(matrix).getnLin());
		Assert.assertEquals(3, Matrix.transposed(matrix).getnCol());

	}

}
