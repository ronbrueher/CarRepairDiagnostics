package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;

public class CarDiagnosticEngine {

	public static final String YEAR = "Year";
	public static final String MAKE = "Make";
	public static final String MODEL = "Model";
	// Retrieve line separator of OS
	public static final String NEWLINE = System.getProperty("line.separator");

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 */

		// Validate "year" data field
		validateString(car.getYear(), YEAR);

		// Validate "make" data field
		validateString(car.getMake(), MAKE);

		// Validate "model" data field
		validateString(car.getModel(), MODEL);

		/*      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 */

		// Get the map of missing parts
		Map<PartType, Integer> missingMap = car.getMissingPartsMap();

		// Loop through missing parts map and print the missing part types and counts
		for (Map.Entry<PartType, Integer> part : missingMap.entrySet()) {
			PartType partType = part.getKey();
			Integer count = part.getValue();

			try {
				printMissingPart(partType, count);
			}
			catch (IllegalArgumentException e){
				System.out.println(String.format(NEWLINE + "Missing Part(s) Detected: %s - Count: %s", partType, count));
			}
			finally {
				System.exit(1);
			}
		}

		/*      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 */

		// Loop through all parts and print any that are not in working condition
		for (Part part : car.getParts()) {
			if (!part.isInWorkingCondition()) {
				try {
					printDamagedPart(part.getType(), part.getCondition());
				}
				catch (IllegalArgumentException e) {
					System.out.println(String.format(NEWLINE + "Damaged Part Detected: %s - Condition: %s", part.getType(), part.getCondition()));
				}
				finally {
					System.exit(1);
				}
			}
		}

		/*      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 *                 A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 */

		// All validations have passed, print success message to user
		System.out.println(String.format(NEWLINE + "All parts are in working condition for: %s %s %s", car.getYear(), car.getMake(), car.getModel()));

		/* Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

	}

	private void validateString(String field, String name) {
		if (field == null || field.isEmpty())  {
			System.out.println(NEWLINE + "Vehicle " + name + " field is missing or empty");
			System.exit(1);
		}
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format(NEWLINE + "Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format(NEWLINE + "Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);
	}

}
