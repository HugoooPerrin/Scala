class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello")
    println("world!")
  }
}

def main(): Unit = {
  val thread1 = new HelloThread
  val thread2 = new HelloThread

  thread1.start()
  thread2.start()

  thread1.join()
  thread2.join()
}

// The two threads can arbitrarily overlap !

main()
main()
main()
main()




