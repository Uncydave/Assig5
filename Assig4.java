
public class Assig4 {

   public static void main(String[] args) 
   {
      // TODO Auto-generated method stub
      System.out.println("Assig4.main() runs!");
      calebDriver();

   }
   
   public static void calebDriver()
   {

      String[] sImageIn =
      {
         "                                               ",
         "                                               ",
         "                                               ",
         "     * * * * * * * * * * * * * * * * * * * * * ",
         "     *                                       * ",
         "     ****** **** ****** ******* ** *** *****   ",
         "     *     *    ****************************** ",
         "     * **    * *        **  *    * * *   *     ",
         "     *   *    *  *****    *   * *   *  **  *** ",
         "     *  **     * *** **   **  *    **  ***  *  ",
         "     ***  * **   **  *   ****    *  *  ** * ** ",
         "     *****  ***  *  * *   ** ** **  *   * *    ",
         "     ***************************************** ",  
         "                                               ",
         "                                               ",
         "                                               "

      }; 
      BarcodeImage bc2 = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix();
      dm.scan(bc2);
      dm.translateImageToText();
      dm.displayImageToConsole();
      dm.displayTextToConsole();
      
      String[] sImageIn_2 =
            {
                  "                                          ",
                  "                                          ",
                  "* * * * * * * * * * * * * * * * * * *     ",
                  "*                                    *    ",
                  "**** *** **   ***** ****   *********      ",
                  "* ************ ************ **********    ",
                  "** *      *    *  * * *         * *       ",
                  "***   *  *           * **    *      **    ",
                  "* ** * *  *   * * * **  *   ***   ***     ",
                  "* *           **    *****  *   **   **    ",
                  "****  *  * *  * **  ** *   ** *  * *      ",
                  "**************************************    ",
                  "                                          ",
                  "                                          ",
                  "                                          ",
                  "                                          "
      
            };
      BarcodeImage bc3 = new BarcodeImage(sImageIn_2);
      DataMatrix dm2 = new DataMatrix();
      dm2.scan(bc3);
      dm2.translateImageToText();
      dm2.displayImageToConsole();
      dm2.displayTextToConsole();
     
      String[] sImageIn_3 =
         {
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          "
   
         };
   BarcodeImage bc4 = new BarcodeImage(sImageIn_3);
   DataMatrix dm3 = new DataMatrix();
   dm3.scan(bc4);
   dm3.translateImageToText();
   dm3.displayImageToConsole();
   dm3.displayTextToConsole();
   
   int test = 34444;
   System.out.println((char)test);
   }

}

interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
}

class BarcodeImage implements Cloneable
{
   //The exact internal dimensions of 2D data.
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   
   /*
   This is where to store your image.  If the
   incoming data is smaller than the max, instantiate
   memory anyway, but leave it blank (white). This
   data will be false for elements that are white,
   and true for elements that are black.
   */
   private boolean[][] image_data;
   
   
   /*
   Constructors.  Two minimum, but you could have others:
    */
   public BarcodeImage()
   {
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      for (int row = 0; row < image_data.length; row++)
      {
         for (int column = 0; column < image_data[row].length; column++)
         {
            image_data[row][column] = false;
         }
      }
   }

   public BarcodeImage(String[] str_data)
   {
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      // Make sure string is not null or larger than MAX_HEIGHT and MAX_WIDTH
      if (checkSize(str_data))
      {
         // Nested for loop to set str_data '*' values to true and ' ' to false
         // in image_data
         for (int row = 0; row < str_data.length; row++)
         {
            for (int width = 0; width < str_data[row].length(); width++)
            {
               if (str_data[row].charAt(width) == '*')
               {
                  image_data[MAX_HEIGHT - str_data.length + row][width] = true;
               } else if (str_data[row].charAt(width) == ' ')
               {
                  image_data[MAX_HEIGHT - str_data.length + row][width] = false;
               }
            }
         }
      }
      //displayToConsole();
   }

   // Returns false if String array is null, or exceeds MAX_HEIGHT or MAX_WIDTH
   // Returns true if String array is smaller or same size as MAX_HEIGHT and
   // MAX_WIDTH
   private boolean checkSize(String[] data)
   {
      if (data == null)
      {
         return false;
      }
      if (data.length > MAX_HEIGHT)
      {
         return false;
      }
      for (String s : data)
      {
         if (s.length() > MAX_WIDTH)
         {
            return false;
         }
      }
      return true;
   }

   // returns a single pixel if row and col are valid values for image_data,
   // false if they are invalid values
   public boolean getPixel(int row, int col)
   {
      if (row > MAX_HEIGHT || row < 0)
      {
         return false;
      }
      if (col > MAX_WIDTH || col < 0)
      {
         return false;
      }
      return image_data[row][col];
   }

   // sets the pixel of image_data if row and col exist in image_data, returns
   // false otherwise
   public boolean setPixel(int row, int col, boolean value)
   {
      if (row > MAX_HEIGHT || row < 0)
      {
         return false;
      }
      if (col > MAX_WIDTH || col < 0)
      {
         return false;
      }
      image_data[row][col] = value;
      return true;
   }

   // Returns a BarcodeImage object identical to current BarcodeImage object
   public BarcodeImage clone() throws CloneNotSupportedException
   {

      BarcodeImage clone = new BarcodeImage();
      for (int row = 0; row < MAX_HEIGHT; row++)
      {
         for (int width = 0; width < MAX_WIDTH; width++)
         {
            clone.setPixel(row, width, image_data[row][width]);
         }
      }
      return clone;

   }
   private void displayToConsole()
   {
      /*
      Optional - A displayToConsole() method that is
      useful for debugging this class, but not very
      useful for the assignment at large.
       */
      //System.out.println(image_data);
      for(int x = 0; x < MAX_WIDTH+2; x++)
      {
         System.out.print("-");
      }
      System.out.println();
      for(int y = 0; y < MAX_HEIGHT; y++)
      {
         System.out.print("|");
         for(int x = 0; x < MAX_WIDTH; x++)
         {
            if(image_data[y][x])
            {
               System.out.print("*");
            }
            else
            {
               System.out.print(" ");
            }
            
         }
         System.out.println("|");
      }
      for(int x = 0; x < MAX_WIDTH+2; x++)
      {
         System.out.print("-");
      }
   }
   
   
}

class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   
   /*
    a single internal copy of any image scanned-in OR
   passed-into the constructor OR created by
   BarcodeIO's generateImageFromText().
    */
   private BarcodeImage image;
   
   /*
   a single internal copy of any text read-in OR
   passed-into the constructor OR created by
   BarcodeIO's translateImageToText(). 
    */
   private String text;
   
   
   /*
   two ints that are typically less than
   BarcodeImage.MAX_WIDTH and BarcodeImage.MAX_HEIGHT
   which represent the actual portion of the
   BarcodeImage that has the real signal.  This is
   dependent on the data in the image, and can change
   as the image changes through mutators.  It can be
   computed from the "spine" of the image.
    */
   private int actualWidth, actualHeight;
   
   
   /*
   Constructors.  Three minimum, but you could have more:
    */
   
   public DataMatrix()
   {
      /*
      constructs an empty, but non-null, image and text
      value.  The initial image should be all white,
      however, actualWidth and actualHeight should start
      at 0, so it won't really matter what's in this
      default image, in practice.  The text can be set
      to blank, "", or something like "undefined".
       */
      this.text = ""; 
      this.actualWidth = 0; 
      this.actualHeight = 0;
   }
   
   public DataMatrix(BarcodeImage image)
   {
      /*
       sets the image but leaves the text at its default
      value.  Call scan() and avoid duplication of code
      here.
       */
      scan(image);
   }
   
   public DataMatrix(String text)
   {
      /*
      sets the text but leaves the image at its default
      value. Call readText() and avoid duplication of
      code here.
       */
      readText(text);
   }
   
   //Accessors for actualWidth and actualHeight but no mutators! (why?)
   public int getActualWidth()
   {
      return this.actualWidth;
   }
   
   public int getActualHeight()
   {
      return this.actualHeight;
   }
   
   public boolean scan( BarcodeImage bc )
   {
      /*
      accepts some image, represented as a BarcodeImage
      object to be described below, and stores a copy of
      this image. Depending on the sophistication of the
      implementing class, the internally stored image
      might be an exact clone of the parameter, or a
      refined, cleaned and processed image. Technically,
      there is no requirement that an implementing class
      use a BarcodeImage object internally, although we
      will do so. For the basic DataMatrix option, it
      will be an exact clone. Also, no translation is
      done here - i.e., any text string that might be
      part of an implementing class is not touched,
      updated or defined during the scan.
      
      FROM LATER IN THE SPEC: 
      a mutator for image.  Like the constructor;  in
      fact it is called by the constructor.  Besides
      calling the clone() method of the BarcodeImage
      class, this method will do a couple of things
      including calling cleanImage() and then set the
      actualWidth and actualHeight.  Because scan()
      calls clone(), it should deal with the
      CloneNotSupportedException by embeddingthe clone()
      call within a try/catch block.  Don't attempt to
      hand-off the exception using a "throws" clause
      in the function header since that will not be
      compatible with the underlying BarcodeIO
      interface.  The catches(...) clause can have an
      empty body that does nothing.
       */
      try
      {
         image = bc.clone();
      }
      catch (CloneNotSupportedException e)
      {
         
      }
      
      //set actualWidth and actualHeight
      actualHeight = computeSignalHeight();
      actualWidth = computeSignalWidth();
      
      //Indicate that the signal is improperly formatted.
      if(actualHeight <= 0 || actualWidth <= 0)
      {
         return false;
      }
      
      //clean the image
      try
      {
         cleanImage();
      }
      catch (Exception e)
      {
         //Indicate that we were unable to clean the image.
         return false;
      }
      
      return true;
      
   }

   public boolean readText( String text )
   {
      /*
       accepts a text string to be eventually encoded in
      an image. No translation is done here - i.e., any
      BarcodeImage that might be part of an implementing
      class is not touched, updated or defined during
      the reading of the text.
      
      FROM LATER IN THE SPEC: 
      a mutator for text.  Like the constructor;  in
      fact it is called by the constructor.
       */
       if(this.equals(text))
       {
          return true;
       }  
       this.text.equals("");
       {
          return false;
       }
   }

   public boolean generateImageFromText()
   {
      /*
      Not technically an I/O operation, this method
      looks at the internal text stored in the
      implementing class and produces a companion
      BarcodeImage, internally (or an image in whatever
      format the implementing class uses).  After this
      is called, we expect the implementing object to
      contain a fully-defined image and text that are in
      agreement with each other.   
      
      FROM 'OTHER CONSIDERATIONS':
      The methods generateImageFromText() and
      translateImageToText(), are the tricky parts, and
      it will                                                        help if you have some methods like the
      following to break up the work:  private char
      readCharFromCol(int col) and private boolean
      WriteCharToCol(int col, int code).  While you
      don't have to use these exact methods, you must
      not turn in huge methods generateImageFromText()
      and translateImageToText() that are not broken
      down to smaller ones.
       */
      return false;
   }

   public boolean translateImageToText()
   {
      /*
      Not technically an I/O operation, this method
      looks at the internal image stored in the
      implementing class, and produces a companion text
      string, internally.  After this is called, we
      expect the implementing object to contain a fully
      defined image and text that are in agreement with
      each other.
      
      FROM 'OTHER CONSIDERATIONS':
      The methods generateImageFromText() and
      translateImageToText(), are the tricky parts, and
      it will help if you have some methods like the
      following to break up the work:  private char
      readCharFromCol(int col) and private boolean
      WriteCharToCol(int col, int code).  While you
      don't have to use these exact methods, you must
      not turn in huge methods generateImageFromText()
      and translateImageToText() that are not broken
      down to smaller ones.
       */
      
      String message = "";
      
      for(int i = 1; i < actualWidth-1; i++)
      {
         try
         {
            //compute and collect the characters from each column
            message += readCharFromCol(i);
         }
         catch (Exception e)
         {
            //Something is wrong with the barcode image.
            return false;
         }
      }
      
      //set the text value.
      this.text = message;
      
      return true;
   }
   
   private char readCharFromCol(int col) throws Exception
   {
      /*
      FROM 'OTHER CONSIDERATIONS':
      The methods generateImageFromText() and
      translateImageToText(), are the tricky parts, and
      it will help if you have some methods like the
      following to break up the work:  private char
      readCharFromCol(int col) and private boolean
      WriteCharToCol(int col, int code).  While you
      don't have to use these exact methods, you must
      not turn in huge methods generateImageFromText()
      and translateImageToText() that are not broken
      down to smaller ones.
       */
      
      //find the offset for pulling signal from the BarcodeImage
      int offsetY = BarcodeImage.MAX_HEIGHT-actualHeight;
      
      //Multipliers for each bit in the column.
      int[] extractedColumn = new int[actualHeight];
      
      //assumes clean image with signal on bottom-left corner.
      for(int y = 0; y <  actualHeight; y++)
      {
         if(image.getPixel(offsetY + y, col))
         {
            extractedColumn[y] = 1;
         }
         else
         {
            extractedColumn[y] = 0;
         }
      }
      
      //collector for the ASCII character value
      int characterValue = 0;
      
      //compute, ignoring top and bottom rows
      for(int i = 1; i < actualHeight-1; i++)
      {
         //read the extractedColumn from the bottom up, multiplying 
         //by 2^n_row. Complicated because n_row is going down in value while i is going up.
         characterValue += ((int)Math.pow(2, i-1)) * extractedColumn[actualHeight-1-i];
      }
      

      if(characterValue > 255 || characterValue < 0)
      {
         throw new Exception("Invalid character value in column!");
      }
      return (char) characterValue;
   }
   
   private boolean writeCharToCol(int col, int code)
   {
      /*
      FROM 'OTHER CONSIDERATIONS':
      The methods generateImageFromText() and
      translateImageToText(), are the tricky parts, and
      it will help if you have some methods like the
      following to break up the work:  private char
      readCharFromCol(int col) and private boolean
      WriteCharToCol(int col, int code).  While you
      don't have to use these exact methods, you must
      not turn in huge methods generateImageFromText()
      and translateImageToText() that are not broken
      down to smaller ones.
       */
      
      return false;
   }

   public void displayTextToConsole()
   {
      /*
      prints out text string to console
       */
      System.out.println(this.text);
   }

   public void displayImageToConsole()
   {
      /*
      prints out the image to the console.  In our
      implementation, we will do this in the form of a
      dot-matrix of blanks and asterisks, e.g.,
      
      UNDER 'OTHER CONSIDERATIONS':
       should display only the relevant portion of the
      image, clipping the excess blank/white from the
      top and right.  Also, show a border
      
      Caleb: SEE 'OTHER CONSIDERATIONS' FOR EXAMPLES
       */
      
      int[] origin = findSpineOrigin();
      

      //could not find the signal origin.
      if(origin[0] < 0 || origin[1] < 0)
      {
         //This is here so we can at least get some kind of output.
         displayRawImage();
      }
      else
      {
         //Initialize the values if not already done
         if(actualHeight <= 0 || actualWidth <= 0)
         {
            actualHeight = computeSignalHeight();
            actualWidth = computeSignalWidth();
         }
         
         System.out.println();
         for(int x = 0; x < actualWidth+2; x++)
         {
            System.out.print("-");
         }
         System.out.println();
         for(int y = 0; y < actualHeight; y++)
         {
            System.out.print("|");
            for(int x = 0; x < actualWidth; x++)
            {
               
               if(image.getPixel(origin[1]-actualHeight+y+1, x+origin[0]))
               {
                  System.out.print(BLACK_CHAR);
               }
               else
               {
                  System.out.print(WHITE_CHAR);
               }
            }
            System.out.println("|");
         }
         for(int x = 0; x < actualWidth+2; x++)
         {
            System.out.print("-");
         }
         System.out.println();
      }
      

   }
   
   private int[] findSpineOrigin()
   {
      /*
       * This method finds the bottom left
       * corner, or 'origin,' of the signal.
       */
      boolean hasFoundSomething = false;
      
      //Starting coordinates of the data's spine.
      int originX, originY;
      originX = originY = -1;
      
      //looking for image 'spine' so we start at bottom left
      for(int y = BarcodeImage.MAX_HEIGHT-1; y >= 0 && !hasFoundSomething; y--)
      {
         for(int x = 0; x < BarcodeImage.MAX_WIDTH && !hasFoundSomething; x++)
         {
            //We keep searching rows until we find something.
            if(image.getPixel(y,x)){
               hasFoundSomething = true;
               originY = y;
               originX = x;
            }
         }
      }
      
      //returning a coordinate pair. 
      int[] coords = {originX,originY};
      return coords;
   }
   
   private int computeSignalWidth()
   {
      /*
       * Find the signal within the BarcodeImage
       * and computes its width.
       */
      int signalWidth = 0;

      //finds the bottom-left point, or origin, of the signal.
      int[] originCoordinates = findSpineOrigin();
      
      //Count how many columns the spine is present within.
      for(int x = originCoordinates[0]; x < BarcodeImage.MAX_WIDTH; x++){
         if(image.getPixel(originCoordinates[1], x))
         {
            signalWidth++;
         }
      }
      
      return signalWidth;
   }
   
   private int computeSignalHeight()
   {
      /*
       * Find the signal within the BarcodeImage
       * and computes its height.
       */
      int signalHeight = 0;
      
      //finds the bottom-left point, or origin, of the signal.
      int[] originCoordinates = findSpineOrigin();
      
      //Count how many rows the spine is present within.
      for(int y = originCoordinates[1]; y >= 0; y--){
         if(image.getPixel(y, originCoordinates[0]))
         {
            signalHeight++;
         }
      }
      
      return signalHeight;
   }
   
   private void cleanImage() throws Exception
   {
      /*
      This private method will make no assumption about
      the placement of the "signal" within a passed-in
      BarcodeImage.  In other words, the in-coming
      BarcodeImage may not be lower-left justified. 
       */
      try
      {
         moveImageToLowerLeft();
      }
      catch (Exception e)
      {
         throw new Exception("Could not clean image!\n" + e.getMessage());
      }
   }
   
   private void moveImageToLowerLeft() throws Exception
   {
      /*
       * This function draws the BarcodeImage's
       * signal in the lower-left corner of 
       * the total image space.
       */
      try
      {
         drawImageAtOrigin(0,BarcodeImage.MAX_HEIGHT-1);
      }
      catch (Exception e)
      {
         throw new Exception("Could not draw image in lower-left corner!\n" + 
               e.getMessage());
      }

   }
   
   private boolean[][] getSignalOnly(int[] origin)
   {
      /*
       * This function is a helper function that makes a
       * signal map. It finds and copies only the portion
       * of the BarcodeImage that has signal and makes a
       * 2d array of booleans (signalMap) to represent it.
       * 
       * This function is used by drawImageAtOrigin()
       * which move the Signal portion to a desired origin
       * 
       * returns null if there is no signal.
       */
      boolean[][] signalMap;
      
      //if actualWidth and actualHeight have been computed and stored.
      if(actualWidth > 0 && actualHeight > 0)
      {
         signalMap = new boolean[actualHeight][actualWidth];
         for(int y = 0; y < actualHeight; y++)
         {
            for(int x = 0; x < actualWidth; x++)
            {
               
               try
               {
                  //fill the signalMap
                  signalMap[y][x] = image.getPixel(origin[1]+1+y-actualHeight, x+origin[0]);   
               }
               catch (IndexOutOfBoundsException e)
               {
                  //this is invoked if the values of origin[] push us out of bounds.
                  return null;
               }
            }
         }
         //return the extracted signal map.
         return signalMap;
      }
      //This section is invoked if actualHeight or actualWidth are 0
      else 
      {
         int sigWidth = computeSignalWidth();
         int sigHeight = computeSignalHeight();
         
         //Here we recall the function with the correct values setup.
         if(sigWidth > 0 && sigHeight > 0)
         {
            actualHeight = sigHeight;
            actualWidth = sigWidth;
            return getSignalOnly(origin);
         }
         //Default is return null.
         else
         {
            return null;
         }
      }

      
   }
   
   private void drawImageAtOrigin(int targetX, int targetY) throws Exception
   {
      /*
       * This function will draw the BarcodeImage's signal
       * at a particular set of image coordinates (targetX, targetY).
       */
      //Find the origin point of the signal
      int[] originCoordinates = findSpineOrigin();
      
      //We extract the signal into a boolean[][] representation
      boolean[][] signalMap = getSignalOnly(originCoordinates);
      
      //change this.image only if a signal was found and extracted.
      if(signalMap != null)
      {
         //we make sure the origin is valid
         if(targetX+actualWidth > BarcodeImage.MAX_WIDTH || 
               targetY-actualHeight < 0)
         {
            throw new Exception("Invalid origin values!");
         }
         
         clearImage();
         
         //input the signalMap
         for(int y = signalMap.length-1; y >= 0; y--)
         {
            for(int x = 0; x < signalMap[y].length; x++)
            {
               image.setPixel(targetY-y, 
                        targetX+x, signalMap[signalMap.length-1 - y][x]);
            }
         }
      }
      else
      {
         throw new Exception("There is no signal in this BarcodeImage!");
      }

   }
   public void displayRawImage()
   {
      /*
      Optional - public void displayRawImage() can be
      implemented to show the full image data including
      the blank top and right.  It is a useful debugging
      tool.
       */
      
      //add top line
      for(int x = 0; x < BarcodeImage.MAX_WIDTH+2; x++)
      {
         System.out.print("-");
      }
      System.out.println();
      //translate BarcodeImage into text.
      for(int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
      {
         System.out.print("|");
         for(int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
         {
            if(image.getPixel(y, x))
            {
               System.out.print(BLACK_CHAR);
            }
            else
            {
               System.out.print(WHITE_CHAR);
            }
            
         }
         System.out.println("|");
      }
      //Add bottom line
      for(int x = 0; x < BarcodeImage.MAX_WIDTH+2; x++)
      {
         System.out.print("-");
      }
      System.out.println();
   
   }
   
   private void clearImage()
   {
      /*
      Optional - private void clearImage() - a nice
      utility that sets the image to white =  false.
       */
      //clear the barcode image.
      for(int y = 0; y < BarcodeImage.MAX_HEIGHT; y++)
      {
         for(int x = 0; x < BarcodeImage.MAX_WIDTH; x++)
         {
            image.setPixel(y, x, false);
         }
      }
   }
}
   

