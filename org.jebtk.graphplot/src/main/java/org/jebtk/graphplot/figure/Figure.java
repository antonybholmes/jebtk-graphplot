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
import java.util.HashMap;
import java.util.Map;

import org.jebtk.core.StringId;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.geom.IntDim;
import org.jebtk.core.geom.IntPos2D;
import org.jebtk.core.geom.IntRect;
import org.jebtk.graphplot.ModernPlotCanvas;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.modern.graphics.CanvasMouseEvent;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ModernCanvasListener;
import org.jebtk.modern.graphics.ModernCanvasMouseListener;
import org.jebtk.modern.graphics.colormap.ColorMap;

// TODO: Auto-generated Javadoc
/**
 * Allows multiple plots to be placed on a grid and drawn together.
 *
 * @author Antony Holmes Holmes
 */
public class Figure extends ModernPlotCanvas implements ZLayer {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant NEXT_ID. */
	private static final StringId NEXT_ID = new StringId("Figure");

	/**
	 * The member sub figures.
	 */
	protected SubFigureZModel mSubFigureZModel = new SubFigureZModel();

	/**
	 * The member offset map.
	 */
	protected Map<SubFigure, IntRect> mOffsetMap =
			new HashMap<SubFigure, IntRect>();

	/** The m name. */
	private String mName;

	/** The m next sub figure id. */
	private final StringId mNextSubFigureId = new StringId("Sub Figure");

	/** The m layout. */
	private FigureLayout mLayout = new FigureLayoutGrid(1, 1);

	/** The m figure size. */
	private IntDim mFigureSize = IntDim.DIM_ZERO;

	/** The m figure internal size. */
	private IntDim mFigureInternalSize = IntDim.DIM_ZERO;

	/** The m margins. */
	private MarginProperties mMargins = MarginProperties.DEFAULT_MARGIN;


	/**
	 * The class MouseEvents.
	 */
	private class MouseEvents implements ModernCanvasMouseListener {

		/**
		 * The member figure.
		 */
		private SubFigure mFigure;

		/**
		 * Gets the figure.
		 *
		 * @param e the e
		 * @return the figure
		 */
		private SubFigure getFigure(CanvasMouseEvent e) {

			for (int z : mSubFigureZModel) {
				SubFigure figure = mSubFigureZModel.getAtZ(z);

				IntRect p = mOffsetMap.get(figure);

				if (p != null && p.contains(e.getScaledPos())) {
					return figure;
				}
			}

			return null;
		}

		/**
		 * Creates the canvas mouse event.
		 *
		 * @param figure the figure
		 * @param e the e
		 * @return the canvas mouse event
		 */
		private CanvasMouseEvent createCanvasMouseEvent(SubFigure figure,
				CanvasMouseEvent e) {
			IntRect dim = mOffsetMap.get(figure);

			// create a point in the sub figure space
			IntPos2D p = new IntPos2D(e.getScaledPos().getX() - dim.getX(), 
					e.getScaledPos().getY() - dim.getY());

			//System.err.println("adjust " + dim.getY() + " " + e.getY());

			return new CanvasMouseEvent(e, p);
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMousePressed(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMousePressed(CanvasMouseEvent e) {
			SubFigure figure = getFigure(e);

			if (figure != null) {
				figure.fireCanvasMousePressed(createCanvasMouseEvent(figure, e));
			}
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMouseClicked(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMouseClicked(CanvasMouseEvent e) {
			SubFigure figure = getFigure(e);

			if (figure != null) {
				figure.fireCanvasMouseClicked(createCanvasMouseEvent(figure, e));
			}
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMouseEntered(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMouseEntered(CanvasMouseEvent e) {
			// Do nothing
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMouseExited(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMouseExited(CanvasMouseEvent e) {
			if (mFigure != null) {
				mFigure.fireCanvasMouseExited(createCanvasMouseEvent(mFigure, e));

				mFigure = null;
			}
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMouseReleased(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMouseReleased(CanvasMouseEvent e) {
			SubFigure figure = getFigure(e);

			if (figure != null) {
				figure.fireCanvasMouseReleased(createCanvasMouseEvent(figure, e));
			}

		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMouseDragged(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMouseDragged(CanvasMouseEvent e) {
			SubFigure figure = getFigure(e);

			if (figure != null) {
				figure.fireCanvasMouseDragged(createCanvasMouseEvent(figure, e));
			}
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.ui.graphics.ModernCanvasMouseListener#canvasMouseMoved(org.abh.common.ui.ui.graphics.CanvasMouseEvent)
		 */
		@Override
		public void canvasMouseMoved(CanvasMouseEvent e) {
			SubFigure figure = getFigure(e);

			if (figure != null) {
				CanvasMouseEvent ne = createCanvasMouseEvent(figure, e);

				// if we are already in another figure, send an exit event
				// to that, an enter event to the new figure and set the
				// working figure to the new one
				if (mFigure != null) {
					if (!mFigure.equals(figure)) {
						mFigure.fireCanvasMouseExited(createCanvasMouseEvent(mFigure, e));

						mFigure = figure;

						mFigure.fireCanvasMouseEntered(ne);
					}
				} else {
					// We have entered with the mouse move so send an enter
					// event
					mFigure = figure;

					mFigure.fireCanvasMouseEntered(ne);
				}

				figure.fireCanvasMouseMoved(ne);
			} else {
				if (mFigure != null) {
					mFigure.fireCanvasMouseExited(createCanvasMouseEvent(mFigure, e));

					mFigure = null;
				}
			}
		}
	}

	/**
	 * The Class CanvasEvents.
	 */
	private class CanvasEvents implements ModernCanvasListener {

		/* (non-Javadoc)
		 * @see org.abh.common.ui.graphics.ModernCanvasListener#canvasChanged(org.abh.common.event.ChangeEvent)
		 */
		@Override
		public void canvasChanged(ChangeEvent e) {
			refresh();

			//fireCanvasChanged();
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.graphics.ModernCanvasListener#redrawCanvas(org.abh.common.event.ChangeEvent)
		 */
		@Override
		public void redrawCanvas(ChangeEvent e) {
			fireCanvasRedraw();
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.graphics.ModernCanvasListener#canvasScrolled(org.abh.common.event.ChangeEvent)
		 */
		@Override
		public void canvasScrolled(ChangeEvent e) {
			fireCanvasScrolled();
		}

		/* (non-Javadoc)
		 * @see org.abh.common.ui.graphics.ModernCanvasListener#canvasResized(org.abh.common.event.ChangeEvent)
		 */
		@Override
		public void canvasResized(ChangeEvent e) {
			refresh();

			//fireCanvasResized();
		}
	}

	/**
	 * Instantiates a new figure.
	 */
	public Figure() {
		this(NEXT_ID.getNextId());
	}

	/**
	 * Instantiates a new figure grid canvas.
	 *
	 * @param name the name
	 */
	public Figure(String name) {
		mName = name;

		addCanvasMouseListener(new MouseEvents());

		mSubFigureZModel.addCanvasListener(new CanvasEvents());
	}


	/**
	 * Instantiates a new figure.
	 *
	 * @param figure the figure
	 */
	public Figure(SubFigure figure) {
		this();

		addSubFigure(figure);
	}

	/**
	 * Instantiates a new figure.
	 *
	 * @param layout the layout
	 */
	public Figure(FigureLayout layout) {
		this();

		setLayout(layout);
	}
	
	/**
	 * Instantiates a new figure.
	 *
	 * @param columns the columns
	 */
	public Figure(int columns) {
		this(new FigureLayoutColumns(columns));
	}
	
	/**
	 * Instantiates a new figure.
	 *
	 * @param rows the rows
	 * @param columns the columns
	 */
	public Figure(int rows, int columns) {
		this(new FigureLayoutGrid(rows, columns));
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.ModernCanvas#getName()
	 */
	@Override
	public String getName() {
		return mName;
	}

	/**
	 * Creates the new axes.
	 *
	 * @return the axes
	 */
	public SubFigure newSubFigure() {
		SubFigure figure = new SubFigure(mNextSubFigureId.getNextId());

		return addSubFigure(figure);
	}


	/**
	 * Adds the sub figure.
	 *
	 * @param figure the figure
	 * @return the sub figure
	 */
	public SubFigure addSubFigure(SubFigure figure) {
		mSubFigureZModel.putZ(figure);

		return figure;
	}


	/**
	 * Gets the axes.
	 *
	 * @param name the name
	 * @return the axes
	 */
	public SubFigure getSubFigure(String name) {
		return mSubFigureZModel.getLayer(name);
	}

	/**
	 * Returns the current axes associated with the figure (i.e. the last
	 * created). If no axes exist, they are automatically recreated.
	 * 
	 * @return	An axes object.
	 */
	public SubFigure getCurrentSubFigure() {
		if (mSubFigureZModel.getCurrentLayer() == null) {
			newSubFigure();
		}

		return mSubFigureZModel.getCurrentLayer();
	}

	/**
	 * Returns the layer model to control what is displayed on the plot
	 * and in what order.
	 *
	 * @return the axes z model
	 */
	public SubFigureZModel getSubFigureZModel() {
		return mSubFigureZModel;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		/*
		int x = 0;
		int y = 0;
		int w;
		int h;
		IntRect p; // = 0;

		int pX = mViewRect.getX() + mViewRect.getW();
		int pY = mViewRect.getY() + mViewRect.getH();

		Graphics2D g2Temp = (Graphics2D)g2.create();

		g2Temp.translate(getInsets().left, getInsets().top);

		for (int z : mSubFigureZModel) {
			SubFigure subFigure = mSubFigureZModel.getAtZ(z);

			p = mOffsetMap.get(subFigure);

			x = p.getX();
			y = p.getY();
			w = p.getW();
			h = p.getH();

			// outside drawing boundary so skip drawing
			if (x > pX || 
					y > pY || 
					x + w < mViewRect.getX() || 
					y + h < mViewRect.getY()) {
				continue;
			}

			g2Temp.translate(x, y);

			subFigure.plot(g2Temp, context);

			g2Temp.translate(-x, -y);
		}

		g2Temp.dispose();
		 */



		mLayout.plot(g2, context, this);
	}

	/**
	 * Sets the style.
	 *
	 * @param style the new style
	 */
	public void setStyle(PlotStyle style) {
		for (int z : mSubFigureZModel) {
			mSubFigureZModel.getAtZ(z).setStyle(style);	
		}
	}

	/**
	 * Adds the style.
	 *
	 * @param styles the styles
	 */
	public void addStyle(PlotStyle... styles) {
		for (int z : mSubFigureZModel) {
			mSubFigureZModel.getAtZ(z).addStyle(styles);		
		}
	}

	/**
	 * Sets the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void setStyle(String name, PlotStyle... styles) {
		for (int z : mSubFigureZModel) {
			mSubFigureZModel.getAtZ(z).setStyle(name, styles);		
		}
	}

	/**
	 * Adds the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void addStyle(String name, PlotStyle... styles) {
		for (int z : mSubFigureZModel) {
			mSubFigureZModel.getAtZ(z).addStyle(name, styles);		
		}
	}

	/**
	 * Set the color map for all figures.
	 *
	 * @param colorMap the new color map
	 */
	public void setColorMap(ColorMap colorMap) {
		for (int z : getSubFigureZModel()) {
			SubFigure subFigure = getSubFigureZModel().getAtZ(z);

			subFigure.setColorMap(colorMap);
		}
	}

	/**
	 * Sets the layout.
	 *
	 * @param layout the new layout
	 */
	public void setLayout(FigureLayout layout) {
		mLayout = layout;

		refresh();
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.ModernCanvas#setFont(java.awt.Font)
	 */
	@Override
	public void setFont(Font font) {
		super.setFont(font);

		setFont(font, Color.BLACK);
	}
	
	/**
	 * Sets the font.
	 *
	 * @param font the font
	 * @param color the color
	 */
	public void setFont(Font font, Color color) {
		if (mSubFigureZModel != null) {
			for (int z :mSubFigureZModel) {
				SubFigure subFigure = getSubFigureZModel().getAtZ(z);

				subFigure.setFont(font, color);
			}
		}
		
		fireCanvasRedraw();
	}

	/**
	 * Refresh.
	 */
	public void refresh() {
		mLayout.refresh(this);
		
		mFigureInternalSize = mLayout.getCanvasSize(this);

		mFigureSize = MovableLayer.addMargins(mFigureInternalSize, getMargins());
		
		setCanvasSize(mFigureSize);
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

	/* (non-Javadoc)
	 * @see org.graphplot.figure.ZLayer#getType()
	 */
	@Override
	public LayerType getType() {
		return LayerType.FIGURE;
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.ModernCanvas#getIntCanvasSize()
	 */
	@Override
	public IntDim getIntCanvasSize() {
		return mFigureInternalSize;
	}
}
