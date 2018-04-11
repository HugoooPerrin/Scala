package frp

trait Subscriber {
  def handler(pub: Publisher)
}
