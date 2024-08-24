package netcraft;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioData.DataType;
import com.jme3.scene.shape.Sphere;

public class Netcraft extends SimpleApplication {

    private Geometry player;
    private AudioNode backgroundMusic;

    public static void main(String[] args) {
        Netcraft app = new Netcraft();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Create a box and a sphere
        createBox();
        createSphere();

        // Set up input handling
        setupInput();

        // Play background music
        playBackgroundMusic();
    }

    private void createBox() {
        Box boxShape = new Box(1, 1, 1);
        Geometry boxGeom = new Geometry("Box", boxShape);

        // Load the texture from the assets directory
        Texture texture = assetManager.loadTexture("Materials/cyberground.jpg");
        
        // Create a new material and set the texture
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setTexture("ColorMap", texture);
        
        // Apply the material to the geometry
        boxGeom.setMaterial(boxMat);

        // Attach the geometry to the root node
        rootNode.attachChild(boxGeom);
    }

    private void createSphere() {
        Sphere sphereShape = new Sphere(32, 32, 1);
        Geometry sphereGeom = new Geometry("Sphere", sphereShape);

        Material sphereMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sphereMat.setColor("Color", ColorRGBA.Red);
        sphereGeom.setMaterial(sphereMat);
        sphereGeom.setLocalTranslation(3, 0, 0);

        rootNode.attachChild(sphereGeom);
    }

    private void setupInput() {
        InputManager inputManager = getInputManager();
        inputManager.addMapping("Move Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Move Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Move Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Move Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(actionListener, "Move Forward", "Move Backward", "Move Left", "Move Right");
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Move Forward") && isPressed) {
                player.move(0, 0, -0.1f);
            } else if (name.equals("Move Backward") && isPressed) {
                player.move(0, 0, 0.1f);
            } else if (name.equals("Move Left") && isPressed) {
                player.move(-0.1f, 0, 0);
            } else if (name.equals("Move Right") && isPressed) {
                player.move(0.1f, 0, 0);
            }
        }
    };

    private void playBackgroundMusic() {
        backgroundMusic = new AudioNode(assetManager, "Sounds/BackgroundMusic.ogg", DataType.Stream);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1);
        rootNode.attachChild(backgroundMusic);
        backgroundMusic.play();
    }

    @Override
    public void simpleUpdate(float tpf) {
        // This method is called every frame and can be used to update game state
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // This method is called during rendering to perform additional rendering tasks
    }
}
