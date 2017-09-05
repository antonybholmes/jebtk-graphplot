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
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.util.List;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.Mathematics;
import org.jebtk.core.text.Formatter;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.matrix.DoubleMatrix;
import org.jebtk.math.matrix.MatrixGroup;
import org.jebtk.math.matrix.MatrixOperations;
import org.jebtk.math.matrix.vec.Coordinate3D;
import org.jebtk.math.matrix.vec.Proj3DTo2DMatrix3D;
import org.jebtk.math.matrix.vec.RotMatrix3DX;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Draw a pie chart.
 *
 * @author Antony Holmes Holmes
 */
public class PieChart3DLayer extends PlotClippedLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The top circle z.
	 */
	private double topCircleZ = 8;
	
	/**
	 * The lower circle z.
	 */
	private double lowerCircleZ = 8.1;

	/**
	 * The member y1 c.
	 */
	private Coordinate3D mY1C = new Coordinate3D(-0.5, 0.5, topCircleZ);
	
	/**
	 * The member y2 c.
	 */
	private Coordinate3D mY2C = new Coordinate3D(0.5, -0.5, topCircleZ);

	/**
	 * The member y1 cl.
	 */
	private Coordinate3D mY1Cl = new Coordinate3D(-0.5, 0.5, lowerCircleZ);
	
	/**
	 * The member y2 cl.
	 */
	private Coordinate3D mY2Cl = new Coordinate3D(0.5, -0.5, lowerCircleZ);


	/**
	 * The member camera c.
	 */
	private Coordinate3D mCameraC = Coordinate3D.ORIGIN;


	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.PlotClippedLayer#plotLayer(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes, edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot, org.abh.lib.math.matrix.AnnotationMatrix)
	 */
	@Override
	public void plotLayer(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m) {
		//int x1 = axes.toPlotX(0.2);

		// distance from camera to viewing screen
		double ez = 5;

		//lets rotate the coordinates a bit

		double rotAngle = Math.PI / 3;

		RotMatrix3DX r = new RotMatrix3DX(rotAngle);

		// shift circle to origin, rotate, then shift back
		Coordinate3D otop = new Coordinate3D(0, 
				(mY1C.getY() + mY2C.getY()) / 2, 
				(mY1C.getZ() + mY1Cl.getZ()) / 2);
		Coordinate3D ptoo = Coordinate3D.invert(otop);

		/*
		drawLowerCircle(g2,
				axes,
				sc,
				ez,
				r,
				ptoo,
				otop);
		 */

		// Only draw segments if they might be visible, if we are looking
		// down on the matrix, we cannot see the underside.
		
		if (rotAngle > 0) {
			drawSegments(g2,
					m,
					axes,
					plot.getAllSeries(),
					ez,
					r,
					ptoo,
					otop,
					mY1C,
					mY2C,
					mY1Cl,
					mY2Cl,
					topCircleZ,
					lowerCircleZ);
		}

		drawUpperCircle(g2,
				m,
				axes,
				plot.getAllSeries(),
				ez,
				r,
				ptoo,
				otop);

		drawLabels(g2,
				m,
				axes,
				plot.getAllSeries(),
				ez,
				r,
				ptoo,
				otop,
				topCircleZ,
				lowerCircleZ);
	}

	/**
	 * Draw upper circle.
	 *
	 * @param g2 the g2
	 * @param m the m
	 * @param axes the axes
	 * @param sc the sc
	 * @param ez the ez
	 * @param r the r
	 * @param ptoo the ptoo
	 * @param otop the otop
	 */
	private void drawUpperCircle(Graphics2D g2,
			AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup sc,
			double ez,
			DoubleMatrix r,
			Coordinate3D ptoo,
			Coordinate3D otop) {
		drawCircle(g2,
				m,
				axes,
				sc,
				ez,
				r,
				ptoo,
				otop,
				mY1C,
				mY2C);
	}

	/**
	 * Draw lower circle.
	 *
	 * @param g2 the g2
	 * @param m the m
	 * @param axes the axes
	 * @param sc the sc
	 * @param ez the ez
	 * @param r the r
	 * @param ptoo the ptoo
	 * @param otop the otop
	 */
	private void drawLowerCircle(Graphics2D g2,
			AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup sc,
			double ez,
			DoubleMatrix r,
			Coordinate3D ptoo,
			Coordinate3D otop) {
		drawCircle(g2,
				m,
				axes,
				sc,
				ez,
				r,
				ptoo,
				otop,
				mY1Cl,
				mY2Cl);
	}

	/**
	 * Draw circle.
	 *
	 * @param g2 the g2
	 * @param m the m
	 * @param axes the axes
	 * @param sc the sc
	 * @param ez the ez
	 * @param r the r
	 * @param ptoo the ptoo
	 * @param otop the otop
	 * @param c1 the c1
	 * @param c2 the c2
	 */
	private void drawCircle(Graphics2D g2,
			AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup sc,
			double ez,
			DoubleMatrix r,
			Coordinate3D ptoo,
			Coordinate3D otop,
			Coordinate3D c1,
			Coordinate3D c2) {
		//
		// lower circle
		//

		//otop = Coordinate3D.getZ(mY1Cl.getZ());
		//ptoo = Coordinate3D.invert(otop);

		Coordinate3D c12 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(c1, ptoo))), otop);

		Coordinate3D c3 = new Coordinate3D(c1.getX(), c2.getY(), c1.getZ());
		Coordinate3D c32 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(c3, ptoo))), otop);

		//otop = Coordinate3D.getZ(mY2Cl.getZ());
		//ptoo = Coordinate3D.invert(otop);
		Coordinate3D c22 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(c2, ptoo))), otop);

		System.err.println("c1 " + c1 + " " + c12);
		System.err.println("c2 " + c1 + " " + c22);

		//System.exit(0);

		//Coordinate3D pr1 = Proj3DTo2DMatrix3D.proj3DTo2D(c1, mCameraC, ez);
		//Coordinate3D pr2 = Proj3DTo2DMatrix3D.proj3DTo2D(c2, mCameraC, ez);
		Coordinate3D p1 = Proj3DTo2DMatrix3D.proj3DTo2D(c12, mCameraC, ez);
		Coordinate3D p3 = Proj3DTo2DMatrix3D.proj3DTo2D(c32, mCameraC, ez);
		Coordinate3D p2 = Proj3DTo2DMatrix3D.proj3DTo2D(c22, mCameraC, ez);

		double x = (p1.getX() + p3.getX()) / 2;

		int x1 = axes.toPlotX1(0.5 + x);
		int x2 = axes.toPlotX1(0.5 - x);

		int y1 = axes.toPlotY1(0.5 + p1.getY());
		int y2 = axes.toPlotY1(0.5 + p2.getY());

		int hX = x2 - x1;
		int hY = y2 - y1;

		System.err.println("hx lower " + p1 + " " + p2 + " " + y1 + " " + y2);

		int startAngle = 90;

		double sum = DoubleMatrix.sum(m);

		int a = 0;


		for (XYSeries series : sc) {
			List<Integer> columns = MatrixGroup.findColumnIndices(m, series);

			int c = columns.get(0);
			
			a = (int)Math.round(360 * m.getValue(0, c) / sum);

			if (series.getStyle().getFillStyle().getVisible()) {
				//System.err.println("has " + series.getTitle().getText());

				g2.setColor(series.getStyle().getFillStyle().getColor());

				g2.fill(new Arc2D.Double(x1, y1, hX, hY, startAngle, -a, Arc2D.PIE));
			}

			/*
			if (series.getLineStyle().getVisible()) {
				g2.setStroke(series.getLineStyle().getStroke());
				g2.setColor(series.getLineStyle().getColor());

				g2.draw(new Arc2D.Double(x1, y1, hX, hY, startAngle, -a, Arc2D.PIE));
			}
			 */

			startAngle -= a;
		}
	}

	/**
	 * Draw segments.
	 *
	 * @param g2 the g2
	 * @param m the m
	 * @param axes the axes
	 * @param sc the sc
	 * @param ez the ez
	 * @param r the r
	 * @param ptoo the ptoo
	 * @param otop the otop
	 * @param m1u the m1u
	 * @param m1l the m1l
	 * @param m2u the m2u
	 * @param m2l the m2l
	 * @param z1 the z1
	 * @param z2 the z2
	 */
	private void drawSegments(Graphics2D g2,
			AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup sc,
			double ez,
			DoubleMatrix r,
			Coordinate3D ptoo,
			Coordinate3D otop,
			Coordinate3D m1u,
			Coordinate3D m1l,
			Coordinate3D m2u,
			Coordinate3D m2l,
			double z1,
			double z2) {

		double z = (z1 + z2) / 2;

		//
		// first parts needed to create arcs
		//

		Coordinate3D c12 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(m1u, ptoo))), otop);
		Coordinate3D c22 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(m1l, ptoo))), otop);

		Coordinate3D c3 = new Coordinate3D(m1u.getX(), m1l.getY(), m1u.getZ());
		Coordinate3D c32 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(c3, ptoo))), otop);


		//Coordinate3D pr1 = Proj3DTo2DMatrix3D.proj3DTo2D(c1, mCameraC, ez);
		//Coordinate3D pr2 = Proj3DTo2DMatrix3D.proj3DTo2D(c2, mCameraC, ez);
		Coordinate3D p1 = Proj3DTo2DMatrix3D.proj3DTo2D(c12, mCameraC, ez);
		Coordinate3D p3 = Proj3DTo2DMatrix3D.proj3DTo2D(c32, mCameraC, ez);
		Coordinate3D p2 = Proj3DTo2DMatrix3D.proj3DTo2D(c22, mCameraC, ez);

		double x = (p1.getX() + p3.getX()) / 2;

		int px1r1 = axes.toPlotX1(0.5 + x);
		int px2r1 = axes.toPlotX1(0.5 - x);

		int py1r1 = axes.toPlotY1(0.5 + p1.getY());
		int py2r1 = axes.toPlotY1(0.5 + p2.getY());

		int hX1 = px2r1 - px1r1;
		int hY1 = py2r1 - py1r1;

		System.err.println("p2 " + p1 + " " + p2);

		//
		// lower
		//

		c12 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(m2u, ptoo))), otop);
		c22 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(m2l, ptoo))), otop);

		c3 = new Coordinate3D(m2u.getX(), m2l.getY(), m2l.getZ());
		c32 = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, Coordinate3D.translate(c3, ptoo))), otop);

		p1 = Proj3DTo2DMatrix3D.proj3DTo2D(c12, mCameraC, ez);
		p3 = Proj3DTo2DMatrix3D.proj3DTo2D(c32, mCameraC, ez);
		p2 = Proj3DTo2DMatrix3D.proj3DTo2D(c22, mCameraC, ez);

		x = (p1.getX() + p3.getX()) / 2;

		int px1r2 = axes.toPlotX1(0.5 + x);
		int px2r2 = axes.toPlotX1(0.5 - x);

		int py1r2 = axes.toPlotY1(0.5 + p1.getY());
		int py2r2 = axes.toPlotY1(0.5 + p2.getY());

		int hX2 = px2r2 - px1r2;
		int hY2 = py2r2 - py1r2;

		System.err.println("p2 " + p1 + " " + p2);

		//
		// stuff
		//

		double a1 = 0;

		double a2 = 0;
		double angle2 = 0;

		double sum = DoubleMatrix.sum(m);

		double startAngle = 90;
		
		double ratio;

		for (XYSeries s1 : sc) {
			a2 = 0;
			angle2 = 0;

			List<Integer> columns = MatrixGroup.findColumnIndices(m, s1);

			int c = columns.get(0);
				
			ratio = m.getValue(0, c) / sum;
			a2 = Mathematics.TWO_PI * ratio;
			angle2 = 360 * ratio;

			g2.setColor(ColorUtils.lighten(s1.getStyle().getFillStyle().getColor(), 0.4));

			a2 += a1;

			double x1 = Math.sin(a1) * 0.5;
			double y1 = Math.cos(a1) * 0.5;

			double x2 = Math.sin(a2) * 0.5;
			double y2 = Math.cos(a2) * 0.5;

			Coordinate3D c1u = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, new Coordinate3D(x1, y1, z1 - z))), otop);
			Coordinate3D c2u = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, new Coordinate3D(x2, y2, z1 - z))), otop);

			Coordinate3D p1u = Proj3DTo2DMatrix3D.proj3DTo2D(c1u, mCameraC, ez);
			Coordinate3D p2u = Proj3DTo2DMatrix3D.proj3DTo2D(c2u, mCameraC, ez);

			System.err.println("pu " + p1u + " " + otop + " " + c1u + " " + z1 + " " + new Coordinate3D(x1, y1, z1 - z));

			Coordinate3D c1l = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, new Coordinate3D(x1, y1, z2 - z))), otop);
			Coordinate3D c2l = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, new Coordinate3D(x2, y2, z2 - z))), otop);

			Coordinate3D p1l = Proj3DTo2DMatrix3D.proj3DTo2D(c1l, mCameraC, ez);
			Coordinate3D p2l = Proj3DTo2DMatrix3D.proj3DTo2D(c2l, mCameraC, ez);



			int px1 = axes.toPlotX1(0.5 + p1u.getX());
			int py1 = axes.toPlotY1(0.5 + p1u.getY());

			int px2 = axes.toPlotX1(0.5 + p1l.getX());
			int py2 = axes.toPlotY1(0.5 + p1l.getY());

			int px12 = axes.toPlotX1(0.5 + p2u.getX());
			int py12 = axes.toPlotY1(0.5 + p2u.getY());

			int px22 = axes.toPlotX1(0.5 + p2l.getX());
			int py22 = axes.toPlotY1(0.5 + p2l.getY());

			if (a1 < Mathematics.HALF_PI && a2 > Mathematics.HALF_PI) {
				double angle3 = startAngle;

				/*
				System.err.println("blah blah " + startAngle + " " + angle3);

				GeneralPath path = new GeneralPath();
				path.moveTo(px1, py1);
				path.append(new Arc2D.Double(px1r1, py1r1, hX1, hY1, startAngle, -angle3, Arc2D.OPEN), true);
				path.lineTo(px2r2, (py1r2 + py2r2) / 2);
				path.append(new Arc2D.Double(px1r2, py1r2, hX2, hY2, startAngle - angle3, angle3, Arc2D.OPEN), true);
				path.closePath();

				g2.fill(path);
				 */

				angle3 = angle2 - angle3;

				GeneralPath path = new GeneralPath();
				path.moveTo(px2r1, (py1r1 + py2r1) / 2);
				path.append(new Arc2D.Double(px1r1, py1r1, hX1, hY1, 0, -angle3, Arc2D.OPEN), true);
				path.lineTo(px22, py22);
				path.append(new Arc2D.Double(px1r2, py1r2, hX2, hY2, -angle3, angle3, Arc2D.OPEN), true);
				path.closePath();

				//g2.setColor(Color.RED);
				//g2.draw(path);
				//g2.setColor(ColorUtils.getTransparentColor50(Color.RED));
				g2.draw(path);
				g2.fill(path);
			} else if (a1 < Mathematics.PI_32 && a2 > Mathematics.PI_32) {
				double angle3 = 180 + startAngle;

				System.err.println("blah blah " + startAngle + " " + angle3);

				GeneralPath path = new GeneralPath();
				path.moveTo(px1, py1);
				path.append(new Arc2D.Double(px1r1, py1r1, hX1, hY1, startAngle, -angle3, Arc2D.OPEN), true);
				//path.lineTo(px12, py12);
				path.lineTo(px1r1, (py1r2 + py2r2) / 2);
				path.append(new Arc2D.Double(px1r1, py1r2, hX2, hY2, startAngle - angle3, angle3, Arc2D.OPEN), true);
				path.closePath();

				g2.draw(path);
				g2.fill(path);

				/*
				angle3 = angle2 - angle3;

				path = new GeneralPath();
				path.moveTo(px1r1, (py1r2 + py2r2) / 2);
				path.append(new Arc2D.Double(px1r1, py1r1, hX1, hY1, 180, -angle3, Arc2D.OPEN), true);
				path.lineTo(px22, py22);
				path.append(new Arc2D.Double(px1r2, py1r2, hX2, hY2, 180 -angle3, angle3, Arc2D.OPEN), true);
				path.closePath();

				g2.fill(path);
				 */
			} else if (a1 >= Mathematics.HALF_PI && a2 <= Mathematics.PI_32) {
				GeneralPath path = new GeneralPath();
				path.moveTo(px1, py1);
				path.append(new Arc2D.Double(px1r1, py1r1, hX1, hY1, startAngle, -angle2, Arc2D.OPEN), true);
				//path.lineTo(px12, py12);
				path.lineTo(px22, py22);
				path.append(new Arc2D.Double(px1r2, py1r2, hX2, hY2, startAngle - angle2, angle2, Arc2D.OPEN), true);
				path.closePath();

				//g2.setColor(Color.RED);
				//g2.draw(path);
				//g2.setColor(ColorUtils.getTransparentColor50(Color.RED));
				g2.draw(path);
				g2.fill(path);
			} else {
				// don't draw background stuff
			}

			System.err.println("args " + " " + px1 + " " + py1 + " " + hX1 + " " + hY1 + " " + startAngle + " " + angle2);
			System.err.println("args2 " + " " + px1r2 + " " + py1r2 + " " + hX2 + " " + hY2 + " " + startAngle + " " + angle2);





			a1 = a2;
			startAngle -= angle2;

			//break;
		}
	}
	
	/**
	 * Draw labels.
	 *
	 * @param g2 the g2
	 * @param m the m
	 * @param axes the axes
	 * @param sc the sc
	 * @param ez the ez
	 * @param r the r
	 * @param ptoo the ptoo
	 * @param otop the otop
	 * @param z1 the z1
	 * @param z2 the z2
	 */
	private void drawLabels(Graphics2D g2,
			AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup sc,
			double ez,
			DoubleMatrix r,
			Coordinate3D ptoo,
			Coordinate3D otop,
			double z1,
			double z2) {

		double z = (z1 + z2) / 2;

		//
		// stuff
		//

		double a1 = 0;

		double a2 = 0;
		double a3 = 0;
		double per = 0;

		double sum = DoubleMatrix.sum(m);

		g2.setColor(Color.WHITE);
		
		for (XYSeries s1 : sc) {
			a2 = 0;

			List<Integer> columns = MatrixGroup.findColumnIndices(m, s1);

			int c = columns.get(0);
			
			per = m.getValue(0, c) / sum;
			a2 = Mathematics.TWO_PI * per;

			g2.setColor(ColorUtils.lighten(s1.getStyle().getFillStyle().getColor(), 0.2));

			a2 += a1;
			
			a3 = (a1 + a2) / 2;

			double x1 = Math.sin(a3) * 0.25;
			double y1 = Math.cos(a3) * 0.25;

			Coordinate3D c1u = Coordinate3D.translate(new Coordinate3D(MatrixOperations.multiply(r, new Coordinate3D(x1, y1, z1 - z))), otop);
			Coordinate3D p1u = Proj3DTo2DMatrix3D.proj3DTo2D(c1u, mCameraC, ez);
	
	
			int px1 = axes.toPlotX1(0.5 + p1u.getX());
			int py1 = axes.toPlotY1(0.5 + p1u.getY());

			String t = s1.getName() + " (" + Formatter.number().dp(1).format(per * 100) + "%)";
			
			g2.setFont(s1.getFontStyle().getFont());
			g2.setColor(s1.getFontStyle().getColor());
			g2.drawString(t, px1 - g2.getFontMetrics().stringWidth(t) / 2, py1);

			a1 = a2;
		}
	}
}
