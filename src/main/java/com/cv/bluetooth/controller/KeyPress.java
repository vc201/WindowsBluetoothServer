package com.cv.bluetooth.controller;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

// Keycodes: https://msdn.microsoft.com/en-us/library/windows/desktop/dd375731(v=vs.85).aspx
// https://stackoverflow.com/questions/28538234/sending-a-keyboard-input-with-java-jna-and-sendinput

public class KeyPress {
    public static final char VOL_DOWN = 0xAE;
    public static final char VOL_UP = 0xAF;
    public static final char NEXT_TRACK = 0xB0;
    public static final char PREV_TRACK = 0xB1;
    public static final char STOP = 0xB2;
    public static final char PLAY_PAUSE = 0xB3;

    public static void PressKey(char val) {
        // Prepare input reference
        WinUser.INPUT input = new WinUser.INPUT();

        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);

        // Press "a"
        input.input.ki.wVk = new WinDef.WORD(val);
        input.input.ki.dwFlags = new WinDef.DWORD(0);  // keydown

        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());

        // Release "a"
        input.input.ki.wVk = new WinDef.WORD(val);
        input.input.ki.dwFlags = new WinDef.DWORD(2);  // keyup

        User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }
}
