package co.com.ingeneo.utils.web.util;

public class SingletonCache {
	
	private static SingletonCache INSTANCE = null;
	private GeoStructure[] structure;

	public static SingletonCache getInstance() {
		synchronized (SingletonCache.class) {
			if (INSTANCE == null) {
				INSTANCE = new SingletonCache();
			}
		}
		return INSTANCE;
	}

	/**
	 * @return the structure
	 */
	public GeoStructure[] getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(GeoStructure[] structure) {
		this.structure = structure;
	}
}
