package enums;

public enum Difficulty {
	EASY("Easy"),
	MEDIUM("Medium"),
	HARD("Hard");
	
	private String label;
	
	private Difficulty(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
