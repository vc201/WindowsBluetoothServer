package com.cv.bluetooth

import java.io.{BufferedReader, InputStreamReader, OutputStreamWriter, PrintWriter}
import javax.bluetooth.{DiscoveryAgent, LocalDevice, RemoteDevice, UUID}
import javax.microedition.io.{Connector, StreamConnectionNotifier}

import com.cv.bluetooth.controller.KeyPress._
import com.cv.bluetooth.internals.CustomDiscoveryListener
import com.cv.bluetooth.monitor.ConnectionMonitor
import com.cv.bluetooth.wrappers.ConnectionWrapper

object BluetoothServer {
  val serverUUID = new UUID("1101", true)

  def main(args: Array[String]): Unit = {
    val connStr = s"btspp://localhost:$serverUUID;name=BluetoothRemoteServer"

//    println(connStr)

    val device = LocalDevice.getLocalDevice

    println(s"Address: ${device.getBluetoothAddress}, Name: ${device.getFriendlyName}")

    val streamConnNotifier = Connector.open(connStr).asInstanceOf[StreamConnectionNotifier]

    println("Starting server...")

    val thread = new Thread(new ConnectionMonitor(streamConnNotifier))

    thread.start()

    var flag = true

    while (flag) {
      try {
        val line = ConnectionWrapper.getLine()
        if (line != null) {
          println(line)
          if (line != "EXITING") {
            line match {
              case "PLAY_PAUSE" => PressKey(PLAY_PAUSE)
              case "PREV" => PressKey(PREV_TRACK)
              case "NEXT" => PressKey(NEXT_TRACK)
              case "VOL_UP" => PressKey(VOL_UP)
              case "VOL_DOWN" => PressKey(VOL_DOWN)
              case "STOP" => PressKey(STOP)
              case _ => // do nothing
            }
          } else {
            flag = false
          }
          ConnectionWrapper.sendLine("ACK")
        } else {
          Thread.sleep(1000)
        }
      } catch {
        case _: Throwable => Thread.sleep(5000)
      }
    }

    thread.interrupt()
    Thread.sleep(1000)
    println("Shutting down...")
    System.exit(0)

//    val listener = new CustomDiscoveryListener
//
//    val agent = device.getDiscoveryAgent
//
//    agent.startInquiry(DiscoveryAgent.GIAC, listener)
//
//    listener.iqLock synchronized listener.iqLock.wait()
//
//    if (listener.devices.size > 0) {
//      val phone = listener.devices(0);
//
//      println(s"Found Name: ${phone.getFriendlyName(false)}, Address: ${phone.getBluetoothAddress}")
//    }
  }
}
