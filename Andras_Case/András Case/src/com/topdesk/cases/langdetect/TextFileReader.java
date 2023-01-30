package com.topdesk.cases.langdetect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextFileReader
{

  /**
   * TODO robi: Charset UTF8?
   */
  public static final String encoding = "utf8";


  /**
   * TODO robi: szebb lenne elkapni az exception és sajátot dobni (ha szeretnénk dobni egyáltalán)
   */
  public static String read(File textFile) throws IOException
  {
    /**
     * TODO robi: try-with resources block jó lenne it a streamekhez.
     */
    BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(textFile),encoding));

    /**
     * TODO robi:  java nios fileokat használhatnánk pl:
     *     Files.lines(new Path(textFile)) és akkor itt stream-ben egyszerűbb lenne az összemappelés mint
     *     egy while loopban
     */
    StringBuilder sb = new StringBuilder();
    String line;

    while ((line = br.readLine()) != null)
    {
      line = line.trim().toLowerCase();
      if (line.isEmpty()) continue;
      sb.append(line + " ");
    }
    br.close();
    return sb.toString();
  }

}
