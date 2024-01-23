package tw.niq.domain;

public enum MenuType {
	
	NONE("None"), 
	CATALOG("Catalog"), 
	MENU("Menu");

	private final String displayValue;
    
    private MenuType(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }

}
