package com.cv.bluetooth.monitor

import javax.bluetooth.RemoteDevice
import javax.microedition.io.{StreamConnection, StreamConnectionNotifier}

import com.cv.bluetooth.wrappers.ConnectionWrapper

class ConnectionMonitor(streamConnectionNotifier: StreamConnectionNotifier) extends Runnable{
  override def run(): Unit = {
    while (true) {
      try {
        val conn: StreamConnection = streamConnectionNotifier.acceptAndOpen()
        ConnectionWrapper.initConnection(conn)
        val phone = RemoteDevice.getRemoteDevice(conn)
        println("Connection established")
        println(s"Found Name: ${phone.getFriendlyName(false)}, Address: ${phone.getBluetoothAddress}")
      } catch {
        case _: Throwable => Thread.sleep(5000)
      }
    }
  }
}
