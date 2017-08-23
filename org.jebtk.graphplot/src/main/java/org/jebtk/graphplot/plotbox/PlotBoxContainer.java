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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jebtk.core.Function;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.geom.IntPos2D;
import org.jebtk.core.stream.Stream;
import org.jebtk.core.text.Join;
import org.jebtk.graphplot.figure.GridLocation;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxContainer extends PlotBox implements ChangeListener {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	
	
	//private IntPos2D mPos = GeomUtils.INT_POINT_ZERO;

	private PlotBoxLayout mLayout = null;
	
	private Map<String, PlotBox> mNameMap = new TreeMap<String, PlotBox>();

	private MarginProperties mMargins = MarginProperties.DEFAULT_MARGIN;

	private PlotBoxStorage mStorage;
	
	
	public PlotBoxContainer(String name) {
		this(name, new PlotBoxDimStorage(), new PlotBoxColumnLayout());
	}
	
	public PlotBoxContainer(String name, PlotBoxLayout layout) {
		this(name, new PlotBoxDimStorage(), layout);
	}
	
	public PlotBoxContainer(String name, 
			PlotBoxStorage storage, 
			PlotBoxLayout layout) {
		super(name);
		setStorage(storage);
		setLayout(layout);
	}
	
	public void setStorage(PlotBoxStorage s) {
		mStorage = s;
		s.addChangeListener(this);
	}
	
	public PlotBoxStorage getStorage() {
		return mStorage;
	}
	
	public void setLayout(PlotBoxLayout layout) {
		mLayout = layout;
	}
	
	public PlotBoxLayout getPlotBoxLayout() {
		return mLayout;
	}
	
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
	 * Sets the bottom margin.
	 *
	 * @param margin the new bottom margin
	 */
	public void setBottomMargin(int margin) {
		setMargins(mMargins.getTop(), 
				mMargins.getLeft(), 
				margin, 
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
		if (updateMargins(margins)) {
			fireChanged();
		}
	}
	
	/**
	 * Update margins.
	 *
	 * @param margins the margins
	 * @return 
	 */
	@Override
	public boolean updateMargins(MarginProperties margins) {
		if (!margins.equals(mMargins)) {
			mMargins = margins;
			return true;
		} else {
			return false;
		}
	}
	
	public void addMargin(Dimension dim) {
		dim.width += mMargins.getLineMargin();
		dim.height += mMargins.getPageMargin();
	}
	
	/**
	 * Update parameter dim to the current plot size. This is to allow for
	 * recursive updating of the size without generating dimension objects.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	@Override
	public void plotSize(Dimension dim) {
		mLayout.plotSize(this, dim);
		
		addMargin(dim);
	}

	/**
	 * Draw recursive.
	 *
	 * @param g2 the g2
	 * @param plotBox the plot box
	 * @param offset the offset
	 * @param context the context
	 */
	@Override
	public void plot(Graphics2D g2, 
			Dimension offset,
			DrawingContext context,
			Object... params) {
		Graphics2D g2Temp = ImageUtils.clone(g2);
		
		try {
			// Translate to account for padding.
			g2Temp.translate(mMargins.getLeft(), mMargins.getTop());
			
			mLayout.plot(g2Temp, this, offset, context, params);	
		} finally {
			g2Temp.dispose();
		}
		
	}
	
	@Override
	public PlotBox addChildByName(PlotBox plot) {
		mNameMap.put(plot.getName(), plot);
		
		fireChanged();
		
		return this;
	}
	
	public <T extends PlotBox> void setChildren(Collection<T> plots) {
		System.err.println("set children");
		mStorage.setChildren(plots);
	}
	
	@Override
	public PlotBox addChild(PlotBox plot) {
		mStorage.addChild(plot);
		addChildByName(plot);
		
		return this;
	}
	
	@Override
	public PlotBox addChild(PlotBox plot, int i) {
		mStorage.addChild(plot, i);
		
		return addChildByName(plot);
	}
	
	@Override
	public PlotBox addReserved(PlotBox plot, int i) {
		mStorage.addReserved(plot, i);
		
		return addChildByName(plot);
	}
	
	@Override
	public PlotBox addChild(PlotBox plot, int i, int j) {
		mStorage.addChild(plot, i, j);
		
		return addChildByName(plot);
	}
	
	@Override
	public PlotBox addChild(PlotBox plot, GridLocation l) {
		mStorage.addChild(plot, l);
		
		return addChildByName(plot);
	}
	
	@Override
	public PlotBox addChild(PlotBox plot, IntPos2D p) {
		mStorage.addChild(plot, p);
		
		return addChildByName(plot);
	}
	
	@Override
	public PlotBox putZ(PlotBox plot) {
		return addChild(plot);
	}
	
	@Override
	public PlotBox putZ(PlotBox plot, GridLocation l) {
		return addChild(plot, l);
	}
	
	public PlotBox getChild(int i, int j) {
		return mStorage.getChild(i, j);
	}
	
	public PlotBox getChild(int i) {
		return mStorage.getChild(i);
	}
	
	public PlotBox getChild(GridLocation l) {
		return mStorage.getChild(l);
	}


	public PlotBox getChild(IntPos2D p) {
		return mStorage.getChild(p);
	}
	
	public PlotBox getChild(String name) {
		return mNameMap.get(name);
	}
	
	/*
	public void setPosition(IntPos2D p) {
		mPos = p;
		
		fireCanvasChanged();
	}
	
	public IntPos2D getPosition() {
		return mPos;
	}
	*/
	
	@Override
	public Iterable<IntPos2D> getPositions() {
		return mStorage.getPositions();
	}
	
	@Override
	public Iterable<GridLocation> getLocations() {
		return GridLocation.LOCATIONS_LIST;
	}
	
	@Override
	public String getType() {
		return "plot-box-container";
	}
	
	public Iterable<String> getNames() {
		return mNameMap.keySet();
	}
	
	@Override
	public int getChildCount() {
		return mStorage.getChildCount();
	}
	
	/**
	 * Returns the next available z layer.
	 * 
	 * @return
	 */
	@Override
	public int getUnusedZ() {
		return mStorage.getUnusedZ();
	}
	
	@Override
	public Iterable<Integer> getZ() {
		return mStorage.getZ();
	}
	
	public void clear() {
		mNameMap.clear();
		mStorage.clear();
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return mStorage.iterator();
	}
	
	/**
	 * Create a unique hash string for this plot box.
	 * @return
	 */
	@Override
	public String hashId() {
		return Join.onColon().values(getName(),
				getMargins(),
				getPreferredSize())
				.toString();
	}
	
	public void removeByName(String name) {
		remove(mNameMap.get(name));
	}
	
	public void remove(PlotBox plot) {
		mStorage.remove(plot);
	}
	
	/**
	 * Return the names of the children
	 * @param figure
	 * @return
	 */
	public static Iterable<String> getNames(PlotBox figure) {
		return Stream.stream(figure).map(new Function<PlotBox, String>() {

			@Override
			public String apply(PlotBox item) {
				return item.getName();
			}});
	}

	@Override
	public void changed(ChangeEvent e) {
		fireChanged();
	}
}
