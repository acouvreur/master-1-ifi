package fr.unice.creatures;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.lang.Math.toRadians;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SmartCreatureTest {

    Environment environment = mock(Environment.class);
    final double w = 200;
    final double h = 100;

    @Before
    public void setup() {
        when(environment.getSize()).thenReturn(new Dimension((int)w, (int)h));
        when(environment.getWidth()).thenReturn((int)w);
        when(environment.getHeight()).thenReturn((int)h);
    }

    @Test
    public void testEmerginBehavior() throws Exception {
        SmartCreature main = new SmartCreature(environment, 0.0, 0.0, toRadians(0), 5, Color.RED);

        AbstractCreature other = mock(AbstractCreature.class);
        when(other.getDirection()).thenReturn(toRadians(270));
        when(other.getSpeed()).thenReturn(10.0);
        when(other.getPosition()).thenReturn(new Point2D.Double(1, 0));
        when(other.directionFromAPoint(eq(main.getPosition()), eq(main.direction))).thenReturn(0.0);

        ArrayList<AbstractCreature> creaturesAround = new ArrayList<AbstractCreature>();
        creaturesAround.add(other);

        when(environment.creaturesAround(main)).thenReturn(creaturesAround);

        main.act();

        assertEquals(toRadians((270+0)/2), main.getDirection(), .01);
        assertEquals((10.0+5.0)/2, main.getSpeed(), .01);

        verify(other).getDirection();
        verify(other).getSpeed();
    }
}