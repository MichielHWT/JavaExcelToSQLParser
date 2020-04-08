/*
This programm specifically reads the excel file nevo_online_2019.xls by line and takes
the columns needed for the project maaltijdplanner and saves them in SQL format file

Author: Michiel Herpers
Date: 6/4/2020
 */

package com.michiel.exceltosqlparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;


@SpringBootApplication
public class ExceltosqlparserApplication {

	public static void main(String[] args) throws FileNotFoundException {
		//SpringApplication.run(ExceltosqlparserApplication.class, args);

		FileParser parser = new FileParser();

	}

}
