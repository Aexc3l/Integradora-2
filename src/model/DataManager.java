package model;

import com.google.gson.Gson;
import exception.InvalidReferenceException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DataManager {

    private Gson gson;

    public DataManager() {
        this.gson = new Gson();
    }

    public boolean exportData(String fileName,StoreData storeData) throws IOException{

        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir + "/data");
        File newFile = new File(dataDirectory + "/" + fileName + ".json");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        String json = gson.toJson(storeData);

        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(json.getBytes(StandardCharsets.UTF_8));
        fos.close();

        return true;
    }

    public StoreData importData(String fileName, StoreData storeData) throws FileNotFoundException{

            File projectDir = new File(System.getProperty("user.dir"));
            File dataDirectory = new File(projectDir + "/data");
            File file = new File(dataDirectory + "/" + fileName + ".json");

            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            if (!file.exists()) {
                throw new FileNotFoundException("The file does not exists");
            }else {
                StoreData data = gson.fromJson(reader, StoreData.class);
                ArrayList<Product> temporalList = new ArrayList<>();


                //Product Reader
                for (Product product : data.getProducts()) {
                    temporalList.add(
                            new Product(
                                    product.getName(),
                                    product.getDescription(),
                                    product.getPrice(),
                                    product.getQuantity(),
                                    product.getCategory()
                            )
                    );
                }
                storeData.setProducts(temporalList);
               //Order Reader
                ArrayList<Order> orderTempList = new ArrayList<>();
                for (Order order : data.getOrders()) {
                    ArrayList<Product> orderedProducts = new ArrayList<>();
                    for (Product productData : order.getProductList()) {
                        if (productData == null){
                            break;
                        }else {
                            Product product = null;
                            try {
                                product = getProductByName(productData.getName(), storeData.getProducts());
                            }catch (InvalidReferenceException e){

                            }
                            if (product != null) {
                                orderedProducts.add(product);
                            }
                        }
                    }
                    Order newOrder = new Order(
                            order.getBuyerName(),
                            orderedProducts
                    );
                    orderTempList.add(newOrder);
                }
                storeData.setOrders(orderTempList);
            }
        return storeData;
    }

    private Product getProductByName(String name, ArrayList<Product> products) throws InvalidReferenceException {
        Product sendCoincidence = null;
        for (Product product : products) {
            if (product.getName().equals(name)) {
                sendCoincidence = product;
                return sendCoincidence;
            }
        }
        if (sendCoincidence == null){
            return null;
        }
        throw new InvalidReferenceException();
    }


}
