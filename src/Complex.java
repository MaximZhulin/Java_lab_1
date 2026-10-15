public class Complex {
    private final double real;
    private final double imaginary;

    public Complex(double Real, double Imaginary){
        this.real = Real;
        this.imaginary = Imaginary;
    }

    public double getReal(){
        return this.real;
    }

    public double getImaginary(){
        return this.imaginary;
    }

    public double abs(){
        return Math.sqrt(this.real*this.real + this.imaginary*this.imaginary);
    }

    public Complex add(Complex other){
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex sub(Complex other){
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    public Complex mul(Complex other){
        double result_real = this.real*other.real - this.imaginary*other.imaginary;
        double result_imaginary = this.real*other.imaginary + this.imaginary*other.real;
        return new Complex(result_real, result_imaginary);
    }

    public Complex divide(Complex other){
        double denom = other.real*other.real + other.imaginary*other.imaginary;
        if(denom == 0){
            throw new ArithmeticException("Делить на ноль нельзя");
        }
        Complex sopr = new Complex(other.real, (-1)*other.imaginary);
        Complex numerator = new Complex(this.real, this.imaginary);
        numerator = numerator.mul(sopr);
        double result_real = numerator.real/denom;
        double result_imaginary = numerator.imaginary/denom;
        return new Complex(result_real, result_imaginary);
    }

    private static double clean(double x){
        if(Math.abs(x) < 1e-10){
            return 0.0;
        }
        return Math.rint(x * 1e10) / 1e10;
    }

    @Override
    public String toString(){
        double Rez = clean(this.real);
        double Imz = clean(this.imaginary);
        if(Imz == 0){
            return String.valueOf(Rez);
        }
        String sign = "+";
        double imaginary_abs = Imz;
        if(Imz < 0){
            sign = "-";
            imaginary_abs = Imz * (-1);
        }

        String Imz_str = String.valueOf(imaginary_abs) + "i";
        if(imaginary_abs == 1.0){
            Imz_str = "i";
        }
        if(Rez == 0){
            if(sign.equals("-")){
                return "-" + Imz_str;
            }
            return Imz_str;
        }
        return String.valueOf(Rez)+sign+Imz_str;
    }
}
