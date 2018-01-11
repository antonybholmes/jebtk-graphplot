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
package org.jebtk.graphplot.icons;

import org.jebtk.modern.graphics.icons.MultiIcon;
import org.jebtk.modern.graphics.icons.Raster16Icon;

// TODO: Auto-generated Javadoc
/**
 * The class FillPatternMultiIcon16.
 */
public class FillPatternMultiIcon16 extends MultiIcon {

  /**
   * Instantiates a new fill pattern multi icon16.
   */
  public FillPatternMultiIcon16() {
    addIcon(new Raster16Icon(new FillPatternSolidIcon()));
    addIcon(new Raster16Icon(new FillPatternCrossIcon()));
    addIcon(new Raster16Icon(new FillPatternBackIcon()));
    addIcon(new Raster16Icon(new FillPatternForwardIcon()));
    addIcon(new Raster16Icon(new FillPatternVertIcon()));
    addIcon(new Raster16Icon(new FillPatternHozIcon()));
  }
}
