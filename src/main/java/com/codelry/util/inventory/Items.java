package com.codelry.util.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.codelry.util.RandomData;

public class Items {
  private final ObjectNode data;

  public Items(int i_id) {
    RandomData util = new RandomData();
    int i_store_id = util.randomNumber(1, 10000);
    String i_name = util.makeAlphaString(14, 24);
    float i_price = (float) (util.randomNumber(100, 10000) / 100.0);
    String i_data = util.makeAlphaString(26, 50);
    String i_brand = "";
    String i_category = "";
    int i_category_key = 0;
    String i_subcategory = "";
    int i_subcategory_key = 0;
    double i_cost = 1.00;

    int position = util.randomNumber(0, i_data.length() - 8);
    i_data = i_data.substring(0, position) + "original" + i_data.substring(position + 8);

    ObjectMapper mapper = new ObjectMapper();
    this.data = mapper.createObjectNode();
    this.data.put("i_id", i_id);
    this.data.put("i_store_id", i_store_id);
    this.data.put("i_name", i_name);
    this.data.put("i_brand", i_brand);
    this.data.put("i_price", i_price);
    this.data.put("i_cost", i_cost);
    this.data.put("i_category", i_category);
    this.data.put("i_category_key", i_category_key);
    this.data.put("i_subcategory", i_subcategory);
    this.data.put("i_subcategory_key", i_subcategory_key);
  }

  public int i_id() {
    return data.get("i_id").asInt();
  }

  public int i_im_id() {
    return data.get("i_im_id").asInt();
  }

  public String i_name() {
    return data.get("i_name").asText();
  }

  public float i_price() {
    return data.get("i_price").floatValue();
  }

  public String i_data() {
    return data.get("i_data").asText();
  }

  public ObjectNode asNode() {
    return data;
  }

  public String asJson() {
    return data.toString();
  }
}
