
public abstract class Number extends Atom {
    
    protected <T extends java.lang.Number> Number createNumber(T x) {
        if (x instanceof Integer) {
            return new Int(x.intValue());
        } else if (x instanceof Double || x instanceof Float) {
            return new Real(x.doubleValue());
        }
        return null;
    }
    
    public java.lang.Number toValue() {
        return 0;
    }
    
    public abstract Number plus(Number n);
    
    public abstract Number minus(Number n);
    
    public abstract Number times(Number car);
    
    public abstract Number divide(Number n);
    
    public boolean lessThan(Number car) {
        // TODO Auto-generated method stub
        return false;
    }
        
    public static class Int extends Number {
        private int value;
        
        public Int(String value) {
            this.value = Integer.parseInt(value);
        }
        
        public Int(int value) {
            this.value = value;
        }

        public Number plus(Number n) {
            java.lang.Number k = n.toValue();
            
            if (k instanceof Integer) {
                return createNumber(value + k.intValue());
            } else {
                return createNumber(value + k.doubleValue());
            }
        }
        
        public Number minus(Number n) {
            java.lang.Number k = n.toValue();
            
            if (k instanceof Integer) {
                return createNumber(value - k.intValue());
            } else {
                return createNumber(value - k.doubleValue());
            }
        }
        
        public Number times(Number n) {
            java.lang.Number k = n.toValue();
            
            if (k instanceof Integer) {
                return createNumber(value * k.intValue());
            } else {
                return createNumber(value * k.doubleValue());
            }
        }
        
        public Number divide(Number n) {
            return createNumber(value / n.toValue().doubleValue());
        }
        
        public boolean lessThan(Number n) {
            return value < n.toValue().doubleValue();
        }
        
        public java.lang.Number toValue() {
            return value;
        }
        
        public String toString() {
            return Integer.toString(value);
        }
        
    }
    
    public static class Real extends Number {
        double value;
        
        public Real(double value) {
            this.value = value;
        }
        
        public String toString() {
            return Double.toString(value);
        }

        public Number plus(Number n) {
            return createNumber(value + n.toValue().doubleValue());
        }

        public Number minus(Number n) {
            return createNumber(value - n.toValue().doubleValue());
        }

        public Number times(Number n) {
            return createNumber(value - n.toValue().doubleValue());
        }

        public Number divide(Number n) {
            return createNumber(value / n.toValue().doubleValue());
        }
        
    }
    
    public static class Rational extends Number {
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number plus(Number n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number minus(Number n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number times(Number car) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number divide(Number n) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }
    
    public static class Complex extends Number {
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number plus(Number n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number minus(Number n) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number times(Number car) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Number divide(Number n) {
            // TODO Auto-generated method stub
            return null;
        }
        
    }

}
