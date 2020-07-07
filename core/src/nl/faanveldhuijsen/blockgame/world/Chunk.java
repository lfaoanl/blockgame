package nl.faanveldhuijsen.blockgame.world;

import com.badlogic.gdx.math.Vector3;
import nl.faanveldhuijsen.blockgame.render.Cube;

public class Chunk {

    public Cube[][][] blocks = new Cube[16][World.height][16];

    public Cube getBlock(Vector3 position) {
        Vector3 chunkPosition = getChunkPosition(position);
        return blocks[(int) chunkPosition.x][(int) chunkPosition.y][(int) chunkPosition.z];
    }

    private Vector3 getChunkPosition(Vector3 position) {
        position = position.cpy();
        position.x = position.x % 16;
        position.y = position.y % 16;
        position.z = position.z % 16;

        return position;
    }
}
