package nl.faanveldhuijsen.blockgame.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import nl.faanveldhuijsen.blockgame.Main;
import nl.faanveldhuijsen.blockgame.world.World;

public class Cube implements RenderableProvider {

    protected final World world = World.getInstance();
    protected Vector3 position;
    private Model model;
    private ModelInstance instance;

    public Cube(Vector3 position) {
        this.position = position;

        TextureAttribute textureAttribute =
                new TextureAttribute(TextureAttribute.Diffuse, Main.assets.get("blocks/dirt.png", Texture.class));
        Material material = new Material(textureAttribute);

        // TODO generate only when all blocks are loaded
        model = generateSingleTextureModel(2F, material);

        instance = new ModelInstance(model);
        instance.transform.translate(position.scl(4F));
    }

    private Model generateSingleTextureModel(float size, Material material) {
        ModelBuilder modelBuilder = new ModelBuilder();

        int attr = Usage.Position | Usage.Normal | Usage.TextureCoordinates;
        modelBuilder.begin();
        MeshPartBuilder mpb = modelBuilder.part("cube", GL20.GL_TRIANGLES, attr, material);

        // North
        if (!hasBlock("north")) {
            mpb.rect(-size, -size, -size, -size, size, -size, size, size, -size, size, -size, -size, 0, 0, -1);
        }

        // South
        if (!hasBlock("south")) {
            mpb.rect(-size, size, size, -size, -size, size, size, -size, size, size, size, size, 0, 0, 1);
        }

        // Bottom
        if (!hasBlock("bottom")) {
            mpb.rect(-size, -size, size, -size, -size, -size, size, -size, -size, size, -size, size, 0, -1, 0);
        }

        // Top
        if (!hasBlock("top")) {
            mpb.rect(-size, size, -size, -size, size, size, size, size, size, size, size, -size, 0, 1, 0);
        }

        // West
        if (!hasBlock("west")) {
            mpb.rect(-size, -size, size, -size, size, size, -size, size, -size, -size, -size, -size, -1, 0, 0);
        }

        // East
        if (!hasBlock("east")) {
            mpb.rect(size, -size, -size, size, size, -size, size, size, size, size, -size, size, 1, 0, 0);
        }

        return modelBuilder.end();
    }

    private boolean hasBlock(String direction) {
        Vector3 position = this.position.cpy();
        switch (direction) {
            case "north":
                position.z -= 1;
                break;
            case "south":
                position.z += 1;
                break;
            case "top":
                position.y += 1;
                break;
            case "bottom":
                position.y -= 1;
                break;
            case "west":
                position.x -= 1;
                break;
            case "east":
                position.x += 1;
                break;
        }
        return world.getBlock(position) != null;
    }

    public Vector3 getPosition() {
        return this.position;
    }

    @Override
    public final void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        instance.getRenderables(renderables, pool);
    }

    public final void dispose() {
        model.dispose();
    }
}
