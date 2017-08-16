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

import java.awt.Graphics2D;

import org.jebtk.core.geom.IntDim;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * A Figure is a collection of axes layered on top of each other. In a 
 * simple plot, there will be typically only be one set of axes.
 * 
 * @author Antony Holmes Holmes
 */
public abstract class PlotLayout {

	/**
	 * Determines if sizes etc should be recalculated. To minimize how
	 * often this is done, the layout manager should try to cache sizes
	 * etc and only update when there is a refresh.
	 */
	protected IntDim mDim = IntDim.DIM_ZERO;

	/** The m internal dim. */
	protected IntDim mInternalDim = IntDim.DIM_ZERO;
	

	/**
	 * Should rearrange components and resize the axes.
	 *
	 * @param l the l
	 */
	public void refresh(MovableLayer l) {
		MarginProperties margins = l.getMargins();

		mInternalDim = new IntDim(l.getInternalWidth(), l.getInternalHeight());
		
		mDim = new IntDim(mInternalDim.getW() + margins.getLeft() + margins.getRight(),
				mInternalDim.getH() + margins.getTop() + margins.getBottom());
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param subFigure the sub figure
	 */
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure subFigure) {
		plot(g2, context, subFigure, subFigure.getCurrentAxes());
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 */
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure subFigure, 
			Axes axes) {
		plot(g2, context, subFigure, axes, axes);
	}

	/**
	 * Translation contains the layers to be rendered, axes contains
	 * the axes can can optionally be used to renderer coordinates. In
	 * some cases the axes and translation will be the same.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param layer the layer
	 */
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure subFigure, 
			Axes axes,
			MovableLayer layer) {

		MovableLayerZModel layerModel = layer.mLocations;

		for (GridLocation l : GridLocation.LOCATIONS) {
			plot(g2, context, subFigure, axes, layerModel.get(l));
		}
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param m the m
	 */
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			ZModel<MovableLayer> m) {

		for (int z : m) {
			MovableLayer layer = m.getAtZ(z);

			Graphics2D g2Temp = (Graphics2D)g2.create();

			g2Temp.translate(layer.getLocation().getX(), layer.getLocation().getY());

			//System.err.println("plot layout " + z + " " + layer.getName() + " " + layer.getId() + " " + layer.getType() + " " + layer.getLocation());

			if (layer.getType() == LayerType.AXES) {
				layer.plot(g2Temp, context, subFigure);
			} else {
				layer.plot(g2Temp, context, subFigure, axes);
			}

			g2Temp.dispose();
		}
	}
	
	/**
	 * Gets the internal plot size.
	 *
	 * @param l the l
	 * @return the internal plot size
	 */
	public IntDim getInternalPlotSize(MovableLayer l) {
		return mInternalDim;
	}

	/**
	 * Gets the plot size.
	 *
	 * @param l the l
	 * @return the plot size
	 */
	public IntDim getPlotSize(MovableLayer l) {
		return mDim;
	}

	/*
	@Override
	public void canvasChanged(ChangeEvent e) {
		mInvalidated = true;
	}

	@Override
	public void redrawCanvas(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void canvasScrolled(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void canvasResized(ChangeEvent e) {
		// TODO Auto-generated method stub

	}
	*/

	
	
}
