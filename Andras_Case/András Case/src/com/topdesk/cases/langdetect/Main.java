package com.topdesk.cases.langdetect;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * TODO robi : szerintem ez a komment mehetne egy fő README-be
 */

/**
 * based on training data (i.e. a text from each given language), 
 * determines the language of a previously unseen text document,
 * see usage for parameters 
 */
public class Main
{

  protected void printUsage()
  {
    System.out.println("usage: Main -decide [textfilename]" );
    System.out.println("       Main -train modelname [textfilename]");
  }

  /**
   * TODO robi : exception-t ha dobunk akkor saját kellene szerintem, mert ha külső szemmel meghívja valaki
   * a readModels()-t és megkapja ezt a kettőt, akkor nem tudhatja, hogy mégis miért történhet ez, ergo nem tudja jól
   * lekezelni
   */
  protected ModelGroup readModels() throws IOException, ClassNotFoundException
  {
    ModelGroup modelGroup = new SimpleMaxprobSelector();
    File currentDir = new File(".");
    /**
     * TODO robi : a listfiles részt belehetne rakni egy külön változóba olvashatóság miatt
     *
     * egyébként az IDEA nagyon sír amitt is, hogy null válasza is lehet a listFiles()-nak, azt is le kellene kezelni
     *
     *
     * ugyan úgy használhatnánk a java nio-s fileokat
     */
    for (File modelFile : currentDir.listFiles(
        new FileFilter()
        {
          @Override
          public boolean accept(File pathname)
          {
            return pathname.getName().endsWith(".model");
          }
        }))
    {

      /**
       * TODO robi : try with resources
       */
      ObjectInputStream objectInputStream = new ObjectInputStream(
          new FileInputStream(modelFile));
      modelGroup.add((Model)objectInputStream.readObject());
      objectInputStream.close();
    }
    return modelGroup;
  }
  
  protected void run(String[] args)
  {
    try
    {
      /**
       * TODO robi : itt szerintem az egész input validációs részt ki lehetne emelni egy külön function-be, és már is
       * sokkal kevesebb if lenne
       *
       */
      if (args.length == 0) printUsage();
      else if (args[0].equals("-decide"))
      {
        if (args.length != 2) printUsage();
        else
        {
          /**
           * TODO robi : itt mehetne egy változóba az args[1], hogy tudjuk, hogy ez mire is utal
           */
          String text = TextFileReader.read(new File(args[1]));
          ModelGroup modelGroup = readModels();
          Model model = modelGroup.select(text);
          System.out.println("selected model " + model.getName());
        }
      }
      else if (args[0].equals("-train"))
      {
        if (args.length != 3) printUsage();
        else
        {
          String modelName = args[1];
          /**
           * TODO robi : itt mehetne egy változóba az args[2], hogy tudjuk, hogy ez mire is utal
           */
          String text = TextFileReader.read(new File(args[2]));
          /**
           * TODO robi : ha nincs dependency injection akkor ez lehetne egy static function
           */
          Model model = new CharBigramBuilder().build(modelName, text);
          /**
           * TODO robi : try with resources
           */
          ObjectOutputStream objectOutputStream = 
            new ObjectOutputStream(new FileOutputStream(new File(modelName + ".model")));
          objectOutputStream.writeObject(model);
          objectOutputStream.close();
        }
      }
      else printUsage();
    }
    /**
     * TODO robi : részletesebb errorok?
     */
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) 
    {
      e.printStackTrace();
    }
 
  }
  
  public static void main(String[] args)
  {
    new Main().run(args);
  }
  
}
