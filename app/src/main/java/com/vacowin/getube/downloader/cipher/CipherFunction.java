package com.vacowin.getube.downloader.cipher;

public interface CipherFunction {

    char[] apply(char[] array, String argument);
}
