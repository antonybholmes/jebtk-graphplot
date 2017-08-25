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
import java.util.List;

import org.jebtk.core.StringId;
import org.jebtk.core.stream.ListReduceFunction;
import org.jebtk.core.stream.Stream;
import org.jebtk.graphplot.plotbox.PlotBox;
import org.jebtk.graphplot.plotbox.PlotBoxCompassGridLayout;
import org.jebtk.graphplot.plotbox.PlotBoxCompassGridStorage;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * A Figure is a collection of axes layered on top of each other. In a 
 * simple plot, there will be typically only be one set of axes.
 * 
 * @author Antony Holmes
 */
public class SubFigure extends PlotBoxGraph { //LayoutLayer

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The constant NEXT_ID.
	 */
	private static final StringId NEXT_ID = new StringId("Sub Figure");

	/** The m next axes id. */
	private final StringId mNextAxesId = new StringId("Axes");
	
	private final StringId mNextPlotId = new StringId("Plot");

	/** The m vert alignment. */
	private FigureVertAlignment mVertAlignment = FigureVertAlignment.TOP;

	private Axes mCurrentAxes;

	/**
	 * Instantiates a new figure.
	 */
	public SubFigure() {
		this(NEXT_ID.getNextId());
	}

	/**
	 * Instantiates a new sub figure.
	 *
	 * @param id the id
	 */
	public SubFigure(String id) {
		super(id, new PlotBoxCompassGridStorage(), new PlotBoxCompassGridLayout());
	}
	
	public Axes newAxes() {
		return newAxes(GridLocation.CENTER);
	}

	/**
	 * New axes.
	 *
	 * @param l the l
	 * @return the axes
	 */
	public Axes newAxes(GridLocation l) {
		mCurrentAxes = new Axes(mNextAxesId.getNextId());

		addChild(mCurrentAxes, l);

		return mCurrentAxes;
	}
	
	public Axes getAxes(String name) {
		return getAxes(name, GridLocation.CENTER);
	}

	public Axes getAxes(String name, GridLocation l) {
		PlotBox c = getChild(l);
		
		if (c == null || !c.getName().equals(name)) {
			c = new Axes(name);
			
			putZ(c, l);
		}

		return (Axes)c;
	}
	
	public Axes getCurrentAxes() {
		return getCurrentAxes(GridLocation.CENTER);
	}

	public Axes getCurrentAxes(GridLocation l) {
		if (mCurrentAxes == null) {
			newAxes(l);
		}

		return mCurrentAxes;
	}

	/**
	 * New sub figure.
	 *
	 * @return the sub figure
	 */
	public SubFigure newSubFigure() {
		return newSubFigure(GridLocation.CENTER);
	}

	/**
	 * New sub figure.
	 *
	 * @param l the l
	 * @return the sub figure
	 */
	public SubFigure newSubFigure(GridLocation l) {
		SubFigure axes = new SubFigure(mNextAxesId.getNextId());

		putZ(axes, l);

		return axes;
	}
	
	public Plot newPlot() {
		return newPlot(GridLocation.CENTER);
	}
	
	public Plot newPlot(GridLocation l) {
		Plot plot = new Plot(mNextPlotId.getNextId());

		addChild(plot);

		return plot;
	}
	
	public void addPlot(Plot plot) {
		addPlot(plot, GridLocation.CENTER);
	}
	
	public void addPlot(Plot plot, GridLocation l) {
		addChild(plot, l);
	}
	
	
	
	//public void putZ(Axes axes, GridLocation l) {
	//	addChild(axes, l);
	//}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getType()
	 */
	@Override
	public String getType() {
		return "sub-figure";
	}

	/**
	 * Returns the axes with the given id. If the axes does not exist,
	 * it will be created.
	 *
	 * @param id the id
	 * @return the axes
	 */
	public Axes getAxes(int id) {
		return getAxes(id, GridLocation.CENTER);
	}

	/**
	 * Gets the axes.
	 *
	 * @param id the id
	 * @param l the l
	 * @return the axes
	 */
	public Axes getAxes(int id, GridLocation l) {
		String name = "Axes " + id;

		return getAxes(name, l);
	}

	/**
	 * Gets the sub figure.
	 *
	 * @param id the id
	 * @return the sub figure
	 */
	public SubFigure getSubFigure(int id) {
		return getSubFigure(id, GridLocation.CENTER);
	}

	/**
	 * Gets the sub figure.
	 *
	 * @param id the id
	 * @param l the l
	 * @return the sub figure
	 */
	public SubFigure getSubFigure(int id, GridLocation l) {
		String name = "Sub Figure " + id;

		PlotBox c = getChild(l);
		
		if (c == null || !c.getName().equals(name)) {
			c = new SubFigure(name);
			
			putZ(c, l);
		}

		return (SubFigure)c;
	}


	/*
	public MovableLayer getCurrentLayer() {
		return getCurrentLayer(GridLocation.CENTER);
	}

	public MovableLayer getCurrentLayer(GridLocation l) {
		if (mLocations.getChild(l).getCurrentLayer() == null) {
			newSubFigure(l);
		}

		return mLocations.getChild(l).getCurrentLayer();
	}
	*/


	/**
	 * Sets the vert alignment.
	 *
	 * @param alignment the new vert alignment
	 */
	public void setVertAlignment(FigureVertAlignment alignment) {
		mVertAlignment = alignment;

		fireChanged();
	}

	/**
	 * Gets the vert alignment.
	 *
	 * @return the vert alignment
	 */
	public FigureVertAlignment getVertAlignment() {
		return mVertAlignment;
	}

	public Iterable<Axes> getAllAxes() {
		return Stream.stream(this).reduce(new ListReduceFunction<PlotBox, Axes>() {

			@Override
			public void apply(PlotBox plot, List<Axes> values) {
				if (plot instanceof Axes) {
					values.add((Axes)plot);
				}
			}});
	}

	@Override
	public void plot(Graphics2D g2, 
			Dimension offset,
			DrawingContext context,
			Object... params) {
		Figure figure = (Figure)params[0];
		
		super.plot(g2, offset, context, figure, this);
	}
}
