package nl.faanveldhuijsen.blockgame.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import nl.faanveldhuijsen.blockgame.Main;

public class Cube implements RenderableProvider {

    private Model model;
    private ModelInstance instance;

    public Cube(Vector3 position) {
        TextureAttribute textureAttribute =
                new TextureAttribute(TextureAttribute.Diffuse, Main.assets.get("blocks/dirt.png", Texture.class));
        Material material = new Material(textureAttribute);

//        model = modelBuilder.createBox(16f, 16f, 16f, material, Usage.Position | Usage.Normal | Usage.TextureCoordinates);


        model = generateSingleTextureModel(8f, material);

        instance = new ModelInstance(model);
        instance.transform.translate(position);
    }

    private Model generateSingleTextureModel(float size, Material material) {
        ModelBuilder modelBuilder = new ModelBuilder();

        int attr = Usage.Position | Usage.Normal | Usage.TextureCoordinates;
        modelBuilder.begin();
        MeshPartBuilder mpb = modelBuilder.part("cube", GL20.GL_TRIANGLES, attr, material);


        mpb.rect(-size,-size,-size, -size, size,-size, size, size,-size, size,-size,-size, 0,0,-1);
        mpb.rect(-size, size, size, -size,-size, size, size,-size, size, size, size, size, 0,0,1);
        mpb.rect(-size,-size, size, -size,-size,-size, size,-size,-size, size,-size, size, 0,-1,0);
        mpb.rect(-size, size,-size, -size, size, size, size, size, size, size, size,-size, 0,1,0);
        mpb.rect(-size,-size, size, -size, size, size,  -size, size,-size, -size,-size,-size, -1,0,0);
        mpb.rect(size,-size,-size, size, size,-size, size, size, size, size,-size, size, 1,0,0);
        return modelBuilder.end();




//        int attr = Usage.Position | Usage.Normal | Usage.TextureCoordinates;
//        modelBuilder.begin();
//        modelBuilder.part("front", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(AssetLoader.tr[0])))
//                .rect(-2f,-2f,-2f, -2f,2f,-2f,  2f,2f,-2, 2f,-2f,-2f, 0,0,-1);
//        modelBuilder.part("back", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(AssetLoader.tr[1])))
//                .rect(-2f,2f,2f, -2f,-2f,2f,  2f,-2f,2f, 2f,2f,2f, 0,0,1);
//        modelBuilder.part("bottom", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(AssetLoader.tr[2])))
//                .rect(-2f,-2f,2f, -2f,-2f,-2f,  2f,-2f,-2f, 2f,-2f,2f, 0,-1,0);
//        modelBuilder.part("top", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(AssetLoader.tr[3])))
//                .rect(-2f,2f,-2f, -2f,2f,2f,  2f,2f,2f, 2f,2f,-2f, 0,1,0);
//        modelBuilder.part("left", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(AssetLoader.tr[4])))
//                .rect(-2f,-2f,2f, -2f,2f,2f,  -2f,2f,-2f, -2f,-2f,-2f, -1,0,0);
//        modelBuilder.part("right", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(AssetLoader.tr[5])))
//                .rect(2f,-2f,-2f, 2f,2f,-2f,  2f,2f,2f, 2f,-2f,2f, 1,0,0);
//        box = modelBuilder.end();
    }

    @Override
    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        instance.getRenderables(renderables, pool);
    }

    public void dispose() {

        model.dispose();
    }
}
