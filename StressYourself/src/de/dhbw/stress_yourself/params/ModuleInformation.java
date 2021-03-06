package de.dhbw.stress_yourself.params;

/**
 * Class stores specific information about module
 * 
 * @author LukasBuchert <Lukas.Buchert@gmx.de>
 */
public class ModuleInformation {

	private String classname;
	private String name;
	private String area;
	private String description;
	private int time;
	private int points;

	public ModuleInformation(String classname, String name, String area,
			String description) {
		this.classname = classname;
		this.name = name;
		this.area = area;
		this.description = description;
	}

	public ModuleInformation(String classname, String name, String area,
			String description, int time) {
		this.classname = classname;
		this.name = name;
		this.area = area;
		this.description = description;
		this.time = time;
	}

	public ModuleInformation(String name, int time) {
		this.name = name;
		this.time = time;
	}

	public String getName() {
		return this.name;
	}

	public String getClassName() {
		return this.classname;
	}

	public String getArea() {
		return this.area;
	}

	public String getDescription() {
		return this.description;
	}

	public int getTime() {
		return this.time;
	}

	public int getPoints() {
		return this.points;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * compares current name with moduleName
	 * 
	 * @param moduleName
	 *            name of object to compare with
	 * @return true - if equal false - if not
	 */
	public boolean equals(String moduleName) {
		return this.name.equals(moduleName);
	}

	/**
	 * compares current object with element
	 * 
	 * @param element
	 *            ModuleInformation object to compare with
	 * @return true - if equal false - if not
	 */
	public boolean equals(ModuleInformation element) {
		return this.name.equals(element.name);
	}

	/**
	 * synchronizes the current object with element
	 * 
	 * @param element
	 *            ModuleInformatio object to synchronize with
	 */
	public void synchronize(ModuleInformation element) {
		this.classname = element.classname;
		this.area = element.area;
		this.description = element.description;
	}
}
