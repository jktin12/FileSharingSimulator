package nullSquad.simulator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import nullSquad.filesharingsystem.users.*;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphGUI extends JFrame implements UserPayoffListener
{

	/* Serializable ID */
	private static final long serialVersionUID = 1L;
	
	private JFreeChart chart;
	private User userInfo;
	private XYSeriesCollection xySeriesCollection;

	/**
	 * Create a GUI that shows a graph for the specified user's payoff history
	 * 
	 * @param user The user to show the Payoff history for
	 */
	public GraphGUI(User user)
	{
		super(user.getUserName() + "'s Payoff History (Over " + user.getPayoffHistory().size() + " Steps)");

		this.userInfo = user;
		this.pack();

		// Set size
		this.setMinimumSize(new Dimension(500, 500));

		// Center the graph
		this.setLocationRelativeTo(null);

		// Create a series collection
		xySeriesCollection = new XYSeriesCollection();


		// Create (refresh) chart data
		refreshChartData();

		// Create an XY Line Chart
		chart = ChartFactory.createXYLineChart("User Payoff Over Time", "Steps (Simulation Iterations)", "User Payoff", xySeriesCollection);
		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chart.getPlot().setOutlinePaint(Color.BLACK);

		// Draw the Initial Chart
		chart.draw((Graphics2D) this.getGraphics(), (Rectangle2D) new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight()));

		// Set the content pane to a graphics panel for drawing the graph
		this.setContentPane(new GraphPanel(this));

		// Show the frame
		this.setVisible(true);

		// Subscribe to the user's UserPayoff events
		user.addPayoffListener(this);

	}

	/**
	 * Sets the chart dataset
	 */
	private void refreshChartData()
	{
		// Get all payoff history (put into readable format for series)
		XYSeries payoffHistory = getUserPayoffData();

		if (payoffHistory == null)
			return;

		// Reupdate series
		xySeriesCollection.removeAllSeries();
		xySeriesCollection.addSeries(payoffHistory);

	}

	/**
	 * Put User Payoff history into a readable double 2D array for JFreeChart
	 * 
	 * @return A 2D double array of coordinates
	 */
	private XYSeries getUserPayoffData()
	{
		XYSeries xySeries = new XYSeries("User: " + userInfo.getUserName());

		if (userInfo == null)
			return null;

		// Set the title of the frame
		this.setTitle(userInfo.getUserName() + "'s Payoff History (Over " + userInfo.getPayoffHistory().size() + " Steps)");

		// Set the points of the data set
		for (int i = 0; i < userInfo.getPayoffHistory().size(); i++)
		{
			xySeries.add(i, userInfo.getPayoffHistory().get(i));
		}

		return xySeries;
	}

	/**
	 * Draws the chart using graphics
	 * 
	 * @param g Graphics to Draw to
	 */
	public void drawChart(Graphics g)
	{
		// Update all chart data
		refreshChartData();

		// Paint the chart
		if (chart != null && g != null)
			chart.draw((Graphics2D) g, (Rectangle2D) new Rectangle2D.Double(0, 0, g.getClipBounds().getWidth(), g.getClipBounds().getHeight()));
	}

	@Override
	public void payoffUpdated(UserPayoffEvent uPE)
	{
		if (userInfo.equals(uPE.getUser()))
		{
			this.repaint();
		}

	}

}

/**
 * Simple Class used to provide a drawing interface (content pane) for the graph
 * Calls the drawChart method from the given GraphGUI
 * 
 * @author MVezina *
 */
class GraphPanel extends JPanel
{
	
	/* Serializable ID */
	private static final long serialVersionUID = 1L;
	
	
	private GraphGUI mainFrame;

	public GraphPanel(GraphGUI mainFrame)
	{
		this.mainFrame = mainFrame;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		mainFrame.drawChart(g);
	}
}
