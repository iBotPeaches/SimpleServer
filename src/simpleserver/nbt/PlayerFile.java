/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.nbt;

import java.io.File;
import java.io.IOException;

import simpleserver.Coordinate;
import simpleserver.Server;

public class PlayerFile {
  private String filename;
  private NBTFile nbt;

  public PlayerFile(String name, Server server) {
    this(server.options.get("levelName") + File.separator + "players" + File.separator + name + ".dat");
  }

  public PlayerFile(String filename) {
    this.filename = filename;
    try {
      nbt = new GZipNBTFile(filename);
    } catch (Exception e) {
      try {
        nbt = new GZipNBTFile(getClass().getResourceAsStream("template.dat"));
      } catch (IOException e1) {

      }
    }
  }

  public void setPosition(Coordinate coord) {
    NBTList<NBTDouble> pos = new NBTList<NBTDouble>("Pos", NBT.DOUBLE);

    pos.add(new NBTDouble(coord.x()));
    pos.add(new NBTDouble(coord.y()));
    pos.add(new NBTDouble(coord.z()));

    nbt.root().put(pos);
    nbt.root().put(new NBTInt("Dimension", coord.dimension().index()));
  }

  public void setInventory(Inventory inv) {
    nbt.root().put(inv.nbt());
  }

  public void save() {
    nbt.save(filename);
  }

  public File file() {
    return new File(filename);
  }

  @Override
  public String toString() {
    return nbt.toString();
  }
}
