
package bouncingball.combined;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A bouncing ball application that prepares and operates the environment, and
 * generates a figure.
 * 
 * @author Brendan Short
 *
 */
public class BouncingBallApplicationGeneralized {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		Console.getSettings().printIntegratorExceptions = false;
		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize bouncing ball parameters (restitution, gravity)
		BouncingBallParameters parameters = new BouncingBallParameters(.97, 9.81);
		// Initialize bouncing ball state (y position, y velocity)
		BouncingBallState state = new BouncingBallState(1.0, 1.0);
		// Initialize the flow map

		// Initialize bouncing ball system
		HybridSys<BouncingBallState> system = createHybridSystem(state, parameters);
		// Add bouncing ball system to environment
		environment.getSystems().add(system);
		// Run environment (max time duration, max jumps)
		TrajectorySet traj = environment.run(10.0, 10);
		// Generate a figure of the trajectories
		FigDef fig = new FigDef(800, 800, "Bouncing Ball Simulation");
		fig.addChart(0, 0, traj, HybridTime.TIME, "yPosition", "Time (sec)", "Y Position (m)", null, false);
		fig.addChart(0, 1, traj, HybridTime.TIME, "yVelocity", "Time (sec)", "Y Velocity (m)", null, false);
		Figure figure = generateFigureFromDef(fig);
		// Figure figure = generateVerticalStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	public static HybridSys<BouncingBallState> createHybridSystem(BouncingBallState state,
			BouncingBallParameters params) {

		// Initialize the flow map
		Fp flowMap = new Fp(params);
		// Initialize the jump map
		Gp jumpMap = new Gp(params);
		// Initialize the flow set
		Cp flowSet = new Cp();
		// Initialize the jump set
		Dp jumpSet = new Dp();
		// Initialize bouncing ball system
		HybridSys<BouncingBallState> system = new HybridSys<BouncingBallState>(state, flowMap, jumpMap, flowSet,
				jumpSet, params);

		return system;
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical bouncing ball state elements
	 */
	public static Figure generateVerticalStateFigure(TrajectorySet solution) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(1000, 600);
		// Assign title to figure
		figure.getTitle().setText("Bouncing Ball Simulation");
		// Create charts
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, yPos);
		figure.addComponent(0, 1, yVel);
		// Return generated figure
		return figure;
	}

	public static class FigDef {

		private int height;

		private int width;

		private String mainTitle;

		public Integer subRow;

		public Integer subCol;

		private ArrayList<ChartDef> charts;

		private ArrayList<FigDef> subFigures;

		public FigDef(int height, int width, String title) {

			this.height = height;
			this.width = width;
			this.mainTitle = title;
			subRow = null;
			subCol = null;
			charts = new ArrayList<ChartDef>();
			subFigures = new ArrayList<FigDef>();
		}

		public void addChart(int row, int col, TrajectorySet solution, String x_axis_var, String y_axis_var,
				String title, String x_axis_label, String y_axis_label, boolean display_legend) {

			charts.add(new ChartDef(row, col, solution, x_axis_var, y_axis_var, title, x_axis_label, y_axis_label,
					display_legend));
		}

		public void addSubFig(int row, int col, FigDef fig_def) {

			fig_def.subCol = col;
			fig_def.subRow = row;
			subFigures.add(fig_def);
		}

		/**
		 * @return the height
		 */
		public int getHeight() {

			return height;
		}

		/**
		 * @return the width
		 */
		public int getWidth() {

			return width;
		}

		/**
		 * @return the charts
		 */
		public ArrayList<ChartDef> getCharts() {

			return charts;
		}

		/**
		 * @return the subFigures
		 */
		public ArrayList<FigDef> getSubFigures() {

			return subFigures;
		}
	}

	public static class ChartDef {

		public int rowIndex;

		public int columnIndex;

		public TrajectorySet solution;

		public String xAxisVariable;

		public String yAxisVariable;

		public String mainTitle;

		public String xAxisTitle;

		public String yAxisTitle;

		public boolean displayLegend;

		public ChartDef(int row, int col, TrajectorySet solution, String x_axis_var, String y_axis_var, String title,
				String x_axis_label, String y_axis_label, boolean display_legend) {

			rowIndex = row;
			columnIndex = col;
			this.solution = solution;
			xAxisVariable = x_axis_var;
			yAxisVariable = y_axis_var;
			mainTitle = title;
			xAxisTitle = x_axis_label;
			yAxisTitle = y_axis_label;
			displayLegend = display_legend;
		}

	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical bouncing ball state elements
	 */
	public static Figure generateVerticalStateFigurez(TrajectorySet solution) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(1000, 600);
		// Assign title to figure
		figure.getTitle().setText("Bouncing Ball Simulation");
		// Create charts
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, yPos);
		figure.addComponent(0, 1, yVel);
		// Return generated figure
		return figure;
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical bouncing ball state elements
	 */
	public static Figure generateFigureFromDef(FigDef fig) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(fig.getWidth(), fig.getHeight());
		// Assign title to figure
		figure.getTitle().setText(fig.mainTitle);
		// Create charts
		for (ChartDef chart : fig.getCharts()) {
			ChartPanel ch = ChartUtils.createPanel(chart.solution, chart.xAxisVariable, chart.yAxisVariable);
			ChartUtils.configureLabels(ch, chart.xAxisTitle, chart.yAxisTitle, chart.mainTitle, chart.displayLegend);
			figure.addComponent(chart.columnIndex, chart.rowIndex, ch);
		}
		// Return generated figure
		return figure;
	}
}