package nl.faanveldhuijsen.blockgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class InputHandler {

    PerspectiveCamera camera;

    private final float speed = 0.5F;

    boolean forward = false;
    boolean back = false;
    boolean right = false;
    boolean left = false;
    boolean up = false;
    boolean down = false;

    public InputHandler(PerspectiveCamera camera) {
        this.camera = camera;

        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(0, 0);
    }

    public void setInputProcessor() {
// when WASD keys are pressed, turn booleans on/off
// call this on create()
// you didn't ask for but I also included the relation between mouse and cam
// if you have a camera controller, please delete mouse moved function.
// also don't forget to override other methods.
        Gdx.input.setInputProcessor(new InputProcessor() {
            private int dragX, dragY;

            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case (Keys.W):
                        forward = true;
                        break;
                    case (Keys.A):
                        left = true;
                        break;
                    case (Keys.S):
                        back = true;
                        break;
                    case (Keys.D):
                        right = true;
                        break;
                    case (Keys.SPACE):
                        up = true;
                        break;
                    case (Keys.SHIFT_LEFT):
                        down = true;
                        break;
                    case Keys.ESCAPE:
                        Gdx.app.exit();
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case (Keys.W):
                        forward = false;
                        break;
                    case (Keys.A):
                        left = false;
                        break;
                    case (Keys.S):
                        back = false;
                        break;
                    case (Keys.D):
                        right = false;
                        break;

                    case (Keys.SPACE):
                        up = false;
                        break;
                    case (Keys.SHIFT_LEFT):
                        down = false;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                Vector3 direction = camera.direction.cpy();

                float x = dragX - screenX;
                camera.rotate(Vector3.Y, x / 5.0f);

                float y = dragY - screenY;
                camera.direction.y += Math.sin(y / 180f);

                camera.update();
                dragX = screenX;
                dragY = screenY;
                return true;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }

        });
    }

    // call on render() or anywhere you see fit.
    public void moveCamera() {
        if (forward) {
            Vector3 v = camera.direction.cpy();
            v.scl(speed);
            v.y=0f;
            camera.translate(v);
            camera.update();
        }
        if (back) {
            Vector3 v = camera.direction.cpy();
            v.scl(speed);
            v.y=0f;
            v.x = - v.x;
            v.z = - v.z;
            camera.translate(v);
            camera.update();
        }
        if (left) {
            Vector3 v = camera.direction.cpy();
            v.scl(speed);
            v.y=0f;
            v.rotate(Vector3.Y, 90);
            camera.translate(v);
            camera.update();
        }
        if (right) {
            Vector3 v = camera.direction.cpy();
            v.scl(speed);
            v.y=0f;
            v.rotate(Vector3.Y, -90);
            camera.translate(v);
            camera.update();
        }

        if (up) {
            Vector3 v = new Vector3(0, 1, 0);
            v.scl(speed);
            camera.translate(v);
            camera.update();
        }

        if (down) {
            Vector3 v = new Vector3(0, -1, 0);
            v.scl(speed);
            camera.translate(v);
            camera.update();
        }
    }

}
