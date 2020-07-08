package nl.faanveldhuijsen.blockgame.world;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import nl.faanveldhuijsen.blockgame.render.Cube;

public class Chunk implements RenderableProvider {

    public Cube[][][] blocks = new Cube[16][World.height][16];

    public Chunk() {

    }

    public Cube getBlock(Vector3 position) {
        Vector3 chunkPosition = getChunkPosition(position);
        return blocks[(int) chunkPosition.x][(int) chunkPosition.y][(int) chunkPosition.z];
    }

    private Vector3 getChunkPosition(Vector3 position) {
        position = position.cpy();
        position.x = position.x % 16;
        position.y = position.y % 16;
        position.z = position.z % 16;

        if (position.x < 0) {
            position.x = 16 + position.x;
        }
        if (position.y < 0) {
            position.y = World.height + position.y;
        }
        if (position.z < 0) {
            position.z = 16 + position.z;
        }

        return position;
    }

    public void registerBlock(Cube cube, Vector3 position) {
        Vector3 p = getChunkPosition(position);

        blocks[(int) p.x][(int) p.y][(int) p.z] = cube;
    }

    @Override
    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        for (Cube[][] cubeListX : blocks) {
            if (cubeListX == null) {
                continue;
            }
            for (Cube[] cubeListY : cubeListX) {
                if (cubeListY == null) {
                    continue;
                }
                for (Cube cube : cubeListY) {
                    if (cube == null) {
                        continue;
                    }
                    cube.getRenderables(renderables, pool);
                }
            }
        }
    }

    public void dispose() {
        for (Cube[][] cubeListX : blocks) {
            if (cubeListX == null) {
                continue;
            }
            for (Cube[] cubeListY : cubeListX) {
                if (cubeListY == null) {
                    continue;
                }
                for (Cube cube : cubeListY) {
                    if (cube == null) {
                        continue;
                    }
                    cube.dispose();
                }
            }
        }
    }
}
