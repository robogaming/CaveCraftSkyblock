package io.github.robogaming.cavecraftskyblock;

import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

public class VoidWorld extends WorldCreator {

    /**
     * Creates an empty WorldCreationOptions for the given world name
     *
     * @param name Name of the world that will be created
     */
    public VoidWorld(String name) {
        super(name);
        super.generator(new VoidGen());
    }
}
