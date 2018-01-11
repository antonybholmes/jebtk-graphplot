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
import java.awt.geom.GeneralPath;
import java.util.List;

import org.jebtk.core.KeyValuePair;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Sets up a spline plot, caching the path to be drawn if necessary to reduce
 * calculation operations.
 * 
 * @author Antony Holmes Holmes
 */
public abstract class PathsPlotLayer extends UniqueXYLayer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member path.
   */
  private KeyValuePair<UniqueXY, List<GeneralPath>> mPaths = null;

  public PathsPlotLayer(String series) {
    this(series, false);
  }

  /**
   * Instantiates a new path plot layer.
   *
   * @param name the name
   * @param series the series
   * @param zeroEnds the zero ends
   */
  public PathsPlotLayer(String series, boolean zeroEnds) {
    super(series, zeroEnds);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.lib.bioinformatics.plot.figure.UniqueXYLayer#plotLayer(
   * java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext,
   * edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure,
   * edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes,
   * edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot,
   * org.abh.lib.math.matrix.DataFrame,
   * edu.columbia.rdf.lib.bioinformatics.plot.figure.series.XYSeries,
   * edu.columbia.rdf.lib.bioinformatics.plot.figure.UniqueXY)
   */
  @Override
  public final void plotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m,
      XYSeries series,
      UniqueXY xy) {

    updatePath(figure, subFigure, axes, plot, m, series, xy);

    if (mPaths == null) {
      return;
    }

    plotLayer(g2,
        context,
        figure,
        subFigure,
        axes,
        plot,
        m,
        series,
        xy,
        mPaths.getValue());
  }

  /**
   * Plot clipped.
   *
   * @param g2 the g2
   * @param context the context
   * @param figure the figure
   * @param axes the axes
   * @param plot the plot
   * @param m the m
   * @param series the series
   * @param xy the xy
   * @param path the path
   */
  public abstract void plotLayer(Graphics2D g2,
      DrawingContext context,
      Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m,
      XYSeries series,
      UniqueXY xy,
      List<GeneralPath> path);

  /**
   * Update path.
   *
   * @param figure the figure
   * @param axes the axes
   * @param plot the plot
   * @param m the m
   * @param series the series
   * @param xy the xy
   */
  private void updatePath(Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m,
      XYSeries series,
      UniqueXY xy) {
    if (mPaths == null || !mPaths.getKey().equals(xy)) {
      mPaths = new KeyValuePair<UniqueXY, List<GeneralPath>>(xy,
          getPaths(figure, subFigure, axes, plot, m, series, xy));
    }
  }

  /**
   * Gets the path.
   *
   * @param figure the figure
   * @param axes the axes
   * @param plot the plot
   * @param m the m
   * @param series the series
   * @param xy the xy
   * @return the path
   */
  protected abstract List<GeneralPath> getPaths(Figure figure,
      SubFigure subFigure,
      Axes axes,
      Plot plot,
      DataFrame m,
      XYSeries series,
      UniqueXY xy);
}
