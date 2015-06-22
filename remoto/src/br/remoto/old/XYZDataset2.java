package br.remoto.old;

/* ---------------------
 * SampleXYZDataset.java
 * ---------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 */

import org.jfree.data.xy.AbstractXYZDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * A quick-and-dirty implementation of the {@link XYZDataset interface}.  
 * Hard-coded and not useful beyond the demo.
 */
public class XYZDataset2 extends AbstractXYZDataset implements XYZDataset {

	private static final long serialVersionUID = 1L;

    private String name = "";

    /** The x values. */
    private double[] xVal;

    /** The y values. */
    private int[] yVal;

    /** The z values. */
    private double[] zVal;

    /**
     * Returns the number of series in the dataset.
     *
     * @return The series count.
     */
    public int getSeriesCount() {
        return 1;
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The key for the series.
     */
    public Comparable getSeriesKey(int series) {
        return "Series 1";
    }

    /**
     * Returns the number of items in a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The number of items within the series.
     */
    public int getItemCount(final int series) {
        return this.xVal.length;
    }

    /**
     * Returns the x-value for an item within a series.
     * <P>
     * The implementation is responsible for ensuring that the x-values are
     * presented in ascending order.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value.
     */
    public Number getX(int series, int item) {
        return new Double(this.xVal[item]);
    }

    /**
     * Returns the y-value for an item within a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The y-value.
     */
    public Number getY(int series, int item) {
        return new Double(this.yVal[item]);
    }

    /**
     * Returns the z-value for the specified series and item.
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The value.
     */
    public Number getZ(final int series, final int item) {
        return new Double(this.zVal[item]);
    }

	public String getSeriesName(int arg0) {
		// TODO Auto-generated method stub
		return name;
	}

	public double[] getXVal() {
		return xVal;
	}

	public void setXVal(double[] val) {
		xVal = val;
	}

	public int[] getYVal() {
		return yVal;
	}

	public void setYVal(int[] val) {
		yVal = val;
	}

	public double[] getZVal() {
		return zVal;
	}

	public void setZVal(double[] val) {
		zVal = val;
	}

	public void setName(String seriesName) {
		this.name = seriesName;
	}
    
}
