package rcs.suport.statistical;

public class Matrix {
	
	int nCol;
    int nLin;
    double[][] content;
    
    public Matrix(int nLin,int nCol)
    {
        super();
        this.nCol=nCol;
        this.nLin=nLin;
        content=new double[nLin][nCol];
    }
    double getContent(int nLin,int nCol )
    {
        return content[nLin][nCol];
    }

    void setContent (int nLin,int nCol, double value)
    {
        content[nLin][nCol] = value;
    }

    static  Matrix product(Matrix matrixB, Matrix matrixA)
    {
        Matrix result = new Matrix(matrixA.nLin, matrixB.nCol);
        double[][] aux= new double[matrixA.nLin][matrixB.nCol]; 
        
        if(matrixA.nCol == matrixB.nLin)
        {
            for(int i = 0; i < matrixA.nLin; i++)
            {
                for(int j= 0; j < matrixB.nCol; j++)
                {
                    aux[i][j] = 0;
                    for(int x = 0; x < matrixB.nLin; x++)
                    {
                        aux[i][j] += ( matrixA.getContent(i, x)) * (matrixB.getContent(x, j));
                        
                    }
                }
            }
            for (int i=0;i<result.nLin;i++)
            {
                for (int j=0;j<result.nCol;j++)
                {
                    result.setContent(i, j, aux[i][j]);
                }
            }
        }
        return result;
    }
    static Matrix transposed(Matrix matrix)
    {
        Matrix result = new Matrix(matrix.nCol, matrix.nLin);
        
        for(int i = 0; i < matrix.nLin; i++)
        {
            for (int j = 0; j < matrix.nCol; j++)
            {
                result.setContent(j, i, matrix.getContent(i, j));
            }
        }
        return result;
    }

 

}
