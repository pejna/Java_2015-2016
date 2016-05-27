package hr.fer.zemris.java.graphics;

import java.util.Scanner;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;


/**
 * Demonstrates the use of {@link GeometricShape}, {@link BWRaster}
 * and {@link RasterView}. Takes 2 argumets from command line, width
 * and height of the raster in that order.
 * Than requests users to input number of shapes and then shapes in 
 * order. Shapes possible and the required information are:
 * SQUARE X Y SIZE
 * RECTANGLE X Y WIDTH HEIGHT
 * CIRCLE X Y RADIUS
 * ELLIPSE X Y HORIZONTAL_RADIUS VERTICAL_RADIUS
 * If user writes FLIP instead of a shape, the raster wll flip the
 * pixels of further shapes instead of turning them on.
 * 
 * @author Juraj Pejnovic
 * @version 10
 */
public class Demo {

	/**
	 * String representing the flip command.
	 */
	private static final String FLIP = "FLIP";
	
	/**
	 * String used for rectangle recognition.
	 */
	private static final String RECTANGLE = "RECTANGLE";
	
	/**
	 * String used for ellipse recognition.
	 */
	private static final String ELLIPSE = "ELLIPSE";
	
	/**
	 * String used for square recognition.
	 */
	private static final String SQUARE = "SQUARE";
	
	/**
	 * String used for circle recognition.
	 */
	private static final String CIRCLE = "CIRCLE";
	
	/**
	 * Represents the value in read input array if found FLIP.
	 */
	private static final GeometricShape FLIP_REPRESENTATION = null;
	
	/**
	 * Used to split the incoming arguments.
	 */
	private static final String ARGUMENT_SPLIT = " ";
	
	/**
	 * Denotes if the flip mode of the raster is active.
	 */
	private static boolean flipMode = false;
	
	
	/**
	 * Executes the program.
	 * 
	 * @param args width and height of the raster in that order
	 */
	public static void main(String[] args) {
		int width = 1;
		int height = 1;
		
		try{
		if(args.length == 1){
			int size = Integer.parseInt(args[0]);
			width = size;
			height = size;
			
		} else if(args.length == 2){
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		} else {
			System.err.println("Aborting - "
					+ "Unsupported number of arguments!");
			System.exit(-1);
		}
		}catch (Exception e){
			System.err.println("Aborting - "
					+ "Unable to read the arguments!");
			System.exit(-1);
		}
		
		Scanner reader = new Scanner(System.in);
		String input = reader.nextLine();
		int shapeNum = Integer.parseInt(input);
		String[] inputs = new String[shapeNum];
		for(int i = 0; i < inputs.length; i++){
			inputs[i] = reader.nextLine();
		}
		reader.close();
		
		GeometricShape[] shapes = parseInput(inputs);
		
		BWRaster raster = new BWRasterMem(width, height);
		
		for(int i = 0; i < shapes.length; i++){
			if(shapes[i] == FLIP_REPRESENTATION){
				flip(raster);
			} else {
				shapes[i].draw(raster);
			}
		}
		
		RasterView view = new SimpleRasterView();
		
		view.produce(raster);
		
		finish();
	}
	
	
	/**
	 * Parses the information to form an array of geometric shapes 
	 * from the given strings.
	 * 
	 * @param input strings to parse
	 * @return returns parsed geometric shapes
	 */
	private static GeometricShape[] parseInput(String[] input){
		GeometricShape[] newArray = new GeometricShape[input.length];
		
		for(int i = 0; i < input.length; i++){
			if(input[i].trim().toUpperCase().equals(FLIP)){
				newArray[i] = FLIP_REPRESENTATION;
				
			} else if(input[i].trim().split(ARGUMENT_SPLIT)[0].
					toUpperCase().equals(RECTANGLE)){
				newArray[i] = parseRectangle(input[i]);
				
			} else if(input[i].trim().split(ARGUMENT_SPLIT)[0].
					toUpperCase().equals(SQUARE)){
				newArray[i] = parseSquare(input[i]);
				
			} else if(input[i].trim().split(ARGUMENT_SPLIT)[0].
					toUpperCase().equals(CIRCLE)){
				newArray[i] = parseCircle(input[i]);
				
			} else if(input[i].trim().split(ARGUMENT_SPLIT)[0].
					toUpperCase().equals(ELLIPSE)){
				newArray[i] = parseEllipse(input[i]);
				
			} else {
				System.err.println("Aborting - Unrecognized shape!");
				System.exit(-1);
			}
		}
		return newArray;
	}
	
	
	/**
	 * Parses the information to form a rectangle from the given 
	 * string.
	 * 
	 * @param input string to parse
	 * @return returns parsed rectangle
	 */
	private static GeometricShape parseRectangle(String input){
		String[] args = input.split(ARGUMENT_SPLIT);
		
		if(args.length != 5){
			System.err.println("Aborting - "
					+ "Incorrect number of arguments for rectangle!");
			System.exit( -1);
		}
		
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		
		try{
			x = Integer.parseInt(args[1].trim());
			y = Integer.parseInt(args[2].trim());
			width = Integer.parseInt(args[3].trim());
			height = Integer.parseInt(args[4].trim());
			
			return new Rectangle(x, y, width, height);
			
		} catch(Exception e){
			System.err.println("Aborting - "
					+ "Unable to parse arguements!");
			System.exit(-1);
		}
		
		return null;
	}
	
	
	/**
	 * Parses the information to form a square from the given 
	 * string.
	 * 
	 * @param input string to parse
	 * @return returns parsed square
	 */
	private static GeometricShape parseSquare(String input){
		String[] args = input.split(ARGUMENT_SPLIT);
		
		if(args.length != 4){
			System.err.println("Aborting - "
					+ "Incorrect number of arguments for square!");
			System.exit( -1);
		}
		
		int x = 0;
		int y = 0;
		int size = 0;
		
		try{
			x = Integer.parseInt(args[1].trim());
			y = Integer.parseInt(args[2].trim());
			size = Integer.parseInt(args[3].trim());
			
			return new Square(x, y, size);
			
		} catch(Exception e){
			System.err.println("Aborting - "
					+ "Unable to parse arguements!");
			System.exit(-1);
		}
		
		return null;
	}

	
	/**
	 * Parses the information to form a circle from the given 
	 * string.
	 * 
	 * @param input string to parse
	 * @return returns parsed circle
	 */
	private static GeometricShape parseCircle(String input){
		String[] args = input.split(ARGUMENT_SPLIT);
		
		if(args.length != 4){
			System.err.println("Aborting - "
					+ "Incorrect number of arguments for circle!");
			System.exit( -1);
		}
		
		int x = 0;
		int y = 0;
		int radius = 0;
		
		try{
			x = Integer.parseInt(args[1].trim());
			y = Integer.parseInt(args[2].trim());
			radius = Integer.parseInt(args[3].trim());
			
			return new Circle(x, y, radius);
			
		} catch(Exception e){
			System.err.println("Aborting - "
					+ "Unable to parse arguements!");
			System.exit(-1);
		}
		
		return null;
	}
	
	
	/**
	 * Parses the information to form an ellipse from the given 
	 * string.
	 * 
	 * @param input string to parse
	 * @return returns parsed ellipse
	 */
	private static GeometricShape parseEllipse(String input){
		String[] args = input.split(ARGUMENT_SPLIT);
		
		if(args.length != 5){
			System.err.println("Aborting - "
					+ "Incorrect number of arguments for ellipse!");
			System.exit( -1);
		}
		
		int x = 0;
		int y = 0;
		int	horizontalRadius = 0;
		int verticalRadius = 0;
		
		try{
			x = Integer.parseInt(args[1].trim());
			y = Integer.parseInt(args[2].trim());
			horizontalRadius = Integer.parseInt(args[3].trim());
			verticalRadius = Integer.parseInt(args[4].trim());
			
			return new Ellipse(x, y, 
					horizontalRadius, verticalRadius);
			
		} catch(Exception e){
			System.err.println("Aborting - "
					+ "Unable to parse arguements!");
			System.exit(-1);
		}
		
		return null;
	}
	
	
	/**
	 * Flips the mode of the given raster.
	 * 
	 * @param raster raster to be flipped
	 */
	private static void flip(BWRaster raster){
		if(!flipMode){
			raster.enableFlipMode();
			flipMode = true;
		} else {
			raster.disableFlipMode();
			flipMode = false;
		}
	}
	
	
	/**
	 * Writes FIN to the standard output, ending the demo.
	 * 
	 */
	private static void finish(){
		BWRaster raster = new BWRasterMem(21, 7);
		raster.enableFlipMode();
		
		new Rectangle(2, 0, 2, 7).draw(raster);
		new Rectangle(4, 0, 3, 4).draw(raster);
		new Rectangle(4, 2, 3, 1).draw(raster);
		new Rectangle(9, 0, 1, 7).draw(raster);
		new Rectangle(12, 0, 2, 7).draw(raster);
		new Rectangle(14, 1, 3, 5).draw(raster);
		new Rectangle(15, 1, 2, 1).draw(raster);
		new Rectangle(14, 4, 2, 2).draw(raster);
		new Rectangle(17, 0, 2, 7).draw(raster);

		
		RasterView view = new SimpleRasterView();
		
		view.produce(raster);
	}
}

