package me.mesh.sim.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileLoader
{
	public static String readFromFile(String fileName)
	{

		StringBuilder source = new StringBuilder();
		try(
			BufferedReader read = new BufferedReader(new InputStreamReader(FileLoader.class.getClassLoader().getResourceAsStream(fileName)))
		)
		{
			String line;
			while ((line = read.readLine()) != null)
			{
				source.append(line).append('\n');
			}

		}
		catch(IOException ex)
		{
			System.err.format("Error loading %s ", fileName);
			ex.printStackTrace();
		}
		
		return source.toString();
	}
	
	public static String[] readAllLines(String fileName)
	{
		return readFromFile(fileName).split("\n");
	}
}

