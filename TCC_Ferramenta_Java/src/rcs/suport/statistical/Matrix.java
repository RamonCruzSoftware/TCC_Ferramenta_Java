package rcs.suport.statistical;

public class Matrix {
	
	private int nCol;
    private int nLin;
    private double[][] content;
    
    public Matrix(int nLin,int nCol)
    {
        super();
        this.setnCol(nCol);
        this.setnLin(nLin);
        setContent(new double[nLin][nCol]);
    }
    double getContent(int nLin,int nCol )
    {
        return content[nLin][nCol];
    }

    void setContent (int nLin,int nCol, double value)
    {
        content[nLin][nCol] = value;
    }

    static  Matrix product(Matrix matrixA, Matrix matrixB)
    {
        Matrix result = new Matrix(matrixA.getnLin(), matrixB.getnCol());
        double[][] aux= new double[matrixA.getnLin()][matrixB.getnCol()]; 
        
        if(matrixA.getnCol() == matrixB.getnLin())
        {
            for(int i = 0; i < matrixA.getnLin(); i++)
            {
                for(int j= 0; j < matrixB.getnCol(); j++)
                {
                    aux[i][j] = 0;
                    for(int x = 0; x < matrixB.getnLin(); x++)
                    {
                        aux[i][j] += ( matrixA.getContent(i, x)) * (matrixB.getContent(x, j));
                        
                    }
                }
            }
            for (int i=0;i<result.getnLin();i++)
            {
                for (int j=0;j<result.getnCol();j++)
                {
                    result.setContent(i, j, aux[i][j]);
                }
            }
            return result;
        }else 
        	return null;
        
    }
    static Matrix transposed(Matrix matrix)
    {
        Matrix result = new Matrix(matrix.getnCol(), matrix.getnLin());
        
        for(int i = 0; i < matrix.getnLin(); i++)
        {
            for (int j = 0; j < matrix.getnCol(); j++)
            {
                result.setContent(j, i, matrix.getContent(i, j));
            }
        }
        return result;
    }
	public int getnCol() {
		return nCol;
	}
	public void setnCol(int nCol) {
		this.nCol = nCol;
	}
	public int getnLin() {
		return nLin;
	}
	public void setnLin(int nLin) {
		this.nLin = nLin;
	}
	public double[][] getContent() {
		return content;
	}
	public void setContent(double[][] content) {
		this.content = content;
	}

 

}
