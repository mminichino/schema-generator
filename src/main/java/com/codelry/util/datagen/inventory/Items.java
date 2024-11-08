package com.codelry.util.datagen.inventory;

import com.codelry.util.datagen.Product;
import com.codelry.util.datagen.Table;
import com.codelry.util.datagen.RandomData;
import com.codelry.util.datagen.ProductList;

import java.util.List;

public class Items extends Table {

  @Override
  public void init(long i_id) {
    RandomData util = new RandomData();
    ProductList products = new ProductList();
    this.recordName = "item";
    this.recordNumber = i_id;

    int count = products.productCount();
    int selection = util.randomNumber(0, count - 1);
    Product product = products.getProductByIndex(selection);
    List<String> brands = products.getProductBrands(selection);
    String brand = brands.get(util.randomNumber(0, brands.size() - 1));

    int i_store_id = util.randomNumber(1, 10000);

    String i_name = brand + " " + product.name;
    double i_price = util.randomDouble(10, 2000, 2);
    int rand_percentage = util.randomNumber(70, 90);
    float percentage = (float) rand_percentage / 100;
    double i_cost = util.roundDouble(i_price * percentage, 2);

    this.data.put("i_id", i_id);
    this.data.put("i_store_id", i_store_id);
    this.data.put("i_name", i_name);
    this.data.put("i_brand", brand);
    this.data.put("i_price", i_price);
    this.data.put("i_cost", i_cost);
    this.data.put("i_category", product.getCategoryName());
    this.data.put("i_category_key", product.category);
    this.data.put("i_subcategory", product.getSubcategoryName());
    this.data.put("i_subcategory_key", product.subcategory);
  }
}
