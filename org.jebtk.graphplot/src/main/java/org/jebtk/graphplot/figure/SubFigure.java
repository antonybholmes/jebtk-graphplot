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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.jebtk.core.StringId;
import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.colormap.ColorMap;


// TODO: Auto-generated Javadoc
/**
 * A Figure is a collection of axes layered on top of each other. In a 
 * simple plot, there will be typically only be one set of axes.
 * 
 * @author Antony Holmes Holmes
 */
public class SubFigure extends LayoutLayer implements PlotHashProperty { //LayoutLayer

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

	/** The m vert alignment. */
	private FigureVertAlignment mVertAlignment = FigureVertAlignment.TOP;

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
		super(id, new SubFigureBorderLayout());

		// Figures do not have margins
		//getMargins().setMargins(0);

		/*
		mLocations.addCanvasListener(new ModernCanvasListener(){

			@Override
			public void canvasChanged(ChangeEvent e) {
				refresh();
				
				fireCanvasChanged();
			}

			@Override
			public void canvasResized(ChangeEvent e) {
				refresh();
				
				fireCanvasResized();
			}

			@Override
			public void redrawCanvas(ChangeEvent e) {
				fireCanvasRedraw();
			}

			@Override
			public void canvasScrolled(ChangeEvent e) {
				fireCanvasScrolled();
			}});
		*/
	}

	/**
	 * Creates a new set of axes and adds them to the figure. Axes are
	 * automatically layered on top of each other,
	 *
	 * @return the axes
	 */
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
		Axes axes = new Axes2D(mNextAxesId.getNextId());

		putZ(axes, l);

		return axes;
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
	
	//public void putZ(Axes axes, GridLocation l) {
	//	addChild(axes, l);
	//}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getType()
	 */
	@Override
	public LayerType getType() {
		return LayerType.SUBFIGURE;
	}

	/**
	 * Returns the axes with the given id. If the axes does not exist,
	 * it will be created.
	 *
	 * @param id the id
	 * @return the axes
	 */
	public Axes2D getAxes(int id) {
		return getAxes(id, GridLocation.CENTER);
	}

	/**
	 * Gets the axes.
	 *
	 * @param id the id
	 * @param l the l
	 * @return the axes
	 */
	public Axes2D getAxes(int id, GridLocation l) {
		String name = "Axes " + id;

		if (!mLocations.get(l).contains(name)) {
			putZ(new Axes2D(name), l);
		}

		return (Axes2D)mLocations.get(l).getLayer(name);
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

		if (!mLocations.get(l).contains(name)) {
			mLocations.get(l).putZ(new SubFigure(name));
		}

		return (SubFigure)mLocations.get(l).getLayer(name);
	}

	/**
	 * Gets a set of axes by name.
	 *
	 * @param name the name
	 * @return the axes
	 */
	public Axes2D getAxes(String name) {
		return getAxes(name, GridLocation.CENTER);
	}

	/**
	 * Gets the axes.
	 *
	 * @param name the name
	 * @param l the l
	 * @return the axes
	 */
	public Axes2D getAxes(String name, GridLocation l) {
		return (Axes2D)mLocations.get(l).getLayer(name);
	}

	/**
	 * Returns the current axes associated with the figure (i.e. the last
	 * created). If no axes exist, they are automatically recreated.
	 * 
	 * @return	An axes object.
	 */
	public Axes2D getCurrentAxes() {
		return getCurrentAxes(GridLocation.CENTER);
	}

	/**
	 * Gets the current axes.
	 *
	 * @param l the l
	 * @return the current axes
	 */
	public Axes2D getCurrentAxes(GridLocation l) {
		Axes2D p = null;

		for (int z : mLocations.get(l)) {
			MovableLayer layer = mLocations.get(l).getAtZ(z);

			if (layer.getType() == LayerType.AXES) {
				p = (Axes2D)layer;
			}
		}

		// If there is no current layer or the 
		if (p == null) {
			p = (Axes2D)newAxes(l);
		}

		return p;
	}


	/**
	 * Gets the current layer.
	 *
	 * @return the current layer
	 */
	public MovableLayer getCurrentLayer() {
		return getCurrentLayer(GridLocation.CENTER);
	}

	/**
	 * Gets the current layer.
	 *
	 * @param l the l
	 * @return the current layer
	 */
	public MovableLayer getCurrentLayer(GridLocation l) {
		if (mLocations.get(l).getCurrentLayer() == null) {
			newSubFigure(l);
		}

		return mLocations.get(l).getCurrentLayer();
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#hashId()
	 */
	@Override
	public String hashId() {
		return TextUtils.join(TextUtils.COLON_DELIMITER, 
				getCurrentLayer().hashId(),
				getCanvasSize());
	}

	/**
	 * Sets the vert alignment.
	 *
	 * @param alignment the new vert alignment
	 */
	public void setVertAlignment(FigureVertAlignment alignment) {
		mVertAlignment = alignment;

		fireCanvasChanged();
	}

	/**
	 * Gets the vert alignment.
	 *
	 * @return the vert alignment
	 */
	public FigureVertAlignment getVertAlignment() {
		return mVertAlignment;
	}


	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getMargins()
	 */
	//@Override
	//public MarginProperties getMargins() {
	//	return mLayout.getMargins();
	//}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#setMargins(org.graphplot.figure.properties.MarginProperties)
	 */
	//@Override
	//public void setMargins(MarginProperties margins) {
	//	setMargins(this, margins);
	//}


	/**
	 * Plot the figure. The drawing context is used to specify where
	 * the plot is being rendered. This is to allow on screen elements to
	 * be omitted when the plot is saved to a file for example.
	 *
	 * @param g2 the g2
	 * @param context the context
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		/*for (int z : mAxesLayers) {
			Axes axes = mAxesLayers.getAtZ(z);

			if (axes.getVisible()) {
				// Translate canvas to axes position so each axis need only
				// deal with relative coordinates and the figure is in charge
				// of global placement
				Graphics2D g2Temp = (Graphics2D)g2.create();

				g2Temp.translate(axes.getLocation().getX(), axes.getLocation().getY());

				axes.plot(g2Temp, context, this);

				g2Temp.dispose();
			}
		}
		*/
		
		mLayout.plot(g2, context, this);
		
		/*
		Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

		try {
			mLayout.plot(g2Temp, context, this);
		} finally {
			g2Temp.dispose();
		}
		*/
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.Layer#setFont(java.awt.Font, java.awt.Color)
	 */
	@Override
	public void setFont(Font font, Color color) {
		super.setFont(font, color);
		
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				layer.setFont(font, color);
			}
		}
	}
	

	/**
	 * Sets the style.
	 *
	 * @param style the new style
	 */
	public void setStyle(PlotStyle style) {
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				if (layer.getType() == LayerType.AXES) {
					Axes axes = (Axes)layer;
					axes.setStyle(style);	
				}
			}
		}
	}

	/**
	 * Adds the style.
	 *
	 * @param styles the styles
	 */
	public void addStyle(PlotStyle... styles) {
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				if (layer.getType() == LayerType.AXES) {
					Axes axes = (Axes)layer;
					axes.addStyle(styles);		
				}
			}
		}
	}

	/**
	 * Sets the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void setStyle(String name, PlotStyle... styles) {
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				if (layer.getType() == LayerType.AXES) {
					Axes axes = (Axes)layer;
					axes.setStyle(name, styles);		
				}
			}
		}
	}

	/**
	 * Adds the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void addStyle(String name, PlotStyle... styles) {
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				if (layer.getType() == LayerType.AXES) {
					Axes axes = (Axes)layer;
					axes.addStyle(name, styles);		
				}
			}
		}
	}

	/**
	 * Sets the matrix.
	 *
	 * @param m the new matrix
	 */
	public void setMatrix(AnnotationMatrix m) {
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				if (layer.getType() == LayerType.AXES) {
					Axes axes = (Axes)layer;

					axes.setMatrix(m);
				}
			}
		}
	}

	/**
	 * Sets the color map.
	 *
	 * @param colorMap the new color map
	 */
	public void setColorMap(ColorMap colorMap) {
		for (GridLocation l : mLocations) {
			for (int z : mLocations.get(l)) {
				Layer layer = mLocations.get(l).getAtZ(z);

				if (layer.getType() == LayerType.AXES) {
					Axes axes = (Axes)layer;

					axes.setColorMap(colorMap);
				}
			}
		}
	}

	/**
	 * Clear.
	 */
	public void clear() {
		for (GridLocation l : getGridLocations()) {
			clear(l);
		}
	}

	/**
	 * Remove all of the layers at a given location on the figure.
	 *
	 * @param l the l
	 */
	public void clear(GridLocation l) {
		ZModel<MovableLayer> layers = getGridLocations().get(l);

		layers.clear();
	}
}
