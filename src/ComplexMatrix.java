public class ComplexMatrix {
    private static final double epsilon = 1e-12;
    private final int rows;
    private final int columns;
    private final Complex[][] matrix;

    public ComplexMatrix(Complex[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Матрица не может быть пустой");
        }
        this.rows = data.length;
        this.columns = data[0].length;
        this.matrix = new Complex[rows][columns];
        for (int i = 0; i < rows; i++) {
            if (data[i].length != columns) {
                throw new IllegalArgumentException("Все строки матрицы должны быть одной длины");
            }
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = data[i][j];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public ComplexMatrix add(ComplexMatrix other){
        if(this.rows != other.rows || this.columns != other.columns){
            throw new IllegalArgumentException(
                    "При сложении у матриц должны соответственно совпадать длины строк и длины столбцов");
        }
        Complex[][] result_matrix = new Complex[this.rows][this.columns];
        for(int i = 0; i < this.rows; ++i){
            for(int j = 0; j < this.columns; ++j){
                result_matrix[i][j] = this.matrix[i][j].add(other.matrix[i][j]);
            }
        }
        return new ComplexMatrix(result_matrix);
    }

    public ComplexMatrix sub(ComplexMatrix other){
        if(this.rows != other.rows || this.columns != other.columns){
            throw new IllegalArgumentException(
                    "При вычитании у матриц должны соответственно совпадать длины строк и длины столбцов.");
        }
        Complex[][] result_matrix = new Complex[this.rows][this.columns];
        for(int i = 0; i < this.rows; ++i){
            for(int j = 0; j < this.columns; ++j){
                result_matrix[i][j] = this.matrix[i][j].sub(other.matrix[i][j]);
            }
        }
        return new ComplexMatrix(result_matrix);
    }

    public ComplexMatrix multiply(ComplexMatrix other){
        if(this.columns != other.rows){
            throw new IllegalArgumentException("Размеры матриц не подходят для умножения.");
        }
        int res_rows = this.rows;
        int res_cols = other.columns;
        Complex[][] result_matrix = new Complex[res_rows][res_cols];
        for(int r = 0; r < res_rows; ++r){
            for(int c = 0; c < res_cols; ++c){
                result_matrix[r][c] = new Complex(0, 0);
                for(int k = 0; k < this.columns; ++k){
                    result_matrix[r][c] = result_matrix[r][c].add(this.matrix[r][k].mul(other.matrix[k][c]));
                }
            }
        }
        return new ComplexMatrix(result_matrix);
    }

    public ComplexMatrix transpose(){
        Complex[][] res_matrix = new Complex[this.columns][this.rows];
        for(int i = 0; i < this.columns; i++){
            for(int j = 0; j < this.rows; j++){
                res_matrix[i][j] = this.matrix[j][i];
            }
        }
        return new ComplexMatrix(res_matrix);
    }

    public Complex determinant() {
        if (rows != columns) {
            throw new IllegalArgumentException("Определитель бывает только у квадратной матрицы.");
        }
        return det_recursive(this.matrix);
    }

    private Complex det_recursive(Complex[][] a) {
        int n = a.length;
        if (n == 1) {
            return a[0][0];
        }
        if (n == 2) {
            return a[0][0].mul(a[1][1]).sub(a[0][1].mul(a[1][0]));
        }
        Complex result = new Complex(0, 0);
        for (int col = 0; col < n; ++col) {
            Complex term = a[0][col].mul(det_recursive(minor(a, 0, col)));
            if (col % 2 == 0) {
                result = result.add(term);
            } else {
                result = result.sub(term);
            }
        }
        return result;
    }

    public ComplexMatrix inverse() {
        if (rows != columns) {
            throw new IllegalArgumentException("Обратная матрица существует только у квадратной матрицы.");
        }
        int n = rows;
        Complex det = determinant();
        if (det.abs() < epsilon) {
            throw new ArithmeticException("Матрица вырождена (определитель равен 0), обратной нет");
        }
        Complex[][] result_matrix = new Complex[n][n];
        if (n == 1) {
            result_matrix[0][0] = new Complex(1, 0).divide(matrix[0][0]);
            return new ComplexMatrix(result_matrix);
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                Complex cofactor = det_recursive(minor(matrix, i, j));
                if ((i + j) % 2 == 1) {
                    cofactor = cofactor.mul(new Complex(-1, 0));
                }
                result_matrix[j][i] = cofactor.divide(det);
            }
        }
        return new ComplexMatrix(result_matrix);
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        ComplexMatrix inverseOther = other.inverse();
        return this.multiply(inverseOther);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                text.append(matrix[i][j]);
                if (j < columns - 1) {
                    text.append("\t");
                }
            }
            text.append("\n");
        }
        return text.toString();
    }

    private Complex[][] minor(Complex[][] matrix, int skip_row, int skip_col) {
        int n = matrix.length;
        Complex[][] res = new Complex[n - 1][n - 1];
        int res_i = 0;
        for (int i = 0; i < n; ++i) {
            if (i == skip_row) {
                continue;
            }
            int res_j = 0;
            for (int j = 0; j < n; ++j) {
                if (j == skip_col) {
                    continue;
                }
                res[res_i][res_j] = matrix[i][j];
                res_j++;
            }
            res_i++;
        }
        return res;
    }
}
