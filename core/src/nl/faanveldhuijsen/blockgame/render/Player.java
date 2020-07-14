package nl.faanveldhuijsen.blockgame.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Player implements RenderableProvider {


    private final Model model;
    private final ModelInstance instance;

    public Player(Vector3 position) {
        Material material = new Material(new ColorAttribute(ColorAttribute.Ambient, 0.1F, 0.1F, 0.1F, 1F));
        model = generateSingleTextureModel(2F, material);
        instance = new ModelInstance(model);
        instance.transform.translate(position.scl(4F));
    }

    private Model generateSingleTextureModel(float size, Material material) {
        ModelBuilder modelBuilder = new ModelBuilder();

        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
        modelBuilder.begin();
        MeshPartBuilder mpb = modelBuilder.part("player", GL20.GL_TRIANGLES, attr, material);

        mpb.rect(-size, -size, size, -size, -size, -size, size, -size, -size, size, -size, size, 0, -1, 0);
        mpb.rect(-size, size, -size, -size, size, size, size, size, size, size, size, -size, 0, 1, 0);
        mpb.rect(-size, -size, -size, -size, size, -size, size, size, -size, size, -size, -size, 0, 0, -1);
        mpb.rect(-size, size, size, -size, -size, size, size, -size, size, size, size, size, 0, 0, 1);
        mpb.rect(-size, -size, size, -size, size, size, -size, size, -size, -size, -size, -size, -1, 0, 0);
        mpb.rect(size, -size, -size, size, size, -size, size, size, size, size, -size, size, 1, 0, 0);

        return modelBuilder.end();
    }

    @Override
    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        instance.getRenderables(renderables, pool);
    }

    public void dispose() {
        model.dispose();
    }
}
