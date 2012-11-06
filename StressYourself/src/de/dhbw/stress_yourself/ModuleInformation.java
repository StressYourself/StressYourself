package de.dhbw.stress_yourself;

/**
 * Class stores specific information about module
 * 
 * @author LukasBuchert <email>
 */
public class ModuleInformation {

	private String name;
	private String classname;
	private String area;
	private String description;
	private int time;
	private int points;

	public ModuleInformation(String name,String classname, String area, String description) {
		this.name = name;
		this.classname = classname;
		this.area = area;
		this.description = description;
	}

	public ModuleInformation(String name, String classname, String area, String description,
			int time) {
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

	public boolean equals(String moduleName) {
		return this.name.equals(moduleName);
	}

	public boolean equals(ModuleInformation element) {
		return this.name.equals(element.name);
	}
}
