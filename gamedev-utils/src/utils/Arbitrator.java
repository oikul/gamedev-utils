package utils;

public class Arbitrator {

	private Behavior[] behaviors;

	public Arbitrator(Behavior[] behaviours) {
		this.behaviors = behaviours;
	}

	public void start() {
		int index = 0;
		while (true) {
			while (index < behaviors.length - 1 && behaviors[index + 1].takeOver()) {
				index++;
			}
			while (behaviors[index].takeOver()) {
				behaviors[index].action();
			}
			index = 0;
		}
	}

}
