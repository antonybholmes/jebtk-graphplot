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
import java.util.List;

import org.jebtk.core.Mathematics;
import org.jebtk.core.NameProperty;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.text.TextUtils;
import org.jebtk.graphplot.figure.properties.GridProperties;
import org.jebtk.graphplot.figure.properties.LineProperties;
import org.jebtk.graphplot.figure.properties.TickProperties;
import org.jebtk.graphplot.figure.properties.TitleProperties;
import org.jebtk.graphplot.figure.properties.VisibleProperties;
import org.jebtk.math.Linspace;

// TODO: Auto-generated Javadoc
/**
 * Adjust properties for a given axis.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class Axis extends VisibleProperties implements NameProperty, PlotHashProperty, ChangeListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The member min.
	 */
	private double mMin = 0;
	
	/**
	 * The member max.
	 */
	private double mMax = 1;
	
	/**
	 * The member stroke.
	 */
	private LineProperties mStroke = new LineProperties();
	
	/**
	 * The member ticks.
	 */
	private TickProperties mTicks = new TickProperties();
	
	/**
	 * The member grid.
	 */
	private GridProperties mGrid = new GridProperties();

	
	/** The m title. */
	private TitleProperties mTitle = new TitleProperties();
	

	/**
	 * Whether to show a zeroth line.
	 */
	private boolean mShowZerothLine = false;

	/** The m name. */
	private String mName;
	
	/**
	 * Instantiates a new axis.
	 *
	 * @param name the name
	 */
	public Axis(String name) {
		mName = name;
		
		setLimitsAutoRound(0, 1);
		
		mTicks.addChangeListener(this);
		mGrid.addChangeListener(this);
		mTitle.addChangeListener(this);
		mStroke.addChangeListener(this);
	}
	
	/**
	 * Sets the font.
	 *
	 * @param font the font
	 * @param color the color
	 */
	public void setFont(Font font, Color color) {
		mTicks.getMajorTicks().getFontStyle().setFont(font, color);
		mTitle.getFontStyle().setFont(font, color);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.NameProperty#getName()
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * Returns the tick properties for this axis.
	 * 
	 * @return 		A tick properties object.
	 */
	public TickProperties getTicks() {
		return mTicks;
	}
	
	/**
	 * Returns the grid properties for this axis.
	 * 
	 * @return 		A grid properties object.
	 */
	public GridProperties getGrid() {
		return mGrid;
	}

	/**
	 * Returns the title properties for this axis.
	 * 
	 * @return 		A title properties object.
	 */
	public TitleProperties getTitle() {
		return mTitle;
	}
	
	/**
	 * Returns true if a line at x=0 should be
	 * drawn when min < 0 and max > 0.
	 *
	 * @return the show zeroth line
	 */
	public boolean getShowZerothLine() {
		return mShowZerothLine;
	}
	
	/**
	 * Set whether to show a line at x=0 when
	 * the min < 0 and max > 0.
	 *
	 * @param show the new show zeroth line
	 */
	public void setShowZerothLine(boolean show) {
		mShowZerothLine = show;
		
		fireChanged();
	}
	
	/**
	 * Set the min limit of this axis.
	 *
	 * @param min the new min
	 */
	public void setMin(double min) {
		mMin = min;
		
		setLimits();
	}
	
	/**
	 * Returns the min limit of the axis.
	 * 
	 * @return		The min limit.
	 */
	public double getMin() {
		return mMin;
	}
	
	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(double max) {
		mMax = max;
		
		setLimits();
	}
	
	/**
	 * Returns the max limit of the axis.
	 * 
	 * @return		The max limit.
	 */
	public double getMax() {
		return mMax;
	}
	
	/**
	 * Returns the line properties object for the axis.
	 *
	 * @return the line style
	 */
	public LineProperties getLineStyle() {
		return mStroke;
	}
	
	/**
	 * Returns true if the point is within the bounds
	 * of the graph.
	 *
	 * @param x the x
	 * @return true, if successful
	 */
	public boolean withinBounds(double x) {
		return x >= mMin && x <= mMax;
	}
	
	/**
	 * Bounds a point so it is within the graph limits.
	 *
	 * @param x the x
	 * @return the double
	 */
	public double bound(double x) {
		return Math.min(mMax, Math.max(mMin, x));
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public final void changed(ChangeEvent e) {
		fireChanged();
	}

	/**
	 * Sets the limits.
	 *
	 * @param min the min
	 * @param max the max
	 * @param step the step
	 */
	public void setLimits(double min, double max, double step) {
		mMin = min;
		mMax = max;

		getTicks().setTicks(Linspace.evenlySpaced(min, max, step));
	}

	/**
	 * Sets the limits.
	 */
	public void setLimits() {
		setLimits(mMin, mMax);
	}
	
	/**
	 * Set the limits of the axis.
	 * 
	 * @param min		The min limit.
	 * @param max		The max limit.
	 */
	public void setLimits(double min, double max) {
		double step = CalcStepSize(max - min);
		
		setLimits(min, max, step);
	}
	
	/**
	 * Sets the limits auto round.
	 *
	 * @param max the new limits auto round
	 */
	public void setLimitsAutoRound(double max) {
		setLimitsAutoRound(0, max);
	}
	
	/**
	 * Set the limits and auto round the min and max to make the plot
	 * more aesthetically pleasing.
	 *
	 * @param min the min
	 * @param max the max
	 */
	public void setLimitsAutoRound(double min, double max) {
		double step = CalcStepSize(max - min);
		
		if (min >= 0) {
			min = step * (int)(min / step);
		} else {
			min = step * ((int)(min / step) - 1);
		}
		
		if (max >= 0) {
			max = step * ((int)(max / step) + 1);
		} else {
			max = step * (int)(max / step);
		}
		
		setLimits(min, max, step);
	}
	
	
	/**
	 * Auto adjust the axis limits.
	 *
	 * @param min the min
	 * @param max the max
	 */
	public void setLimitsAuto(double min, double max) {
		setLimits(adjustedMin(min, max), adjustedMax(min, max));
	}
	
	/**
	 * Display tick marks only at the minimum and maximum of the axis.
	 */
	public void startEndTicksOnly() {
		List<Double> range = Linspace.range(mMin, mMax);
		
		getTicks().setTicks(range);
	}
	
	/**
	 * Returns an id describing properties of an axis. Comparing hashes
	 * can indicate when an axis properties have changed.
	 *
	 * @return the string
	 */
	@Override
	public String hashId() {
		return TextUtils.join(TextUtils.COLON_DELIMITER, 
				getMin(),
				getMax(),
				getTicks().getMajorTicks().getTickCount(),
				getTicks().getMinorTicks().getTickCount());
	}
	
	/**
	 * Adjusted min.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the double
	 */
	public static double adjustedMin(double min, double max) {
		if (min == 0) {
			return 0;
		}
		
		if (min > 0) {
			return Math.max(0, min - (max - min) / 100.0);
		} else {
			return min - (max - min) / 100.0;
		}
	}
	
	/**
	 * Adjusted max.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the double
	 */
	public static double adjustedMax(double min, double max) {
		if (max == 0) {
			return 0;
		}
		
		if (max < 0) {
			return Math.min(0, max + (max - min) / 100.0);
		} else {
			return max + (max - min) / 100.0;
		}
	}
	
	/**
	 * Calc step size.
	 *
	 * @param range the range
	 * @return the float
	 */
	public static double CalcStepSize(double range) {
		if (range < 1) {
			return range;
		}
		
		// Negative powers mess up the scaling
		double power = Mathematics.log10(range);
		int ipower = (int)power;
		double factor = Math.pow(10, power - ipower);
		
		double scalar;
		
		if (factor < 1.2) {
			scalar = 0.2;
		} else if (factor < 2.5) {
			scalar = 0.2;
		} else if (factor < 5) {
			scalar = 0.5;
		} else if (factor < 10) {
			scalar = 1.0;
		} else {
			scalar = 2.0;
		}
		
		double step = scalar * Math.pow(10, ipower);
		
		return step;
		
		/*
        // calculate an initial guess at step size
        double tempStep = range / targetSteps;

        // get the magnitude of the step size
        float mag = (float)Math.round(Mathematics.log10(tempStep));
        float magPow = (float)Math.pow(10, mag);

        // calculate most significant digit of the new step size
        float magMsd = (int)(tempStep / magPow + 0.5);

        // promote the MSD to either 1, 2, or 5
        if (magMsd > 5.0) {
            magMsd = 10.0f;
    	} else if (magMsd > 2.0) {
            magMsd = 5.0f;
    	} else if (magMsd > 1.0) {
            magMsd = 2.0f;
    	}
        
        return magMsd * magPow;
        */
    }

	
	/**
	 * Disable all features.
	 *
	 * @param axis the axis
	 */
	public static void disableAllFeatures(Axis axis) {
		enableAllFeatures(axis, false);
	}
	
	/**
	 * Enable all features.
	 *
	 * @param axis the axis
	 */
	public static void enableAllFeatures(Axis axis) {
		enableAllFeatures(axis, true);
	}
	
	/**
	 * Enable all features.
	 *
	 * @param axis the axis
	 * @param enable the enable
	 */
	public static void enableAllFeatures(Axis axis, boolean enable) {
		axis.getTitle().getFontStyle().setVisible(enable);
		axis.getGrid().setVisible(enable);
		axis.getLineStyle().setVisible(enable);
		
		axis.getTicks().getMajorTicks().getLineStyle().setVisible(enable);
		axis.getTicks().getMajorTicks().getFontStyle().setVisible(enable);
		axis.getTicks().getMinorTicks().getLineStyle().setVisible(enable);
		axis.getTicks().getMinorTicks().getFontStyle().setVisible(enable);
	}
}
