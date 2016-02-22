
public class Record {
	private Attribute[] attributes;
	private ClassName className;

	private Record(Attribute[] attributes, ClassName className) {
		this.attributes = attributes;
		this.className = className;
	}

	public ClassName getClassName() {
		return this.className;
	}
}
