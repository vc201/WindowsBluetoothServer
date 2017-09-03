package com.cv.bluetooth.internals

import javax.bluetooth.{DeviceClass, DiscoveryListener, RemoteDevice, ServiceRecord}

import scala.collection.mutable.ListBuffer

class CustomDiscoveryListener extends DiscoveryListener{

  val devices = ListBuffer[RemoteDevice]()
  val iqLock = new Object

//  def inquiryCompleted(discType: Int): Unit = {
//    System.out.println("Device Inquiry completed!")
//    inquiryCompletedEvent synchronized inquiryCompletedEvent.notifyAll
//
//  }

  override def inquiryCompleted(i: Int): Unit = {
    println("Device Inquiry Completed")
    iqLock synchronized iqLock.notifyAll()
  }

  override def servicesDiscovered(i: Int, serviceRecords: Array[ServiceRecord]): Unit = {
    // do nothing
  }

  override def deviceDiscovered(remoteDevice: RemoteDevice, deviceClass: DeviceClass): Unit = {
    println("Device Discovered")
    devices.append(remoteDevice)
    println(remoteDevice.getFriendlyName(false))
  }

  override def serviceSearchCompleted(i: Int, i1: Int): Unit = {
    // do nothing
  }
}
