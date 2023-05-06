package model;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    private String sort;
    public ProductComparator(String sort) {
        this.sort = sort;
    }

    @Override
    public int compare(Product p1, Product p2) {
        switch (sort){
            case("name"):
                return sortGivenTwoValues(p1.getName(), p2.getName());
            case("category"):
                return sortGivenTwoValues(p1.getCategory().toString(), p2.getCategory().toString());
            case("price"):
                return sortGivenTwoNumericValues(Double.valueOf(p1.getPrice()), Double.valueOf(p2.getPrice()));
            case("timesPurchased"):
                return sortGivenTwoNumericValues(Double.valueOf(p1.getTimesPurchased()), Double.valueOf(p2.getTimesPurchased()));
            case("quantity"):
                return sortGivenTwoNumericValues(Double.valueOf(p1.getQuantity()), Double.valueOf(p2.getQuantity()));
        }
        return 0;
    }
    private int sortGivenTwoValues(String firstValue, String secondValue){
        if(firstValue.compareTo(secondValue)<0) return -1;
        else if(firstValue.compareTo(secondValue)>0) return 1;
        else return 0;
    }
    private int sortGivenTwoNumericValues(Double firstValue, Double secondValue){
        if(firstValue.compareTo(secondValue)<0) return -1;
        else if(firstValue.compareTo(secondValue)>0) return 1;
        else return 0;
    }
}