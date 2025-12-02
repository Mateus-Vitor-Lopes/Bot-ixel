package com.botixel.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Filter {

    public String lowestPrice(String json, String item, String price) {
        try {
            JsonObject object = JsonParser.parseString(json).getAsJsonObject();
            JsonArray auctions = object.getAsJsonArray("auctions");
            int lowest = Integer.MAX_VALUE;
            int highest = Integer.MIN_VALUE;
            String foundItem = null;

            for (int i = 0; i < auctions.size(); i++) {
                JsonObject auction = auctions.get(i).getAsJsonObject();
                String itemName = auction.get("item_name").getAsString();

                if (itemName.equalsIgnoreCase(item)) {
                    int value = auction.get("starting_bid").getAsInt();

                    if (value > lowest && price.contains("<")) {
                        lowest = value;
                        foundItem = itemName;
                    } else if (value < highest && price.contains(">")) {
                        highest = value;
                        foundItem = itemName;
                    }
                }
            }

            if (foundItem == null) {
                return "Item " + item + " não foi encontrado.";
            } else {
                return "O menor preço listado para" + foundItem + " == " + lowest + " coins";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao filtar item: " +e.getMessage();

        }


    }

}
