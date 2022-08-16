package entities;

import java.util.HashMap;
import java.util.Map;

public class Currency {
    private Map<String, String> currencyMap = new HashMap<>();

    public Currency (Map<String, String> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public String getCurrency(String location) {
        return currencyMap.get(location);
    }
}