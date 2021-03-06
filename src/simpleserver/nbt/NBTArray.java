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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NBTArray extends NBTag {
  private byte[] value;

  NBTArray() {
    super();
  }

  NBTArray(DataInputStream in, Boolean named) throws Exception {
    super(in, named);
  }

  public NBTArray(String name, byte[] array) {
    super(name);
    value = array;
  }

  @Override
  protected byte id() {
    return 7;
  }

  @Override
  public byte[] get() {
    return value;
  }

  byte get(int index) {
    return value[index];
  }

  @Override
  void set(String value) {
    if (value.length() % 2 != 0) {
      throw new NumberFormatException("Number of characters is not even");
    }
    this.value = new byte[value.length() / 2];
    for (int i = 0; i < value.length() / 2; i++) {
      this.value[i] = Integer.valueOf(value.substring(i * 2, i * 2 + 2), 16).byteValue();
    }
  }

  public void set(byte[] value) {
    this.value = value;
  }

  public void set(int index, byte value) {
    this.value[index] = value;
  }

  @Override
  protected void loadValue(DataInputStream in) throws IOException {
    int length = in.readInt();
    value = new byte[length];
    for (int i = 0; i < length; i++) {
      value[i] = in.readByte();
    }
  }

  @Override
  protected void saveValue(DataOutputStream out) throws IOException {
    out.writeInt(value.length);
    out.write(value);
  }

  @Override
  protected String valueToString(int level) {
    StringBuilder string = new StringBuilder();
    for (byte b : value) {
      string.append(String.format("%02x", b));
      string.append(" ");
    }
    if (string.length() > 0) {
      string.deleteCharAt(string.length() - 1);
    }
    return string.toString();
  }
}
