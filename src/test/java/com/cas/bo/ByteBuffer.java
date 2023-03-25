//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cas.bo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteBuffer {
    private ByteArrayOutputStream os = new ByteArrayOutputStream();

    public ByteBuffer() {
    }

    public synchronized ByteBuffer append(byte[] bs) {
        try {
            this.os.write(bs);
            return this;
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public synchronized ByteBuffer append(byte b) {
        this.os.write(b);
        return this;
    }

    public synchronized ByteBuffer append(ByteBuffer bb) {
        try {
            bb.os.writeTo(this.os);
            return this;
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }


    public byte[] toByteArray() {
        return this.os.toByteArray();
    }

    public int length() {
        return this.os.size();
    }
}
