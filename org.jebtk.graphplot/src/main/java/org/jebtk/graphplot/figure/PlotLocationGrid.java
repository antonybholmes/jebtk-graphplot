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

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jebtk.core.StringId;
import org.jebtk.core.collections.DefaultHashMap;
import org.jebtk.core.collections.HashMapCreator;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.geom.IntDim;

// TODO: Auto-generated Javadoc
/**
 * Convert between axes space and screen.
 *  
 * @author Antony Holmes Holmes
 */
public class PlotLocationGrid extends MovableLayer {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	//
	// Conversion from plot to pixels
	//

	/**
	 * The member x diff.
	 * 
	 */
	//protected double mX1Diff;

	//protected double mX2Diff;

	/**
	 * The member x min.
	 */
	//protected double mX1Min;

	//protected double mX2Min;

	/**
	 * The member y1 diff.
	 */
	//protected double mY1Diff;



	/**
	 * The member y1 min.
	 */
	//protected double mY1Min;

	/**
	 * The member y2 diff.
	 */
	//protected double mY2Diff;

	/**
	 * The member y2 min.
	 */
	//protected double mY2Min;

	/**
	 * The member z min.
	 */
	//protected double mZMin;

	/**
	 * The member z diff.
	 */
	//protected double mZDiff;

	/**
	 * The member x offset.
	 */
	protected int mXOffset;

	/**
	 * The member y offset.
	 */
	protected int mYOffset;

	/**
	 * The member z offset.
	 */
	protected int mZOffset;

	/**
	 * The member height.
	 */
	protected int mHeight;

	/**
	 * The member width.
	 */
	protected int mWidth;

	/**
	 * The member depth.
	 */
	protected int mDepth;

	//private Map<Double, Integer> mX1Map = 
	//		new HashMap<Double, Integer>();

	//private Map<Double, Integer> mX2Map = 
	//		new HashMap<Double, Integer>();

	//private Map<Double, Integer> mY1Map = 
	//		new HashMap<Double, Integer>();

	//private Map<Double, Integer> mY2Map = 
	//		new HashMap<Double, Integer>();

	//private Map<Double, Integer> mZMap = 
	//		new HashMap<Double, Integer>();

	//private Map<Double, Double> mX1NormMap = 
	//		new HashMap<Double, Double>();

	//private Map<Double, Double> mX2NormMap = 
	//		new HashMap<Double, Double>();

	//private Map<Double, Double> mY1NormMap = 
	//		new HashMap<Double, Double>();

	//private Map<Double, Double> mY2NormMap = 
	//		new HashMap<Double, Double>();

	//private Map<Double, Double> mZNormMap = 
	//		new HashMap<Double, Double>();

	/** The m x y1 map. */
	protected Map<Double, Map<Double, Point>> mXY1Map =
			DefaultHashMap.create(new HashMapCreator<Double, Point>());

	/** The m x y2 map. */
	protected Map<Double, Map<Double, Point>> mXY2Map =
			DefaultHashMap.create(new HashMapCreator<Double, Point>());

	/** The m layout. */
	//protected PlotLayout mLayout = null;

	/** The Constant NEXT_ID. */
	private static final StringId NEXT_ID = 
			new StringId("Plot Location Grid");

	/** The m next plot id. */
	private final StringId mNextPlotId = new StringId("Plot");

	/**
	 * Instantiates a new plot location grid.
	 */
	public PlotLocationGrid() {
		this(NEXT_ID.getNextId());
	}

	/**
	 * Instantiates a new plot location grid.
	 *
	 * @param id the id
	 */
	public PlotLocationGrid(String id) {
		this(id, new SubFigureBorderLayout()); //new AxesBorderLayout());
	}
	
	/**
	 * Instantiates a new plot location grid.
	 *
	 * @param layout the layout
	 */
	public PlotLocationGrid(PlotLayout layout) {
		this(NEXT_ID.getNextId(), layout);
	}

	/**
	 * Instantiates a new plot location grid.
	 *
	 * @param id the id
	 * @param layout the layout
	 */
	public PlotLocationGrid(String id, PlotLayout layout) {
		super(id); // layout);
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getType()
	 */
	@Override
	public LayerType getType() {
		return LayerType.GRID;
	}

	/**
	 * Creates the new plot.
	 *
	 * @return the plot
	 */
	public Plot createNewPlot() {
		return newPlot(GridLocation.CENTER);
	}

	/**
	 * New plot.
	 *
	 * @param l the l
	 * @return the plot
	 */
	public Plot newPlot(GridLocation l) {
		Plot plot = new Plot(mNextPlotId.getNextId());

		return addPlot(plot, l);
	}

	/**
	 * Adds the plot.
	 *
	 * @param plot the plot
	 * @return the plot
	 */
	public Plot addPlot(Plot plot) {
		return addPlot(plot, GridLocation.CENTER);
	}

	/**
	 * Adds the plot.
	 *
	 * @param plot the plot
	 * @param l the l
	 * @return the plot
	 */
	public Plot addPlot(Plot plot, GridLocation l) {
		//mLocations.get(l).putZ(plot);
		
		addLayer(plot, l);

		return plot;
	}
	
	

	/**
	 * Gets the current plot.
	 *
	 * @return the current plot
	 */
	public Plot getCurrentPlot() {
		return getCurrentPlot(GridLocation.CENTER);
	}

	/**
	 * Gets the current plot.
	 *
	 * @param l the l
	 * @return the current plot
	 */
	public Plot getCurrentPlot(GridLocation l) {
		Plot p = null;
		
		for (int z : mLocations.get(l)) {
			MovableLayer layer = mLocations.get(l).getAtZ(z);
			
			if (layer.getType() == LayerType.PLOT) {
				p = (Plot)layer;
			}
		}
		
		// If there is no current layer or the 
		if (p == null) {
			p = newPlot(l);
		}

		return p;
	}
	
	/**
	 * Returns all of the plots in the center.
	 *
	 * @return the plots
	 */
	public List<Plot> getPlots() {
		return getPlots(GridLocation.CENTER);
	}

	/**
	 * Returns all of the plots at the given location.
	 *
	 * @param l the l
	 * @return the current plot
	 */
	public List<Plot> getPlots(GridLocation l) {
		ZModel<MovableLayer> zmodel = mLocations.get(l);
		
		List<Plot> ret = new ArrayList<Plot>(zmodel.size());
		
		for (int z : zmodel) {
			MovableLayer layer = mLocations.get(l).getAtZ(z);
			
			if (layer.getType() == LayerType.PLOT) {
				ret.add((Plot)layer);
			}
		}
		
		return ret;
	}

	/**
	 * Gets the plot.
	 *
	 * @param name the name
	 * @return the plot
	 */
	public Plot getPlot(String name) {
		return getPlot(name, GridLocation.CENTER);
	}

	/**
	 * Gets the plot.
	 *
	 * @param name the name
	 * @param l the l
	 * @return the plot
	 */
	public Plot getPlot(String name, GridLocation l) {
		return (Plot)mLocations.get(l).getLayer(name);
	}

	/**
	 * Adds the layer.
	 *
	 * @param layer the layer
	 * @return the movable layer
	 */
	public MovableLayer addLayer(MovableLayer layer) {
		return addLayer(layer, GridLocation.CENTER);
	}

	/**
	 * Adds the layer.
	 *
	 * @param layer the layer
	 * @param l the l
	 * @return the movable layer
	 */
	public MovableLayer addLayer(MovableLayer layer, GridLocation l) {
		mLocations.get(l).putZ(layer);

		return layer;
	}
	
	/**
	 * Adds the layer.
	 *
	 * @param layer the layer
	 * @param z the z
	 * @return the movable layer
	 */
	public MovableLayer addLayer(MovableLayer layer, int z) {
		return addLayer(layer, GridLocation.CENTER, z);
	}
	
	/**
	 * Adds the layer.
	 *
	 * @param layer the layer
	 * @param l the l
	 * @param z the z
	 * @return the movable layer
	 */
	public MovableLayer addLayer(MovableLayer layer, GridLocation l, int z) {
		mLocations.get(l).putZ(layer, z);

		return layer;
	}

	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#setInternalPlotSize(org.abh.common.IntDim)
	 */
	@Override
	public boolean setInternalPlotSize(IntDim size) {
		boolean fire = super.setInternalPlotSize(size);

		// We also need to force the layout manager to adjust any surrounding
		// components and adjust the overall size
		if (fire) {
			refresh();
		}
		 
		return fire;
	}
	
	/**
	 * Axes margins are defined by margin fillers.
	 *
	 * @return the margins
	 */
	//@Override
	//public MarginProperties getMargins() {
	//	return mLayout.getMargins();
	//}
	
	/**
	 * Axes margins defined by fillers.
	 *
	 * @param margins the new margins
	 */
	//@Override
	//ublic void setMargins(MarginProperties margins) {
	//	setMargins(this, margins);
    //}
	
	/*
	@Override
	public void plot(Graphics2D g2, 
			DrawingContext context, 
			SubFigure subFigure,
			Axes axes) {
		mLayout.plot(g2, context, subFigure, axes, this);
	}
	*/

	/**
	 * Gets the plot z model.
	 *
	 * @return the plot z model
	 */
	public MovableLayerZModel getPlotZModel() {
		return mLocations;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#canvasChanged(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasChanged(ChangeEvent e) {
		refresh();
		
		super.canvasChanged(e);
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#canvasResized(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void canvasResized(ChangeEvent e) {
		refresh();
		
		super.canvasResized(e);
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		mWidth = getInternalPlotSize().getW();

		mXOffset = 0; //getMargins().getLeft();
		mYOffset = getInternalPlotSize().getH(); //getMargins().getTop() + getInternalPlotSize().getH();

		mZOffset = 0;

		mHeight = getInternalPlotSize().getH();

		mDepth = mHeight;

		clearCaches();
		
		//mLayout.refresh(this);
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#redraw()
	 */
	@Override
	public void redraw() {
		clearCaches();

		super.redraw();
	}

	/**
	 * Clear caches.
	 */
	public void clearCaches() {
		//mX1Map.clear();
		//mX2Map.clear();
		//mY1Map.clear();
		//mY2Map.clear();
		//mZMap.clear();

		//mX1NormMap.clear();
		//mX2NormMap.clear();
		//mY1NormMap.clear();
		//mY2NormMap.clear();
		//mZNormMap.clear();

		mXY1Map.clear();
		mXY2Map.clear();
	}

	/**
	 * Clear.
	 */
	public void clear() {
		//mPlotLayers.clearUnreservedLayers();

		for (GridLocation l : mLocations) {
			clear(l);
		}
	}

	/**
	 * Remove all of the layers at a given location on the figure.
	 *
	 * @param l the l
	 */
	public void clear(GridLocation l) {
		ZModel<MovableLayer> layers = mLocations.get(l);

		layers.clear();
	}


	//
	// Conversion from plot to pixel
	//

	/**
	 * To plot x.
	 *
	 * @param x the x
	 * @return the int
	 */
	/*
	public int toPlotX1(double x) {
		if (!mX1Map.containsKey(x)) {
			mX1Map.put(x, (int)Math.round(mXOffset + plotNormalizeX1(x) * mWidth));
		}

		return mX1Map.get(x);
	}
	 */

	/*
	public int toPlotX2(double x) {
		if (!mX2Map.containsKey(x)) {
			mX2Map.put(x, (int)Math.round(mXOffset + plotNormalizeX2(x) * mWidth));
		}

		return mX2Map.get(x);
	}
	 */

	/**
	 * To plot y1.
	 *
	 * @param y the y
	 * @return the int
	 */
	/*
	public int toPlotY1(double y) {
		if (!mY1Map.containsKey(y)) {
			mY1Map.put(y, (int)Math.round(mYOffset - plotNormalizeY1(y) * mHeight));
		}

		return mY1Map.get(y);
	}
	 */

	/**
	 * To plot y2.
	 *
	 * @param y the y
	 * @return the int
	 */
	/*
	public int toPlotY2(double y) {
		if (!mY2Map.containsKey(y)) {
			mY2Map.put(y, (int)Math.round(mYOffset - plotNormalizeY2(y) * mHeight));
		}

		return mY2Map.get(y);
	}
	 */

	/**
	 * To plot z.
	 *
	 * @param z the z
	 * @return the int
	 */
	/*
	public int toPlotZ(double z) {
		if (!mZMap.containsKey(z)) {
			mZMap.put(z, (int)Math.round(mZOffset - plotNormalizeZ(z) * mDepth));
		}

		return mZMap.get(z);
	}
	 */

	/**
	 * Normalize an x coordinate between (0, 1) inclusive.
	 *
	 * @param x the x
	 * @return the double
	 */
	/*
	public double plotNormalizeX1(double x) {
		if (!mX1NormMap.containsKey(x)) {
			mX1NormMap.put(x, (x - mX1Min) / mX1Diff);
		}

		return mX1NormMap.get(x);
	}
	 */

	/*
	public double plotNormalizeX2(double x) {
		if (!mX2NormMap.containsKey(x)) {
			mX2NormMap.put(x, (x - mX2Min) / mX2Diff);
		}

		return mX2NormMap.get(x);
	}
	 */

	/**
	 * Plot normalize y1.
	 *
	 * @param y the y
	 * @return the double
	 */
	/*
	public double plotNormalizeY1(double y) {
		if (!mY1NormMap.containsKey(y)) {
			mY1NormMap.put(y, (y - mY1Min) / mY1Diff);
		}

		return mY1NormMap.get(y);
	}
	 */

	/**
	 * Plot normalize y2.
	 *
	 * @param y the y
	 * @return the double
	 */
	/*
	public double plotNormalizeY2(double y) {
		if (!mY2NormMap.containsKey(y)) {
			mY2NormMap.put(y, (y - mY2Min) / mY2Diff);
		}

		return mY2NormMap.get(y);

		//double ret = (y - mY2Min) / mY2Diff;

		//return ret;
	}
	 */

	/**
	 * Plot normalize z.
	 *
	 * @param z the z
	 * @return the double
	 */
	/*
	public double plotNormalizeZ(double z) {
		if (!mZNormMap.containsKey(z)) {
			mZNormMap.put(z, (z - mZMin) / mZDiff);
		}

		return mZNormMap.get(z);

		//return (z - mZMin) / mZDiff;
	}
	 */

}
