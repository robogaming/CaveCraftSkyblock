package io.github.robogaming.cavecraftskyblock;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class VoidGen extends ChunkGenerator
{
    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
    {
        return new byte[16][];
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 130 ,0);
    }
}