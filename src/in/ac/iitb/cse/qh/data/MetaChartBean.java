package in.ac.iitb.cse.qh.data;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

public class MetaChartBean {
	private CartesianChartModel model;
	private int iteration;
	private ChartSeries data;
	private String message;

	public MetaChartBean() {
		model = new CartesianChartModel();
		data = new ChartSeries();
		data.setLabel("Divergence");
		iteration = 0;
		// int i = 0;
		// data.set(i++, 135);
		// data.set(i++, 128);
		// data.set(i++, 102);
		model.addSeries(data);
		System.out.println("Chart object : " + this);
	}

	public void addData(double div) {
		System.out.println("Adding to the chart " + div);
		if (null != data)
			data.getData().put(++iteration, div);
	}

	public CartesianChartModel getModel() {
		return model;
	}

	public void itemSelect(ItemSelectEvent event) {
		System.out.println("Received event!");
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item selected", "Item Index: " + event.getItemIndex()
						+ ", Series Index:" + event.getSeriesIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
		message = "Item Index: " + event.getItemIndex() + ", Series Index:"
				+ event.getSeriesIndex();
		System.out.println(message);
	}

	public String getMessage() {
		return message;
	}

	public void reset() {
		iteration = 0;
		data.getData().clear();
	}
}
