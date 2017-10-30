package creatures.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Iterator;

import simulator.IObserver;
import visual.Visualizer;
import creatures.ICreature;

@SuppressWarnings("serial")
public class CreatureVisualizer extends Visualizer {

	private CreatureInspector inspector;
	private final CreatureSimulator simulator;
	private boolean debug;

	public CreatureVisualizer(final CreatureSimulator simulator) {
		this.simulator = simulator;

		simulator.add(new IObserver(){

			@Override
			public void simulationCycleCompute() {
				repaint();
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				handleMousePressed(e);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				handleMouseMoved(e);
			}
		});

		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent componentEvent) {
				simulator.setSize(getSize());
			}

			@Override
			public void componentMoved(ComponentEvent componentEvent) {

			}

			@Override
			public void componentShown(ComponentEvent componentEvent) {

			}

			@Override
			public void componentHidden(ComponentEvent componentEvent) {

			}
		});
	}

	protected void handleMouseMoved(MouseEvent event) {
		synchronized (simulator) {
			if (simulator.isRunning()) {
				return;
			}

			if (inspector == null) {
				inspector = new CreatureInspector();
			}

			Point2D point;
			try {
				point = getTransform().inverseTransform(event.getPoint(), null);
			} catch (NoninvertibleTransformException e) {
				throw new RuntimeException(e);
			}

			Iterator<ICreature> nearBy = simulator.creaturesNearByAPoint(point, 10.0).iterator();

			if (!nearBy.hasNext()) {
				inspector.setCreature(null);
				return;
			}

			if (!inspector.isVisible()) {
				inspector.setVisible(true);
			}

			inspector.setCreature(nearBy.next());
		}
	}

	protected void handleMousePressed(MouseEvent e) {
		synchronized (simulator) {
			if (simulator.isRunning()) {
				simulator.stop();
			} else {
				simulator.start();
			}
		}
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
		repaint();
	}

	@Override
	protected void paint(Graphics2D g2) {
		if (debug) {
			paintDebuggingFrame(g2);
		}

		super.paint(g2);
	}

	protected void paintDebuggingFrame(Graphics2D g2) {
		// save color
		Color oldColor = g2.getColor();
		g2.setColor(Color.BLACK);

		// draw coordinates
		g2.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0);
		g2.drawLine(0, -getHeight() / 2, 0, getHeight() / 2);

		// restore color
		g2.setColor(oldColor);
	}

	@Override
	protected Iterable<ICreature> getDrawables() {
		return simulator.getCreatures();
	}
}
