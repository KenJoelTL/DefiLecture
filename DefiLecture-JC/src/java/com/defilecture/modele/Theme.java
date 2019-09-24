/**
 * This file is part of DefiLecture.
 *
 * <p>DefiLecture is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>DefiLecture is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with DefiLecture. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package com.defilecture.modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Theme {
  private String ReadFile(String nomFile) throws FileNotFoundException, IOException {
    File f = new File(this.getURL(nomFile));
    FileWriter fw;
    FileReader fr;
    fr = new FileReader(f);
    String str;
    str = "";
    int i = 0;
    while ((i = fr.read()) != -1) {
      str += (char) i;
    }
    str += (char) i;
    // Un charactère bizarre apparait à la fin du fichier
    str = str.substring(0, str.length() - 1);
    return str;
  }

  public String getURL(String nomFile) {
    return this.getClass().getResource("../../../../../" + nomFile).toString().replace("file:", "");
  }

  public void Theme() {}

  public Map<String, String> getTheme() throws FileNotFoundException, IOException {
    Map<String, String> _html = new HashMap<>();
    Map<String, String> _css = new HashMap<>();
    String str;
    int i;
    str = this.ReadFile("theme/theme.txt");
    str = str.replace("\t", "");
    String[] listeProvisoire = str.split("[\r\n]+");
    String key;
    String value;

    String[] provisoire;
    for (i = 0; i < listeProvisoire.length; i++) {
      if (!listeProvisoire[i].contains("//") && listeProvisoire[i].contains("=")) {
        provisoire = listeProvisoire[i].split("=");
        key = provisoire[0].trim();
        value = provisoire[1].trim();
        if (key.startsWith("css")) {
          _css.put(key, value);
        } else if (key.startsWith("voc")) {
          _html.put(key, value);
        } else if (key.startsWith("txt")) {
          value = this.ReadFile("theme/" + value);
          _html.put(key, value);
        }
      }
    }
    // Changeons maintenant le fichier de style
    str = this.ReadFile("theme/defiLectureStylesPattern.css");

    Iterator<Map.Entry<String, String>> it = _css.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> pair = it.next();
      str = str.replaceAll(pair.getKey(), pair.getValue());
    }
    try (FileWriter fw = new FileWriter(this.getURL("css/defiLectureStyles.css"), false)) {
      fw.write(str);
    }
    return _html;
  }
}
