import java.util.HashMap;
/*
*
*/
public class Upgrade {
    private Integer level;
    private String currency;
    private String amt;
    private HashMap<String,String> area;

    //setters
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public void setArea(HashMap<String, String> area) {
        this.area = area;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    // getters
    public String getAmt() {
        return amt;
    }
    public HashMap<String, String> getArea() {
        return area;
    }
    public String getCurrency() {
        return currency;
    }
    public Integer getLevel() {
        return level;
    } 
}
