package nl.faanveldhuijsen.blockgame.world;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import nl.faanveldhuijsen.blockgame.render.Cube;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

public final class World implements RenderableProvider {

    public static final int height = 64;
    private static World INSTANCE;

    public Map<Integer, Map<Integer, Chunk>> chunks = new TreeMap<>();

    private World() {
    }

    public static World getInstance() {
        if (INSTANCE == null) {
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
        int z = (int) Math.floor(position.z / 16);

        if (x > 0) {
            System.out.println(x);
            System.out.println(position.x);
        }

        return new Vector2(x, z);
    }

    private Chunk getChunk(Vector2 position) {
        int x = (int) position.x;
        int y = (int) position.y;

        if (!chunks.containsKey(x)) {
            chunks.put(x, new TreeMap<Integer, Chunk>());
        }

        if (!chunks.get(x).containsKey(y)) {
            chunks.get(x).put(y, new Chunk());
        }
        return chunks.get(x).get(y);
    }

    public void registerBlock(Cube cube) {
        Vector2 chunkCoordinates = getChunkCoordinates(cube.getPosition());
        Chunk chunk = getChunk(chunkCoordinates);

        chunk.registerBlock(cube, cube.getPosition());
    }

    /**
     * Returns {@link Renderable} instances. Renderables are obtained from the provided {@link Pool} and added to the provided
     * array. The Renderables obtained using {@link Pool#obtain()} will later be put back into the pool, do not store them
     * internally. The resulting array can be rendered via a {@link com.badlogic.gdx.graphics.g3d.ModelBatch}.
     *
     * @param renderables the output array
     * @param pool        the pool to obtain Renderables from
     */
    @Override
    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        for (Map.Entry<Integer, Map<Integer, Chunk>> chunkList : chunks.entrySet()) {
            for (Map.Entry<Integer, Chunk> chunk : chunkList.getValue().entrySet()) {
                // TODO if chunk is loaded or within distance
                chunk.getValue().getRenderables(renderables, pool);
            }
        }
    }

    public void dispose() {
        for (Map.Entry<Integer, Map<Integer, Chunk>> chunkList : chunks.entrySet()) {
            for (Map.Entry<Integer, Chunk> chunk : chunkList.getValue().entrySet()) {
                chunk.getValue().dispose();
            }
        }
    }

    public void initialise() {
        for (Map.Entry<Integer, Map<Integer, Chunk>> chunkList : chunks.entrySet()) {
            for (Map.Entry<Integer, Chunk> chunk : chunkList.getValue().entrySet()) {
                // TODO if chunk is loaded or within distance
                chunk.getValue().update();
            }
        }
    }

}
