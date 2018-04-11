package frp

trait Publisher {

   private var subscribers: Set[Suscriber] = Set()

  def subscribe(subscriber: Suscriber): Unit =
    subscribers += subscriber

  def unsubscribe(subscriber: Suscriber): Unit =
    subscribers -= subscriber

  def publish(): Unit =
    subscribers.foreach(_.handler(this))
}
