package model;

public class Currency {
    
    private String code;
    private String name;
    private String symbol;
   
    
    public Currency (String code, String name , String symbol){
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }
    public  Currency (String code, String name){
        this(code, name,"");
    }

    @Override
    public String toString() {
        return code + " " + name + " " + symbol;
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

     @Override
    public boolean equals(Object currency) {
        if (currency == null || getClass() != currency.getClass())
            return false;
        Currency currency2 = (Currency) currency;       
        if (!this.code.equalsIgnoreCase(currency2.code)) return false;
        if (!this.name.equalsIgnoreCase(currency2.name)) return false;
        if (!this.symbol.equalsIgnoreCase(currency2.symbol)) return false;

        return true;
    }

   
}
