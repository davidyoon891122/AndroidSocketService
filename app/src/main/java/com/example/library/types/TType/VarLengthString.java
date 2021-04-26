package com.example.library.types.TType;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class VarLengthString {
    public static final String DefaultEncoding = "UTF-8";
    private ByteBuffer _data;

    public VarLengthString(String value) {
        try{
            byte[] bytes = value.getBytes(DefaultEncoding);
            init(bytes);
        } catch (Exception e) {
            init(new byte[0]);
        }
    }

    public VarLengthString(String value, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = value.getBytes(encoding);
        init(bytes);
    }

    private void init(byte[] bytes) {
        _data = ByteBuffer.allocate(bytes.length + 2);
        _data.putShort((short)bytes.length);
        _data.put(bytes);
    }

    public int getLength(){
        return _data.capacity();
    }

    public byte[] getBytes() {
        return _data.array();
    }

}
