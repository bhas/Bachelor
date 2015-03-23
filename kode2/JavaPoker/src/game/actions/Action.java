package game.actions;

public abstract class Action {

	protected String name;
	protected int value;

	public Action(String name) {
		this.name = name;
	}
	
	public Action(String name, int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name + " $" + value;
	}
}
