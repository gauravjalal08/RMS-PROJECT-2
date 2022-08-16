package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import entities.Ingredient;
import entities.Inventory;
import entities.Item;

public class IngredientIO {
    public static Inventory readIngredientList(String filePath) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filePath);

        List<Item> items = new ArrayList<>();
        for(String line: lines) {
            String[] splitLine = line.split(" ");
            // name, quantity, price
            items.add(new Item(splitLine[0], Double.parseDouble(splitLine[1]), Double.parseDouble(splitLine[2])));
        }

        //System.out.println("Read " + lines.size() + "ingredients");
        return new Inventory(items);
    }
}