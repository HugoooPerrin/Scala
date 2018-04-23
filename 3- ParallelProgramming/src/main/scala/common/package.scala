
import java.util.concurrent._
import scala.util.DynamicVariable

package object common {

  val forkJoinPool = new ForkJoinPool

  abstract class TaskScheduler {
    def schedule[T](body: => T): ForkJoinTask[T]
    def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
      val right = task {
        taskB
      }
      val left = taskA
      (left, right.join())
    }
  }

  def time[R](block: => R): (R, Long) = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    val time = (t1 - t0)/1000
    (result, time)
  }

  class DefaultTaskScheduler extends TaskScheduler {
    def schedule[T](body: => T): ForkJoinTask[T] = {
      val t = new RecursiveTask[T] {
        def compute = body
      }
      Thread.currentThread match {
        case wt: ForkJoinWorkerThread =>
          t.fork()
        case _ =>
          forkJoinPool.execute(t)
      }
      t
    }
  }

  val scheduler =
    new DynamicVariable[TaskScheduler](new DefaultTaskScheduler)

  def task[T](body: => T): ForkJoinTask[T] = {
    scheduler.value.schedule(body)
  }

  def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
    scheduler.value.parallel(taskA, taskB)
  }

/*  def parallel[A, B, C, D, E, F, G, H](taskA: => A, taskB: => B, taskC: => C, taskD: => D,
                                       taskE: => E, taskF: => F, taskG: => G, taskH: => H): (A, B, C, D, E, F, G, H) = {
    val ta = task { taskA }
    val tb = task { taskB }
    val tc = task { taskC }
    val td = task { taskD }
    val te = task { taskE }
    val tf = task { taskF }
    val tg = task { taskG }
    val th = taskH
    (ta.join(), tb.join(), tc.join(), td.join(), te.join(), tf.join(), tg.join(), th)
  }*/
}
