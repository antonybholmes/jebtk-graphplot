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

import org.jebtk.core.geom.IntDim;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * The Class FigureLayout.
 */
public abstract class FigureLayout {
	
	/**
	 * Refresh.
	 *
	 * @param figure the figure
	 */
	public abstract void refresh(Figure figure);
	
	/**
	 * Gets the internal canvas size.
	 *
	 * @param l the l
	 * @return the internal canvas size
	 */
	public abstract IntDim getInternalCanvasSize(Figure l);
	
	/**
	 * Gets the canvas size.
	 *
	 * @param l the l
	 * @return the canvas size
	 */
	public abstract IntDim getCanvasSize(Figure l);

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param figure the figure
	 */
	public void plot(Graphics2D g2, 
			DrawingContext context,
			Figure figure) {
		// In this case we are not interested in the layers function,
		// so we override it.

		//System.err.println("plot");

		//int x = 0;
		//int y = 0;
		//int w;
		//int h;
		//IntRect p; // = 0;

		//IntRect viewRect = figure.getViewRectangle();
		
		//int pX = viewRect.getX() + viewRect.getW();
		//int pY = viewRect.getY() + viewRect.getH();

		
		for (int z : figure.mSubFigureZModel) {
			SubFigure subFigure = figure.mSubFigureZModel.getChild(z);
			
			Graphics2D g2Temp = (Graphics2D)g2.create();

			g2Temp.translate(subFigure.getLocation().getX(), subFigure.getLocation().getY());

			
			//p = figure.mOffsetMap.get(subFigure);
			
			//x = p.getX();
			//y = p.getY();
			//w = p.getW();
			//h = p.getH();

			// outside drawing boundary so skip drawing
			//if (x > pX || 
			//		y > pY || 
			//		x + w < viewRect.getX() || 
			//		y + h < viewRect.getY()) {
			//	continue;
			//}

			//g2Temp.translate(x, y);

			subFigure.plot(g2Temp, context);

			g2Temp.dispose();
		}

		
	}
	
	//
	// Static methods
	//
	
	/**
	 * Max width.
	 *
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int maxWidth(SubFigureZModel layerModel) {
		int w = 0;

		for (int z : layerModel) {
			MovableLayer a = layerModel.getChild(z);

			if (a.getVisible()) {
				w = Math.max(w, a.getCanvasSize().getW());
			}
		}

		return w;
	}
	
	/**
	 * Sum height.
	 *
	 * @param layerModel the layer model
	 * @return the int
	 */
	public static int sumHeight(SubFigureZModel layerModel) {

		int h = 0;

		for (int z : layerModel) {
			MovableLayer a = layerModel.getChild(z);

			if (a.getVisible()) {
				h += a.getCanvasSize().getH();
			}
		}

		return h;
	}
}
