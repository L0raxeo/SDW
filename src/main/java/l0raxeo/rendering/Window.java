package l0raxeo.rendering;

import l0raxeo.sdw.input.keyboard.KeyManager;
import l0raxeo.sdw.input.mouse.MouseManager;
import l0raxeo.sdw.scenes.Arena;
import l0raxeo.sdw.scenes.MainMenu;
import l0raxeo.sdw.scenes.Scene;
import l0raxeo.sdw.ui.GuiLayer;
import l0raxeo.network.MultiplayerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Window implements Runnable
{

    private static Window instance;

    private JFrame frame;
    private Canvas canvas;
    public static String APP_TITLE;
    public static int WINDOW_WIDTH, WINDOW_HEIGHT;
    public static Dimension WINDOW_SIZE;
    private Thread thread;
    private boolean running = false;

    private static final List<Scene> scenes = new ArrayList<>();
    private static Scene currentScene = null;
    private Color backdrop;
    private KeyManager keyListener;
    private MouseManager mouseListener;
    private GuiLayer guiLayer;

    private Window()
    {
        APP_TITLE = "Stupid Dopey Warfare";
        WINDOW_WIDTH = 720;
        WINDOW_HEIGHT = 720;
        WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void changeScene(Class<?> sceneClass)
    {
        if (sceneClass.isInstance(Scene.class))
        {
            assert false : "Class '" + sceneClass + "' is not a subclass of Scene";
            return;
        }

        Scene targetScene = null;

        if (currentScene != null && currentScene.getClass().equals(sceneClass))
        {
            assert false : "Cannot change to current scene '" + currentScene + "'";
        }
        else
        {
            boolean sceneExists = false;

            for (Scene s : scenes)
                if (s.getClass().equals(sceneClass))
                {
                    sceneExists = true;
                    targetScene = s;
                    break;
                }

            if (!sceneExists)
            {
                try {
                    targetScene = (Scene) sceneClass.getDeclaredConstructor().newInstance();
                    scenes.add(targetScene);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            //GuiLayer.getInstance().clear();

            if (currentScene != null)
                currentScene.onDestroy();
            currentScene = targetScene;
            currentScene.loadResources();
            currentScene.init();
            currentScene.start();
        }
    }

    public static Scene getScene()
    {
        return currentScene;
    }

    public void run()
    {
        init();
        loop();
        stop();
    }

    private void init()
    {
        // Create JFrame
        frame = new JFrame(APP_TITLE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Create Canvas
        canvas = new Canvas();
        canvas.setPreferredSize(WINDOW_SIZE);
        canvas.setMaximumSize(WINDOW_SIZE);
        canvas.setMinimumSize(WINDOW_SIZE);
        canvas.setFocusable(false);

        // Combine JFrame and Canvas
        frame.add(canvas);
        frame.pack();

        // Create Input Listeners
        keyListener = new KeyManager();
        mouseListener = new MouseManager();

        // Add Input Listeners
        frame.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);

        // GuiLayer
        guiLayer = GuiLayer.getInstance();

        setVisible(true);

        changeScene(Arena.class);
    }

    private void loop()
    {
        long lastTime = System.nanoTime();
        double timePerTick = 1000000000D / 60D;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        while (running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            while (delta >= 1)
            {
                ticks++;
                MultiplayerHandler.localThreadUpdate();
                update(delta);
                /*
                caps the FPS to 60 but reduces lag spikes
                move outside of while loop (next to frames++) to increase FPS
                    increase lag spikes if on older machine
                 */
                render();
                frames++;
                delta -= 1;
            }

            if (System.currentTimeMillis() - lastTimer >= 1000)
            {
                lastTimer += 1000;
                getFrame().setTitle(APP_TITLE + " | TPS: " + ticks + " FPS: " + frames);
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void update(double dt)
    {
        mouseListener.update();
        keyListener.update();
        currentScene.update(dt);
    }

    private void render()
    {
        BufferStrategy bs = getCanvas().getBufferStrategy();

        if (bs == null)
        {
            getCanvas().createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        // clear screen
        g.clearRect(0, 0, getFrame().getWidth(), getFrame().getHeight());
        // render scene here

        g.setColor(backdrop);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        currentScene.render(g);
        guiLayer.render(g);

        // end drawing
        bs.show();
        g.dispose();
    }

    public static Window getInstance()
    {
        if (instance == null)
            instance = new Window();

        return instance;
    }

    public void setVisible(boolean isVisible)
    {
        frame.setVisible(isVisible);
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public static void setBackdrop(Color color)
    {
        getInstance().backdrop = color;
    }

    public synchronized void start()
    {
        running = true;

        thread = new Thread(this, "Rebound" + "_main");
        thread.start();
    }

    public synchronized void stop()
    {
        if (!running) return;
        running = false;

        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
