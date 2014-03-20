package ndi.commands.descriptors;

import java.io.IOException;

import metricspaces.Progress;
import metricspaces.descriptors.Descriptor;
import metricspaces.files.DescriptorFile;
import metricspaces.metrics.Metric;
import ndi.MetricLoader;
import ndi.files.DescriptorFileLoader;
import commandline.Command;
import commandline.ParameterException;
import commandline.Parameters;
import commandline.ProgressReporter;

public class SetStatsCommand implements Command {
	private Parameters parameters;
	private DescriptorFileLoader descriptorLoader;

	
	@Override
	public void init(Parameters parameters) {
		this.parameters = parameters;
		descriptorLoader = new DescriptorFileLoader(parameters);
	}

	@Override
	public void run() {
		Progress progress = new Progress();
		ProgressReporter reporter = new ProgressReporter(progress, 250);
		
		try {
			DescriptorFile<Integer, Descriptor> objects = descriptorLoader.load(parameters.require("file"));
			
			double elementMean = getElementMean(objects, progress);
			double elementMax = Double.NEGATIVE_INFINITY;
			double acc = 0;
			
			progress.setOperation("Calculating std deviation", objects.getCapacity());
			
			for (int i = 0; i < objects.getCapacity(); i++) {
				double[] data = objects.get(i).getDescriptor().getData();
				
				for (int j = 0; j < data.length; j++) {
					double d = elementMean - data[j];
					acc += d * d;
					
					if (data[j] > elementMax)
						elementMax = data[j];
				}
				
				progress.incrementDone();
			}
			
			double elementStdDev = Math.sqrt(acc / objects.getCapacity());
			
			objects.getHeader().setStats(elementMean, elementStdDev, elementMax);
			reporter.stop();
			
			DescriptorInfoCommand cmd = new DescriptorInfoCommand();
			cmd.init(parameters);
			cmd.run();
		}
		catch (ParameterException | IOException ex) {
			reporter.stop();
			System.out.println(ex.getMessage());
		}
	}
	
	
	private double getElementMean(DescriptorFile<Integer, Descriptor> objects, Progress progress) {
		double sum = 0;
		
		progress.setOperation("Calculating element mean.", objects.getCapacity());
		
		for (int i = 0; i < objects.getCapacity(); i++) {
			double[] data = objects.get(i).getDescriptor().getData();
			
			for (int j = 0; j < data.length; j++)
				sum += data[j];
			
			progress.incrementDone();
		}
		
		return sum / (objects.getCapacity() * objects.getDimensions());
	}
	

	@Override
	public String getName() {
		return "SetStats";
	}

	@Override
	public String describe() {
		parameters.describe("file", "The path to the descriptor file.");
		return "Calculates statistics about the descriptor file and stores them in the file header.";
	}

}
