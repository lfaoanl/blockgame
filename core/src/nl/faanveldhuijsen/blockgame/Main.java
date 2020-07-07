package nl.faanveldhuijsen.blockgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Vector3;
import nl.faanveldhuijsen.blockgame.render.Cube;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    public static AssetManager assets = new AssetManager();

    PerspectiveCamera cam;
	ModelBatch modelBatch;
    Environment environment;

    ArrayList<Cube> cubes = new ArrayList<Cube>();
    public static InputHandler inputHandler;

    boolean loaded = false;
    boolean initialised = false;

    @Override
	public void create () {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	    modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(40f, 16f * 5, 40f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        assets.load("blocks/dirt.png", Texture.class);

        inputHandler = new InputHandler(cam);
        inputHandler.setInputProcessor();
        Gdx.graphics.isFullscreen();

	}

	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(.65f, .65f, 1, 1);

        if(assets.update() && !loaded) {
            loaded = true;
        }

        if (loaded && !initialised) {
            this.initialised = true;
            this.initialise();
        }

        if (loaded && initialised) {

            inputHandler.moveCamera();

            // Render stuff...
            modelBatch.begin(cam);
            for (Cube cube : cubes) {
                modelBatch.render(cube, environment);
            }
            modelBatch.end();
        }
	}

    private void initialise() {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 16; z++) {
                    cubes.add(new Cube(new Vector3(x, y, z)));
                }
            }
        }
    }

    @Override
	public void dispose () {
        for (Cube cube : cubes) {
            cube.dispose();
        }
	}
}
