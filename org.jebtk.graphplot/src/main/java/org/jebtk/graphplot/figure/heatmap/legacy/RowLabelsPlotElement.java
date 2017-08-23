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
package org.jebtk.graphplot.figure.heatmap.legacy;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jebtk.core.Mathematics;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.geom.IntDim;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.core.text.Formatter;
import org.jebtk.core.text.Join;
import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.matrix.Matrix;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.widget.ModernWidget;


// TODO: Auto-generated Javadoc
/**
 * The class RowLabelsPlotElement.
 */
public class RowLabelsPlotElement extends RowMatrixPlotElement {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	//private static String[][] mAnnotations;

	/**
	 * The member labels.
	 */
	//private List<String> mLabels;

	/**
	 * The member title.
	 */
	//private String mTitle = null;

	/**
	 * The member properties.
	 */
	private RowLabelProperties mProperties;

	/** The m labels. */
	private String[][] mLabels;

	/** The m widths. */
	private int[] mWidths;

	/** The m char width. */
	private int mCharWidth;

	/** The m titles. */
	private String[] mTitles;

	private List<String> mTypes;

	/** The Constant FIELD_GAP. */
	// Pixel gap between fields
	private static final int FIELD_GAP = 
			SettingsService.getInstance().getAsInt("graphplot.plot.field-gap");


	/**
	 * Instantiates a new row labels plot element.
	 *
	 * @param matrix the matrix
	 * @param properties the properties
	 * @param aspectRatio the aspect ratio
	 * @param charWidth the char width
	 */
	public RowLabelsPlotElement(AnnotationMatrix matrix,
			RowLabelProperties properties,
			IntDim aspectRatio,
			int charWidth) {
		super(matrix, -1, aspectRatio);

		//mTitle = title;
		mProperties = properties;
		mCharWidth = charWidth;

		//List<String> labels = new ArrayList<String>(matrix.getRowCount());

		String[][] labels = new String[matrix.getRowCount()][properties.showAnnotations.getVisibleCount()];
		
		List<String> types = CollectionUtils.replicate("string", 
				properties.showAnnotations.getVisibleCount());
		
		List<String> names = matrix.getRowAnnotationNames();
		//List<String> lnames = TextUtils.toLowerCase(names);
		
		Map<String, String> lnames = TextUtils.toLowerCaseMap(names);
		
		for (int r = 0; r < matrix.getRowCount(); ++r) {
			int c = 0;
			
			for (int i = 0; i < names.size(); ++i) {
				String name = names.get(i);
				
				if (properties.showAnnotations.isVisible(name)) {

					double v = matrix.getRowAnnotationValue(name, r);

					
					if (Matrix.isValidMatrixNum(v)) {
						//System.err.println("i " + i + " " + name + " " + r + " " + v + " " + matrix.getRowAnnotationText(name, r));
						
						types.set(c, "number");
						
						if (Mathematics.isInt(v)) {
							//System.err.println("i " + i + " " + name + " " + r + " " + v + " " + matrix.getRowAnnotationText(name, r));
							
							String ln = lnames.get(name);
							
							int vi = (int)v;
							
							if (ln.contains("entrez") || ln.contains("id")) {
								// items that appear to be ids, should not
								// be formatted with commas or periods.
								labels[r][c] = Integer.toString(vi);
							} else {
								labels[r][c] = Formatter.number().format(vi);
							}
						} else {
							labels[r][c] = Formatter.number().format(v);
						}
					} else {
						labels[r][c] = matrix.getRowAnnotationText(name, r);
					}
					
					++c;
				}
			}
			
			
			
			/*
			List<String> annotations = new ArrayList<String>();

			if (properties.showAnnotations.get(matrix.getRowAnnotationNames().get(0))) {
				text.append(matrix.getRowName(r));
			}



			for (int i = 1; i < matrix.getRowAnnotationNames().size(); ++i) {
				String name = matrix.getRowAnnotationNames().get(i);

				if (properties.showAnnotations.get(name)) {
					extra.add(matrix.getRowAnnotationText(name, i));
				}
			}

			if (extra.size() > 0) {
				text.append(" (").append(TextUtils.commaJoin(extra)).append(")");
			}
			 */

			/*
			for (int i = 0; i < matrix.getRowAnnotationNames().size(); ++i) {
				String name = matrix.getRowAnnotationNames().get(i);

				if (properties.showAnnotations != null && 
						properties.showAnnotations.containsKey(name) && 
						properties.showAnnotations.get(name)) {

					double v = matrix.getRowAnnotationValue(name, r);

					if (Matrix.isValidMatrixNum(v)) {
						annotations.add(Formatter.number().format(v));
					} else {
						annotations.add(matrix.getRowAnnotationText(name, r));
					}
				}
			}

			labels.add(Join.on(", ").values(annotations).toString());
			 */

			//labels.add(getLabel(matrix, r, properties));
		}
		
		setLabels(labels);
		
		mTypes = types;
	}
	
	/**
	 * Sets the labels.
	 *
	 * @param labels the new labels
	 */
	public void setLabels(String[][] labels) {
		mLabels = labels;
		
		mTitles = new String[mProperties.showAnnotations.getVisibleCount()];
		
		int c = 0;
		
		for (int i = 0; i < mMatrix.getRowAnnotationNames().size(); ++i) {
			String name = mMatrix.getRowAnnotationNames().get(i);

			if (mProperties.showAnnotations.isVisible(name)) {
				mTitles[c] = name;
				
				++c;
			}
		}
		
		// Find the max width of each column
		mWidths = Mathematics.zerosIntArray(mLabels[0].length);
		
		for (int i = 0; i < mWidths.length; ++i) {
			mWidths[i] = mTitles[i].length();
			
			for (int j = 0; j < mLabels.length; ++j) {
				mWidths[i] = Math.max(mWidths[i], mLabels[j][i].length());
			}
		}
		
		int width = 0;
		
		for (int w : mWidths) {
			width += w;
		}
		
		width += FIELD_GAP * (mWidths.length -1);

		setWidth(mCharWidth * width);
	}

	/**
	 * Gets the label.
	 *
	 * @param matrix the matrix
	 * @param row the row
	 * @param properties the properties
	 * @return the label
	 */
	public static String getLabel(AnnotationMatrix matrix,
			int row,
			RowLabelProperties properties) {
		List<String> annotations = new ArrayList<String>();
		
		for (int i = 0; i < matrix.getRowAnnotationNames().size(); ++i) {
			String name = matrix.getRowAnnotationNames().get(i);

			if (properties.showAnnotations.isVisible(name)) {

				double v = matrix.getRowAnnotationValue(name, row);

				if (Matrix.isValidMatrixNum(v)) {
					annotations.add(Formatter.number().format(v));
				} else {
					annotations.add(matrix.getRowAnnotationText(name, row));
				}
			}
		}

		return Join.on(", ").values(annotations).toString();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, Dimension offset, DrawingContext context, Object... params) {
		drawLabels(g2);
		
		super.plot(g2, offset, context, params);
	}

	/**
	 * Draw labels.
	 *
	 * @param g2 the g2
	 */
	private void drawLabels(Graphics2D g2) {
		g2.setColor(mProperties.color);

		int x = 0;
		int y = -mBlockSize.getH();
		
		for (int i = 0; i < mTitles.length; ++i) {
			String title = mTitles[i];
			
			int d = mCharWidth * mWidths[i];
			
			int x1 = x + (d - ModernWidget.getStringWidth(g2, title)) / 2;
			
			g2.drawString(title, x1, y);
			
			x += d + FIELD_GAP;
		}
		
		//if (mTitle != null) {
		//	g2.drawString(mTitle, (getPreferredSize().width - g2.getFontMetrics().stringWidth(mTitle)), -mBlockSize.getH());
		//}

		y = (mBlockSize.getH() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;

		for (int r = 0; r < mLabels.length; ++r) {
			x = 0;
			
			for (int i = 0; i < mWidths.length; ++i) {
				int d = mCharWidth * mWidths[i];
				
				String label = mLabels[r][i];
				
				int x1 = x + (d - ModernWidget.getStringWidth(g2, label)) / 2;
				
				
				//if (mTypes.get(i).equals("number")) {
				//	x1 = x+ d - ModernWidget.getStringWidth(g2, label);
				//}

				g2.drawString(label, x1, y);
				
				x += d + FIELD_GAP;
			}
			
			y += mBlockSize.getH();
		}
		
		/*
		for (String label : mLabels) {
			g2.drawString(label, 0, y);

			y += mBlockSize.getH();
		}
		*/
	}
}
