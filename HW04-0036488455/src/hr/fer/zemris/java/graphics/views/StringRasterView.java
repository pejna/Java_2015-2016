package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;


/**
 * Raster view that produces a {@link String} with the contents
 * of the given raster.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class StringRasterView implements RasterView {

	/**
	 * Character used to represent a turned-on pixel.
	 */
	private final char characterOn;
	
	/**
	 * Character used to represent a turned-off pixel.
	 */
	private final char characterOff;
	
	/**
	 * Default representation of a turned-on character.
	 */
	private static final char DEFAULT_ON = '*';
	
	/**
	 * Default representation of a turned-off character.
	 */
	private static final char DEFAULT_OFF = '.';
	
	/**
	 * Character that represents the end of a raster line.
	 */
	private static final char LINE_END = '\n';
	
	
	/**
	 * Creates a raster view with default character representation.
	 */
	public StringRasterView(){
		characterOn = DEFAULT_ON;
		characterOff = DEFAULT_OFF;
	}
	
	
	/**
	 * Creates a raster vuew with given characters as pixel
	 * representation.
	 * @param characterOn representation of a turned-on pixel
	 * @param characterOff representation of a turned-off pixel
	 */
	public StringRasterView(char characterOn, char characterOff) {
		this.characterOn = characterOn;
		this.characterOff = characterOff;
	}



	@Override
	public Object produce(BWRaster raster) {
		int width = raster.getWidth();
		int height = raster.getHeight();
		
		char[] lineBuffer = new char[width];
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(raster.isTurnedOn(x, y)){
					lineBuffer[x] = characterOn;
					
				} else {
					lineBuffer[x] = characterOff;
				}
			}
		}
		
		return stringBuilder.toString();
	}

}
