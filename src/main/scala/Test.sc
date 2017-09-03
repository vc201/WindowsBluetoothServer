import com.sun.jna.platform.win32.BaseTSD
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser

val input = new WinUser.INPUT()


input.`type` = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD)


input.input.setType("ki")
input.input.ki.wScan = new WinDef.WORD(0)
input.input.ki.time = new WinDef.DWORD(0)
input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0)

// Press "a"
input.input.ki.wVk = new WinDef.WORD('A'); // 0x41
input.input.ki.dwFlags = new WinDef.DWORD(0); // keydown


User32.INSTANCE.SendInput(new WinDef.DWORD(1), input.toArray(1).asInstanceOf[Array[WinUser.INPUT]], input.size)


// Release "a"
input.input.ki.wVk = new WinDef.WORD('A') // 0x41
input.input.ki.dwFlags = new WinDef.DWORD(2) // keyup


User32.INSTANCE.SendInput(new WinDef.DWORD(1), input.toArray(1).asInstanceOf[Array[WinUser.INPUT]], input.size)