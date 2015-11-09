package nullSquad.simulator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.data.xy.DefaultXYDataset;

import nullSquad.network.*;

public class GraphGUI extends JFrame{

	private JFreeChart chart;
	private User userInfo;
	private DefaultXYDataset xyDataset;
	
	
	/**
	 * Create a GUI that shows a graph for the specified user's payoff history
	 * @param user
	 */
	public GraphGUI(User user) 
	{
		super("History of Payoff For User: " + user.getUserName());
		
		
		this.userInfo = user;
		this.pack();
		
		this.setMinimumSize(new Dimension(500, 500));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		
		// Create a XY Dataset
		xyDataset = new DefaultXYDataset();
		
		refreshChartData();
		// Create an XY Line Chart
		chart = ChartFactory.createXYLineChart("User Payoff Over Time", "Time (Simulation Iterations)", "User Payoff", xyDataset);
		
		
		// Draw the Initial Chart
		chart.draw((Graphics2D)this.getGraphics(), (Rectangle2D)new Rectangle2D.Double(0, 0 , this.getWidth(), this.getHeight()));
		
		// Set the content pane to a graphics panel for drawing the graph
		this.setContentPane(new GraphPanel(this));
		
		this.setVisible(true);
		
	}
	
	/**
	 * Sets the chart dataset
	 */
	private void refreshChartData()
	{
		// Get all payoff history (put into readable format for series)
		double payoffHistory[][] = getUserPayoffData();
		
		if(payoffHistory == null)
			return;
		
		// Add a new XY Series (Using User Payoff Data)
		xyDataset.addSeries("User: " + userInfo.getUserName(), payoffHistory);
		
	}
	
	/**
	 * Put User Payoff history into a readable double 2D array for JFreeChart
	 * @return A 2D double array of coordinates
	 */
	private double[][] getUserPayoffData()
	{
		if(userInfo == null)
			return null;
		
		// 2D Array for X and Y coordinates
		double payoffHistory[][] = new double[2][userInfo.getPayoffHistory().size()];
		
		
		for(int i = 0; i < userInfo.getPayoffHistory().size(); i++)
		{
			payoffHistory[0][i] = i;
			payoffHistory[1][i] = userInfo.getPayoffHistory().get(i);
		}
		
		return payoffHistory;
	}

	/**
	 * Draws the chart using graphics
	 * @param g
	 */
	public void drawChart(Graphics g)
	{
		// Update all chart data
		refreshChartData();
		
		// Paint the chart
		if(chart != null)
			chart.draw((Graphics2D)g, (Rectangle2D)new Rectangle2D.Double(0, 0 , g.getClipBounds().getWidth(), g.getClipBounds().getHeight()));
	}
	
}

/**
 * Simple Class used to provide a drawing interface (content pane) for the graph
 * Calls the drawChart method from the given GraphGUI
 * @author MVezina *
 */
class GraphPanel extends JPanel
{
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
