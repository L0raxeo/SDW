package com.l0raxeo.sdw.exceptions;

public class PacketMismatchException extends RuntimeException
{

    public PacketMismatchException(String message)
    {
        super(message);
    }

    public PacketMismatchException() {}

}
