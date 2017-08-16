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
package org.jebtk.graphplot.plotbox;

import java.util.Iterator;

import org.jebtk.core.collections.CollectionUtils;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxPadding extends PlotBox {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private PlotBox mPlotBox;
	
	public PlotBoxPadding(PlotBox plotBox,
			int t,
			int l,
			int b,
			int r) {
		super(new PlotBoxPaddingLayout(t, l, b, r));
		
		mPlotBox = plotBox;
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return CollectionUtils.toList(mPlotBox).iterator();
	}
}
