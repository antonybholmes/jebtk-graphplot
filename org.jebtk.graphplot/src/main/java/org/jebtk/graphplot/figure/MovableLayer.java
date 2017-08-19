/**
 * Copyright 2016 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.graphplot.figure;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.geom.GeomUtils;
import org.jebtk.core.geom.IntDim;
import org.jebtk.core.geom.IntPos2D;
import org.jebtk.core.text.TextUtils;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * A movable layer, unlike a conventional layer, may have an independent
 * dimension and location that is not dependent on its parent. Instead the
 * parent renders according to the child.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class MovableLayer extends Layer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant DEFAULT_SIZE. */
	private static final IntDim DEFAULT_SIZE = GeomUtils.DIM_ZERO;
	
	/** The default width. */
	public static int DEFAULT_WIDTH = 1200;
	
	/** The default height. */
	public static int DEFAULT_HEIGHT = 600;
	
	//
	// Plot Size
	//
	
	/**
	 * The member location.
	 */
	private IntPos2D mLocation = GeomUtils.INT_POINT_ZERO;
	
	/**
	 * The member internal size.
	 */
	protected IntDim mInternalSize = DEFAULT_SIZE;
	
	/**
	 * The member margins.
	 */
	private MarginProperties mMargins = MarginProperties.DEFAULT_MARGIN;
	
	/**
	 * The member axes layer.
	 */
	protected final MovableLayerZModel mLocations = new MovableLayerZModel();

	/**
	 * Instantiates a new plot layer.
	 *
	 * @param name the name
	 */
	public MovableLayer(String name) {
		super(name);
		
		mLocations.addCanvasListener(this);

		// Pass on mouse events to sub plot parts
		//addCanvasMouseListener(mAxesLayers);
		addCanvasMouseListener(mLocations);
		
		/*
		mMargins.addChangeListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent e) {
				change();
			}});
		*/
		
		//change();
	}
	
	/**
	 * Returns the layer model to control what is displayed on the plot
	 * and in what order.
	 *
	 * @return the axes z model
	 */
	public MovableLayerZModel getGridLocations() {
		return mLocations;
	}
	
	/**
	 * Gets the grid location.
	 *
	 * @param l the l
	 * @return the grid location
	 */
	public ZModel<MovableLayer> getGridLocation(GridLocation l) {
		return mLocations.getChild(l);
	}
	
	/**
	 * Adds a layer to the center location.
	 *
	 * @param layer the layer
	 */
	public void putZ(MovableLayer layer) {
		putZ(layer, GridLocation.CENTER);
	}
	
	/**
	 * Equivalent to mLayers.get(l).setZ(layer);
	 *
	 * @param layer the layer
	 * @param l the l
	 */
	public void putZ(MovableLayer layer, GridLocation l) {
		mLocations.getChild(l).putZ(layer);
	}
	
	/**
	 * Put z.
	 *
	 * @param layers the layers
	 */
	public void putZ(Collection<MovableLayer> layers) {
		putZ(layers, GridLocation.CENTER);
	}
	
	/**
	 * Put z.
	 *
	 * @param layers the layers
	 * @param l the l
	 */
	public void putZ(Collection<MovableLayer> layers, GridLocation l) {
		mLocations.getChild(l).putZ(layers);
	}
	
	//
	// Physical space controls
	//
	
	/**
	 * Sets the location.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setLocation(int x, int y) {
		setLocation(new IntPos2D(x, y));
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(IntPos2D location) {
		boolean fire = updateLocation(location);
		
		if (fire) {
			fireCanvasChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Update location.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void updateLocation(int x, int y) {
		updateLocation(new IntPos2D(x, y));
	}
	
	/**
	 * Update the location. Returns true if the location is different to the
	 * current location.
	 *
	 * @param location the location
	 * @return true, if successful
	 */
	public boolean updateLocation(IntPos2D location) {
		if (mLocation == null || 
				location.getX() != mLocation.getX() || 
				location.getY() != mLocation.getY()) {
			mLocation = location;
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public IntPos2D getLocation() {
		return mLocation;
	}
	

	
	
	/**
	 * Set the total width/height of the plot. This will set the internal
	 * plot size by subtracting the appropriate margins from the width and
	 * height supplied.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public void setPlotSize(int w, int h) {
		setInternalPlotSize(w - getMargins().getLeft() - getMargins().getRight(),
				h - getMargins().getTop() - getMargins().getBottom());
	}
	
	/**
	 * Update plot size.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public void updatePlotSize(int w, int h) {
		updateInternalPlotSize(w - getMargins().getLeft() - getMargins().getRight(),
				h - getMargins().getTop() - getMargins().getBottom());
	}
	
	/**
	 * Sets the plot width.
	 *
	 * @param w the new plot width
	 */
	public void setPlotWidth(int w) {
		setInternalPlotSize(w - getMargins().getLeft() - getMargins().getRight(),
				mInternalSize.getH());
	}
	
	/**
	 * Sets the plot height.
	 *
	 * @param h the new plot height
	 */
	public void setPlotHeight(int h) {
		setInternalPlotSize(mInternalSize.getW(),
				h - getMargins().getTop() - getMargins().getBottom());
	}
	
	/**
	 * Update plot width.
	 *
	 * @param w the w
	 */
	public void updatePlotWidth(int w) {
		updateInternalPlotSize(w - getMargins().getLeft() - getMargins().getRight(),
				mInternalSize.getH());
	}
	
	/**
	 * Update plot height.
	 *
	 * @param h the h
	 */
	public void updatePlotHeight(int h) {
		updateInternalPlotSize(mInternalSize.getW(),
				h - getMargins().getTop() - getMargins().getBottom());
	}
	
	/**
	 * Sets the internal plot size.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public void setInternalPlotSize(int w, int h) {
		setInternalPlotSize(new IntDim(w, h));
	}
	
	/**
	 * Update internal plot size.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public void updateInternalPlotSize(int w, int h) {
		updateInternalPlotSize(new IntDim(w, h));
	}
	
	/**
	 * Sets the internal plot height.
	 *
	 * @param h the new internal plot height
	 */
	public void setInternalPlotHeight(int h) {
		setInternalPlotSize(new IntDim(mInternalSize.getW(), h));
	}
	
	/**
	 * Sets the internal plot width.
	 *
	 * @param w the new internal plot width
	 */
	public void setInternalPlotWidth(int w) {
		setInternalPlotSize(new IntDim(w, mInternalSize.getH()));
	}
	
	/**
	 * Gets the internal width.
	 *
	 * @return the internal width
	 */
	public int getInternalWidth() {
		return getInternalPlotSize().getW();
	}
	
	/**
	 * Gets the internal height.
	 *
	 * @return the internal height
	 */
	public int getInternalHeight() {
		return getInternalPlotSize().getH();
	}
	
	/**
	 * Sets the size of the plot excluding the margins.
	 * The actual size of the plot will be this plus the
	 * margins. Use getMargins() to change the plot margins.
	 *
	 * @param size the new internal plot size
	 */
	public void setInternalPlotSize(Dimension size) {
		setInternalPlotSize(new IntDim(size.width, size.height));
	}
	
	/**
	 * Sets the internal plot size.
	 *
	 * @param size the new internal plot size
	 * @return true, if successful
	 */
	public boolean setInternalPlotSize(IntDim size) {
		// Only fire event if canvas has actually changed size
		boolean fire = updateInternalPlotSize(size);
			
		if (fire) {
			fireCanvasResized();
		}
		
		return fire;
	}
	
	/**
	 * Update internal plot size.
	 *
	 * @param size the size
	 * @return true, if successful
	 */
	public boolean updateInternalPlotSize(IntDim size) {
		int w = Math.max(0, size.getW());
		int h = Math.max(0, size.getH());

		/*
		boolean fire = mInternalSize == null ||
				mInternalSize.getW() != w || 
				mInternalSize.getH() != h;
		*/
		
		boolean fire = true;
		
		if (fire) {
			mInternalSize = new IntDim(w, h);
		}
		
		return fire;
	}
	
	/**
	 * Returns the size of the plot exclusive of the margins.
	 * The return dimension object is a copy so modification will
	 * not affect the plot size, instead use setPlotSize() to
	 * adjust the plot dimensions.
	 *
	 * @return the internal plot size
	 */
	public IntDim getInternalPlotSize() {
		return mInternalSize;
	}

	/**
	 * Returns the size of the plot inclusive of the margins.
	 * The return dimension object is a copy so modification will
	 * not affect the plot size, instead use setPlotSize() to
	 * adjust the plot dimensions.
	 *
	 * @return the plot size
	 */
	public IntDim getCanvasSize() {
		return addMargins(mInternalSize, getMargins());
	}
	
	/**
	 * Returns the plot margins (space around the plot).
	 *
	 * @return the margins
	 */
	public MarginProperties getMargins() {
		return mMargins;
	}
	
	/**
	 * Sets the left margin.
	 *
	 * @param margin the new left margin
	 */
	public void setLeftMargin(int margin) {
		setMargins(mMargins.getTop(), 
				margin, 
				mMargins.getBottom(), 
				mMargins.getRight());
	}
	
	/**
	 * Sets the bottom margin.
	 *
	 * @param margin the new bottom margin
	 */
	public void setBottomMargin(int margin) {
		setMargins(mMargins.getTop(), 
				mMargins.getLeft(), 
				margin, 
				mMargins.getRight());
	}
	
	/**
	 * Sets the margins.
	 *
	 * @param s the new margins
	 */
	public void setMargins(int s) {
		setMargins(s, s, s, s);
	}
	
	/**
	 * Sets the margins.
	 *
	 * @param t 	the top margin.
	 * @param l 	the left margin.
	 * @param b 	the bottom margin.
	 * @param r 	the right margin.
	 */
	public void setMargins(int t, int l, int b, int r) {
		setMargins(new MarginProperties(t, l, b, r));
	}
	
	/**
	 * Sets the margins.
	 *
	 * @param margins the new margins
	 */
	public void setMargins(MarginProperties margins) {
		updateMargins(margins);
		
		fireCanvasResized();
	}
	
	/**
	 * Update margins.
	 *
	 * @param margins the margins
	 */
	public void updateMargins(MarginProperties margins) {
		mMargins = margins;
	}
	
	/**
	 * Redraw.
	 */
	public void redraw() {
		fireCanvasRedraw();
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.Layer#getType()
	 */
	@Override
	public String getType() {
		return "moveable";
	}
	
	/**
	 * Hash id.
	 *
	 * @return the string
	 */
	public String hashId() {
		return TextUtils.join(TextUtils.COLON_DELIMITER,
				getId(),
				getMargins(),
				getCanvasSize());
	}
	
	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 */
	public void plot(Graphics2D g2, DrawingContext context) {
		// Do nothing
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param figure the figure
	 */
	public void plot(Graphics2D g2, 
			DrawingContext context, 
			SubFigure figure) {
		// Do nothing
	}
	
	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 */
	public void plot(Graphics2D g2, 
			DrawingContext context, 
			SubFigure figure, 
			Axes axes) {
		// Do nothing
	}
	
	/**
	 * Removes the by name.
	 *
	 * @param name the name
	 */
	public void removeByName(String name) {
		Deque<Layer> stack = new ArrayDeque<Layer>();
		
		stack.push(this);
		
		while (!stack.isEmpty()) {
			Layer layer = stack.pop();
			
			if (layer instanceof MovableLayer) {
				MovableLayer ml = (MovableLayer)layer;
				
				// Remove any layers with the given name
				ml.mLocations.removeByName(name);
				
				// Stack all of the child layers in case we can find what
				// we are looking for there
				for (GridLocation l : ml.mLocations) {
					for (int z : ml.mLocations.getChild(l)) {
						stack.push(ml.mLocations.getChild(l).getChild(z));
					}
				}
			}
		}
	}
	
	/**
	 * Find a plot element by name. The plot hierarchy is searched depth
	 * wise and the first plot element matching the name will be returned.
	 * If no matching element is found, null will be returned.
	 *
	 * @param name the name
	 * @return the movable layer
	 */
	public MovableLayer findByName(String name) {
		String ls = name.toLowerCase();
		
		MovableLayer ret = null;
		
		Deque<MovableLayer> stack = new ArrayDeque<MovableLayer>();
		
		stack.push(this);
		
		while (!stack.isEmpty()) {
			MovableLayer layer = stack.pop();
			
			if (layer.getName().toLowerCase().contains(ls)) {
				ret = layer;
				break;
			}
			
			if (layer instanceof MovableLayer) {
				MovableLayer ml = (MovableLayer)layer;
				
				// Stack all of the child layers in case we can find what
				// we are looking for there
				for (GridLocation l : ml.mLocations) {
					for (int z : ml.mLocations.getChild(l)) {
						stack.push(ml.mLocations.getChild(l).getChild(z));
					}
				}
			}
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#canvasChanged(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasChanged(ChangeEvent e) {
		fireCanvasChanged();
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.LayerCanvasListener#canvasResized(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void canvasResized(ChangeEvent e) {
		fireCanvasResized();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#redrawCanvas(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void redrawCanvas(ChangeEvent e) {
		fireCanvasRedraw();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#canvasScrolled(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasScrolled(ChangeEvent e) {
		fireCanvasScrolled();
	}
	
	//
	// Static methods
	//
	
	/**
	 * Sets the margins.
	 *
	 * @param l the l
	 * @param layerModel the layer model
	 * @return the int
	 */
	//public static void setMargins(MovableLayer layer, 
	//		int t) {
	//	setMargins(layer, t, t, t, t);
	//}
	
	/**
	 * Sets the margins.
	 *
	 * @param layer the layer
	 * @param margins the margins
	 */
	//public static void setMargins(MovableLayer layer, 
	//		MarginProperties margins) {
	//	setMargins(layer, 
	//			margins.getTop(), 
	//			margins.getLeft(), 
	//			margins.getBottom(), 
	//			margins.getRight());
	//}
	
	/**
	 * Sets the margins.
	 *
	 * @param layer the layer
	 * @param t the t
	 * @param l the l
	 * @param b the b
	 * @param r the r
	 */
	/*
	public static void setMargins(MovableLayer layer, 
			int t, 
			int l, 
			int b, 
			int r) {
		layer.getGridLocation(GridLocation.N).clear();
		layer.getGridLocation(GridLocation.W).clear();
		layer.getGridLocation(GridLocation.S).clear();
		layer.getGridLocation(GridLocation.E).clear();
		
		layer.getGridLocation(GridLocation.N).putZ(new MarginFillerV(t));
		layer.getGridLocation(GridLocation.W).putZ(new MarginFillerH(l));
		layer.getGridLocation(GridLocation.S).putZ(new MarginFillerV(b));
		layer.getGridLocation(GridLocation.E).putZ(new MarginFillerH(r));
	}
	*/
	
	/**
	 * Sets the left margin.
	 *
	 * @param layer the layer
	 * @param l the l
	 */
	/*
	public static void setLeftMargin(MovableLayer layer, int l) {
		layer.getGridLocation(GridLocation.W).clear();
		layer.getGridLocation(GridLocation.W).putZ(new MarginFillerH(l));
	}
	
	public static void setBottomMargin(MovableLayer layer, int l) {
		layer.getGridLocation(GridLocation.S).clear();
		layer.getGridLocation(GridLocation.S).putZ(new MarginFillerV(l));
	}
	*/
	
	
	//
	// Static methods
	//

	/**
	 * Max width.
	 *
	 * @param l the l
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int maxWidth(GridLocation l, MovableLayerZModel layerModel) {

		int w = 0;

		ZModel<MovableLayer> m = layerModel.getChild(l);

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			if (a.getVisible()) {
				w = Math.max(w, a.getCanvasSize().getW());
			}
		}

		return w;
	}

	/**
	 * Sum width.
	 *
	 * @param l the l
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int sumWidth(GridLocation l, MovableLayerZModel layerModel) {

		int w = 0;

		ZModel<MovableLayer> m = layerModel.getChild(l);

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			if (a.getVisible()) {
				w += a.getCanvasSize().getW();
			}
		}

		return w;
	}

	/**
	 * Max height.
	 *
	 * @param l the l
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int maxHeight(GridLocation l, MovableLayerZModel layerModel) {

		int h = 0;

		ZModel<MovableLayer> m = layerModel.getChild(l);

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			if (a.getVisible()) {
				h = Math.max(h, a.getCanvasSize().getH());
			}
		}

		return h;
	}

	/**
	 * Sum height.
	 *
	 * @param l the l
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int sumHeight(GridLocation l, MovableLayerZModel layerModel) {

		int h = 0;

		ZModel<MovableLayer> m = layerModel.getChild(l);

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			if (a.getVisible()) {
				h += a.getCanvasSize().getH();
			}
		}

		return h;
	}

	/**
	 * Gets the max center.
	 *
	 * @param layerModel the layer model
	 * @return the max center
	 */
	public static IntDim getMaxCenter(MovableLayerZModel layerModel) {
		return new IntDim(maxWidth(GridLocation.CENTER, layerModel), 
				maxHeight(GridLocation.CENTER, layerModel));
	}
	
	


	/**
	 * Gets the max top margin.
	 *
	 * @param layerModel the layer model
	 * @return the max top margin
	 */
	public static int getMaxTopMargin(MovableLayerZModel layerModel) {
		return Math.max(maxHeight(GridLocation.NW, layerModel), 
				Math.max(maxHeight(GridLocation.N, layerModel), 
						maxHeight(GridLocation.NE, layerModel)));
	}

	/**
	 * Gets the max left margin.
	 *
	 * @param layerModel the layer model
	 * @return the max left margin
	 */
	public static int getMaxLeftMargin(MovableLayerZModel layerModel) {
		return Math.max(maxWidth(GridLocation.NW, layerModel), 
				Math.max(maxWidth(GridLocation.W, layerModel), 
						maxWidth(GridLocation.SW, layerModel)));
	}

	/**
	 * Gets the max bottom margin.
	 *
	 * @param layerModel the layer model
	 * @return the max bottom margin
	 */
	public static int getMaxBottomMargin(MovableLayerZModel layerModel) {
		return Math.max(maxHeight(GridLocation.SW, layerModel), 
				Math.max(maxHeight(GridLocation.S, layerModel), 
						maxHeight(GridLocation.SE, layerModel)));
	}

	/**
	 * Gets the max right margin.
	 *
	 * @param layerModel the layer model
	 * @return the max right margin
	 */
	public static int getMaxRightMargin(MovableLayerZModel layerModel) {
		return Math.max(maxWidth(GridLocation.NE, layerModel), 
				Math.max(maxWidth(GridLocation.E, layerModel), 
						maxWidth(GridLocation.SE, layerModel)));
	}
	
	/**
	 * Max width.
	 *
	 * @param m the layer model
	 * @return the int
	 */
	public static int maxWidth(ZModel<? extends MovableLayer> m) {
		int w = 0;

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			if (a.getVisible()) {
				w = Math.max(w, a.getCanvasSize().getW());
			}
		}

		return w;
	}
	
	/**
	 * Sum height.
	 *
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int sumHeight(ZModel<? extends MovableLayer> layerModel) {

		int h = 0;

		for (int z : layerModel) {
			MovableLayer a = layerModel.getChild(z);

			if (a.getVisible()) {
				h += a.getCanvasSize().getH();
			}
		}

		return h;
	}
	
	/**
	 * Add Margins.
	 *
	 * @param dim the dim
	 * @param margins the margins
	 * @return the int dim
	 */
	public static IntDim addMargins(final IntDim dim, 
			final MarginProperties margins) {
		return new IntDim(dim.getW() + margins.getLeft() + margins.getRight(),
				dim.getH() + margins.getTop() + margins.getBottom());
	}
}
