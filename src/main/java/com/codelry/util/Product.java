package com.codelry.util;

public class Product {
  public String name;
  public int category;
  public int subcategory;

  public Product(String name, int category, int subcategory) {
    this.name = name;
    this.category = category;
    this.subcategory = subcategory;
  }

  public String getCategoryName() {
    switch (category) {
      case 1: return "Electronics";
      case 2: return "Household Items";
      case 3: return "Clothing";
      case 4: return "Food & Drink";
      default: return "Unknown Category";
    }
  }

  public String getSubcategoryName() {
    switch (subcategory) {
      case 101: return "Mobile Devices";
      case 102: return "Computers";
      case 103: return "Wearables";
      case 104: return "Audio";
      case 105: return "Home Entertainment";
      case 106: return "Accessories";
      case 107: return "Photography";
      case 108: return "Gadgets";
      case 109: return "Virtual Reality";
      case 110: return "Smart Home";
      case 111: return "Storage";
      case 112: return "Office Equipment";
      case 113: return "Networking";
      case 114: return "Car Audio";
      case 115: return "Personal Care";
      case 116: return "Business Electronics";
      case 201: return "Cleaning Appliances";
      case 202: return "Kitchen Appliances";
      case 203: return "Laundry Appliances";
      case 204: return "Climate Control";
      case 205: return "Plumbing";
      case 206: return "Laundry Supplies";
      case 207: return "Cleaning Supplies";
      case 208: return "Home Furnishings";
      case 209: return "Furniture";
      case 210: return "Bedding";
      case 211: return "Lighting";
      case 212: return "Decor";
      case 301: return "Tops";
      case 302: return "Bottoms";
      case 303: return "Outerwear";
      case 304: return "Footwear";
      case 305: return "Women";
      case 306: return "Men";
      case 307: return "Accessories";
      case 308: return "Activewear";
      case 309: return "Loungewear";
      case 310: return "Jewelry";
      case 401: return "Bakery";
      case 402: return "Dairy Alternatives";
      case 403: return "Dairy";
      case 404: return "Poultry";
      case 405: return "Meat";
      case 406: return "Seafood";
      case 407: return "Grains";
      case 408: return "Snacks";
      case 409: return "Sweets";
      case 410: return "Fruits";
      case 411: return "Vegetables";
      case 412: return "Canned Goods";
      case 413: return "Condiments";
      case 414: return "Sauces";
      case 415: return "Beverages";
      case 416: return "Spreads";
      case 417: return "Breakfast Foods";
      case 418: return "Sweeteners";
      case 419: return "Baking Supplies";
      case 420: return "Spices";
      default: return "Unknown Subcategory";
    }
  }
}
