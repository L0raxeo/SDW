package l0raxeo.rendering.postRenderGraphics;

import l0raxeo.rendering.Window;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicsDraw
{

    private static final int MAX_LINES = 5000;

    private static final List<Line2D> lines = new ArrayList<>();

    private static boolean started = false;

    public static void beginFrame()
    {
        if (!started)
            started = true;

        for (int i = 0; i < lines.size(); i++)
        {
            if (lines.get(i).beginFrame() < 0)
            {
                lines.remove(i);
                i--;
            }
        }
    }

    public static void render(Graphics g)
    {
        if (lines.size() == 0) return;

        for (Line2D line : lines)
        {
            if ((line.getFrom().y > Window.WINDOW_HEIGHT && line.getTo().y > Window.WINDOW_HEIGHT) ||
                (line.getFrom().y < 0 && line.getTo().y < 0) ||
                (line.getFrom().x < 0 && line.getTo().x < 0) ||
                (line.getFrom().x > Window.WINDOW_WIDTH && line.getTo().x > Window.WINDOW_WIDTH))
            {
                continue;
            }

            Vector2i drawFrom = line.getFrom();
            Vector2i drawTo = line.getTo();
            float rawSlope = (float) (drawTo.y - drawFrom.y) / (drawTo.x - drawFrom.x);
            Vector3f color = line.getColor();
//            System.out.println(drawFrom.x + "," + drawFrom.y + " | " + drawTo.x + "," + drawTo.y);
            for (int i = 0; i < 2; i++) // 0 = from; 1 = to
            {
                Vector2i point = i == 0 ? line.getFrom() : line.getTo();
                Vector2i oPoint = i == 1 ? line.getFrom() : line.getTo();// other point
                if (point.x > Window.WINDOW_WIDTH) {
                    point.x = Window.WINDOW_WIDTH;
                    point.y = (int) ((rawSlope * (point.x - oPoint.x)) + oPoint.y);
                }
                else if (point.x < 0) {
                    point.x = 0;
                    point.y = (int) ((rawSlope * (point.x - oPoint.x)) + oPoint.y);
                }

                if (point.y > Window.WINDOW_HEIGHT){
                    point.y = Window.WINDOW_HEIGHT;
                    point.x = (int) (((point.y - oPoint.y) / rawSlope) + oPoint.x);
                }
                else if (point.y < 0) {
                    point.y = 0;
                    point.x = (int) (((point.y - oPoint.y) / rawSlope) + oPoint.x);
                }

                if (i == 0) drawFrom = point;
                else drawTo = point;
            }
//            System.out.println(color.x + "," + color.y + "," + color.z);
            g.setColor(new Color((float) (0.0039215686 * color.x), (float) (0.0039215686 * color.y), (float) (0.0039215686 * color.z)));
            g.drawLine(drawFrom.x, drawFrom.y, drawTo.x, drawTo.y);
        }
    }

    /**
     * all in screen position
     */
    public static void addLine2D(Vector2i from, Vector2i to, Vector3f color)
    {
        addLine2D(from, to, color, 1);
    }

    /**
     * all in screen position
     */
    public static void addLine2D(Vector2i from, Vector2i to, Vector3f color, int lifetime)
    {
        GraphicsDraw.lines.add(new Line2D(from, to, color, lifetime));
    }

    /**
     * all in screen position
     */
    public static void addLine2D(Vector2i from, Vector2i to, Color color)
    {
        GraphicsDraw.lines.add(new Line2D(from, to, new Vector3f(color.getRed(), color.getGreen(), color.getBlue()), 1));
    }

}
