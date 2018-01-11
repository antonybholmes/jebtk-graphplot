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
import java.awt.image.BufferedImage;

import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;

// TODO: Auto-generated Javadoc
/**
 * Plot layers control how a plot is visualized within an axes object.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class PlotLayer extends Layer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /** The m buffered image. */
  private BufferedImage mBufferedImage;

  private static final PlotClip CLIP = new PlotClipRect();

  private PlotClip mPlotClip = CLIP;

  public PlotLayer() {
    // Do nothing
  }

  public PlotLayer(String name) {
    super(name);
  }

  @Override
  public String getType() {
    return "Plot Layer";
  }

  /**
   * Set how the layer should be clipped.
   * 
   * @param plotClip
   */
  public void setClip(PlotClip plotClip) {
    mPlotClip = plotClip;

    setClipMode(true);
  }

  /**
   * Plot.
   *
   * @param g2 the g2
   * @param context the context
   * @param subFigure the sub figure
   * @param axes the axes
   * @param plot the plot
   */
  @Override
  public void plot(Graphics2D g2,
      Dimension offset,
      DrawingContext context,
      Object... params) {
    // System.err.println("plot layer " + getName());

    Figure figure = (Figure) params[0];
    SubFigure subFigure = (SubFigure) params[1];
    Axes axes = (Axes) params[2];
    Plot plot = (Plot) params[3];

    DataFrame m = null;

    if (params.length > 4) {
      m = (DataFrame) params[4];
    } else {
      m = plot.getMatrix();
    }

    plotContext(g2, context, figure, subFigure, axes, plot, m);
  }

  public void plotContext(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m) {
    if (context == DrawingContext.SCREEN) {
      screenPlotLayer(g2, context, figure, subFigure, axes, plot, m);
    } else {
      clipPlotLayer(g2, context, figure, subFigure, axes, plot, m);
    }
  }

  /**
   * Aa plot.
   *
   * @param g2 the g 2
   * @param context the context
   * @param subFigure the sub figure
   * @param axes the axes
   * @param plot the plot
   * @param m the m
   */
  public void screenPlotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m) {

    if (mRasterMode) {
      rasterPlotLayer(g2, context, figure, subFigure, axes, plot, m);
    } else if (mAAMode) {
      aaPlotLayer(g2, context, figure, subFigure, axes, plot, m);
    } else {
      clipPlotLayer(g2, context, figure, subFigure, axes, plot, m);
    }
  }

  public void aaPlotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m) {

    // Anti-alias by default
    Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

    try {
      clipPlotLayer(g2Temp, context, figure, subFigure, axes, plot, m);
    } finally {
      g2Temp.dispose();
    }
  }

  /**
   * Cache plot.
   *
   * @param g2 the g 2
   * @param context the context
   * @param subFigure the sub figure
   * @param axes the axes
   * @param plot the plot
   * @param m the m
   */
  public void rasterPlotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m) {
    // Create an image version of the canvas and draw that to spped
    // up operations
    if (mBufferedImage == null || figure.invalidated()
        || subFigure.invalidated() || axes.invalidated()
        || plot.invalidated()) {
      // The canvas need only be the size of the available display
      mBufferedImage = ImageUtils.createImage(axes.getPreferredSize());

      Graphics2D g2Temp = ImageUtils.createGraphics(mBufferedImage);

      try {
        if (mAAMode) {
          aaPlotLayer(g2Temp, context, figure, subFigure, axes, plot, m);
        } else {
          clipPlotLayer(g2Temp, context, figure, subFigure, axes, plot, m);
        }
      } finally {
        g2Temp.dispose();
      }
    }

    g2.drawImage(mBufferedImage, 0, 0, null);
  }

  public void clipPlotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m) {
    if (mClipMode) {
      Graphics2D g2Temp = mPlotClip
          .clip(g2, context, figure, subFigure, axes, plot);

      try {
        plotLayer(g2Temp, context, figure, subFigure, axes, plot, m);
      } finally {
        g2Temp.dispose();
      }
    } else {
      plotLayer(g2, context, figure, subFigure, axes, plot, m);
    }
  }

  /**
   * Draw plot.
   *
   * @param g2 the g 2
   * @param context the context
   * @param subFigure the sub figure
   * @param axes the axes
   * @param plot the plot
   * @param m the m
   */
  public void plotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m) {
    // Do nothing
  }

  /**
   * Gets the id.
   *
   * @param m the m
   * @param axes the axes
   * @return the id
   */
  protected static String getId(DataFrame m, Axes axes) {
    return TextUtils.join(TextUtils.COLON_DELIMITER,
        m.hashCode(),
        axes.getMargins(),
        axes.getPreferredSize(),
        axes.getX1Axis().getMin(),
        axes.getX1Axis().getMax(),
        axes.getY1Axis().getMin(),
        axes.getY1Axis().getMax());
  }
}
