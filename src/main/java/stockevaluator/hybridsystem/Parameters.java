package stockevaluator.hybridsystem;

import pl.zankowski.iextrading4j.api.stocks.ChartRange;

public class Parameters
{

	public double evaluationPeriod;

	/*
	 * String array containing all of the stock indicies
	 */
	public String[] stockIndicesListString;

	/*
	 * Frequency that stock history will be gathered, ie Daily, Hourly, etc
	 */
	public ChartRange stockHistoryDataInterval;

	public Parameters(ChartRange data_interval, double evaluation_period, String[] indicies)
	{
		stockHistoryDataInterval = data_interval;
		evaluationPeriod = evaluation_period;
		stockIndicesListString = indicies;
	}

}
