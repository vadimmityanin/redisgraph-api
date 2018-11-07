package util;

import model.Item;

import java.util.Random;

public class ItemGenerator {

    private static Random random = new Random();

    public static Item generateRandomItem() {
        return new Item(
                String.valueOf(random.nextDouble()),
                String.valueOf(random.nextDouble()),
                String.valueOf(random.nextDouble()),
                String.valueOf(random.nextDouble()),
                String.valueOf(random.nextDouble()),
                random.nextLong(),
                random.nextLong(),
                random.nextLong()
        );
    }
}
