package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class KategorieRepresentation {
    private final String name;
    private String id;
    private boolean isMixable;

    public KategorieRepresentation(String id, String name, boolean isMixable) {
        this.id = id;
        this.isMixable = isMixable;
        this.name = name;
    }
    
    
    

	public boolean isMixable() {
		return isMixable;
	}




	public void setMixable(boolean isMixable) {
		this.isMixable = isMixable;
	}




	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
