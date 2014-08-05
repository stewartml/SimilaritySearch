package metricspaces.update.common;

import java.nio.ByteBuffer;

public abstract class AbstractObjectSelector<DescriptorType> implements ObjectSelector {
	protected AbstractMetricSpace<DescriptorType> space;
	protected DescriptorFormat<DescriptorType> outputFormat;
	
	protected AbstractObjectSelector(AbstractMetricSpace<DescriptorType> space) {
		this.space = space;
	}
	
	@Override
	public void setOutputBuffer(ByteBuffer buffer, int size) {
		outputFormat = space.descriptors.getFormat(buffer, size);
	}
	
	protected void save(DescriptorType descriptor) {
		if (outputFormat != null)
			outputFormat.put(descriptor);
	}
}
