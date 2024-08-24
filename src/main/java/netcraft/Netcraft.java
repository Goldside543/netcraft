package netcraft;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.ui.Picture;
import com.jme3.ui.Text;
import com.jme3.util.TangentBinormalGenerator;

public class Netcraft extends SimpleApplication {

    private Geometry player;
    private AudioNode backgroundMusic;
    private Node playerNode;
    private Node levelNode;
    private boolean isPaused = false;
    private Text hudText;

    public static void main(String[] args) {
        Netcraft app = new Netcraft();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Initialize player and level nodes
        playerNode = new Node("PlayerNode");
        levelNode = new Node("LevelNode");
        
        // Create game objects
        createBox();
        createSphere();
        createLighting();
    
        // Set up input handling
        setupInput();

        // Play background music
        playBackgroundMusic();

        // Set the camera
        setCamera();
    }

    private void createBox() {
        Box boxShape = new Box(1, 1, 1);
        Geometry boxGeom = new Geometry("Box", boxShape);

        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", ColorRGBA.Blue);
        boxGeom.setMaterial(boxMat);

        levelNode.attachChild(boxGeom);
        rootNode.attachChild(levelNode);
    }

    private void createSphere() {
        Sphere sphereShape = new Sphere(32, 32, 1);
        Geometry sphereGeom = new Geometry("Sphere", sphereShape);

        Material sphereMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sphereMat.setColor("Color", ColorRGBA.Red);
        sphereGeom.setMaterial(sphereMat);
        sphereGeom.setLocalTranslation(3, 0, 0);

        levelNode.attachChild(sphereGeom);
    }

    private void createLighting() {
        // Directional light
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        // Point light
        PointLight pointLight = new PointLight();
        pointLight.setPosition(new Vector3f(2, 2, 2));
        pointLight.setColor(ColorRGBA.Yellow);
        rootNode.addLight(pointLight);
    }

    private void setupInput() {
        InputManager inputManager = getInputManager();
        inputManager.addMapping("Move Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Move Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Move Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Move Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Move Forward", "Move Backward", "Move Left", "Move Right", "Pause");
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Move Forward") && isPressed) {
                playerNode.move(0, 0, -0.1f);
            } else if (name.equals("Move Backward") && isPressed) {
                playerNode.move(0, 0, 0.1f);
            } else if (name.equals("Move Left") && isPressed) {
                playerNode.move(-0.1f, 0, 0);
            } else if (name.equals("Move Right") && isPressed) {
                playerNode.move(0.1f, 0, 0);
            } else if (name.equals("Pause") && isPressed) {
                isPaused = !isPaused;
                if (isPaused) {
                    setPauseOn();
                } else {
                    setPauseOff();
                }
            }
        }
    };

    private void setCamera() {
        cam.setLocation(new Vector3f(0, 5, 10));
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
    }

    private void playBackgroundMusic() {
        backgroundMusic = new AudioNode(assetManager, "Sounds/BackgroundMusic.ogg", DataType.Stream);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1);
        rootNode.attachChild(backgroundMusic);
        backgroundMusic.play();
    }

    private void setPauseOn() {
        getInputManager().setCursorVisible(true);
        System.out.println("Game paused");
    }

    private void setPauseOff() {
        getInputManager().setCursorVisible(false);
        System.out.println("Game resumed");
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (!isPaused) {
            // Update game state here
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // Render additional effects or debug information
    }
}
