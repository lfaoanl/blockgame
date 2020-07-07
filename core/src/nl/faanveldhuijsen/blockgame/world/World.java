package nl.faanveldhuijsen.blockgame.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import nl.faanveldhuijsen.blockgame.render.Cube;

import java.util.ArrayList;

public final class World {

    private static World INSTANCE;
    private String info = "Initial info class";

    public static final int height = 64;

    public ArrayList<ArrayList<Chunk>> chunks = new ArrayList<ArrayList<Chunk>>();

    private World() {
    }

    public static World getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new World();
        }

        return INSTANCE;
    }

    public Cube getBlock(Vector3 position) {
        Chunk chunk = getChunk(getChunkCoordinates(position));
        return chunk.getBlock(position);
    }

    private Vector2 getChunkCoordinates(Vector3 position) {
        int x = (int) Math.floor(position.x / 16);
        int y = (int) Math.floor(position.y / 16);

        return new Vector2(x, y);
    }

    private Chunk getChunk(Vector2 position) {
        return chunks.get((int) position.x).get((int) position.y);
    }

    public void registerBlock(Cube cube, Vector3 position) {
        Vector2 chunkCoords = getChunkCoordinates(position);

        try {
            Chunk chunk = chunks.get((int) chunkCoords.x).get((int) chunkCoords.y);
        } catch(NullPointerException e) {
            Chunk chunk = new Chunk();
        }
        // TODO add block to chunk , above might be shit
    }
}
