package in.ac.iitb.cse.qh.data;

import org.jsflot.components.FlotChartRendererData;
import org.jsflot.xydata.XYDataList;
import org.jsflot.xydata.XYDataPoint;
import org.jsflot.xydata.XYDataSetCollection;

public class DivergenceChart {
	private XYDataList dataList = new XYDataList();
	private FlotChartRendererData data;
	private int minX = 0;
	private int maxX;

	public XYDataList getDataList() {
		return dataList;
	}

	public void setDataList(XYDataList dataList) {
		this.dataList = dataList;
	}

	public FlotChartRendererData getData() {
		return data;
	}

	public void setData(FlotChartRendererData data) {
		this.data = data;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public DivergenceChart() {
		data = new FlotChartRendererData();
	}

	public void update(int iter, double divergence) {
		getDataList().addDataPoint(new XYDataPoint(iter, divergence));

	}

	public XYDataSetCollection getChartSeries() {
		XYDataSetCollection collection = new XYDataSetCollection();
		collection.addDataList(dataList);
		return collection;
	}
}
