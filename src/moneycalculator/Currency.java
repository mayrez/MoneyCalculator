package moneycalculator;

public class Currency {
    private String name;
    private String symbol;
    private String code;
    
    public Currency (String name, String symbol, String code){
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Currency{" + "name=" + name + ", symbol=" + symbol + ", code=" + code + '}';
    }
    public  Currency (String code, String name){
        this(code, "", name);
    }
     public String getCode() {
        return code;
    }
     public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

   
   
}
