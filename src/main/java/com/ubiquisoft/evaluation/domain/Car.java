package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		// Create hashmap to store the frequency of parts
		Map<PartType, Integer> missingMap = createMap();

		// Update map with frequency of each part
		for (Part part : parts) {
			Integer i = missingMap.get(part.getType());
			missingMap.put(part.getType(), (i == null) ? 1 : i + 1);
		}

		// Remove parts that have correct number of occurrences so only missing parts remain
		if (missingMap.get(PartType.ENGINE) > 0) missingMap.remove(PartType.ENGINE);
		if (missingMap.get(PartType.ELECTRICAL) > 0) missingMap.remove(PartType.ELECTRICAL);
		if (missingMap.get(PartType.FUEL_FILTER) > 0) missingMap.remove(PartType.FUEL_FILTER);
		if (missingMap.get(PartType.OIL_FILTER) > 0) missingMap.remove(PartType.OIL_FILTER);
		if (missingMap.get(PartType.TIRE) > 3) missingMap.remove(PartType.TIRE);

		return missingMap;
	}

	private static Map<PartType, Integer> createMap() {
		// Initialize map of PartTypes and counts
		Map<PartType, Integer> typeMap = new HashMap<PartType, Integer>();
		typeMap.put(PartType.ENGINE, 0);
		typeMap.put(PartType.ELECTRICAL, 0);
		typeMap.put(PartType.FUEL_FILTER, 0);
		typeMap.put(PartType.OIL_FILTER, 0);
		typeMap.put(PartType.TIRE, 0);

		return typeMap;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
