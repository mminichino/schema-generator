package com.codelry.util;

import java.util.*;

public class ProductList {
  static List<Product> products = new ArrayList<>();
  static Map<Product, List<String>> productBrands = new HashMap<>();

  // Category keys
  static final int ELECTRONICS = 1;
  static final int HOUSEHOLD_ITEMS = 2;
  static final int CLOTHING = 3;
  static final int FOOD_AND_DRINK = 4;

  // Subcategory keys for Electronics
  static final int MOBILE_DEVICES = 101;
  static final int COMPUTERS = 102;
  static final int WEARABLES = 103;
  static final int AUDIO = 104;
  static final int HOME_ENTERTAINMENT = 105;
  static final int ACCESSORIES = 106;
  static final int PHOTOGRAPHY = 107;
  static final int GADGETS = 108;
  static final int VIRTUAL_REALITY = 109;
  static final int SMART_HOME = 110;
  static final int STORAGE = 111;
  static final int OFFICE_EQUIPMENT = 112;
  static final int NETWORKING = 113;
  static final int CAR_AUDIO = 114;
  static final int PERSONAL_CARE = 115;
  static final int BUSINESS_ELECTRONICS = 116;

  // Subcategory keys for Household Items
  static final int CLEANING_APPLIANCES = 201;
  static final int KITCHEN_APPLIANCES = 202;
  static final int LAUNDRY_APPLIANCES = 203;
  static final int CLIMATE_CONTROL = 204;
  static final int PLUMBING = 205;
  static final int LAUNDRY_SUPPLIES = 206;
  static final int CLEANING_SUPPLIES = 207;
  static final int HOME_FURNISHINGS = 208;
  static final int FURNITURE = 209;
  static final int BEDDING = 210;
  static final int LIGHTING = 211;
  static final int DECOR = 212;

  // Subcategory keys for Clothing
  static final int TOPS = 301;
  static final int BOTTOMS = 302;
  static final int OUTERWEAR = 303;
  static final int FOOTWEAR = 304;
  static final int WOMEN = 305;
  static final int MEN = 306;
  static final int ACCESSORIES_CLOTHING = 307; // To differentiate it from electronics
  static final int ACTIVEWEAR = 308;
  static final int LOUNGEWEAR = 309;
  static final int JEWELRY = 310;

  // Subcategory keys for Food & Drink
  static final int BAKERY = 401;
  static final int DAIRY_ALTERNATIVES = 402;
  static final int DAIRY = 403;
  static final int POULTRY = 404;
  static final int MEAT = 405;
  static final int SEAFOOD = 406;
  static final int GRAINS = 407;
  static final int SNACKS = 408;
  static final int SWEETS = 409;
  static final int FRUITS = 410;
  static final int VEGETABLES = 411;
  static final int CANNED_GOODS = 412;
  static final int CONDIMENTS = 413;
  static final int SAUCES = 414;
  static final int BEVERAGES = 415;
  static final int SPREADS = 416;
  static final int BREAKFAST_FOODS = 417;
  static final int SWEETENERS = 418;
  static final int BAKING_SUPPLIES = 419;
  static final int SPICES = 420;

  public static void init() {
    productBrands.put(new Product("Smartphone", ELECTRONICS, MOBILE_DEVICES),
        Arrays.asList("Apple", "Samsung", "Google", "OnePlus"));
    productBrands.put(new Product("Laptop", ELECTRONICS, COMPUTERS),
        Arrays.asList("Dell", "HP", "Apple", "Lenovo", "Acer"));
    productBrands.put(new Product("Tablet", ELECTRONICS, MOBILE_DEVICES),
        Arrays.asList("Apple", "Samsung", "Amazon", "Microsoft"));
    productBrands.put(new Product("Smartwatch", ELECTRONICS, WEARABLES),
        Arrays.asList("Apple", "Samsung", "Fitbit", "Garmin"));
    productBrands.put(new Product("Bluetooth Headphones", ELECTRONICS, AUDIO),
        Arrays.asList("Sony", "Bose", "JBL", "Beats"));
    productBrands.put(new Product("4K Television", ELECTRONICS, HOME_ENTERTAINMENT),
        Arrays.asList("Sony", "Samsung", "LG", "TCL"));
    productBrands.put(new Product("Gaming Console", ELECTRONICS, HOME_ENTERTAINMENT),
        Arrays.asList("Sony", "Microsoft", "Nintendo"));
    productBrands.put(new Product("Wireless Charger", ELECTRONICS, ACCESSORIES),
        Arrays.asList("Anker", "Samsung", "Belkin", "Mophie"));
    productBrands.put(new Product("Bluetooth Speaker", ELECTRONICS, AUDIO),
        Arrays.asList("JBL", "Bose", "Sony", "Ultimate Ears"));
    productBrands.put(new Product("Digital Camera", ELECTRONICS, PHOTOGRAPHY),
        Arrays.asList("Canon", "Nikon", "Sony", "Fujifilm"));
    productBrands.put(new Product("E-Reader", ELECTRONICS, MOBILE_DEVICES),
        Arrays.asList("Amazon", "Barnes & Noble", "Kobo"));
    productBrands.put(new Product("Drone", ELECTRONICS, GADGETS),
        Arrays.asList("DJI", "Parrot", "Autel Robotics"));
    productBrands.put(new Product("VR Headset", ELECTRONICS, VIRTUAL_REALITY),
        Arrays.asList("Oculus", "HTC", "Sony", "Valve"));
    productBrands.put(new Product("Home Theater System", ELECTRONICS, HOME_ENTERTAINMENT),
        Arrays.asList("Sony", "Bose", "Yamaha", "LG"));
    productBrands.put(new Product("Smart Home Hub", ELECTRONICS, SMART_HOME),
        Arrays.asList("Amazon", "Google", "Apple", "Samsung"));
    productBrands.put(new Product("Fitness Tracker", ELECTRONICS, WEARABLES),
        Arrays.asList("Fitbit", "Garmin", "Xiaomi", "Apple"));
    productBrands.put(new Product("Desktop Computer", ELECTRONICS, COMPUTERS),
        Arrays.asList("Dell", "HP", "Apple", "Lenovo", "Asus"));
    productBrands.put(new Product("External Hard Drive", ELECTRONICS, STORAGE),
        Arrays.asList("Seagate", "Western Digital", "Samsung", "Toshiba"));
    productBrands.put(new Product("Portable Projector", ELECTRONICS, OFFICE_EQUIPMENT),
        Arrays.asList("Epson", "BenQ", "ViewSonic", "Anker"));
    productBrands.put(new Product("Webcam", ELECTRONICS, COMPUTERS),
        Arrays.asList("Logitech", "Microsoft", "Razer", "Ausdom"));
    productBrands.put(new Product("Soundbar", ELECTRONICS, AUDIO),
        Arrays.asList("Samsung", "Sony", "Bose", "Vizio"));
    productBrands.put(new Product("Wireless Mouse", ELECTRONICS, COMPUTERS),
        Arrays.asList("Logitech", "Microsoft", "Razer", "Corsair"));
    productBrands.put(new Product("Keyboard", ELECTRONICS, COMPUTERS),
        Arrays.asList("Logitech", "Corsair", "Razer", "Microsoft"));
    productBrands.put(new Product("Smart Light Bulbs", ELECTRONICS, SMART_HOME),
        Arrays.asList("Philips Hue", "Sengled", "LIFX", "Wyze"));
    productBrands.put(new Product("Router", ELECTRONICS, NETWORKING),
        Arrays.asList("Netgear", "TP-Link", "Asus", "Linksys"));
    productBrands.put(new Product("Smart Doorbell", ELECTRONICS, SMART_HOME),
        Arrays.asList("Ring", "Nest", "Arlo", "Eufy"));
    productBrands.put(new Product("Action Camera", ELECTRONICS, PHOTOGRAPHY),
        Arrays.asList("GoPro", "DJI", "Sony", "Akaso"));
    productBrands.put(new Product("Video Doorbell", ELECTRONICS, SMART_HOME),
        Arrays.asList("Ring", "Nest", "Arlo", "Eufy"));
    productBrands.put(new Product("3D Printer", ELECTRONICS, OFFICE_EQUIPMENT),
        Arrays.asList("Creality", "Prusa", "Anycubic", "FlashForge"));
    productBrands.put(new Product("Noise-cancelling Headphones", ELECTRONICS, AUDIO),
        Arrays.asList("Sony", "Bose", "Sennheiser", "JBL"));
    productBrands.put(new Product("Smart Thermostat", ELECTRONICS, SMART_HOME),
        Arrays.asList("Nest", "Ecobee", "Honeywell", "Emerson"));
    productBrands.put(new Product("Security Camera", ELECTRONICS, SMART_HOME),
        Arrays.asList("Ring", "Nest", "Arlo", "Wyze"));
    productBrands.put(new Product("Monitor", ELECTRONICS, COMPUTERS),
        Arrays.asList("Dell", "LG", "Asus", "Samsung"));
    productBrands.put(new Product("USB Drive", ELECTRONICS, STORAGE),
        Arrays.asList("SanDisk", "Kingston", "Samsung", "PNY"));
    productBrands.put(new Product("Inkjet Printer", ELECTRONICS, OFFICE_EQUIPMENT),
        Arrays.asList("HP", "Canon", "Epson", "Brother"));
    productBrands.put(new Product("Graphic Tablet", ELECTRONICS, OFFICE_EQUIPMENT),
        Arrays.asList("Wacom", "Huion", "XP-Pen", "Gaomon"));
    productBrands.put(new Product("Electric Toothbrush", ELECTRONICS, PERSONAL_CARE),
        Arrays.asList("Oral-B", "Philips", "Colgate", "Foreo"));
    productBrands.put(new Product("Hair Clipper", ELECTRONICS, PERSONAL_CARE),
        Arrays.asList("Wahl", "Philips", "Remington", "BaByliss"));
    productBrands.put(new Product("MP3 Player", ELECTRONICS, AUDIO),
        Arrays.asList("Apple", "Sony", "SanDisk"));
    productBrands.put(new Product("Satellite Radio", ELECTRONICS, CAR_AUDIO),
        Arrays.asList("SiriusXM", "Pioneer", "JVC"));
    productBrands.put(new Product("Smart Plug", ELECTRONICS, SMART_HOME),
        Arrays.asList("Amazon", "TP-Link", "Wemo", "Wyze"));
    productBrands.put(new Product("Smart Lock", ELECTRONICS, SMART_HOME),
        Arrays.asList("August", "Schlage", "Yale", "Kwikset"));
    productBrands.put(new Product("Digital Photo Frame", ELECTRONICS, GADGETS),
        Arrays.asList("Nixplay", "Aura", "Skylight", "Pix-Star"));
    productBrands.put(new Product("Car Charger", ELECTRONICS, NETWORKING),
        Arrays.asList("Anker", "Aukey", "Belkin", "RAVPower"));
    productBrands.put(new Product("Surround Sound System", ELECTRONICS, AUDIO),
        Arrays.asList("Sony", "Bose", "Yamaha", "Vizio"));
    productBrands.put(new Product("Smart Scale", ELECTRONICS, PERSONAL_CARE),
        Arrays.asList("Withings", "Fitbit", "Eufy", "Garmin"));
    productBrands.put(new Product("Bar Code Scanner", ELECTRONICS, BUSINESS_ELECTRONICS),
        Arrays.asList("Honeywell", "Zebra", "Datalogic", "Motorola"));
    productBrands.put(new Product("POS System", ELECTRONICS, BUSINESS_ELECTRONICS),
        Arrays.asList("Square", "Clover", "Toast", "Lightspeed"));
    productBrands.put(new Product("Blood Pressure Monitor", ELECTRONICS, PERSONAL_CARE),
        Arrays.asList("Omron", "iHealth", "Withings", "Beurer"));

    productBrands.put(new Product("Vacuum Cleaner", HOUSEHOLD_ITEMS, CLEANING_APPLIANCES),
        Arrays.asList("Dyson", "Shark", "Hoover", "Bissell"));
    productBrands.put(new Product("Microwave Oven", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Panasonic", "Samsung", "LG", "Whirlpool"));
    productBrands.put(new Product("Refrigerator", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Whirlpool", "Samsung", "LG", "GE"));
    productBrands.put(new Product("Washing Machine", HOUSEHOLD_ITEMS, LAUNDRY_APPLIANCES),
        Arrays.asList("Whirlpool", "LG", "Samsung", "Maytag"));
    productBrands.put(new Product("Dryer", HOUSEHOLD_ITEMS, LAUNDRY_APPLIANCES),
        Arrays.asList("Whirlpool", "LG", "Samsung", "Maytag"));
    productBrands.put(new Product("Dishwasher", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Bosch", "Whirlpool", "KitchenAid", "Samsung"));
    productBrands.put(new Product("Coffee Maker", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Keurig", "Nespresso", "Breville", "Cuisinart"));
    productBrands.put(new Product("Blender", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Vitamix", "Ninja", "Breville", "Oster"));
    productBrands.put(new Product("Electric Kettle", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Cuisinart", "Breville", "Hamilton Beach", "Mueller"));
    productBrands.put(new Product("Toaster", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Breville", "Cuisinart", "KitchenAid", "Hamilton Beach"));
    productBrands.put(new Product("Air Conditioner", HOUSEHOLD_ITEMS, CLIMATE_CONTROL),
        Arrays.asList("LG", "Whirlpool", "Frigidaire", "GE"));
    productBrands.put(new Product("Heater", HOUSEHOLD_ITEMS, CLIMATE_CONTROL),
        Arrays.asList("Dyson", "Lasko", "DeLonghi", "Honeywell"));
    productBrands.put(new Product("Ceiling Fan", HOUSEHOLD_ITEMS, CLIMATE_CONTROL),
        Arrays.asList("Hunter", "Hampton Bay", "Minka-Aire", "Westinghouse"));
    productBrands.put(new Product("Water Heater", HOUSEHOLD_ITEMS, PLUMBING),
        Arrays.asList("Rheem", "A.O. Smith", "Bosch", "Bradford White"));
    productBrands.put(new Product("Ironing Board", HOUSEHOLD_ITEMS, LAUNDRY_SUPPLIES),
        Arrays.asList("Homz", "Brabantia", "Minky", "Honey-Can-Do"));
    productBrands.put(new Product("Steam Iron", HOUSEHOLD_ITEMS, LAUNDRY_SUPPLIES),
        Arrays.asList("Rowenta", "Philips", "Black+Decker", "Tefal"));
    productBrands.put(new Product("Broom and Dustpan", HOUSEHOLD_ITEMS, CLEANING_SUPPLIES),
        Arrays.asList("O-Cedar", "Libman", "Casabella", "Quickie"));
    productBrands.put(new Product("Mop", HOUSEHOLD_ITEMS, CLEANING_SUPPLIES),
        Arrays.asList("O-Cedar", "Swiffer", "Bissell", "Vileda"));
    productBrands.put(new Product("Curtains", HOUSEHOLD_ITEMS, HOME_FURNISHINGS),
        Arrays.asList("IKEA", "Wayfair", "Pottery Barn", "West Elm"));
    productBrands.put(new Product("Rug", HOUSEHOLD_ITEMS, HOME_FURNISHINGS),
        Arrays.asList("IKEA", "Wayfair", "Ruggable", "Safavieh"));
    productBrands.put(new Product("Sofa", HOUSEHOLD_ITEMS, FURNITURE),
        Arrays.asList("IKEA", "West Elm", "Ashley Furniture", "Crate & Barrel"));
    productBrands.put(new Product("Dining Table Set", HOUSEHOLD_ITEMS, FURNITURE),
        Arrays.asList("IKEA", "Wayfair", "Ashley Furniture", "West Elm"));
    productBrands.put(new Product("Bookshelf", HOUSEHOLD_ITEMS, FURNITURE),
        Arrays.asList("IKEA", "Sauder", "Wayfair", "West Elm"));
    productBrands.put(new Product("Bed Frame", HOUSEHOLD_ITEMS, FURNITURE),
        Arrays.asList("Zinus", "IKEA", "Wayfair", "Ashley Furniture"));
    productBrands.put(new Product("Mattress", HOUSEHOLD_ITEMS, BEDDING),
        Arrays.asList("Casper", "Tempur-Pedic", "Purple", "Sealy"));
    productBrands.put(new Product("Pillows", HOUSEHOLD_ITEMS, BEDDING),
        Arrays.asList("MyPillow", "Tempur-Pedic", "Casper", "Sealy"));
    productBrands.put(new Product("Blankets", HOUSEHOLD_ITEMS, BEDDING),
        Arrays.asList("IKEA", "Brooklinen", "Pendleton", "Ugg"));
    productBrands.put(new Product("LED Light Bulbs", HOUSEHOLD_ITEMS, LIGHTING),
        Arrays.asList("Philips", "GE", "Sylvania", "Cree"));
    productBrands.put(new Product("Flashlight", HOUSEHOLD_ITEMS, LIGHTING),
        Arrays.asList("Maglite", "Streamlight", "Energizer", "Duracell"));
    productBrands.put(new Product("Wall Clock", HOUSEHOLD_ITEMS, DECOR),
        Arrays.asList("Seiko", "Bulova", "Howard Miller", "La Crosse Technology"));
    productBrands.put(new Product("Picture Frames", HOUSEHOLD_ITEMS, DECOR),
        Arrays.asList("IKEA", "Wayfair", "Michaels", "Target"));
    productBrands.put(new Product("Vase", HOUSEHOLD_ITEMS, DECOR),
        Arrays.asList("IKEA", "West Elm", "Crate & Barrel", "Pottery Barn"));
    productBrands.put(new Product("Candle Holder", HOUSEHOLD_ITEMS, DECOR),
        Arrays.asList("IKEA", "Crate & Barrel", "West Elm", "Pottery Barn"));
    productBrands.put(new Product("Laundry Basket", HOUSEHOLD_ITEMS, LAUNDRY_SUPPLIES),
        Arrays.asList("Sterilite", "Simple Houseware", "Whitmor", "Honey-Can-Do"));
    productBrands.put(new Product("Trash Can", HOUSEHOLD_ITEMS, CLEANING_SUPPLIES),
        Arrays.asList("Simplehuman", "Rubbermaid", "Hefty", "iTouchless"));
    productBrands.put(new Product("Ice Maker", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Whynter", "Frigidaire", "NewAir", "Igloo"));
    productBrands.put(new Product("Air Purifier", HOUSEHOLD_ITEMS, CLIMATE_CONTROL),
        Arrays.asList("Dyson", "Honeywell", "Levoit", "Blueair"));
    productBrands.put(new Product("Dehumidifier", HOUSEHOLD_ITEMS, CLIMATE_CONTROL),
        Arrays.asList("Frigidaire", "Honeywell", "GE", "hOmeLabs"));
    productBrands.put(new Product("Table Lamp", HOUSEHOLD_ITEMS, LIGHTING),
        Arrays.asList("IKEA", "West Elm", "Pottery Barn", "Wayfair"));
    productBrands.put(new Product("Floor Lamp", HOUSEHOLD_ITEMS, LIGHTING),
        Arrays.asList("IKEA", "Wayfair", "West Elm", "Crate & Barrel"));
    productBrands.put(new Product("Window Blinds", HOUSEHOLD_ITEMS, HOME_FURNISHINGS),
        Arrays.asList("Levolor", "Bali", "IKEA", "Wayfair"));
    productBrands.put(new Product("Door Mat", HOUSEHOLD_ITEMS, HOME_FURNISHINGS),
        Arrays.asList("IKEA", "Wayfair", "Home Depot", "Target"));
    productBrands.put(new Product("Chair", HOUSEHOLD_ITEMS, FURNITURE),
        Arrays.asList("IKEA", "Ashley Furniture", "West Elm", "Herman Miller"));
    productBrands.put(new Product("Office Desk", HOUSEHOLD_ITEMS, FURNITURE),
        Arrays.asList("IKEA", "Wayfair", "Sauder", "Bush Furniture"));
    productBrands.put(new Product("Paintings", HOUSEHOLD_ITEMS, DECOR),
        Arrays.asList("IKEA", "West Elm", "Wayfair", "Society6"));
    productBrands.put(new Product("Mirror", HOUSEHOLD_ITEMS, DECOR),
        Arrays.asList("IKEA", "West Elm", "Wayfair", "Crate & Barrel"));
    productBrands.put(new Product("Salad Spinner", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("OXO", "Zyliss", "Cuisinart", "Mueller"));
    productBrands.put(new Product("Pressure Cooker", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("Instant Pot", "Cuisinart", "Ninja", "Breville"));
    productBrands.put(new Product("Electric Grill", HOUSEHOLD_ITEMS, KITCHEN_APPLIANCES),
        Arrays.asList("George Foreman", "Hamilton Beach", "Cuisinart", "Weber"));

    productBrands.put(new Product("T-Shirt", CLOTHING, TOPS),
        Arrays.asList("Hanes", "Nike", "Adidas", "Uniqlo"));
    productBrands.put(new Product("Jeans", CLOTHING, BOTTOMS),
        Arrays.asList("Levi's", "Wrangler", "Lee", "Guess"));
    productBrands.put(new Product("Jacket", CLOTHING, OUTERWEAR),
        Arrays.asList("North Face", "Columbia", "Patagonia", "Canada Goose"));
    productBrands.put(new Product("Sneakers", CLOTHING, FOOTWEAR),
        Arrays.asList("Nike", "Adidas", "Puma", "Converse"));
    productBrands.put(new Product("Dress", CLOTHING, WOMEN),
        Arrays.asList("Zara", "H&M", "Forever 21", "Mango"));
    productBrands.put(new Product("Skirt", CLOTHING, WOMEN),
        Arrays.asList("Zara", "H&M", "Uniqlo", "Topshop"));
    productBrands.put(new Product("Suit", CLOTHING, MEN),
        Arrays.asList("Hugo Boss", "Armani", "Ralph Lauren", "Tommy Hilfiger"));
    productBrands.put(new Product("Tie", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Hugo Boss", "Gucci", "Ralph Lauren", "Hermès"));
    productBrands.put(new Product("Belt", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Gucci", "Hermès", "Levi's", "Calvin Klein"));
    productBrands.put(new Product("Socks", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Nike", "Adidas", "Hanes", "Puma"));
    productBrands.put(new Product("Hat", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("New Era", "Nike", "Adidas", "Under Armour"));
    productBrands.put(new Product("Scarf", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Burberry", "Hermès", "Gucci", "Louis Vuitton"));
    productBrands.put(new Product("Gloves", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("North Face", "Columbia", "Patagonia", "Hestra"));
    productBrands.put(new Product("Boots", CLOTHING, FOOTWEAR),
        Arrays.asList("Timberland", "Dr. Martens", "Clarks", "Sorel"));
    productBrands.put(new Product("Sandals", CLOTHING, FOOTWEAR),
        Arrays.asList("Birkenstock", "Teva", "Nike", "Adidas"));
    productBrands.put(new Product("Shorts", CLOTHING, BOTTOMS),
        Arrays.asList("Nike", "Adidas", "Under Armour", "Patagonia"));
    productBrands.put(new Product("Sweater", CLOTHING, TOPS),
        Arrays.asList("Ralph Lauren", "Uniqlo", "Tommy Hilfiger", "Gap"));
    productBrands.put(new Product("Blazer", CLOTHING, OUTERWEAR),
        Arrays.asList("Hugo Boss", "Armani", "Zara", "Ralph Lauren"));
    productBrands.put(new Product("Tank Top", CLOTHING, TOPS),
        Arrays.asList("Hanes", "Nike", "Adidas", "Champion"));
    productBrands.put(new Product("Cargo Pants", CLOTHING, BOTTOMS),
        Arrays.asList("Levi's", "Columbia", "Carhartt", "Dickies"));
    productBrands.put(new Product("Tracksuit", CLOTHING, ACTIVEWEAR),
        Arrays.asList("Adidas", "Nike", "Puma", "Under Armour"));
    productBrands.put(new Product("Swimwear", CLOTHING, ACTIVEWEAR),
        Arrays.asList("Speedo", "Billabong", "Nike", "Adidas"));
    productBrands.put(new Product("Raincoat", CLOTHING, OUTERWEAR),
        Arrays.asList("North Face", "Columbia", "Patagonia", "Helly Hansen"));
    productBrands.put(new Product("Pajamas", CLOTHING, LOUNGEWEAR),
        Arrays.asList("Victoria's Secret", "Calvin Klein", "Uniqlo", "Hanes"));
    productBrands.put(new Product("Underwear", CLOTHING, LOUNGEWEAR),
        Arrays.asList("Calvin Klein", "Hanes", "Victoria's Secret", "Tommy Hilfiger"));
    productBrands.put(new Product("Sports Bra", CLOTHING, ACTIVEWEAR),
        Arrays.asList("Nike", "Adidas", "Under Armour", "Lululemon"));
    productBrands.put(new Product("High Heels", CLOTHING, FOOTWEAR),
        Arrays.asList("Christian Louboutin", "Jimmy Choo", "Gucci", "Manolo Blahnik"));
    productBrands.put(new Product("Loafers", CLOTHING, FOOTWEAR),
        Arrays.asList("Gucci", "Cole Haan", "Clarks", "Tod's"));
    productBrands.put(new Product("Button-Up Shirt", CLOTHING, TOPS),
        Arrays.asList("Brooks Brothers", "Ralph Lauren", "Calvin Klein", "Tommy Hilfiger"));
    productBrands.put(new Product("Cardigan", CLOTHING, TOPS),
        Arrays.asList("J.Crew", "Uniqlo", "Ralph Lauren", "Gap"));
    productBrands.put(new Product("Blouse", CLOTHING, TOPS),
        Arrays.asList("Zara", "H&M", "Banana Republic", "Mango"));
    productBrands.put(new Product("Vest", CLOTHING, OUTERWEAR),
        Arrays.asList("Patagonia", "North Face", "Columbia", "Canada Goose"));
    productBrands.put(new Product("Leggings", CLOTHING, BOTTOMS),
        Arrays.asList("Lululemon", "Nike", "Adidas", "Under Armour"));
    productBrands.put(new Product("Chinos", CLOTHING, BOTTOMS),
        Arrays.asList("Dockers", "Uniqlo", "J.Crew", "Banana Republic"));
    productBrands.put(new Product("Winter Coat", CLOTHING, OUTERWEAR),
        Arrays.asList("Canada Goose", "North Face", "Patagonia", "Columbia"));
    productBrands.put(new Product("Cap", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("New Era", "Nike", "Adidas", "Under Armour"));
    productBrands.put(new Product("Earrings", CLOTHING, JEWELRY),
        Arrays.asList("Tiffany & Co.", "Pandora", "Swarovski", "Cartier"));
    productBrands.put(new Product("Necklace", CLOTHING, JEWELRY),
        Arrays.asList("Tiffany & Co.", "Pandora", "Cartier", "Swarovski"));
    productBrands.put(new Product("Bracelet", CLOTHING, JEWELRY),
        Arrays.asList("Pandora", "Cartier", "Swarovski", "Tiffany & Co."));
    productBrands.put(new Product("Backpack", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Herschel", "JanSport", "North Face", "Patagonia"));
    productBrands.put(new Product("Clutch", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Chanel", "Gucci", "Michael Kors", "Prada"));
    productBrands.put(new Product("Duffel Bag", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Nike", "Adidas", "North Face", "Patagonia"));
    productBrands.put(new Product("Watch", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Rolex", "Omega", "Tag Heuer", "Seiko"));
    productBrands.put(new Product("Suspenders", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Albert Thurston", "Y-Back", "Dockers", "Perry Ellis"));
    productBrands.put(new Product("Visor", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Nike", "Adidas", "Under Armour", "Puma"));
    productBrands.put(new Product("Cufflinks", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Tiffany & Co.", "Hugo Boss", "Montblanc", "Cartier"));
    productBrands.put(new Product("Bow Tie", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Brooks Brothers", "Hugo Boss", "Ralph Lauren", "Gucci"));
    productBrands.put(new Product("Pocket Square", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Brooks Brothers", "Ralph Lauren", "Tom Ford", "Gucci"));
    productBrands.put(new Product("Beanie", CLOTHING, ACCESSORIES_CLOTHING),
        Arrays.asList("Carhartt", "North Face", "Patagonia", "Nike"));

    productBrands.put(new Product("Whole Wheat Bread", FOOD_AND_DRINK, BAKERY),
        Arrays.asList("Pepperidge Farm", "Nature's Own", "Dave's Killer Bread", "Arnold"));
    productBrands.put(new Product("Almond Milk", FOOD_AND_DRINK, DAIRY_ALTERNATIVES),
        Arrays.asList("Almond Breeze", "Silk", "Califia Farms", "So Delicious"));
    productBrands.put(new Product("Cheddar Cheese", FOOD_AND_DRINK, DAIRY),
        Arrays.asList("Kraft", "Tillamook", "Cabot", "Sargento"));
    productBrands.put(new Product("Butter", FOOD_AND_DRINK, DAIRY),
        Arrays.asList("Land O'Lakes", "Kerrygold", "Challenge", "Plugra"));
    productBrands.put(new Product("Eggs", FOOD_AND_DRINK, POULTRY),
        Arrays.asList("Eggland's Best", "Vital Farms", "Happy Egg", "Pete and Gerry's"));
    productBrands.put(new Product("Chicken Breast", FOOD_AND_DRINK, MEAT),
        Arrays.asList("Tyson", "Perdue", "Foster Farms", "Bell & Evans"));
    productBrands.put(new Product("Ground Beef", FOOD_AND_DRINK, MEAT),
        Arrays.asList("Beyond Meat", "Tyson", "Organic Prairie", "Laura's Lean"));
    productBrands.put(new Product("Salmon Fillets", FOOD_AND_DRINK, SEAFOOD),
        Arrays.asList("Atlantic Salmon", "Wild Alaskan", "Trident Seafoods", "Silver Bay"));
    productBrands.put(new Product("Rice", FOOD_AND_DRINK, GRAINS),
        Arrays.asList("Uncle Ben's", "Lundberg", "Mahatma", "Nishiki"));
    productBrands.put(new Product("Oatmeal", FOOD_AND_DRINK, GRAINS),
        Arrays.asList("Quaker", "Bob's Red Mill", "Nature's Path", "McCann's"));
    productBrands.put(new Product("Pasta", FOOD_AND_DRINK, GRAINS),
        Arrays.asList("Barilla", "De Cecco", "Rao's", "Buitoni"));
    productBrands.put(new Product("Granola Bars", FOOD_AND_DRINK, SNACKS),
        Arrays.asList("Nature Valley", "Kind", "Clif Bar", "Quaker"));
    productBrands.put(new Product("Potato Chips", FOOD_AND_DRINK, SNACKS),
        Arrays.asList("Lay's", "Kettle Brand", "Pringles", "Cape Cod"));
    productBrands.put(new Product("Dark Chocolate", FOOD_AND_DRINK, SWEETS),
        Arrays.asList("Ghirardelli", "Lindt", "Green & Black's", "Endangered Species"));
    productBrands.put(new Product("Apples", FOOD_AND_DRINK, FRUITS),
        Arrays.asList("Honeycrisp", "Granny Smith", "Fuji", "Gala"));
    productBrands.put(new Product("Bananas", FOOD_AND_DRINK, FRUITS),
        Arrays.asList("Dole", "Chiquita", "Del Monte", "Organic Bananas"));
    productBrands.put(new Product("Carrots", FOOD_AND_DRINK, VEGETABLES),
        Arrays.asList("Bolthouse Farms", "Grimmway Farms", "Organic Carrots", "Baby Carrots"));
    productBrands.put(new Product("Broccoli", FOOD_AND_DRINK, VEGETABLES),
        Arrays.asList("Mann's", "Organic Broccoli", "Foxy", "Taylor Farms"));
    productBrands.put(new Product("Tomato Soup", FOOD_AND_DRINK, CANNED_GOODS),
        Arrays.asList("Campbell's", "Progresso", "Amy's", "Pacific Foods"));
    productBrands.put(new Product("Chickpeas", FOOD_AND_DRINK, CANNED_GOODS),
        Arrays.asList("Goya", "Eden Organic", "Bush's", "S&W"));
    productBrands.put(new Product("Olive Oil", FOOD_AND_DRINK, CONDIMENTS),
        Arrays.asList("Bertolli", "Filippo Berio", "California Olive Ranch", "Colavita"));
    productBrands.put(new Product("Vinegar", FOOD_AND_DRINK, CONDIMENTS),
        Arrays.asList("Bragg", "Heinz", "Spectrum", "Colavita"));
    productBrands.put(new Product("Ketchup", FOOD_AND_DRINK, CONDIMENTS),
        Arrays.asList("Heinz", "Hunt's", "French's", "Sir Kensington's"));
    productBrands.put(new Product("Mustard", FOOD_AND_DRINK, CONDIMENTS),
        Arrays.asList("French's", "Grey Poupon", "Gulden's", "Annie's"));
    productBrands.put(new Product("Pasta Sauce", FOOD_AND_DRINK, SAUCES),
        Arrays.asList("Rao's", "Prego", "Barilla", "Bertolli"));
    productBrands.put(new Product("Soy Sauce", FOOD_AND_DRINK, SAUCES),
        Arrays.asList("Kikkoman", "San-J", "Lee Kum Kee", "La Choy"));
    productBrands.put(new Product("Herbal Tea", FOOD_AND_DRINK, BEVERAGES),
        Arrays.asList("Celestial Seasonings", "Traditional Medicinals", "Tazo", "Yogi"));
    productBrands.put(new Product("Coffee Beans", FOOD_AND_DRINK, BEVERAGES),
        Arrays.asList("Starbucks", "Peet's", "Lavazza", "Dunkin' Donuts"));
    productBrands.put(new Product("Orange Juice", FOOD_AND_DRINK, BEVERAGES),
        Arrays.asList("Tropicana", "Simply Orange", "Florida's Natural", "Minute Maid"));
    productBrands.put(new Product("Green Tea", FOOD_AND_DRINK, BEVERAGES),
        Arrays.asList("Tazo", "Bigelow", "Twinings", "Lipton"));
    productBrands.put(new Product("Peanut Butter", FOOD_AND_DRINK, SPREADS),
        Arrays.asList("Jif", "Skippy", "Justin's", "Smucker's"));
    productBrands.put(new Product("Jelly", FOOD_AND_DRINK, SPREADS),
        Arrays.asList("Smucker's", "Welch's", "Bonne Maman", "St. Dalfour"));
    productBrands.put(new Product("Cereal", FOOD_AND_DRINK, BREAKFAST_FOODS),
        Arrays.asList("Kellogg's", "General Mills", "Post", "Nature's Path"));
    productBrands.put(new Product("Maple Syrup", FOOD_AND_DRINK, BREAKFAST_FOODS),
        Arrays.asList("Aunt Jemima", "Log Cabin", "Butternut Mountain", "Coombs Family Farms"));
    productBrands.put(new Product("Pancake Mix", FOOD_AND_DRINK, BREAKFAST_FOODS),
        Arrays.asList("Aunt Jemima", "Krusteaz", "Bisquick", "Kodiak Cakes"));
    productBrands.put(new Product("Honey", FOOD_AND_DRINK, SWEETENERS),
        Arrays.asList("Sue Bee", "Nature Nate's", "Manuka Doctor", "Clover Sonoma"));
    productBrands.put(new Product("Brown Sugar", FOOD_AND_DRINK, BAKING_SUPPLIES),
        Arrays.asList("Domino", "C&H", "Wholesome", "Imperial Sugar"));
    productBrands.put(new Product("Flour", FOOD_AND_DRINK, BAKING_SUPPLIES),
        Arrays.asList("King Arthur", "Gold Medal", "Bob's Red Mill", "Pillsbury"));
    productBrands.put(new Product("Baking Powder", FOOD_AND_DRINK, BAKING_SUPPLIES),
        Arrays.asList("Clabber Girl", "Rumford", "Bob's Red Mill", "Argo"));
    productBrands.put(new Product("Yeast", FOOD_AND_DRINK, BAKING_SUPPLIES),
        Arrays.asList("Red Star", "Fleischmann's", "SAF", "Bob's Red Mill"));
    productBrands.put(new Product("Vanilla Extract", FOOD_AND_DRINK, BAKING_SUPPLIES),
        Arrays.asList("McCormick", "Nielsen-Massey", "Watkins", "Simply Organic"));
    productBrands.put(new Product("Cinnamon", FOOD_AND_DRINK, SPICES),
        Arrays.asList("McCormick", "Simply Organic", "Frontier", "Spice Islands"));
    productBrands.put(new Product("Salt", FOOD_AND_DRINK, SPICES),
        Arrays.asList("Morton", "Diamond Crystal", "Himalayan Pink", "Redmond"));
    productBrands.put(new Product("Black Pepper", FOOD_AND_DRINK, SPICES),
        Arrays.asList("McCormick", "Simply Organic", "Morton & Bassett", "Spice Islands"));
    productBrands.put(new Product("Garlic Powder", FOOD_AND_DRINK, SPICES),
        Arrays.asList("McCormick", "Badia", "Simply Organic", "Spice Islands"));
    productBrands.put(new Product("Coconut Milk", FOOD_AND_DRINK, CANNED_GOODS),
        Arrays.asList("Thai Kitchen", "Goya", "Chaokoh", "Aroy-D"));
    productBrands.put(new Product("Tomato Paste", FOOD_AND_DRINK, CANNED_GOODS),
        Arrays.asList("Hunt's", "Contadina", "Muir Glen", "Cento"));
    productBrands.put(new Product("Pickles", FOOD_AND_DRINK, CONDIMENTS),
        Arrays.asList("Vlasic", "Claussen", "Mt. Olive", "Bubbies"));
    productBrands.put(new Product("Salsa", FOOD_AND_DRINK, SAUCES),
        Arrays.asList("Pace", "Tostitos", "Herdez", "Chi-Chi's"));
    productBrands.put(new Product("Ranch Dressing", FOOD_AND_DRINK, SAUCES),
        Arrays.asList("Hidden Valley", "Ken's", "Newman's Own", "Wish-Bone"));
  }
}
