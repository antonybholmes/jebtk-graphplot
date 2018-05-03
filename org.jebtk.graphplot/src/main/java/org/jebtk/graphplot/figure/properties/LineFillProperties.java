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
package org.jebtk.graphplot.figure.properties;

import java.awt.Color;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;

/**
 * Set the color and stroke of a line on a plot element.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class LineFillProperties extends ChangeListeners
    implements ChangeListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The m fill color.
   */
  private FillProperties mFill = new FillProperties();

  /**
   * The m line style.
   */
  protected LineProperties mLineStyle = new LineProperties();

  /**
   * Instantiates a new exon properties.
   */
  public LineFillProperties() {
    mLineStyle.addChangeListener(this);
    mFill.addChangeListener(this);
  }

  /**
   * Gets the line style.
   *
   * @return the line style
   */
  public LineProperties getLineStyle() {
    return mLineStyle;
  }

  /**
   * Set the line color.
   * 
   * @param color The line color.
   */
  public void setFillColor(Color color) {
    mFill.setColor(color);
  }

  /**
   * Gets the fill color.
   *
   * @return the fill color
   */
  public FillProperties getFillStyle() {
    return mFill;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
   */
  @Override
  public void changed(ChangeEvent e) {
    fireChanged();
  }
}
