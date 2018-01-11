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

import org.jebtk.math.Linspace;

// TODO: Auto-generated Javadoc
/**
 * The class MinorTickMarkProperties.
 */
public class MinorTickMarkProperties extends TickMarkProperties {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new minor tick mark properties.
   */
  public MinorTickMarkProperties() {
    setTicks(Linspace.evenlySpaced(0, 1, 0.1));

    setTickSize(2);

    // Don't usually want to show minor labels
    getFontStyle().setVisible(false);
  }

}
