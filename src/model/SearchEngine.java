package model;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchEngine {
    private ArrayList<Product> productlist;
    public SearchEngine(ArrayList<Product> productlist) {
        this.productlist = productlist;
    }

    public void setProductList(ArrayList<Product> productsList) {
        this.productlist = productsList;
    }

    public ArrayList<Product> getProductlist() {
        return productlist;
    }

    public ArrayList<Product> sortUsingAVariable(String var, boolean order) {
        Collections.sort(productlist, new ProductComparator(var));
        if(!order)
            Collections.reverse(productlist);
        return productlist;
    }

    public ArrayList<Product> binarySearchOfProductUsingStringValue(String value, String var){
        sortUsingAVariable(var, true);
        ArrayList<Product> result = new ArrayList<>();
        int low = 0;
        int high= productlist.size()-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            String valueToCompare = getStringValueFromProductList(mid, var);
            if (valueToCompare.toLowerCase().contains(value.toLowerCase())) {
                result.add(productlist.get(mid));
                int i = mid-1;
                while (i >= 0 && productlist.get(i).getName().toLowerCase().contains(value.toLowerCase())) {
                    result.add(productlist.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < productlist.size() && productlist.get(i).getName().toLowerCase().contains(value.toLowerCase())) {
                    result.add(productlist.get(i));
                    i++;
                }
                break;
            } else if (value.compareTo(valueToCompare) < 0) {
                high = mid-1;
            } else {
                low = mid+1;
            }
        }
        return result;
    }

    public Product binarySearchOfProductUsingNumericValue( Double value, String var){
        sortUsingAVariable(var, true);
        int low = 0;
        int high= productlist.size()-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            Double valueToCompare = getNumericValueFromProductList(mid, var);

            if(valueToCompare!=-1){
                if(value.compareTo(valueToCompare) > 0) low = mid+1;
                else if(value.compareTo(valueToCompare) < 0) high = mid-1;
                else return productlist.get(mid);
            }else break;
        }
        return null;
    }

    public List<Product> splitArrayList(int start, int end){
        return productlist.subList(start, end);
    }

    public List<Product> bsProductUsingRangeOfNumericValue(Double min, Double max, String var){
        int start = binarySearchOfIndexGivingANumericValue( min, var);
        int end = binarySearchOfIndexGivingANumericValue( max, var);
        return splitArrayList(start, end);
    }

    public int binarySearchOfIndexGivingANumericValue(Double value, String var){
        sortUsingAVariable(var, true);
        int low = 0;
        int high= productlist.size()-1;
        int mid = 0;
        while(low<=high){
            mid = low + (high-low)/2;
            Double valueToCompare = getNumericValueFromProductList(mid, var);
            if(value.compareTo(valueToCompare) < 0) high = mid-1;
            else low = mid+1;
        }
        return mid;
    }
    private String getStringValueFromProductList(int index, String value){
        return switch (value) {
            case "name" -> productlist.get(index).getName();
            case "category" -> productlist.get(index).getCategory().toString();
            default -> "";
        };
    }

    private Double getNumericValueFromProductList(int index, String value){
        switch (value){
            case "price":
                return productlist.get(index).getPrice();
            case "purchasedAmount":
                return (double) productlist.get(index).getTimesPurchased();
            case "amount":
                return (double) productlist.get(index).getQuantity();
            default:
                return -1.0;
        }
    }
}