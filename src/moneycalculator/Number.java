
package moneycalculator;
  
public class Number {
    private long numerator;
    private long denominator;
    public Number (long numerator, long denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        return "Number{" + "numerator=" + numerator + ", denominator=" + denominator + '}';
    }
    public Number (Number number){
        this(number.numerator, number.denominator);
    }
    public Number (int number){
        this(number, 1);
    }
    public Number (long number){
        this(number,1);
    }
    public Number (double number){
        this(fromDouble(number));
    }
  
    public static Number fromDouble(double number){
        long denominator = 1;
        while((long)number !=number){
           number*=10;
           denominator*=10;
        }  
        return new Number((long)number, denominator);
        
    }
     public static Number valueOf (String amount){
        return fromDouble(Double.valueOf(amount));
    }
    private int[] getPrimeNumbers(){
        return new int[] {2,3,5,7,11,13,17,19,23};
    }
    private boolean isDivisible (int number){
        return ((numerator%number==0) && (denominator%number==0));
    }
    private void reduce (){
        int[] primeNumber = getPrimeNumbers();
        for(int number: primeNumber){
           if(numerator<number)break;
           if(denominator<number)break;
           reduce (number);
        }
    }
    private void reduce (int number){
        while(isDivisible(number)){
            numerator/=number;
            denominator/=number;
        }
    }
}
