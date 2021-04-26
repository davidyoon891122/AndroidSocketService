package com.example.library;

import com.example.library.types.TType.VarLengthString;



import java.nio.ByteBuffer;

public class NetoolQuery {
    protected byte[] headerBytes;
    protected MessageHeader header;

    protected byte[] dataBytes;
    protected ByteBuffer data;

    protected static final int DefaultDataBufferSize = 512;
    private int _process;

    public NetoolQuery(){
        header = new MessageHeader();
        allocateData(DefaultDataBufferSize);
    }

    protected void allocateData(int nLength){
        dataBytes = new byte[nLength];
        data = ByteBuffer.wrap(dataBytes);
    }

    public int length() {
        return headerLength() + dataLength();
    }

    static public int headerLength() {
        return MessageHeader.HeaderLength;
    }

    public int dataLength() {
        return data.remaining();
    }

    protected void set_process(int value) {
        _process = value;
        header.set_process((short)_process);
    }

    protected void set_service(int value) {
        header.set_service((short) value);
    }

    protected void willBuildDataBytes() {}

    protected void putByteData(byte value){
        data.put(value);

    }

    protected void putIntData(int value) {
        data.putInt(value);

    }

    protected void putShortData(short value) {
        data.putShort(value);

    }

    protected void putDoubleData(double value) {
        data.putDouble(value);

    }

    protected void putStringData(String value) {
        data.put(new VarLengthString(value).getBytes());

    }

    protected void putFixedLengthStringData(String value) {
        data.put(value.getBytes());

    }

    protected void putFixedLengthStringData(String value, int length) {
        data.put(String.format("%-"+length+"s", value).getBytes());

    }

    public void buildDataBytes(){
        data.clear();
        willBuildDataBytes();
        data.flip();
    }

    public byte[] getBytes() {
        buildDataBytes();
        int length = length();
        header.set_length(length);
        headerBytes = header.getBytes();

        byte[] ret = new byte[length];
        System.arraycopy(headerBytes, 0, ret, 0, headerLength());
        System.arraycopy(dataBytes, 0, ret, headerLength(), dataLength());

        return ret;
    }
}
