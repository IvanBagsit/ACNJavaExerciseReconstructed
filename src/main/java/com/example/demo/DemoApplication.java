package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class DemoApplication implements CommandLineRunner {

	private final Configuration configuration;

	public DemoApplication(Configuration configuration) {
		this.configuration = configuration;
	}

	record Person (String firstName, int age){}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createFile();
	}

	private void createFile() throws IOException {
		List<String> names = List.of("Mary", "Alina", "John", "Nicole", "Mike");

		printATxt(names);
		log.info("count of names with 4 character long: " + getPersonsWithLength(names));
		List<Person> personList = convertStringToPerson(names);
		printBTxt(personList);
	}

	private void printATxt(List<String> names) throws IOException {
		File file = new File(configuration.directory + configuration.fileName);
		FileWriter fileWriter = new FileWriter(file);

		names
				.forEach(name -> {
					try {
						fileWriter.write(name + "\n");
					} catch (IOException e) {
						log.error("Error encountered during writing: " + e);
					}
				});

		fileWriter.close();
	}

	private void printBTxt(List<Person> personList) {
		File file = new File(configuration.directory + configuration.fileName2);
		try(FileWriter fileWriter = new FileWriter(file)) {

			fileWriter.write("CSV Format:\n");
			fileWriter.write("FirstName, Age\n");
			printCSVFormat(personList, fileWriter);

			List<Person> personsOver24 = personList
					.stream()
					.filter(person -> person.age > 24)
					.toList();
			fileWriter.write("\nPersons with age over 24\n");
			printCSVFormat(personsOver24, fileWriter);

		} catch (IOException e) {
			log.error("Error encountered during writing of CSV Format: " + e);
		}
	}

	private void printCSVFormat (List<Person> personList, FileWriter fileWriter) {
		personList
				.forEach(person -> {
					try {
						fileWriter.write(person.firstName + ", " + person.age + "\n");
					} catch (IOException e) {
						log.error("Unable to write data: " + e);
					}
				});
	}

	private long getPersonsWithLength(List<String> names) {
		return names
				.stream()
				.filter(name -> name.length() == 4)
				.count();
	}

	private List<Person> convertStringToPerson(List<String> names) {
		List<Person> personList = new ArrayList<>();
		names.forEach(name -> {
			Person person = new Person(name, name.length() + 20);
			personList.add(person);
		});
		return personList;
	}
}
