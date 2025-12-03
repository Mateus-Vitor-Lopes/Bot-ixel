package com.botixel.utils;

import com.botixel.api.ApiAcess;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Filter {

    public String lowestPrice(String item, String price, ApiAcess apiAcess) {
        try {
            long lowest = Long.MAX_VALUE;
            long highest = Long.MIN_VALUE;
            String jsonFirstPage = apiAcess.encontrarItem(0);
            JsonObject object = JsonParser.parseString(jsonFirstPage).getAsJsonObject();
            String foundItem = null;
            int totalPages = object.get("totalPages").getAsInt();

            long start = System.currentTimeMillis();

            for (int page = 0; page < totalPages; page++) {

                String json = (page == 0) ? jsonFirstPage : apiAcess.encontrarItem(page);
                JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                JsonArray auctions = obj.getAsJsonArray("auctions");

                for (JsonElement element : auctions) {
                    JsonObject auction = element.getAsJsonObject();
                    String itemName = auction.get("item_name").getAsString();

                    if (itemName.toLowerCase().contains(item.toLowerCase())) {
                        long value = auction.get("starting_bid").getAsLong();

                        if (price.contains("<") && value < lowest) {
                            lowest = value;
                            foundItem = itemName;

                        } else if (price.contains(">") && value > highest) {
                            highest = value;
                            foundItem = itemName;
                        }
                    }
                }
            }
            long fim = System.currentTimeMillis();
            long total = fim - start;
            if (foundItem == null) {
                return "Item " + item + " não foi encontrado.";
            } else if (price.equals("<")) {
                return "O menor preço listado para " + foundItem + " == " + lowest + " coins " + total;
            } else {
                return "O maior preço listado para " + foundItem + " == " + highest + " coins " + total;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao filtar item: " + e.getMessage();

        }

    }
}


