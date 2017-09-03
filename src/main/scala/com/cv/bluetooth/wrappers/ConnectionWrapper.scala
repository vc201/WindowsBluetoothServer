package com.cv.bluetooth.wrappers

import java.io._
import javax.microedition.io.StreamConnection

object ConnectionWrapper {
  var conn: StreamConnection = null
  var in: BufferedReader = null
  var out: PrintWriter = null

  def initConnection(conn: StreamConnection): Unit = synchronized {
    this.conn = conn
    try {
      in = new BufferedReader(new InputStreamReader(conn.openInputStream()))
      out = new PrintWriter(new OutputStreamWriter(conn.openOutputStream()))
    } catch {
      case _: Throwable => {
        in = null
        out = null
      }
    }
  }

  def getLine(): String = synchronized {
    if (in != null) {
      try {
        in.readLine()
      } catch {
        case _: Throwable => {
          in = null
          out = null
          Thread.sleep(5000)
          null
        }
      }
    } else {
      null
    }
  }

  def sendLine(s: String): Unit = synchronized {
    if (out != null) {
      try {
        out.println(s)
        out.flush()
      } catch {
        case _: Throwable => {
          in = null
          out = null
          Thread.sleep(5000)
        }
      }
    }
  }
}
