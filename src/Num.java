public class Num extends Atom {

    /**
     *  The numerators and denominators of the real and imaginary parts.
     */
    private int realNum, realDenom = 1, imaginNum, imaginDenom = 1;

    private Type typeOfNum = Type.INTEGER;

    public enum Type implements Comparable<Num.Type> {
        INTEGER, RATIONAL, REAL, COMPLEX;
    }
    
    public Num(int realNum) {
        this(realNum, 1, Type.INTEGER);
    }
    
    public Num(int realNum, int realDenom) {
        // TODO throw exception if realDenom == 0;
        this(realNum, realDenom, Type.REAL);
    }
    
    public Num(int realNum, int realDenom, Type t) {
        this.realNum = realNum;
        this.realDenom = realDenom;
        typeOfNum = t;
    }
    

    public Num(int realNum, int realDenom, int imaginNum, int imaginDenom, Type t) {
        this(realNum, realDenom, t);
        this.imaginDenom = imaginDenom;
        this.realDenom = realDenom;
    }

    public Num plus(Num x) {
        int rNum, rDenom, iNum, iDenom;
        
        rNum = realNum * x.realDenominator() + x.realNumerator() * realDenom;
        rDenom = realDenom * x.realDenominator();
        
        iNum = imaginNum * x.imaginaryDenominator() 
                + x.imaginaryNumerator() * imaginDenom;
        iDenom = imaginDenom * x.imaginaryDenominator();

        Type t = typeOfNum.compareTo(x.getType()) < 0? x.getType() : typeOfNum;

        return new Num(rNum, rDenom, iNum, iDenom, t);
    }
    
    public Num minus(Num x) {
        int rNum, rDenom, iNum, iDenom;

        rNum = realNum * x.realDenominator() - x.realNumerator() * realDenom;
        rDenom = realDenom * x.realDenominator();
        
        iNum = imaginNum * x.imaginaryDenominator() 
                - x.imaginaryNumerator() * imaginDenom;
        iDenom = imaginDenom * x.imaginaryDenominator();

        Type t = getSuperType(x);
        
        return new Num(rNum, rDenom, iNum, iDenom, t);
    }
    
    public Num times(Num x) {
        int rNum, rDenom;

        rNum = realNum * x.realNumerator();
        rDenom = realDenom * x.realDenominator();
        
        Type t = getSuperType(x);
        
        if (rDenom != 1 && rNum % rDenom == 0) {
            rNum /= rDenom;
            rDenom = 1;
            
            t = Type.INTEGER;
        }
        
        // TODO imaginary part.

        return new Num(rNum, rDenom, 0, 1, t);
    }
    
    public Num divide(Num x) {
        int rNum, rDenom;

        rNum = realNum * x.realDenominator();
        rDenom = realDenom * x.realNumerator();
        
        if (rNum % rDenom == 0) {
            rNum /= rDenom;
            rDenom = 1;
        }
        
        // TODO imaginary part.

        Type t = getSuperType(x);
        
        if (rDenom != 1 && t == Type.INTEGER) {
            t = Type.RATIONAL;
        }

        return new Num(rNum, rDenom, 0, 1, t);
    }
    
    public boolean lessThan(Num x) {
        if (typeOfNum == Type.COMPLEX || x.getType() == Type.COMPLEX) {
            // Throw error;
        }
        
        double thisValue = (realNum * 1.0) / realDenom,
               xValue = (x.realNumerator() * 1.0) / x.realDenominator();

        return thisValue < xValue;
    }
    
    public boolean lessThanOrEqualTo(Num x) {
        if (typeOfNum == Type.COMPLEX || x.getType() == Type.COMPLEX) {
            // Throw error;
        }
        
        double thisValue = (realNum * 1.0) / realDenom,
               xValue = (x.realNumerator() * 1.0) / x.realDenominator();

        return thisValue <= xValue;
    }
    
    public boolean greaterThan(Num x) {
        if (typeOfNum == Type.COMPLEX || x.getType() == Type.COMPLEX) {
            // Throw error;
        }
        
        double thisValue = (realNum * 1.0) / realDenom,
               xValue = (x.realNumerator() * 1.0) / x.realDenominator();

        return thisValue > xValue;
    }
    
    public boolean greaterThanOrEqualTo(Num x) {
        if (typeOfNum == Type.COMPLEX || x.getType() == Type.COMPLEX) {
            // Throw error;
        }
        
        double thisValue = (realNum * 1.0) / realDenom,
               xValue = (x.realNumerator() * 1.0) / x.realDenominator();

        return thisValue >= xValue;
    }

    public int realNumerator() {
        return realNum;
    }
    
    
    public int realDenominator() {
        return realDenom;
    }
    
    public int imaginaryNumerator() {
        return imaginNum;
    }
    
    public int imaginaryDenominator() {
        return imaginDenom;
    }
    
    public Type getType() {
        return typeOfNum;
    }
    
    public String toString() {
                
        switch (this.typeOfNum) {
             case RATIONAL:
                 return realNum + "/" + realDenom;
             case REAL: 
                 return Double.toString((realNum * 1.0) / realDenom);
             case COMPLEX:               
                 String imaginPart = (realDenom == 1) ? Integer.toString(realNum)
                         : Double.toString((imaginNum * 1.0) / imaginDenom);
                 
                 if (realDenom == 0) {
                     return imaginPart;
                 }
                 
                 String realPart = (realDenom == 1) ? Integer.toString(realNum)
                         : Double.toString((realNum * 1.0)/ realDenom);
                 
                 return realPart + "+" + imaginPart + "i";
             case INTEGER: default:
                 return Integer.toString(realNum);
        }
    }
    
    /**
     * Returns the "SuperType" of this and the given number x. For example if 
     * either this or the given Num is of type RATIONAL and the other is of type
     * REAL, then this method will return REAL since the reals is a superset of
     * the rationals.
     */
    private Type getSuperType(Num x) {
        return typeOfNum.compareTo(x.getType()) < 0? x.getType() : typeOfNum;
    }
    
}
