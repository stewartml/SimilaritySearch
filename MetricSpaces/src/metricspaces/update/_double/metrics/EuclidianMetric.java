package metricspaces.update._double.metrics;

import metricspaces.descriptors.DoubleDescriptor;
import metricspaces.update.common.AbstractMetric;

public class EuclidianMetric extends AbstractMetric<DoubleDescriptor> {

	@Override
	public double getDistance(DoubleDescriptor x, DoubleDescriptor y) {
		double[] xdata = x.getData();
		double[] ydata = y.getData();	
		double acc = 0;
		
		for (int i = 0; i < xdata.length; i++) {
            final double d = xdata[i] - ydata[i];
            acc += d * d;
        }

        return Math.sqrt(acc);
	}
}
