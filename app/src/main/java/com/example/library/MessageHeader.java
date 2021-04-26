package com.example.library;

import java.nio.ByteBuffer;

public class MessageHeader {
    protected int _length;
    protected short _process;
    protected short _service;
    protected int _window;
    protected byte _control;
    protected byte _flag;

    protected byte[] _headerBytes;
    protected ByteBuffer _headerBuffer;

    final static public int HeaderLength = 16;

    public MessageHeader() {
        _headerBytes = new byte[HeaderLength];
        _headerBuffer = ByteBuffer.wrap(_headerBytes);
    }

    public void set_length(int value) {
        _length = value;
    }

    public void set_process(short value) {
        _process = value;
    }

    public void set_service(short value) {
        _service = value;
    }

    public void set_window(int value) {
        _window = value;
    }

    public byte[] getBytes() {
        _headerBuffer.clear();
        _headerBuffer.putInt(_length);
        _headerBuffer.putShort(_process);
        _headerBuffer.putShort(_service);
        _headerBuffer.putInt(_window);
        _headerBuffer.put(_control);
        _headerBuffer.put(_flag);
        _headerBuffer.put(new byte[2]);
        _headerBuffer.flip();
        return _headerBuffer.array();
    }


}
