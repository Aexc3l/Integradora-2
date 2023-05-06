package model;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class SearchEngine {
    private ArrayList<Product> productlist;
    private ArrayList<Order> orderlist;
    public SearchEngine(ArrayList<Product> productlist, ArrayList<Order> orderlist) {
        this.productlist = productlist;
        this.orderlist = orderlist;
    }

    public void setProductList(ArrayList<Product> productsList) {
        this.productlist = productsList;
    }

    public ArrayList<Product> getProductlist() {
        return productlist;
    }

    public ArrayList<Order> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(ArrayList<Order> orderlist) {
        this.orderlist = orderlist;
    }

    public ArrayList variableSort(String var, boolean order) {
        if(!order) {
            Collections.sort(productlist, new ProductComparator(var));
            Collections.reverse(productlist);
            return productlist;
        }else{
            Collections.sort(orderlist, new OrderComparator(var));
            Collections.reverse(orderlist);
            return orderlist;
        }
    }

    public ArrayList<Product> searchByProductString(String value, String var){
        variableSort(var, true);
        ArrayList<Product> result = new ArrayList<>();
        int low = 0;
        int high= productlist.size()-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            String valueToCompare = getStringValueFromProductList(mid, var);
            if (valueToCompare.toLowerCase().contains(value.toLowerCase())) {
                result.add(productlist.get(mid));
                int i = mid-1;
                while (i >= 0 && getStringValueFromProductList(i, var).toLowerCase().contains(value.toLowerCase())) {
                    result.add(productlist.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < productlist.size() && getStringValueFromProductList(i, var).toLowerCase().contains(value.toLowerCase())) {
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

    public ArrayList<Order> searchByOrderString(String value, String var){
        variableSort(var, false);
        ArrayList<Order> result = new ArrayList<>();
        int low = 0;
        int high= orderlist.size()-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            String valueToCompare = getStringValueFromOrderList(mid, var);
            if (valueToCompare.toLowerCase().contains(value.toLowerCase())) {
                result.add(orderlist.get(mid));
                int i = mid-1;
                while (i >= 0 && getStringValueFromOrderList(i, var).toLowerCase().contains(value.toLowerCase())) {
                    result.add(orderlist.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < orderlist.size() && getStringValueFromOrderList(i, var).toLowerCase().contains(value.toLowerCase())) {
                    result.add(orderlist.get(i));
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

    public Product searchByProductValue(Double value, String var){
        variableSort(var, true);
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
    public Order searchByOrderValue(Double value, String var){
        variableSort(var, false);
        int low = 0;
        int high= orderlist.size()-1;
        while(low<=high){
            int mid = low + (high-low)/2;
            Double valueToCompare = getNumericValueFromOrderList(mid, var);

            if(valueToCompare!=-1){
                if(value.compareTo(valueToCompare) > 0) low = mid+1;
                else if(value.compareTo(valueToCompare) < 0) high = mid-1;
                else return orderlist.get(mid);
            }else break;
        }
        return null;
    }

    public List<Product> splitProductArrayList(int start, int end){
        return productlist.subList(start, end);
    }
    public List<Order> splitOrderArrayList(int start, int end){
        return orderlist.subList(start, end);
    }

    public List<Product> searchRangeByProductValue(Double min, Double max, String var){
        int start = searchByIndexProductValue( min, var);
        int end = searchByIndexProductValue( max, var);
        return splitProductArrayList(start, end);
    }

    public List<Order> searchRangeByOrderValue(Double min, Double max, String var){
        int start = searchByIndexOrderValue( min, var);
        int end = searchByIndexOrderValue( max, var);
        return splitOrderArrayList(start, end);
    }

    public int searchByIndexProductValue(Double value, String var){
        variableSort(var, true);
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
    public int searchByIndexOrderValue(Double value, String var){
        variableSort(var, false);
        int low = 0;
        int high= orderlist.size()-1;
        int mid = 0;
        while(low<=high){
            mid = low + (high-low)/2;
            Double valueToCompare = getNumericValueFromOrderList(mid, var);
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

    private String getStringValueFromOrderList(int index, String value){
        return switch (value) {
            case "buyerName" -> orderlist.get(index).getBuyerName();
            default -> "";
        };
    }

    private Double getNumericValueFromOrderList(int index, String value){
        switch (value){
            case "totalPrice":
                return orderlist.get(index).getTotalPrice();
            default:
                return -1.0;
        }
    }

    private Double getNumericValueFromProductList(int index, String value){
        switch (value){
            case "price":
                return productlist.get(index).getPrice();
            case "timesPurchased":
                return (double) productlist.get(index).getTimesPurchased();
            case "quantity":
                return (double) productlist.get(index).getQuantity();
            default:
                return -1.0;
        }
    }
    private Calendar getCalendarValueFromOrderList(int index, String value){
        switch (value){
            case "date":
                return orderlist.get(index).getDate();
            default:
                return null;
        }
    }
}