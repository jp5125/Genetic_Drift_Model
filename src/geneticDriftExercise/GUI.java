package geneticDriftExercise;

import java.awt.Color;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;

import sim.engine.SimState;
import sim.engine.Steppable;
import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class GUI extends GUIStateSweep
{
    public GUI(SimStateSweep state, int gridWidth, int gridHeight,
               Color backdrop, Color agentDefaultColor,
               boolean agentPortrayal)
    {
        super(state, gridWidth, gridHeight, backdrop,
              agentDefaultColor, agentPortrayal);
    }

    @Override
    public void setupPortrayals()
    {
        
        chartTimeSeries.removeAllSeries();

        series = new XYSeries("Allele 1 frequency", false);
        chartTimeSeries.addSeries(series, null);
        NumberAxis generationAxis =
        	    (NumberAxis) chartTimeSeries.getChart()
        	                               .getXYPlot()
        	                               .getDomainAxis();

        	generationAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        Environment environment = (Environment) state;

        // Record the initial population before reproduction.
        series.add(
            state.schedule.getSteps(),
            environment.getAllele1Frequency(),
            true
        );

        // Record each new population after reproduction.
        scheduleRepeatingImmediatelyAfter(new Steppable()
        {
        	@Override
        	public void step(SimState state)
        	{
        	    Environment environment = (Environment) state;

        	    long generation = state.schedule.getSteps();
        	    double frequency = environment.getAllele1Frequency();

        	    // Record the generation, including the final fixation or loss.
        	    series.add(generation, frequency, true);
        	    chartTimeSeries.updateChartWithin(generation, 100);

        	    if (frequency == 0.0 || frequency == 1.0)
        	    {
        	        state.schedule.clear();
        	    }
        	}
        });
    }

    public static void main(String[] args)
    {
        // Enable the chart BEFORE initializing the GUI.
        initializeTimeSeriesChart(
            "Genetic drift",
            "Generation",
            "Allele 1 frequency"
        );

        initialize(
            Environment.class,
            null,
            GUI.class,
            400,
            400,
            Color.WHITE,
            Color.BLUE,
            false,
            Spaces.SPARSE
        );
    }
}