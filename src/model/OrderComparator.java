package model;

import java.util.Calendar;
import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {
    private String sort;
    public OrderComparator(String sort) {
        this.sort = sort;
    }

    @Override
    public int compare(Order o1, Order o2) {
        switch (sort){
            case("buyerName"):
                return sortGivenTwoValues(o1.getBuyerName(), o2.getBuyerName());
            case("productList"):
                return sortGivenTwoValues(o1.getProductList().toString(), o2.getProductList().toString());
            case("totalPrice"):
                return sortGivenTwoNumericValues(Double.valueOf(o1.getTotalPrice()), Double.valueOf(o2.getTotalPrice()));
            case("date"):
                return sortGivenTwoDateValues(o1.getDate(), o2.getDate());
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
    private int sortGivenTwoDateValues(Calendar firstValue, Calendar secondValue){
        int year1 = firstValue.get(Calendar.YEAR);
        int year2 = secondValue.get(Calendar.YEAR);
        if (year1 < year2) {
            return -1;
        } else if (year1 > year2) {
            return 1;
        } else {
            int month1 = firstValue.get(Calendar.MONTH);
            int month2 = secondValue.get(Calendar.MONTH);
            if (month1 < month2) {
                return -1;
            } else if (month1 > month2) {
                return 1;
            } else {
                int day1 = firstValue.get(Calendar.DAY_OF_MONTH);
                int day2 = secondValue.get(Calendar.DAY_OF_MONTH);
                if (day1 < day2) {
                    return -1;
                } else if (day1 > day2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

}
