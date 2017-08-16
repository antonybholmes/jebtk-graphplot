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

import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Renders all plots in a group in order. Since a plot is itself a layered
 * graph, each plot can control the sub z ordering within itself.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class PlotsLayer extends AxesLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The m layout. */
	private PlotLayout mLayout;

	/**
	 * Instantiates a new plots layer.
	 *
	 * @param layout the layout
	 */
	public PlotsLayer(PlotLayout layout) {
		super("Plots");
		
		mLayout = layout;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.AxesLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes)
	 */
	@Override
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure subFigure, 
			Axes axes) {

		mLayout.plot(g2, context, subFigure, axes);
		
		/*
		for (int z : axes.mPlotLayers) {
			Plot plot = axes.mPlotLayers.getAtZ(z);
			
			//System.err.println("plot " + plot.getName());
			
			plot.plot(g2, context, figure, axes);
		}
		*/
	}
}